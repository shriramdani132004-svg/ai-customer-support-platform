package com.support.backend.controller;


import com.support.backend.entity.User;
import com.support.backend.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;



    @PostMapping
    public User createUser(@RequestBody User user) {

        return userService.createUser(user);

    }



    @GetMapping
    public List<User> getUsers() {

        return userService.getAllUsers();

    }


}