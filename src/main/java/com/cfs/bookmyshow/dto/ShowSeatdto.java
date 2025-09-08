package com.cfs.bookmyshow.dto;


import com.cfs.bookmyshow.model.Seat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowSeatdto {
    private Long id;
    private Seatdto seat;
    private String status;
    private Double price;

}
