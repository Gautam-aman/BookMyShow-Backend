package com.cfs.bookmyshow.repository;

import com.cfs.bookmyshow.model.Show;
import com.cfs.bookmyshow.model.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

    List<ShowSeat> findByShowId(Long movieId);

    List<ShowSeat> findByShowIdAndStatus(Long showId , String status);




}
