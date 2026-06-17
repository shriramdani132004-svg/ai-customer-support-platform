import api from "../api/axiosConfig";




export const getAiResponse = async(ticketId)=>{


    try{


        const response = await api.post(

            `/tickets/${ticketId}/ai-response`

        );



        return response.data;


    }
    catch(error){



        throw error;



    }


};