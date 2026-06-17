import { Link } from "react-router-dom";

import { useAuth } from "../context/AuthContext";




function Navbar(){


    const {logout} = useAuth();





    const handleLogout = ()=>{


        logout();


        window.location.href="/login";


    };





    return(


        <div className="bg-gray-900 text-white p-4 flex justify-between">


            <h2 className="font-bold text-xl">

                AI Support

            </h2>




            <div className="flex gap-6">


                <Link to="/dashboard">
                    Dashboard
                </Link>


                <Link to="/tickets">
                    Tickets
                </Link>


                <Link to="/chat">
                    AI Chat
                </Link>



                <button

                    onClick={handleLogout}

                    className="text-red-400"

                >

                    Logout

                </button>


            </div>


        </div>


    );


}



export default Navbar;