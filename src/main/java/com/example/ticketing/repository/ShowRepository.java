package com.example.ticketing.repository;

import com.example.ticketing.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {

    List<Show> findByMovieIdOrderByStartTimeAsc(Long movieId);
}
