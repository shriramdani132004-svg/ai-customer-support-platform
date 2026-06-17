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

        "🚨 CUSTOMER SUPPORT ESCALATION\n\n"

        +

        "AI could not resolve this issue.\n"

        +

        "Human agent action required.\n\n"

        +

        "Ticket ID: "

        + ticket.getId()

        +

        "\n\nTitle:\n"

        + ticket.getTitle()

        +

        "\n\nCustomer Issue:\n"

        + ticket.getDescription()

        +

        "\n\nCategory: "

        + ticket.getCategory()

        +

        "\nPriority: "

        + ticket.getPriority()

        +

        "\nAI Attempts: "

        + ticket.getAttemptCount()

        +

        "\nStatus: "

        + ticket.getStatus()

        +

        "\n\nAI Conversation Summary:\n"

        + ticket.getConversationSummary()

        +

        "\n\nPlease contact customer and resolve manually."

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