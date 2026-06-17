import { useEffect, useState } from "react";


import Navbar from "../components/Navbar";


import {

    getEscalatedTickets,

    resolveTicket,

    sendAgentReply,

    getTicketMessages

} from "../services/agentService";








function Agent(){



    const [tickets,setTickets] = useState([]);


    const [replies,setReplies] = useState({});


    const [messages,setMessages] = useState({});








    const loadTickets = async()=>{



        const data = await getEscalatedTickets();



        setTickets(data);





        data.forEach(

            async(ticket)=>{



                const msg = await getTicketMessages(

                    ticket.id

                );



                setMessages(

                    previous=>({

                        ...previous,

                        [ticket.id]:msg

                    })

                );



            }

        );



    };










    const handleSendReply = async(id)=>{



        if(!replies[id]){


            return;


        }





        await sendAgentReply(

            id,

            replies[id]

        );





        setReplies({

            ...replies,

            [id]:""

        });





        loadTickets();



    };










    const handleResolve = async(id)=>{



        await resolveTicket(id);



        loadTickets();



    };










    useEffect(()=>{


        loadTickets();


    },[]);











    return(

        <>

            <Navbar />



            <div className="p-10 bg-gray-100 min-h-screen">



                <h1 className="text-3xl font-bold mb-8">

                    Human Agent Dashboard

                </h1>





                {

                    tickets.length===0 &&

                    <h2>No escalated tickets</h2>

                }






                {

                    tickets.map(ticket=>(


                        <div

                            key={ticket.id}

                            className="bg-white p-5 rounded shadow mb-5"

                        >



                            <h2 className="font-bold text-xl">

                                Ticket ID: {ticket.id}

                            </h2>




                            <p>{ticket.title}</p>


                            <p>{ticket.description}</p>




                            <h3 className="font-bold mt-5">

                                Conversation:

                            </h3>






                            {

                                messages[ticket.id]?.map(

                                    msg=>(


                                        <p key={msg.id}>


                                            <b>{msg.sender}:</b>


                                            {" "}


                                            {msg.message}


                                        </p>


                                    )


                                )


                            }








                            <textarea

                                className="border w-full p-3 mt-5"

                                placeholder="Agent reply"


                                value={

                                    replies[ticket.id] || ""

                                }


                                onChange={

                                    e=>

                                    setReplies({

                                        ...replies,

                                        [ticket.id]:

                                        e.target.value

                                    })

                                }

                            />







                            <button

                                onClick={()=>handleSendReply(ticket.id)}

                                className="bg-blue-600 text-white p-3 mt-3 mr-3 rounded"

                            >


                                Send Reply


                            </button>







                            <button

                                onClick={()=>handleResolve(ticket.id)}

                                className="bg-green-600 text-white p-3 mt-3 rounded"

                            >


                                Resolve Ticket


                            </button>




                        </div>


                    ))

                }




            </div>


        </>

    );


}



export default Agent;