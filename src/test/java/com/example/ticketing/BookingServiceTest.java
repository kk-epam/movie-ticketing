package com.example.ticketing;

import com.example.ticketing.dto.Dtos.BookingRequest;
import com.example.ticketing.model.Show;
import com.example.ticketing.repository.ShowRepository;
import com.example.ticketing.service.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookingServiceTest {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ShowRepository showRepository;

    @Test
    void bookedSeatCannotBeBookedAgain() {
        Show show = showRepository.findAll().get(0);

        var first = bookingService.createBooking(new BookingRequest(
                show.getId(), "Ada Lovelace", "ada@example.com", List.of("A1", "A2")));
        assertEquals(2, first.seatLabels().size());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> bookingService.createBooking(new BookingRequest(
                        show.getId(), "Grace Hopper", "grace@example.com", List.of("A2", "A3"))));
        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());
    }
}
