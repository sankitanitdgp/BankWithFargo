

// function App() {
//   return (
//     <div className="App">
//       <Login/>
//     </div>
//   );
// }

// export default App;

import React from 'react';
<<<<<<< Updated upstream
import "bootstrap/dist/css/bootstrap.min.css"
import "./App.css"
import { BrowserRouter, Routes, Route } from "react-router-dom"
import Auth from './components/Auth';


function App() {
  return (
    <BrowserRouter>
      <Routes>
        
        <Route path="/" element={<Auth />} />
       
      </Routes>
    </BrowserRouter>
  )
=======
import './App.css';
import Auth from './components/Auth';
import "bootstrap/dist/css/bootstrap.min.css"

function App() {
  return (
    <div className="App">
      <Auth/>
    </div>
  );
>>>>>>> Stashed changes
}

export default App