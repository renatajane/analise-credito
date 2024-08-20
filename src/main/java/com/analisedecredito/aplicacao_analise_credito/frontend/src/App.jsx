import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import '@govbr-ds/core/dist/core.min.js';
import Header from "./components/Header/Header"
import Footer from "./components/Footer/Footer"

function App() {
  // const [count, setCount] = useState(0)

  return (
    <>
      <Header />
      <Footer/>
    </>
  )
}

export default App
