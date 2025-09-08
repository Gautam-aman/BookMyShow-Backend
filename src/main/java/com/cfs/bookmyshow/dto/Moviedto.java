package com.cfs.bookmyshow.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Moviedto {
    private Long id;
    private String title;
    private String description;
    private String genre;
    private String Language;
    private Integer durationMinutes;
    private String releaseDate;
    private String posterUrl;
}
