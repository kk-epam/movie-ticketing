package com.example.ticketing.model;

import jakarta.persistence.*;

/**
 * One physical seat for one show. Seats are created up front for every show,
 * so availability is just a flag that bookings flip.
 */
@Entity
@Table(name = "show_seats",
       uniqueConstraints = @UniqueConstraint(name = "uk_show_seat", columnNames = {"show_id", "seat_label"}))
public class ShowSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "show_id")
    private Show show;

    @Column(name = "seat_label", nullable = false, length = 5)
    private String seatLabel;

    @Column(nullable = false)
    private boolean booked;

    protected ShowSeat() {
    }

    public ShowSeat(Show show, String seatLabel) {
        this.show = show;
        this.seatLabel = seatLabel;
        this.booked = false;
    }

    public void markBooked() { this.booked = true; }

    public Long getId() { return id; }
    public Show getShow() { return show; }
    public String getSeatLabel() { return seatLabel; }
    public boolean isBooked() { return booked; }
}
