import api from "../api/axiosConfig";






export const getAnalytics = async()=>{



    const response = await api.get(

        "/analytics"

    );




    return response.data;



};