package com.example.ticketing.config;

import com.example.ticketing.model.Movie;
import com.example.ticketing.model.Show;
import com.example.ticketing.model.ShowSeat;
import com.example.ticketing.repository.MovieRepository;
import com.example.ticketing.repository.ShowRepository;
import com.example.ticketing.repository.ShowSeatRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/** Seeds sample movies, two days of showtimes, and a 6x10 seat grid per show. */
@Component
public class DataInitializer implements CommandLineRunner {

    private static final LocalTime[] TIMES = {
            LocalTime.of(11, 0), LocalTime.of(15, 30), LocalTime.of(19, 0), LocalTime.of(21, 45)
    };
    private static final String ROWS = "ABCDEF";
    private static final int SEATS_PER_ROW = 10;

    private final MovieRepository movieRepository;
    private final ShowRepository showRepository;
    private final ShowSeatRepository seatRepository;

    public DataInitializer(MovieRepository movieRepository,
                           ShowRepository showRepository,
                           ShowSeatRepository seatRepository) {
        this.movieRepository = movieRepository;
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
    }

    @Override
    public void run(String... args) {
        if (movieRepository.count() > 0) {
            return;
        }

        List<Movie> movies = movieRepository.saveAll(List.of(
                new Movie("Starfall Protocol", "Sci-Fi", 128, "PG-13",
                        "A crew of deep-space engineers must repair a failing relay before a solar storm hits."),
                new Movie("The Last Lighthouse", "Drama", 112, "PG",
                        "A retired keeper and a young cartographer uncover a secret about their coastal town."),
                new Movie("Midnight Mayhem", "Comedy", 96, "R",
                        "A wedding planner's worst weekend becomes the best night of her life."),
                new Movie("Echoes of Kyoto", "Romance", 105, "PG-13",
                        "Two strangers reconnect through old letters hidden in a Kyoto bookshop.")));

        for (Movie movie : movies) {
            for (int dayOffset = 0; dayOffset < 2; dayOffset++) {
                for (LocalTime time : TIMES) {
                    boolean evening = time.getHour() >= 17;
                    Show show = showRepository.save(new Show(
                            movie,
                            evening ? "IMAX" : "Screen 1",
                            LocalDate.now().plusDays(dayOffset).atTime(time),
                            evening ? new BigDecimal("15.00") : new BigDecimal("12.00")));
                    createSeats(show);
                }
            }
        }
    }

    private void createSeats(Show show) {
        List<ShowSeat> seats = new ArrayList<>();
        for (char row : ROWS.toCharArray()) {
            for (int n = 1; n <= SEATS_PER_ROW; n++) {
                seats.add(new ShowSeat(show, row + String.valueOf(n)));
            }
        }
        seatRepository.saveAll(seats);
    }
}
