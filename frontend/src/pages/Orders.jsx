import { useEffect, useState } from "react";

import Navbar from "../components/Navbar";

import api from "../api/axiosConfig";

import { useNavigate } from "react-router-dom";





function Orders(){


    const navigate = useNavigate();


    const [orders,setOrders] = useState([]);






    const loadOrders = async()=>{


        const response = await api.get(

            "/customer/orders"

        );


        setOrders(

            response.data

        );


    };







    useEffect(()=>{


        loadOrders();


    },[]);








    return(

        <>


            <Navbar />



            <div className="min-h-screen bg-gray-100 p-10">



                <h1 className="text-3xl font-bold mb-8">


                    My Orders 📦


                </h1>






                {

                    orders.length===0 &&


                    <h2>


                        No orders found


                    </h2>


                }








                {

                    orders.map(

                        order=>(


                            <div

                                key={order.id}

                                className="bg-white p-6 rounded shadow mb-5"

                            >



                                <h2 className="text-xl font-bold">


                                    {order.productName}


                                </h2>




                                <p>


                                    Order No: {order.orderNumber}


                                </p>




                                <p>


                                    Price: ₹{order.price}


                                </p>





                                <p>


                                    Status: {order.status}


                                </p>






                                <button

                                    onClick={

                                        ()=>navigate(

                                            `/tickets?order=${order.id}`

                                        )

                                    }


                                    className="bg-blue-600 text-white p-3 mt-5 rounded"

                                >


                                    Contact Support


                                </button>





                            </div>


                        )

                    )


                }




            </div>


        </>

    );


}



export default Orders;