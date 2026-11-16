package com.example.ticketing.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_code", nullable = false, unique = true, length = 12)
    private String bookingCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "show_id")
    private Show show;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private String customerEmail;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "booking_seats", joinColumns = @JoinColumn(name = "booking_id"))
    @Column(name = "seat_label")
    private List<String> seatLabels = new ArrayList<>();

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    protected Booking() {
    }

    public Booking(String bookingCode, Show show, String customerName, String customerEmail,
                   List<String> seatLabels, BigDecimal totalAmount) {
        this.bookingCode = bookingCode;
        this.show = show;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.seatLabels = new ArrayList<>(seatLabels);
        this.totalAmount = totalAmount;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getBookingCode() { return bookingCode; }
    public Show getShow() { return show; }
    public String getCustomerName() { return customerName; }
    public String getCustomerEmail() { return customerEmail; }
    public List<String> getSeatLabels() { return seatLabels; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
