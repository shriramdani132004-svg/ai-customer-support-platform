package com.support.backend.service;



import org.springframework.beans.factory.annotation.Value;

import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;



import com.support.backend.entity.Ticket;






@Service
public class EmailService {




    private final JavaMailSender mailSender;






    @Value("${spring.mail.username}")
    private String supportEmail;






    public EmailService(

            JavaMailSender mailSender

    ){


        this.mailSender =
                mailSender;


    }








    public void sendEscalationEmail(

            Ticket ticket

    ){



        try{



            SimpleMailMessage message =
                    new SimpleMailMessage();






            message.setTo(

                    supportEmail

            );






            message.setSubject(

                    "Ticket Escalated - Human Support Required"

            );







            message.setText(


                    "A ticket needs human support."


                    +

                    "\n\nTicket ID: "

                    + ticket.getId()


                    +

                    "\nTitle: "

                    + ticket.getTitle()


                    +

                    "\nDescription: "

                    + ticket.getDescription()


                    +

                    "\nPriority: "

                    + ticket.getPriority()


                    +

                    "\nAttempts: "

                    + ticket.getAttemptCount()


            );









            mailSender.send(

                    message

            );







            System.out.println(

                    "Escalation email sent successfully"

            );



        }
        catch(Exception e){



            System.out.println(

                    "Email failed: "

                    + e.getMessage()

            );



        }



    }




}