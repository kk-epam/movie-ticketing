package com.example.ticketing.controller;

import com.example.ticketing.dto.Dtos.BookingDto;
import com.example.ticketing.dto.Dtos.BookingRequest;
import com.example.ticketing.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDto create(@Valid @RequestBody BookingRequest request) {
        return bookingService.createBooking(request);
    }

    @GetMapping("/{code}")
    public BookingDto get(@PathVariable String code) {
        return bookingService.getBooking(code);
    }
}
