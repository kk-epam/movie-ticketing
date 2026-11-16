package com.example.ticketing.controller;

import com.example.ticketing.dto.Dtos.MovieDto;
import com.example.ticketing.dto.Dtos.SeatDto;
import com.example.ticketing.dto.Dtos.ShowDto;
import com.example.ticketing.service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    /** GET /api/movies            -> all movies
     *  GET /api/movies?title=dark -> movies whose title contains "dark" */
    @GetMapping("/movies")
    public List<MovieDto> searchMovies(@RequestParam(required = false) String title) {
        return movieService.searchMovies(title);
    }

    @GetMapping("/movies/{id}")
    public MovieDto getMovie(@PathVariable Long id) {
        return movieService.getMovie(id);
    }

    @GetMapping("/movies/{id}/shows")
    public List<ShowDto> getShows(@PathVariable Long id) {
        return movieService.getShowsForMovie(id);
    }

    @GetMapping("/shows/{showId}/seats")
    public List<SeatDto> getSeats(@PathVariable Long showId) {
        return movieService.getSeatsForShow(showId);
    }
}
