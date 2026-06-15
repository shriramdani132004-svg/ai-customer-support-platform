package com.support.backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.support.backend.entity.User;


public interface UserRepository 
extends JpaRepository<User,Long> {


}