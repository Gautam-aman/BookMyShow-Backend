package com.cfs.bookmyshow.Service;


import com.cfs.bookmyshow.dto.Moviedto;
import com.cfs.bookmyshow.exceptions.ResourceNotFoundException;
import com.cfs.bookmyshow.model.Movie;
import com.cfs.bookmyshow.repository.MovieRepository;
import com.cfs.bookmyshow.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

   public Moviedto createMovie(Moviedto moviedto){
        Movie movie = mapToEntity(moviedto);
        Movie savedMovie = movieRepository.save(movie);
        return mapToDto(savedMovie);

   }

   public Moviedto getMovieById(Long id){
       Movie movie = movieRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Movie not found with id " + id));
       return mapToDto(movie);
   }

   public List<Moviedto> getAllMovies(){
       List<Movie> movies = movieRepository.findAll();
      return movies.stream()
              .map(this :: mapToDto)
              .collect(Collectors.toList());
   }

   public List<Moviedto> getMovieByLanguage(String language){
       List<Movie> movies = movieRepository.findByLanguage(language);
       return movies.stream().map(this :: mapToDto)
              .collect(Collectors.toList());
   }

    public List<Moviedto> getMovieByGenre(String genre){
        List<Movie> movies = movieRepository.findByGenre(genre);
        return movies.stream().map(this :: mapToDto)
                .collect(Collectors.toList());
    }

    public List<Moviedto> getMovieByTitle(String title){
        List<Movie> movies = movieRepository.findByTitle(title);
        return movies.stream().map(this :: mapToDto)
                .collect(Collectors.toList());
    }

    public Moviedto updateMovie(Long id ,  Moviedto moviedto){
       Movie movie = movieRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Movie not found with id " + id));
        movie.setId(moviedto.getId());
        movie.setTitle(moviedto.getTitle());
        movie.setDescription(moviedto.getDescription());
        movie.setGenre(moviedto.getGenre());
        movie.setLanguage(moviedto.getLanguage());
        movie.setDurationMins(moviedto.getDurationMinutes());
        movie.setReleaseDate(moviedto.getReleaseDate());
        movie.setPosterUrl(moviedto.getPosterUrl());
        Movie updatemovie = movieRepository.save(movie);
        return mapToDto(updatemovie);
    }

    public void deleteMovie(Long id){
       Movie movie = movieRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Movie not found with id " + id));
       movieRepository.delete(movie);
    }

   private Moviedto mapToDto(Movie movie){
       Moviedto moviedto = new Moviedto();
       moviedto.setId(movie.getId());
       moviedto.setTitle(movie.getTitle());
       moviedto.setDescription(movie.getDescription());
       moviedto.setDurationMinutes(movie.getDurationMins());
       moviedto.setLanguage(movie.getLanguage());
       moviedto.setGenre(movie.getGenre());
       moviedto.setReleaseDate(movie.getReleaseDate());
       moviedto.setPosterUrl(movie.getPosterUrl());
       return moviedto;

   }

   public Movie mapToEntity(Moviedto moviedto){
       Movie movie = new Movie();
       movie.setId(moviedto.getId());
       movie.setTitle(moviedto.getTitle());
       movie.setDescription(moviedto.getDescription());
       movie.setGenre(moviedto.getGenre());
       movie.setLanguage(moviedto.getLanguage());
       movie.setDurationMins(moviedto.getDurationMinutes());
       movie.setReleaseDate(moviedto.getReleaseDate());
       movie.setPosterUrl(moviedto.getPosterUrl());
       return movie ;
   }



}
