package com.support.backend.repository;



import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;


import com.support.backend.entity.Ticket;
import com.support.backend.entity.User;





public interface TicketRepository

        extends JpaRepository<Ticket,Long> {




	List<Ticket> findByUser(

        User user

	);


    List<Ticket> findByStatus(

            String status

    );









    long countByStatus(

            String status

    );










    long countByPriority(

            String priority

    );





}