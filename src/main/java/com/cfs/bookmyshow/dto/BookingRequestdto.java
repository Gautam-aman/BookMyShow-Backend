package com.cfs.bookmyshow.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestdto {
    private Long userId;
    protected  Long showId;
    private List<Long> seatIds;
    private String paymentMethod;

}
