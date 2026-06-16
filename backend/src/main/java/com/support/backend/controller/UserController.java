package com.support.backend.controller;



import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.support.backend.dto.UserDto;
import com.support.backend.service.UserService;




@RestController

@RequestMapping("/api/users")

public class UserController {




    private final UserService userService;




    public UserController(

            UserService userService

    ) {


        this.userService = userService;


    }






    @GetMapping

    public List<UserDto> users(){


        return userService.getAllUsers();


    }



}