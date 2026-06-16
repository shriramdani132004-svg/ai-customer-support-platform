import { BrowserRouter, Routes, Route } from "react-router-dom";


import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import Tickets from "./pages/Tickets";
import Chat from "./pages/Chat";


function App() {


    return (


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
                    element={<Dashboard />} 
                />


                <Route 
                    path="/tickets" 
                    element={<Tickets />} 
                />


                <Route 
                    path="/chat" 
                    element={<Chat />} 
                />


            </Routes>


        </BrowserRouter>


    );


}


export default App;