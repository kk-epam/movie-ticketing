package com.example.ticketing.service;

import com.example.ticketing.dto.Dtos.BookingDto;
import com.example.ticketing.dto.Dtos.BookingRequest;
import com.example.ticketing.model.Booking;
import com.example.ticketing.model.Show;
import com.example.ticketing.model.ShowSeat;
import com.example.ticketing.repository.BookingRepository;
import com.example.ticketing.repository.ShowRepository;
import com.example.ticketing.repository.ShowSeatRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class BookingService {

    private final ShowRepository showRepository;
    private final ShowSeatRepository seatRepository;
    private final BookingRepository bookingRepository;

    public BookingService(ShowRepository showRepository,
                          ShowSeatRepository seatRepository,
                          BookingRepository bookingRepository) {
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
        this.bookingRepository = bookingRepository;
    }

    /**
     * Books the requested seats atomically. Seats are locked with
     * SELECT ... FOR UPDATE, so a concurrent request for the same seat waits
     * and then fails cleanly with 409 instead of double-booking.
     *
     */
    @Transactional
    public BookingDto createBooking(BookingRequest request) {
        Show show = showRepository.findById(request.showId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Show not found"));

        List<String> labels = request.seatLabels().stream()
                .map(l -> l.trim().toUpperCase(Locale.ROOT))
                .toList();
        if (labels.stream().distinct().count() != labels.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duplicate seats in request");
        }

        List<ShowSeat> seats = seatRepository.findForUpdate(show.getId(), labels);
        if (seats.size() != labels.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "One or more seats do not exist");
        }

        List<String> taken = seats.stream()
                .filter(ShowSeat::isBooked)
                .map(ShowSeat::getSeatLabel)
                .toList();
        if (!taken.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Seats already booked: " + String.join(", ", taken));
        }

        seats.forEach(ShowSeat::markBooked);

        BigDecimal total = show.getPrice().multiply(BigDecimal.valueOf(labels.size()));
        Booking booking = new Booking(
                generateBookingCode(),
                show,
                request.customerName().trim(),
                request.customerEmail().trim(),
                labels,
                total);
        bookingRepository.save(booking);

        return toDto(booking);
    }

    @Transactional(readOnly = true)
    public BookingDto getBooking(String code) {
        return bookingRepository.findByBookingCode(code.toUpperCase(Locale.ROOT))
                .map(this::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));
    }

    private BookingDto toDto(Booking b) {
        return new BookingDto(
                b.getBookingCode(),
                b.getShow().getMovie().getTitle(),
                b.getShow().getScreenName(),
                b.getShow().getStartTime(),
                b.getSeatLabels(),
                b.getTotalAmount(),
                b.getCustomerName());
    }

    private String generateBookingCode() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase(Locale.ROOT);
    }
}
