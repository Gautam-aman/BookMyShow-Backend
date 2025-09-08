package com.cfs.bookmyshow.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Theaterdto {
    private Long id;
    private String name;
    private String address;
    private Integer totalScreens;
    private String city;
}
