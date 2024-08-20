import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import '@govbr-ds/core/dist/core.min.js';
import Header from "./components/Header/Header";
import Footer from "./components/Footer/Footer";
import Home from "./pages/Home";


function App() {
  // const [count, setCount] = useState(0)

  return (
    // <>
    //   <Header />
    //   <Footer/>
    // </>
    <div>
      <Header/>
      <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home/>}/>

      </Routes>
      </BrowserRouter>
      <Footer/>
    </div>
  )
}

export default App
