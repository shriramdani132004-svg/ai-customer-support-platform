import { useState } from "react";

import { useNavigate } from "react-router-dom";

import { registerUser } from "../services/authService";



function Register(){



    const navigate = useNavigate();



    const [name,setName] = useState("");

    const [email,setEmail] = useState("");

    const [password,setPassword] = useState("");

    const [message,setMessage] = useState("");




    const handleRegister = async(e)=>{


        e.preventDefault();



        try{


            await registerUser({


                name:name,


                email:email,


                password:password


            });




            navigate("/login");



        }
        catch(error){



            setMessage(

                "Registration failed"

            );



        }



    };






    return(



        <div className="h-screen flex justify-center items-center bg-gray-900">



            <form


                onSubmit={handleRegister}


                className="bg-white p-8 rounded w-96"


            >



                <h1 className="text-3xl font-bold mb-5">


                    Register


                </h1>




                <p className="text-red-500">


                    {message}


                </p>





                <input


                    className="border p-2 w-full mb-3"


                    placeholder="Name"


                    value={name}


                    onChange={(e)=>setName(e.target.value)}


                />





                <input


                    className="border p-2 w-full mb-3"


                    placeholder="Email"


                    value={email}


                    onChange={(e)=>setEmail(e.target.value)}


                />





                <input


                    className="border p-2 w-full mb-3"


                    type="password"


                    placeholder="Password"


                    value={password}


                    onChange={(e)=>setPassword(e.target.value)}


                />





                <button


                    className="bg-green-600 text-white p-2 w-full"


                >


                    Register


                </button>



            </form>



        </div>



    );



}



export default Register;