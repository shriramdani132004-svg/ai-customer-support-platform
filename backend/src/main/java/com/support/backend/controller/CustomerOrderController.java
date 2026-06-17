package com.support.backend.controller;


import java.util.List;


import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


import com.support.backend.entity.Order;

import com.support.backend.service.CustomerOrderService;





@RestController

@RequestMapping("/api/customer/orders")

public class CustomerOrderController {




    private final CustomerOrderService customerOrderService;





    public CustomerOrderController(

            CustomerOrderService customerOrderService

    ){


        this.customerOrderService = customerOrderService;


    }








    @GetMapping

    public List<Order> getOrders(){


        return customerOrderService.getMyOrders();


    }




}