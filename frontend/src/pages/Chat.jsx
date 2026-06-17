import { useState } from "react";


import Navbar from "../components/Navbar";


import { getAiResponse } from "../services/aiService";


import { getTicketMessages } from "../services/agentService";







function Chat(){



    const [ticketId,setTicketId] = useState("");



    const [message,setMessage] = useState("");



    const [chat,setChat] = useState([]);



    const [loading,setLoading] = useState(false);



    const [error,setError] = useState("");









    const loadMessages = async(id)=>{


        if(id.trim()===""){

            return;

        }



        try{


            const data = await getTicketMessages(id);



            setChat(

                data.map(

                    msg=>({

                        sender:msg.sender,

                        text:msg.message

                    })

                )

            );


        }

        catch(error){


            setChat([]);


        }


    };











    const sendMessage = async(e)=>{


        e.preventDefault();




        if(ticketId.trim()===""){


            setError("Enter ticket id first");


            return;


        }




        if(message.trim()===""){


            return;


        }





        const userMessage = message;





        setChat(

            previous=>[

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


            await getAiResponse(

                ticketId,

                userMessage

            );




            await loadMessages(

                ticketId

            );


        }

        catch(error){



            setError(

                "Unable to contact support"

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







                <input

                    className="border p-3 mb-5 w-full"

                    placeholder="Enter Ticket ID"

                    value={ticketId}

                    onChange={(e)=>{

                        setTicketId(e.target.value);

                        loadMessages(e.target.value);

                    }}

                />









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


                        placeholder="Ask AI about this ticket..."


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