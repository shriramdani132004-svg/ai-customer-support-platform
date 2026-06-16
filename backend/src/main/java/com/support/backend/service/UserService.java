package com.support.backend.service;



import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.support.backend.dto.UserDto;
import com.support.backend.entity.User;
import com.support.backend.repository.UserRepository;




@Service

public class UserService {




    private final UserRepository userRepository;




    public UserService(

            UserRepository userRepository

    ) {


        this.userRepository = userRepository;


    }





    public List<UserDto> getAllUsers(){



        return userRepository.findAll()

                .stream()

                .map(this::convertToDto)

                .collect(Collectors.toList());



    }





    private UserDto convertToDto(User user){



        return new UserDto(

                user.getId(),

                user.getName(),

                user.getEmail(),

                user.getRole()

        );



    }



}