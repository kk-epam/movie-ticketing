package com.example.ticketing.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "shows")
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Column(nullable = false)
    private String screenName;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal price;

    protected Show() {
    }

    public Show(Movie movie, String screenName, LocalDateTime startTime, BigDecimal price) {
        this.movie = movie;
        this.screenName = screenName;
        this.startTime = startTime;
        this.price = price;
    }

    public Long getId() { return id; }
    public Movie getMovie() { return movie; }
    public String getScreenName() { return screenName; }
    public LocalDateTime getStartTime() { return startTime; }
    public BigDecimal getPrice() { return price; }
}
