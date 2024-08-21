import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Header from "./components/Header/Header";
import Footer from "./components/Footer/Footer";
import Home from "./pages/Home";
import ClientList from './components/ClienteList';
import Relatorios from "./pages/Relatorios"

function App() {
  return (
    <BrowserRouter>
      <Header />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/clientes" element={<ClientList />} />
        <Route path="/relatorios" element={<Relatorios />} />
      </Routes>
      <Footer />
    </BrowserRouter>
  );
}

export default App;
