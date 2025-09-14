package com.cfs.bookmyshow.Service;


import com.cfs.bookmyshow.dto.Theaterdto;
import com.cfs.bookmyshow.exceptions.ResourceNotFoundException;
import com.cfs.bookmyshow.model.Theater;
import com.cfs.bookmyshow.repository.TheaterRepository;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TheaterService {

   // @Autowired
    private TheaterRepository theaterRepository;

    public Theaterdto createTheater(Theaterdto theaterdto) {
        Theater theater = mapToEntitiy(theaterdto);
        Theater savedTheater = theaterRepository.save(theater);
        return mapToDto(savedTheater);

    }

    private Theaterdto getTheaterById(Long theaterId) {
        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> new ResourceNotFoundException("Theater not found with id " + theaterId));
        return mapToDto(theater);
    }

    private List<Theaterdto> getAllTheaters(){
        List<Theater> theaters = theaterRepository.findAll();
        return  theaters.stream()
                .map(theater->mapToDto(theater))
                .collect(Collectors.toList());
    }

    private List<Theaterdto> getTheaterByCity(String city) {
        List<Theater> theaters = theaterRepository.findByCity(city);
        return  theaters.stream()
                .map(theater->mapToDto(theater))
                .collect(Collectors.toList());
    }



    private Theaterdto mapToDto(Theater savedTheater) {
         Theaterdto theaterdto = new Theaterdto();
         theaterdto.setId(savedTheater.getId());
         theaterdto.setName(savedTheater.getName());
         theaterdto.setCity(savedTheater.getCity());
         theaterdto.setAddress(savedTheater.getAddress());
         theaterdto.setTotalScreens(savedTheater.getTotalScreens());
         return theaterdto;
    }

    private Theater mapToEntitiy(Theaterdto theaterdto) {
        Theater theater = new Theater();
        theater.setId(theaterdto.getId());
        theater.setName(theaterdto.getName());
        theater.setAddress(theaterdto.getAddress());
        theater.setCity(theaterdto.getCity());
        theater.setTotalScreens(theaterdto.getTotalScreens());
        return theater;
    }

}
