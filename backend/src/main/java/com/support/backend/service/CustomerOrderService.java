package com.support.backend.service;


import java.util.List;


import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;


import com.support.backend.entity.Order;

import com.support.backend.entity.User;

import com.support.backend.exception.ResourceNotFoundException;

import com.support.backend.repository.OrderRepository;

import com.support.backend.repository.UserRepository;





@Service
public class CustomerOrderService {



    private final OrderRepository orderRepository;


    private final UserRepository userRepository;




    public CustomerOrderService(

            OrderRepository orderRepository,

            UserRepository userRepository

    ){


        this.orderRepository = orderRepository;


        this.userRepository = userRepository;


    }







    public List<Order> getMyOrders(){



        String email =

                SecurityContextHolder

                        .getContext()

                        .getAuthentication()

                        .getName();




        User user =

                userRepository

                        .findByEmail(email)

                        .orElseThrow(

                                () -> new ResourceNotFoundException(

                                        "User not found"

                                )

                        );




        return orderRepository.findByUser(

                user

        );


    }



}