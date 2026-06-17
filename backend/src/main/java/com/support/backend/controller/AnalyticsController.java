package com.support.backend.controller;





import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


import com.support.backend.dto.AnalyticsResponse;

import com.support.backend.service.AnalyticsService;








@RestController

@RequestMapping("/api/analytics")

public class AnalyticsController {







    private final AnalyticsService analyticsService;








    public AnalyticsController(

            AnalyticsService analyticsService

    ){



        this.analyticsService =
                analyticsService;



    }










    @GetMapping

    public AnalyticsResponse analytics(){



        return analyticsService.getAnalytics();



    }






}