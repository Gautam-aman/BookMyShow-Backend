package com.cfs.bookmyshow.repository;

import com.cfs.bookmyshow.model.Booking;
import com.cfs.bookmyshow.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByLanguage (String language);

    List<Movie> findByGenre(String genre);

    List<Movie> findByTitleContaining(String title);


    List<Movie> findByTitle(String title);
}
