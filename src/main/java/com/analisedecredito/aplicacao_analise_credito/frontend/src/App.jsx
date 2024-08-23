import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Header from "./components/Header/Header";
import Footer from "./components/Footer/Footer";
import Home from "./pages/Home";
import ClientList from './components/ClienteList';
import BuscarRequisicao from './pages/BuscarRequisicao'
import ListarRequisicoes from './pages/ListarRequisicoes';

function App() {
  return (
    <BrowserRouter>
      <Header />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/clientes" element={<ClientList />} />
        <Route path="/listarRequisicoes" element={<ListarRequisicoes/>}/>
        <Route path="/buscarRequisicao" element={<BuscarRequisicao />} />
      </Routes>
      <Footer />
    </BrowserRouter>
  );
}

export default App;
