package com.cfs.bookmyshow.dto;

import com.cfs.bookmyshow.model.ShowSeat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {

    private Long id;
    private String bookingName;
    private LocalDateTime bookingTime;
    private Userdto user;
    private Showdto show;
    private String status;
    private double totalAmount;
    private List<ShowSeat> seats;
    private Paymentdto payment;



}
