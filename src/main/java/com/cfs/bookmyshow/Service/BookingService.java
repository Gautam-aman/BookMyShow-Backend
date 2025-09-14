package com.cfs.bookmyshow.Service;


import com.cfs.bookmyshow.dto.*;
import com.cfs.bookmyshow.exceptions.ResourceNotFoundException;
import com.cfs.bookmyshow.exceptions.SeatUnavailableException;
import com.cfs.bookmyshow.model.*;
import com.cfs.bookmyshow.repository.BookingRepository;
import com.cfs.bookmyshow.repository.ShowRepository;
import com.cfs.bookmyshow.repository.ShowSeatRepository;
import com.cfs.bookmyshow.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingService {

    //@Autowired
    private UserRepository userRepository;

    //@Autowired
    private ShowRepository showRepository;

   // @Autowired
    private ShowSeatRepository showSeatRepository;

   // @Autowired
    private BookingRepository bookingRepository;

    @Transactional
    public BookingDto createBooking(BookingRequestdto bookingRequest) {
        User user = userRepository.findById(bookingRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        Show show = showRepository.findById(bookingRequest.getShowId())
                .orElseThrow(() -> new ResourceNotFoundException("Show Not Found"));

        List<ShowSeat> selectedSeats = showSeatRepository.findAllById(bookingRequest.getSeatIds());

        for (ShowSeat seat : selectedSeats) {
            if (!"AVAILABLE".equals(seat.getStatus())) {
                throw  new SeatUnavailableException("Seat " + seat.getSeat().getSeatNumber() + " is unavailable");
            }
            seat.setStatus("LOCKED");
        }
        showSeatRepository.saveAll(selectedSeats);

        Double Amount = selectedSeats.stream()
                .mapToDouble(ShowSeat::getPrice)
                .sum();


        // Payment
        Payment payment = new Payment();
        payment.setAmount(Amount);
        payment.setPaymentTime(LocalDateTime.now());
        payment.setPaymentMethod(bookingRequest.getPaymentMethod());
        payment.setPaymentStatus("SUCCESS");
        payment.setTransactionId(UUID.randomUUID().toString());

        //Booking
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShow(show);
        booking.setPayment(payment);
        booking.setBookingTime(LocalDateTime.now());
        booking.setStatus("CONFIRMED");
        booking.setPrice(Amount);
        booking.setBookingNumber(UUID.randomUUID().toString());

        Booking savedBooking = bookingRepository.save(booking);
        selectedSeats.forEach(seat -> {
            seat.setStatus("BOOKED");
            seat.setBooking(savedBooking);
        });
        showSeatRepository.saveAll(selectedSeats);

        return mapToBookingDto(savedBooking , selectedSeats);
    }

    public BookingDto getBookingById(Long id){
        Booking booking = bookingRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Booking not Found"));
        List<ShowSeat> showSeats  =     showSeatRepository.findAll()
                .stream()
                .filter(seat -> seat.getBooking().getId().equals(booking.getId()))
                .collect(Collectors.toList());
        return mapToBookingDto(booking, showSeats);
    }

    public BookingDto getBookingByNumber(String bookingNumber){
        Booking booking = bookingRepository.findByBookingNumber(bookingNumber).
                orElseThrow(() -> new ResourceNotFoundException("Booking Not Found"));
        List<ShowSeat> showSeats  =     showSeatRepository.findAll()
                .stream()
                .filter(seat -> seat.getBooking().getId().equals(booking.getId()))
                .collect(Collectors.toList());
        return mapToBookingDto(booking, showSeats);
    }

    public List<BookingDto> getBookingByUserId(Long userId){
        List<Booking> bookings  = bookingRepository.findByUserId(userId);
        return bookings.stream()
                .map(booking->{
                    List<ShowSeat> showSeats = showSeatRepository.findAll()
                            .stream()
                            .filter(seat -> seat.getBooking().getId().equals(booking.getId()))
                            .collect(Collectors.toList());
                    return mapToBookingDto(booking, showSeats);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public BookingDto cancelBooking(Long id){
         Booking booking = bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Booking Not Found"));
         booking.setStatus("CANCELLED");
         List<ShowSeat> seats= showSeatRepository.findAll()
                 .stream().
                 filter(seat -> seat.getBooking() != null &&  seat.getBooking().getId().equals(booking.getId()))
                 .collect(Collectors.toList());
         seats.forEach(seat->{
             seat.setStatus("AVAILABLE");
             seat.setBooking(null);
         });

         if (booking.getPayment() != null){
             booking.getPayment().setStatus("REFUNDED");
         }
        Booking updateBooking = bookingRepository.save(booking);
         showSeatRepository.saveAll(seats);
         return mapToBookingDto(updateBooking, seats);
    }

    private BookingDto  mapToBookingDto(Booking booking , List<ShowSeat> seats) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setSetBookingNumber(booking.getBookingNumber());
        bookingDto.setBookingTime(booking.getBookingTime());
        bookingDto.setStatus(booking.getStatus());
        bookingDto.setTotalAmount(booking.getPrice());

        Userdto userDto = new Userdto();
        userDto.setId(booking.getUser().getId());
        userDto.setName(booking.getUser().getName());
        userDto.setEmail(booking.getUser().getEmail());
        userDto.setPhoneNumber(booking.getUser().getPhoneNumber());
        bookingDto.setUser(userDto);

        Showdto showDto = new Showdto();
        showDto.setId(booking.getShow().getId());
        showDto.setStartTime(booking.getShow().getStartTime());
        showDto.setEndTime(booking.getShow().getEndTime());

        Moviedto moviedto = new Moviedto();
        moviedto.setId(booking.getShow().getMovie().getId());
        moviedto.setTitle(booking.getShow().getMovie().getTitle());
        moviedto.setDescription(booking.getShow().getMovie().getDescription());
        moviedto.setGenre(booking.getShow().getMovie().getGenre());
        moviedto.setLanguage(booking.getShow().getMovie().getLanguage());
        moviedto.setDurationMinutes(booking.getShow().getMovie().getDurationMins());
        moviedto.setReleaseDate(booking.getShow().getMovie().getReleaseDate());
        moviedto.setPosterUrl(booking.getShow().getMovie().getPosterUrl());

        showDto.setMovie(moviedto);

        Screendto screendto = new Screendto();
        screendto.setId(booking.getShow().getScreen().getId());
        screendto.setName(booking.getShow().getScreen().getName());
        screendto.setTotalSeats(booking.getShow().getScreen().getTotalSeats());


        Theaterdto theaterdto = new Theaterdto();
        theaterdto.setId(booking.getShow().getScreen().getTheater().getId());
        theaterdto.setName(booking.getShow().getScreen().getTheater().getName());
        theaterdto.setAddress(booking.getShow().getScreen().getTheater().getAddress());
        theaterdto.setCity(booking.getShow().getScreen().getTheater().getCity());
        theaterdto.setTotalScreens(booking.getShow().getScreen().getTheater().getTotalScreens());

        screendto.setTheater(theaterdto);
        showDto.setScreen(screendto);
        bookingDto.setShow(showDto);

        List<ShowSeatdto> seatDtos  =  seats.stream()
                .map(seat->{
                    ShowSeatdto seatdto = new ShowSeatdto();
                    seatdto.setId(seat.getId());
                    seatdto.setStatus(seat.getStatus());
                    seatdto.setPrice(seat.getPrice());

                    Seatdto baseSeatdto = new Seatdto();
                    baseSeatdto.setId(seat.getSeat().getId());
                    baseSeatdto.setSeatNumber(seat.getSeat().getSeatNumber());
                    baseSeatdto.setSeatType(seat.getSeat().getSeatType());
                    baseSeatdto.setBasePrice(seat.getSeat().getBasePrice());
                    seatdto.setSeat(baseSeatdto);
                    return seatdto;
                })
                .collect(Collectors.toList());
        bookingDto.setSeats(seatDtos);

        if (booking.getPayment() != null) {
            Paymentdto paymentdto = new Paymentdto();
            paymentdto.setId(booking.getPayment().getId());
            paymentdto.setAmount(booking.getPayment().getAmount());
            paymentdto.setPaymentMethod(booking.getPayment().getPaymentMethod());
            paymentdto.setPaymentTime(booking.getPayment().getPaymentTime());
            paymentdto.setTransactionId(booking.getPayment().getTransactionId());
            paymentdto.setStatus(booking.getPayment().getStatus());
            bookingDto.setPayment(paymentdto);
        }

        return bookingDto;
    }

}
