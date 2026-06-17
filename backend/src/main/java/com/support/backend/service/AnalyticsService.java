package com.support.backend.service;





import org.springframework.stereotype.Service;


import com.support.backend.dto.AnalyticsResponse;

import com.support.backend.repository.TicketRepository;







@Service

public class AnalyticsService {





    private final TicketRepository ticketRepository;








    public AnalyticsService(

            TicketRepository ticketRepository

    ){



        this.ticketRepository =
                ticketRepository;



    }











    public AnalyticsResponse getAnalytics(){






        long total =

                ticketRepository.count();







        long resolved =

                ticketRepository.countByStatus(

                        "RESOLVED"

                );








        long escalated =

                ticketRepository.countByStatus(

                        "HUMAN_REQUIRED"

                );








        long open =

                ticketRepository.countByStatus(

                        "OPEN"

                );








        long highPriority =

                ticketRepository.countByPriority(

                        "HIGH"

                );









        return new AnalyticsResponse(

                total,

                resolved,

                escalated,

                open,

                highPriority

        );



    }





}