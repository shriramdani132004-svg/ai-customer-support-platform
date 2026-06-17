import api from "../api/axiosConfig";






export const getEscalatedTickets = async()=>{



    const response = await api.get(

        "/agent/escalated"

    );



    return response.data;



};








export const resolveTicket = async(id)=>{



    const response = await api.put(

        `/agent/tickets/${id}/resolve`

    );



    return response.data;



};









export const sendAgentReply = async(

    id,

    message

)=>{



    const response = await api.post(

        `/agent/tickets/${id}/reply`,

        message,

        {

            headers:{

                "Content-Type":"text/plain"

            }

        }

    );




    return response.data;



};


export const getTicketMessages = async(id)=>{



    const response = await api.get(

        `/tickets/${id}/messages`

    );



    return response.data;



};
