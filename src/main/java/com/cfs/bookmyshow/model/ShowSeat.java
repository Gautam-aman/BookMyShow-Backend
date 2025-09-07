package com.cfs.bookmyshow.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.annotation.Documented;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "show_seats")
public class ShowSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="seat_id" , nullable = false)
    private Seat seat;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(name = "booking_id" , nullable = false)
    private Booking booking;

}
