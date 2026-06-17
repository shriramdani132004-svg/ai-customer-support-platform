import { useEffect, useState } from "react";


import Navbar from "../components/Navbar";


import { getTickets } from "../services/ticketService";




function Dashboard(){



    const [stats,setStats] = useState({


        total:0,


        open:0,


        resolved:0,


        high:0


    });






    const loadAnalytics = async()=>{



        try{



            const tickets = await getTickets();




            setStats({



                total:

                    tickets.length,



                open:

                    tickets.filter(

                        ticket => ticket.status==="OPEN"

                    ).length,




                resolved:


                    tickets.filter(

                        ticket => ticket.status==="RESOLVED"

                    ).length,




                high:


                    tickets.filter(

                        ticket => ticket.priority==="HIGH"

                    ).length



            });



        }

        catch(error){


            console.log(error);


        }



    };








    useEffect(()=>{


        loadAnalytics();


    },[]);










    return(



        <>



            <Navbar />





            <div className="p-10 bg-gray-100 min-h-screen">






                <h1 className="text-3xl font-bold mb-8">


                    AI Support Analytics


                </h1>









                <div className="grid grid-cols-4 gap-5">








                    <div className="bg-white p-6 rounded shadow">



                        <h2>Total Tickets</h2>



                        <p className="text-3xl font-bold">


                            {stats.total}


                        </p>



                    </div>









                    <div className="bg-white p-6 rounded shadow">




                        <h2>Open Tickets</h2>



                        <p className="text-3xl font-bold">


                            {stats.open}


                        </p>




                    </div>










                    <div className="bg-white p-6 rounded shadow">




                        <h2>Resolved</h2>




                        <p className="text-3xl font-bold">


                            {stats.resolved}


                        </p>




                    </div>










                    <div className="bg-white p-6 rounded shadow">



                        <h2>High Priority</h2>



                        <p className="text-3xl font-bold">


                            {stats.high}


                        </p>



                    </div>







                </div>




            </div>



        </>



    );


}





export default Dashboard;