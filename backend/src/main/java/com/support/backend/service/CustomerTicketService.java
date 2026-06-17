package com.support.backend.service;


import java.util.List;


import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;


import com.support.backend.entity.Order;

import com.support.backend.entity.Ticket;

import com.support.backend.entity.User;


import com.support.backend.repository.OrderRepository;

import com.support.backend.repository.TicketRepository;

import com.support.backend.repository.UserRepository;



@Service
public class CustomerTicketService {



    private final TicketRepository ticketRepository;


    private final OrderRepository orderRepository;


    private final UserRepository userRepository;





    public CustomerTicketService(

            TicketRepository ticketRepository,

            OrderRepository orderRepository,

            UserRepository userRepository

    ){


        this.ticketRepository = ticketRepository;


        this.orderRepository = orderRepository;


        this.userRepository = userRepository;


    }









    public Ticket createTicket(

            Long orderId,

            Ticket ticket

    ){



        String email =

                SecurityContextHolder

                        .getContext()

                        .getAuthentication()

                        .getName();





        User user =

                userRepository

                        .findByEmail(email)

                        .orElseThrow();






        Order order =

                orderRepository

                        .findById(orderId)

                        .orElseThrow();








        ticket.setUser(

                user

        );



        ticket.setOrder(

                order

        );



        ticket.setStatus(

                "OPEN"

        );






        return ticketRepository.save(

                ticket

        );


    }









    public List<Ticket> myTickets(){



        String email =

                SecurityContextHolder

                        .getContext()

                        .getAuthentication()

                        .getName();





        User user =

                userRepository

                        .findByEmail(email)

                        .orElseThrow();





        return ticketRepository.findByUser(

                user

        );


    }




}