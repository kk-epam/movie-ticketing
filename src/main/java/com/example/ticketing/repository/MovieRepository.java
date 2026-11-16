package com.example.ticketing.repository;

import com.example.ticketing.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByTitleContainingIgnoreCaseOrderByTitleAsc(String title);
}
