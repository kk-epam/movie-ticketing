package com.example.ticketing.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/** Request and response shapes used by the REST API. */
public final class Dtos {

    private Dtos() {
    }

    public record MovieDto(Long id, String title, String genre, int durationMinutes,
                           String rating, String description) {
    }

    public record ShowDto(Long id, Long movieId, String movieTitle, String screenName,
                          LocalDateTime startTime, BigDecimal price) {
    }

    public record SeatDto(String label, boolean booked) {
    }

    public record BookingRequest(
            @NotNull Long showId,
            @NotBlank @Size(max = 100) String customerName,
            @NotBlank @Email @Size(max = 150) String customerEmail,
            @NotEmpty @Size(max = 10) List<@NotBlank String> seatLabels) {
    }

    public record BookingDto(String bookingCode, String movieTitle, String screenName,
                             LocalDateTime startTime, List<String> seatLabels,
                             BigDecimal totalAmount, String customerName) {
    }
}
