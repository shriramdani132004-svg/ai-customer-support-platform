package com.support.backend.service;


import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;


import com.support.backend.dto.AuthResponse;

import com.support.backend.dto.LoginRequest;

import com.support.backend.dto.RegisterRequest;


import com.support.backend.entity.Order;

import com.support.backend.entity.User;


import com.support.backend.exception.ResourceNotFoundException;


import com.support.backend.repository.OrderRepository;

import com.support.backend.repository.UserRepository;


import com.support.backend.security.JwtService;





@Service
public class AuthService {




    private final UserRepository userRepository;


    private final OrderRepository orderRepository;


    private final PasswordEncoder passwordEncoder;


    private final JwtService jwtService;






    public AuthService(

            UserRepository userRepository,

            OrderRepository orderRepository,

            PasswordEncoder passwordEncoder,

            JwtService jwtService

    ){


        this.userRepository = userRepository;


        this.orderRepository = orderRepository;


        this.passwordEncoder = passwordEncoder;


        this.jwtService = jwtService;


    }










    public AuthResponse register(

            RegisterRequest request

    ){





        if(

                userRepository

                        .findByEmail(

                                request.getEmail()

                        )

                        .isPresent()

        ){



            User existingUser =

                    userRepository

                            .findByEmail(

                                    request.getEmail()

                            )

                            .get();




            String token =

                    jwtService.generateToken(

                            existingUser.getEmail()

                    );




            return new AuthResponse(

                    token

            );


        }









        User user =

                new User();




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






        if(request.getRole()==null){



            user.setRole(

                    "CUSTOMER"

            );



        }
        else{



            user.setRole(

                    request.getRole()

            );



        }








        User savedUser =

                userRepository.save(

                        user

                );








        if(

                "CUSTOMER".equals(

                        savedUser.getRole()

                )

        ){



            createFakeOrders(

                    savedUser

            );



        }









        String token =

                jwtService.generateToken(

                        savedUser.getEmail()

                );





        return new AuthResponse(

                token

        );



    }












    private void createFakeOrders(

            User user

    ){





        Order order1 =

                Order.builder()

                        .orderNumber("ORD-1001")

                        .productName("Apple iPhone 16")

                        .price(79999.0)

                        .status("DELIVERED")

                        .user(user)

                        .build();








        Order order2 =

                Order.builder()

                        .orderNumber("ORD-1002")

                        .productName("Sony Headphones")

                        .price(9999.0)

                        .status("SHIPPED")

                        .user(user)

                        .build();








        Order order3 =

                Order.builder()

                        .orderNumber("ORD-1003")

                        .productName("Gaming Laptop")

                        .price(65000.0)

                        .status("PAYMENT ISSUE")

                        .user(user)

                        .build();








        orderRepository.save(

                order1

        );



        orderRepository.save(

                order2

        );



        orderRepository.save(

                order3

        );



    }













    public AuthResponse login(

            LoginRequest request

    ){





        User user =

                userRepository

                        .findByEmail(

                                request.getEmail()

                        )

                        .orElseThrow(

                                () -> new ResourceNotFoundException(

                                        "User not found"

                                )

                        );









        if(

                !passwordEncoder.matches(

                        request.getPassword(),

                        user.getPassword()

                )

        ){



            throw new ResourceNotFoundException(

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