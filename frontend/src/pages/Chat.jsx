import { useState } from "react";

import Navbar from "../components/Navbar";

import { createTicket } from "../services/ticketService";

import { getAiResponse } from "../services/aiService";





function Chat(){



    const [message,setMessage] = useState("");



    const [chat,setChat] = useState([]);



    const [loading,setLoading] = useState(false);



    const [error,setError] = useState("");








    const sendMessage = async(e)=>{


        e.preventDefault();



        if(message.trim()===""){


            return;


        }





        const userMessage = message;



        setChat(

            previous => [

                ...previous,

                {

                    sender:"YOU",

                    text:userMessage

                }

            ]

        );





        setMessage("");



        setLoading(true);



        setError("");







        try{



            const ticketResponse = await createTicket({


                title:userMessage.substring(0,30),


                description:userMessage,


                status:"OPEN",


                priority:"LOW"


            });








            const ticket = ticketResponse.data || ticketResponse;








            const aiResponse = await getAiResponse(


                ticket.id


            );









            setChat(

                previous => [

                    ...previous,


                    {

                        sender:"AI",


                        text:
                            aiResponse.data?.content
                            ||
                            aiResponse.data?.message
                            ||
                            aiResponse.message
                            ||
                            "AI response generated"


                    }

                ]

            );



        }
        catch(error){





            setError(


                "Unable to contact AI support"


            );




        }
        finally{


            setLoading(false);


        }



    };











    return(



        <>


            <Navbar />





            <div className="p-10 bg-gray-100 min-h-screen">





                <h1 className="text-3xl font-bold mb-6">


                    AI Customer Support


                </h1>








                <div className="bg-white rounded shadow p-5 mb-5 min-h-96">






                    {


                        chat.map(

                            (item,index)=>(




                                <div

                                    key={index}

                                    className="mb-4"

                                >



                                    <b>


                                        {item.sender}:


                                    </b>





                                    <p>


                                        {item.text}


                                    </p>



                                </div>



                            )


                        )

                    }







                    {


                        loading &&


                        <p>


                            AI thinking...


                        </p>


                    }








                    {


                        error &&


                        <p className="text-red-500">


                            {error}


                        </p>


                    }





                </div>









                <form

                    onSubmit={sendMessage}

                    className="flex"

                >





                    <input


                        className="border p-3 flex-1"


                        placeholder="Describe your issue..."


                        value={message}


                        onChange={(e)=>setMessage(e.target.value)}


                    />







                    <button


                        className="bg-blue-600 text-white p-3"


                    >


                        Send


                    </button>





                </form>





            </div>




        </>



    );



}





export default Chat;
