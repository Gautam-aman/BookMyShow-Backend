package com.cfs.bookmyshow.Service;


import com.cfs.bookmyshow.dto.*;
import com.cfs.bookmyshow.exceptions.ResourceNotFoundException;
import com.cfs.bookmyshow.model.Movie;
import com.cfs.bookmyshow.model.Screen;
import com.cfs.bookmyshow.model.Show;
import com.cfs.bookmyshow.model.ShowSeat;
import com.cfs.bookmyshow.repository.MovieRepository;
import com.cfs.bookmyshow.repository.ScreenRepository;
import com.cfs.bookmyshow.repository.ShowRepository;
import com.cfs.bookmyshow.repository.ShowSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowService {

    //@Autowired
    private MovieRepository movieRepository;

   // @Autowired
    private ScreenRepository screenRepository;

   // @Autowired
    private ShowRepository showRepository;

  //  @Autowired
    private ShowSeatRepository showSeatRepository;


    public Showdto createShow(Showdto showdto){
        Show show = new Show();
        Movie movie = movieRepository.findById(showdto.getMovie().getId()).
                orElseThrow(()->new ResourceNotFoundException("Movie not found with id " + showdto.getMovie().getId()));

        Screen screen = screenRepository.findById(showdto.getScreen().getId()).
                orElseThrow(()->new ResourceNotFoundException("Screen not found with id " + showdto.getScreen().getId()));

        show.setMovie(movie);
        show.setScreen(screen);
        show.setStartTime(showdto.getStartTime());
        show.setEndTime(showdto.getEndTime());

        Show savedShow = showRepository.save(show);

        List<ShowSeat> availableSeats = showSeatRepository.findByShowIdAndStatus(savedShow.getId()  ,"AVAILABLE");

        return mapToDto(savedShow, availableSeats);

    }

    public Showdto getShowById(Long id){
        Show show = showRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Show Not Found"));

        List<ShowSeat> availableSeats = showSeatRepository.findByShowIdAndStatus(show.getId()  ,"AVAILABLE");
        return mapToDto(show, availableSeats);
    }

    public List<Showdto> getAllShows(){
        List<Show> shows = showRepository.findAll();
        return shows.stream()
                .map(show->{
                   List<ShowSeat> availableSeat =  showSeatRepository.findByShowIdAndStatus(show.getId()  ,"AVAILABLE");
                    return mapToDto(show , availableSeat);
                })
                .collect(Collectors.toList());
    }

    public List<Showdto> getShowsByMovie(Long movieId){
        List<Show> shows = showRepository.findByMovieId(movieId);
        return shows.stream()
                .map(show->{
                    List<ShowSeat> availableSeat =  showSeatRepository.findByShowIdAndStatus(show.getId()  ,"AVAILABLE");
                    return mapToDto(show , availableSeat);
                })
                .collect(Collectors.toList());
    }

    public List<Showdto> getShowsByMovieAndCity(Long movieId , String city){
        List<Show> shows = showRepository.findByMovie_IdAndScreen_Theater_City(movieId , city );
        return shows.stream()
                .map(show->{
                    List<ShowSeat> availableSeat =  showSeatRepository.findByShowIdAndStatus(show.getId()  ,"AVAILABLE");
                    return mapToDto(show , availableSeat);
                })
                .collect(Collectors.toList());
    }

    public List<Showdto> getShowsByDateRange(LocalDateTime startTime, LocalDateTime endTime){
        List<Show> shows = showRepository.findByStartTimeBetween(startTime, endTime);
        return shows.stream()
                .map(show->{
                    List<ShowSeat> availableSeat =  showSeatRepository.findByShowIdAndStatus(show.getId()  ,"AVAILABLE");
                    return mapToDto(show , availableSeat);
                })
                .collect(Collectors.toList());
    }

    private Showdto mapToDto(Show show , List<ShowSeat> availableSeats){
        Showdto showdto = new Showdto();
        showdto.setId(show.getId());
        showdto.setStartTime(show.getStartTime());
        showdto.setEndTime(show.getEndTime());
        showdto.setMovie(new Moviedto(
                show.getMovie().getId(),
                show.getMovie().getTitle(),
                show.getMovie().getDescription(),
                show.getMovie().getLanguage(),
                show.getMovie().getGenre(),
                show.getMovie().getDurationMins(),
                show.getMovie().getPosterUrl(),
                show.getMovie().getReleaseDate()
        ));

        Theaterdto theaterdto = new Theaterdto(
                show.getScreen().getTheater().getId(),
                show.getScreen().getTheater().getCity(),
                show.getScreen().getTheater().getAddress(),
                show.getScreen().getTheater().getTotalScreens(),
                show.getScreen().getTheater().getName()
        );

        showdto.setScreen(new Screendto(
                show.getScreen().getId(),
                show.getScreen().getName(),
                show.getScreen().getTotalSeats(),
                theaterdto
        ));

       List<ShowSeatdto> seatdtos =  availableSeats.stream()
                .map(seat -> {
                    ShowSeatdto seatdto = new ShowSeatdto();
                    seatdto.setId(seat.getId());
                    seatdto.setStatus(seat.getStatus());
                    seatdto.setPrice(seat.getPrice());

                    Seatdto baseSeatDto = new Seatdto();
                    baseSeatDto.setId(seat.getSeat().getId());
                    baseSeatDto.setSeatNumber(seat.getSeat().getSeatNumber());
                    baseSeatDto.setSeatType(seat.getSeat().getSeatType());
                    baseSeatDto.setBasePrice(seat.getSeat().getBasePrice());
                    seatdto.setSeat(baseSeatDto);
                    return seatdto;

                })
                .collect(Collectors.toList());

       showdto.setAvailableSeats(seatdtos);
       return showdto;

    }

}
