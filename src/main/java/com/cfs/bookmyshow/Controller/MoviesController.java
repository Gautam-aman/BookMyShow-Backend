package com.cfs.bookmyshow.Controller;

import com.cfs.bookmyshow.Service.MovieService;
import com.cfs.bookmyshow.dto.BookingDto;
import com.cfs.bookmyshow.dto.Moviedto;
import com.cfs.bookmyshow.model.Movie;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MoviesController {

    @Autowired
    private MovieService movieService;

    @PostMapping
    public ResponseEntity<Moviedto> createMovie(@Valid @RequestBody Moviedto moviedto) {
        return new  ResponseEntity<>(movieService.createMovie(moviedto), HttpStatus.CREATED);
    }

    @GetMapping({"/{id}"})
   public ResponseEntity<Moviedto> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
   }

   @GetMapping
   public ResponseEntity<List<Moviedto>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
   }

}
