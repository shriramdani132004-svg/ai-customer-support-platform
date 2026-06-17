package com.support.backend.dto;





public class AnalyticsResponse {





    private long totalTickets;


    private long resolvedTickets;


    private long escalatedTickets;


    private long openTickets;


    private long highPriorityTickets;








    public AnalyticsResponse(

            long totalTickets,

            long resolvedTickets,

            long escalatedTickets,

            long openTickets,

            long highPriorityTickets

    ){



        this.totalTickets =
                totalTickets;



        this.resolvedTickets =
                resolvedTickets;



        this.escalatedTickets =
                escalatedTickets;



        this.openTickets =
                openTickets;



        this.highPriorityTickets =
                highPriorityTickets;



    }








    public long getTotalTickets(){

        return totalTickets;

    }




    public long getResolvedTickets(){

        return resolvedTickets;

    }




    public long getEscalatedTickets(){

        return escalatedTickets;

    }




    public long getOpenTickets(){

        return openTickets;

    }





    public long getHighPriorityTickets(){

        return highPriorityTickets;

    }





}