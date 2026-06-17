import api from "../api/axiosConfig";



export const getAiResponse = async(ticketId, message)=>{


    try{


        const response = await api.post(

            `/tickets/${ticketId}/ai-response`,

            {
                message: message
            }

        );



        return response.data;


    }
    catch(error){



        throw error;



    }


};