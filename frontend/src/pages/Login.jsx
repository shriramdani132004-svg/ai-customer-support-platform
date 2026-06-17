import { useState } from "react";

import { useNavigate } from "react-router-dom";

import { loginUser } from "../services/authService";

import { useAuth } from "../context/AuthContext";





function Login(){



    const navigate = useNavigate();



    const { login } = useAuth();





    const [email,setEmail] = useState("");


    const [password,setPassword] = useState("");



    const [error,setError] = useState("");






    const handleLogin = async(e)=>{


        e.preventDefault();




        try{



            const response = await loginUser({


                email:email,


                password:password


            });






            login(

                response.token

            );






            navigate(

                "/dashboard"

            );




        }
        catch(error){



            setError(

                "Invalid email or password"

            );



        }



    };









    return(



        <div className="h-screen flex items-center justify-center bg-gray-900">



            <form


                onSubmit={handleLogin}


                className="bg-white p-8 rounded-xl w-96"


            >




                <h1 className="text-3xl font-bold mb-6 text-center">


                    Login


                </h1>







                {


                    error &&


                    <p className="text-red-500 mb-3">


                        {error}


                    </p>


                }








                <input


                    className="border p-3 w-full mb-4"


                    placeholder="Email"


                    value={email}


                    onChange={(e)=>setEmail(e.target.value)}


                />







                <input


                    className="border p-3 w-full mb-4"


                    type="password"


                    placeholder="Password"


                    value={password}


                    onChange={(e)=>setPassword(e.target.value)}


                />








                <button


                    className="bg-blue-600 text-white p-3 w-full rounded"


                >



                    Login



                </button>





            </form>




        </div>



    );



}




export default Login;