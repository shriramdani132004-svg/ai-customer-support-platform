import { useEffect, useState } from "react";

import Navbar from "../components/Navbar";

import {

    getTickets,

    createTicket

} from "../services/ticketService";





function Tickets(){


    const [tickets,setTickets] = useState([]);


    const [title,setTitle] = useState("");


    const [description,setDescription] = useState("");


    const [priority,setPriority] = useState("LOW");








    const loadTickets = async()=>{


        try{


            const data = await getTickets();


            setTickets(data);



        }
        catch(error){


            console.log(error);


        }



    };









    const handleCreate = async(e)=>{


        e.preventDefault();




        await createTicket({


            title:title,


            description:description,


            status:"OPEN",


            priority:priority


        });






        setTitle("");


        setDescription("");


        setPriority("LOW");



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


                    Support Tickets


                </h1>






                <form

                    onSubmit={handleCreate}

                    className="bg-white p-5 rounded shadow mb-8"

                >



                    <input

                        className="border p-3 mr-3"

                        placeholder="Issue title"

                        value={title}

                        onChange={(e)=>setTitle(e.target.value)}

                    />






                    <input

                        className="border p-3 mr-3"

                        placeholder="Description"

                        value={description}

                        onChange={(e)=>setDescription(e.target.value)}

                    />







                    <select

                        className="border p-3 mr-3"

                        value={priority}

                        onChange={(e)=>setPriority(e.target.value)}

                    >


                        <option value="HIGH">

                            HIGH

                        </option>


                        <option value="MEDIUM">

                            MEDIUM

                        </option>


                        <option value="LOW">

                            LOW

                        </option>


                    </select>








                    <button

                        className="bg-blue-600 text-white p-3 rounded"

                    >


                        Create Ticket


                    </button>



                </form>










                {


                    tickets.map(

                        ticket => (



                            <div

                                key={ticket.id}

                                className="bg-white rounded shadow p-5 mb-5"

                            >





                                <div className="flex justify-between">



                                    <div>

   					 <p className="font-bold">

     					   Ticket ID: {ticket.id}

 					   </p>


 				   <h2 className="text-xl font-bold">

    				    {ticket.title}

  					  </h2>


					</div>







                                    {

                                        ticket.escalated &&


                                        <span className="font-bold">


                                            🚨 ESCALATED


                                        </span>


                                    }





                                </div>







                                <p className="mt-3">


                                    {ticket.description}


                                </p>






                                <p>


                                    Priority: {ticket.priority}


                                </p>
				
				


                                <p>


                                    Category: {ticket.category}


                                </p>






                                <div className="bg-gray-100 p-3 mt-3 rounded">


                                    <b>


                                        AI Summary:


                                    </b>



                                    <p>


                                        {ticket.summary}


                                    </p>


                                </div>








                                <p>


                                    Status: {ticket.status}


                                </p>





                                <p>


                                    Attempts: {ticket.attemptCount || 0}


                                </p>







                                {


                                    ticket.status==="HUMAN_REQUIRED" &&


                                    <h3 className="font-bold mt-3">


                                        Human Support Required


                                    </h3>


                                }




                            </div>




                        )

                    )

                }





            </div>


        </>


    );



}



export default Tickets;