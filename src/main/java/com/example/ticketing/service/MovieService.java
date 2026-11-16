package com.example.ticketing.service;

import com.example.ticketing.dto.Dtos.MovieDto;
import com.example.ticketing.dto.Dtos.SeatDto;
import com.example.ticketing.dto.Dtos.ShowDto;
import com.example.ticketing.model.Movie;
import com.example.ticketing.model.Show;
import com.example.ticketing.model.ShowSeat;
import com.example.ticketing.repository.MovieRepository;
import com.example.ticketing.repository.ShowRepository;
import com.example.ticketing.repository.ShowSeatRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MovieService {

    private final MovieRepository movieRepository;
    private final ShowRepository showRepository;
    private final ShowSeatRepository seatRepository;

    public MovieService(MovieRepository movieRepository,
                        ShowRepository showRepository,
                        ShowSeatRepository seatRepository) {
        this.movieRepository = movieRepository;
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
    }

    /** Browse all movies, or filter by a partial, case-insensitive title match. */
    public List<MovieDto> searchMovies(String title) {
        List<Movie> movies = (title == null || title.isBlank())
                ? movieRepository.findAll(Sort.by("title"))
                : movieRepository.findByTitleContainingIgnoreCaseOrderByTitleAsc(title.trim());
        return movies.stream().map(this::toMovieDto).toList();
    }

    public MovieDto getMovie(Long id) {
        return movieRepository.findById(id)
                .map(this::toMovieDto)
                .orElseThrow(() -> notFound("Movie not found"));
    }

    public List<ShowDto> getShowsForMovie(Long movieId) {
        if (!movieRepository.existsById(movieId)) {
            throw notFound("Movie not found");
        }
        return showRepository.findByMovieIdOrderByStartTimeAsc(movieId).stream()
                .map(this::toShowDto)
                .toList();
    }

    public List<SeatDto> getSeatsForShow(Long showId) {
        if (!showRepository.existsById(showId)) {
            throw notFound("Show not found");
        }
        return seatRepository.findByShowIdOrderByIdAsc(showId).stream()
                .map(this::toSeatDto)
                .toList();
    }

    MovieDto toMovieDto(Movie m) {
        return new MovieDto(m.getId(), m.getTitle(), m.getGenre(), m.getDurationMinutes(),
                m.getRating(), m.getDescription());
    }

    ShowDto toShowDto(Show s) {
        return new ShowDto(s.getId(), s.getMovie().getId(), s.getMovie().getTitle(),
                s.getScreenName(), s.getStartTime(), s.getPrice());
    }

    SeatDto toSeatDto(ShowSeat s) {
        return new SeatDto(s.getSeatLabel(), s.isBooked());
    }

    private ResponseStatusException notFound(String message) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, message);
    }
}
