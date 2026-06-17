import { useNavigate } from "react-router-dom";


function CustomerDashboard(){


    const navigate = useNavigate();



    return(

        <div className="min-h-screen bg-gray-100 p-10">


            <h1 className="text-4xl font-bold mb-10">

                Customer Dashboard 🛒

            </h1>



            <div className="grid grid-cols-2 gap-8">



                <div

                    onClick={()=>navigate("/orders")}

                    className="bg-white p-10 rounded-xl shadow cursor-pointer hover:scale-105"

                >


                    <h2 className="text-2xl font-bold">

                        📦 My Orders

                    </h2>


                    <p>

                        View your purchased products

                    </p>


                </div>





                <div

                    onClick={()=>navigate("/tickets")}

                    className="bg-white p-10 rounded-xl shadow cursor-pointer hover:scale-105"

                >


                    <h2 className="text-2xl font-bold">

                        🎫 My Tickets

                    </h2>


                    <p>

                        View your support issues

                    </p>


                </div>



            </div>


        </div>

    );


}



export default CustomerDashboard;