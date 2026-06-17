import { BrowserRouter, Routes, Route } from "react-router-dom";


import Login from "./pages/Login";

import Register from "./pages/Register";

import Dashboard from "./pages/Dashboard";

import Tickets from "./pages/Tickets";

import Chat from "./pages/Chat";

import Agent from "./pages/Agent";


import ProtectedRoute from "./routes/ProtectedRoute";






function App(){



    return(



        <BrowserRouter>



            <Routes>






                <Route

                    path="/"

                    element={<Login />}

                />







                <Route

                    path="/login"

                    element={<Login />}

                />








                <Route

                    path="/register"

                    element={<Register />}

                />









                <Route

                    path="/dashboard"

                    element={


                        <ProtectedRoute>


                            <Dashboard />


                        </ProtectedRoute>


                    }

                />










                <Route

                    path="/tickets"

                    element={


                        <ProtectedRoute>


                            <Tickets />


                        </ProtectedRoute>


                    }

                />










                <Route

                    path="/chat"

                    element={



                        <ProtectedRoute>


                            <Chat />


                        </ProtectedRoute>



                    }

                />











                <Route

                    path="/agent"

                    element={


                        <ProtectedRoute>


                            <Agent />


                        </ProtectedRoute>


                    }

                />






            </Routes>




        </BrowserRouter>



    );


}




export default App;