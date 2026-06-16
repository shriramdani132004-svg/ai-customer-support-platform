package com.support.backend.service;

import com.support.backend.exception.ResourceNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.support.backend.dto.AuthResponse;
import com.support.backend.dto.LoginRequest;
import com.support.backend.dto.RegisterRequest;

import com.support.backend.entity.User;
import com.support.backend.repository.UserRepository;
import com.support.backend.security.JwtService;



@Service
public class AuthService {



    private final UserRepository userRepository;


    private final PasswordEncoder passwordEncoder;


    private final JwtService jwtService;




    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {


        this.userRepository = userRepository;

        this.passwordEncoder = passwordEncoder;

        this.jwtService = jwtService;


    }





    public AuthResponse register(
            RegisterRequest request
    ) {


        User user = new User();


        user.setName(
                request.getName()
        );


        user.setEmail(
                request.getEmail()
        );


        user.setPassword(

                passwordEncoder.encode(
                        request.getPassword()
                )

        );


        user.setRole(
                request.getRole()
        );



        userRepository.save(user);



        String token =
                jwtService.generateToken(
                        user.getEmail()
                );



        return new AuthResponse(
                token
        );


    }







    public AuthResponse login(
            LoginRequest request
    ) {


        User user =
                userRepository

                .findByEmail(
                        request.getEmail()
                )

                .orElseThrow();





        if (

                !passwordEncoder.matches(

                        request.getPassword(),

                        user.getPassword()

                )

        ) {


            throw new RuntimeException(
                    "Invalid password"
            );


        }





        String token =
                jwtService.generateToken(
                        user.getEmail()
                );



        return new AuthResponse(
                token
        );


    }




}