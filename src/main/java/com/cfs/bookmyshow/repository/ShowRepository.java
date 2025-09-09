package com.cfs.bookmyshow.repository;

import com.cfs.bookmyshow.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {
    List<Show> findByMovieId(Long movieId);
    List<Show> findByScreenId(Long screenId);

    List<Show> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

    List<Show> findByMovie_IdAndScreen_Theater_City(Long movieId, String city);
}
