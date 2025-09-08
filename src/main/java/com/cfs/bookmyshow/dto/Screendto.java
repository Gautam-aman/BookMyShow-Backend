package com.cfs.bookmyshow.dto;


import com.cfs.bookmyshow.model.Theater;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Screendto {
    private Long id;
    private String name;
    private Integer totalSeats;
    private Theaterdto theater;
}
