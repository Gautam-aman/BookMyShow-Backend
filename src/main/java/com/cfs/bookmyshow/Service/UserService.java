package com.cfs.bookmyshow.Service;


import com.cfs.bookmyshow.dto.Userdto;
import com.cfs.bookmyshow.exceptions.ResourceNotFoundException;
import com.cfs.bookmyshow.model.User;
import com.cfs.bookmyshow.repository.UserRepository;
import jakarta.persistence.Id;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {


    @Id
    private UserRepository userRepository;

    private Userdto createUser(Userdto userdto) {
        User user = mapToEntity(userdto);
        User savedUser = userRepository.save(user);
        return mapToDto(savedUser);
    }

    public Userdto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return mapToDto(user);
    }

    public Userdto getUserByName(String name) {
        User user = userRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return mapToDto(user);
    }

    public List<Userdto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }




    private User mapToEntity(Userdto userdto) {
        User user = new User();
        user.setId(userdto.getId());
        user.setEmail(userdto.getEmail());
        user.setName(userdto.getName());
        user.setPhoneNumber(String.valueOf(userdto.getPhoneNumber()));
        return user;
    }

    private Userdto mapToDto(User user) {
        Userdto userdto = new Userdto();
        userdto.setId(user.getId());
        userdto.setEmail(user.getEmail());
        userdto.setName(user.getName());
        userdto.setPhoneNumber(user.getPhoneNumber());
        return userdto;
    }

}
