package com.support.backend.service;



import org.springframework.stereotype.Service;





@Service
public class PriorityService {






    public String detectPriority(

            String text

    ){





        if(text == null){


            return "LOW";


        }







        String issue =

                text.toLowerCase();








        if(

                issue.contains("payment")

                ||

                issue.contains("money")

                ||

                issue.contains("refund")

                ||

                issue.contains("deducted")

                ||

                issue.contains("account locked")

                ||

                issue.contains("login failed")


        ){



            return "HIGH";



        }









        if(

                issue.contains("order")

                ||

                issue.contains("delivery")

                ||

                issue.contains("tracking")

                ||

                issue.contains("cancel")


        ){



            return "MEDIUM";



        }









        return "LOW";



    }




}