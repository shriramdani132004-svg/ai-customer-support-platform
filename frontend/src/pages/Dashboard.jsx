import { useEffect, useState } from "react";


import Navbar from "../components/Navbar";


import { getAnalytics } from "../services/analyticsService";


import {

    BarChart,

    Bar,

    XAxis,

    YAxis,

    Tooltip,

    ResponsiveContainer

} from "recharts";








function Dashboard(){





    const [analytics,setAnalytics] = useState(null);








    const loadAnalytics = async()=>{



        try{


            const data =
                await getAnalytics();



            setAnalytics(data);


        }


        catch(error){


            console.log(error);


        }



    };










    useEffect(()=>{


        loadAnalytics();


    },[]);










    if(!analytics){



        return(

            <>

                <Navbar />

                <h1 className="p-10">

                    Loading...

                </h1>


            </>

        );


    }











    const chartData = [



        {

            name:"Open",

            value:analytics.openTickets

        },



        {

            name:"Resolved",

            value:analytics.resolvedTickets

        },



        {

            name:"Escalated",

            value:analytics.escalatedTickets

        },



        {

            name:"High Priority",

            value:analytics.highPriorityTickets

        }



    ];









    return(


        <>


            <Navbar />






            <div className="p-10 bg-gray-100 min-h-screen">






                <h1 className="text-3xl font-bold mb-8">


                    Analytics Dashboard


                </h1>









                <div className="grid grid-cols-5 gap-5 mb-10">





                    <div className="bg-white p-5 shadow rounded">


                        Total


                        <h2 className="text-3xl font-bold">


                            {analytics.totalTickets}


                        </h2>


                    </div>









                    <div className="bg-white p-5 shadow rounded">


                        Open


                        <h2 className="text-3xl font-bold">


                            {analytics.openTickets}


                        </h2>


                    </div>








                    <div className="bg-white p-5 shadow rounded">


                        Resolved


                        <h2 className="text-3xl font-bold">


                            {analytics.resolvedTickets}


                        </h2>


                    </div>








                    <div className="bg-white p-5 shadow rounded">


                        Escalated


                        <h2 className="text-3xl font-bold">


                            {analytics.escalatedTickets}


                        </h2>


                    </div>








                    <div className="bg-white p-5 shadow rounded">


                        High Priority


                        <h2 className="text-3xl font-bold">


                            {analytics.highPriorityTickets}


                        </h2>


                    </div>





                </div>









                <div className="bg-white p-8 rounded shadow">



                    <h2 className="text-xl font-bold mb-5">


                        Ticket Analytics


                    </h2>








                    <ResponsiveContainer

                        width="100%"

                        height={300}

                    >



                        <BarChart data={chartData}>



                            <XAxis dataKey="name" />


                            <YAxis />


                            <Tooltip />


                            <Bar dataKey="value" />



                        </BarChart>



                    </ResponsiveContainer>







                </div>







            </div>



        </>

    );


}




export default Dashboard;