import { useState } from "react";

import { useNavigate } from "react-router-dom";

import { loginUser } from "../services/authService";



function Login(){


    const navigate = useNavigate();



    const [email,setEmail] = useState("");

    const [password,setPassword] = useState("");

    const [error,setError] = useState("");




    const handleLogin = async(e)=>{


        e.preventDefault();


        try{


            const result = await loginUser({

                email:email,

                password:password

            });



            console.log(result);



            localStorage.setItem(

                "token",

                result.token

            );



            navigate("/dashboard");


        }

        catch(err){


            console.log(err);


            setError("Login failed");


        }


    };




    return(


        <div className="h-screen flex justify-center items-center bg-gray-900">


            <form 

                onSubmit={handleLogin}

                className="bg-white p-8 rounded w-96"

            >


                <h1 className="text-3xl font-bold mb-5">

                    Login

                </h1>



                <p className="text-red-500">

                    {error}

                </p>




                <input

                    className="border p-2 w-full mb-3"

                    placeholder="Email"

                    value={email}

                    onChange={(e)=>setEmail(e.target.value)}

                />




                <input

                    className="border p-2 w-full mb-3"

                    placeholder="Password"

                    type="password"

                    value={password}

                    onChange={(e)=>setPassword(e.target.value)}

                />




                <button

                    className="bg-blue-600 text-white p-2 w-full"

                >

                    Login

                </button>



            </form>


        </div>


    );


}



export default Login;