package com.cfs.bookmyshow.dto;

import com.cfs.bookmyshow.model.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Showdto {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Moviedto movie;
    private Screendto screen;
    private List<ShowSeatdto> availableSeats;
}
