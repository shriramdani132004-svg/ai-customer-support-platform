package com.support.backend.repository;


import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;


import com.support.backend.entity.Order;

import com.support.backend.entity.User;




public interface OrderRepository

        extends JpaRepository<Order,Long>{



    List<Order> findByUser(

            User user

    );



}