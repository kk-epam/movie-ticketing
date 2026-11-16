package com.example.ticketing.model;

import jakarta.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String genre;
    private int durationMinutes;
    private String rating;

    @Column(length = 1000)
    private String description;

    protected Movie() {
    }

    public Movie(String title, String genre, int durationMinutes, String rating, String description) {
        this.title = title;
        this.genre = genre;
        this.durationMinutes = durationMinutes;
        this.rating = rating;
        this.description = description;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public int getDurationMinutes() { return durationMinutes; }
    public String getRating() { return rating; }
    public String getDescription() { return description; }
}
