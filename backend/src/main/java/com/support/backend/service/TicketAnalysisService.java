package com.support.backend.service;



import org.springframework.stereotype.Service;





@Service
public class TicketAnalysisService {






    public String detectPriority(

            String text

    ){





        String issue =

                text.toLowerCase();








        if(

                issue.contains("payment")

                ||

                issue.contains("money")

                ||

                issue.contains("refund")

                ||

                issue.contains("account")

        ){


            return "HIGH";


        }








        if(

                issue.contains("order")

                ||

                issue.contains("delivery")

                ||

                issue.contains("shipping")

        ){


            return "MEDIUM";


        }









        return "LOW";



    }











    public String detectCategory(

            String text

    ){





        String issue =

                text.toLowerCase();







        if(issue.contains("payment")

                ||

                issue.contains("refund")

                ||

                issue.contains("money")

        ){


            return "PAYMENT";


        }







        if(issue.contains("order")

                ||

                issue.contains("delivery")

        ){


            return "ORDER";


        }








        if(issue.contains("account")

                ||

                issue.contains("login")

        ){


            return "ACCOUNT";


        }







        return "GENERAL";



    }











    public String generateSummary(

            String text

    ){



        return

                "Customer issue summary: "

                +

                text;



    }






}