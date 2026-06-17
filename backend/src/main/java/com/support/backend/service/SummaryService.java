package com.support.backend.service;




import org.springframework.stereotype.Service;





@Service
public class SummaryService {





    public String generateSummary(

            String description

    ){






        if(description == null){


            return "No conversation available";


        }







        String text = description.toLowerCase();









        if(

                text.contains("payment")

                ||

                text.contains("money")

                ||

                text.contains("refund")

        ){


            return "Customer reported a payment issue. Payment troubleshooting completed. Human billing support required.";


        }









        if(

                text.contains("login")

                ||

                text.contains("password")

        ){



            return "Customer reported login issue. Basic account recovery steps attempted. Technical support required.";



        }










        if(

                text.contains("order")

                ||

                text.contains("delivery")

        ){



            return "Customer reported order related issue. Order verification required by support agent.";



        }










        return "Customer issue reviewed by AI assistant. Further human support may be required.";





    }




}