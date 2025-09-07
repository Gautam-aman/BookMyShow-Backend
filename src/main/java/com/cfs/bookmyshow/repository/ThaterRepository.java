package com.cfs.bookmyshow.repository;

import com.cfs.bookmyshow.model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThaterRepository extends JpaRepository<Theater , Long > {
    List<Theater> findByShowId(String city);
}
