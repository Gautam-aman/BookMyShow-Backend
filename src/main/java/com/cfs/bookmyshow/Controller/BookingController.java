package com.cfs.bookmyshow.Controller;


import com.cfs.bookmyshow.Service.BookingService;
import com.cfs.bookmyshow.dto.BookingDto;
import com.cfs.bookmyshow.dto.BookingRequestdto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/booking ")
public class BookingController {

    BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingDto> createBooking(@Valid @RequestBody BookingRequestdto bookingRequest) {

        return new ResponseEntity<>(bookingService.createBooking(bookingRequest) , HttpStatus.CREATED);

    }

}
