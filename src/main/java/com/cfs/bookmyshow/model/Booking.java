package com.cfs.bookmyshow.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="bookings")
@Data // Create Getter and Setter At RunTime
@NoArgsConstructor
@AllArgsConstructor // These are functions of Lombok
public class Booking {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String bookingNumber;

    @Column(nullable = false)
    private LocalDateTime BookingTime;



    @Column(nullable = false)
    private String Status; // Confirmed Pending Cancelled

    @Column(nullable = false)
    private Double Price;

    @ManyToOne
    @JoinColumn (name = "user_id" , nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn (name = "show_id" , nullable = false)
    private Show show;

    @OneToMany(mappedBy = "booking" , cascade = CascadeType.ALL)
    private List<ShowSeat> showSeats;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    private Payment payment;

}

