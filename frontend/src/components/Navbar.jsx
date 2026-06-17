import { Link, useNavigate } from "react-router-dom";

import { useAuth } from "../context/AuthContext";





function Navbar(){


    const navigate = useNavigate();


    const { logout } = useAuth();





    const handleLogout = ()=>{


        logout();


        navigate("/login");


    };







    return(


        <div className="bg-gray-900 text-white p-5 flex justify-between">



            <h1 className="font-bold text-xl">

                ShopSphere Support

            </h1>





            <div className="flex gap-5">



                <Link to="/customer/dashboard">

                    Dashboard

                </Link>





                <Link to="/orders">

                    Orders

                </Link>





                <Link to="/tickets">

                    Tickets

                </Link>





                <button onClick={handleLogout}>

                    Logout

                </button>




            </div>


        </div>


    );


}



export default Navbar;