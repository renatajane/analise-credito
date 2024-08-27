import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Header from "./components/Header/Header";
import Footer from "./components/Footer/Footer";
import Home from "./pages/Home";
import CadastroCliente from './pages/CadastroCliente';
import BuscarRequisicao from './pages/BuscarRequisicao'
import ListarRequisicoes from './pages/ListarRequisicoes';
import StatusRequisicao from './pages/StatusRequisicao';

function App() {
  return (
    <BrowserRouter>
      <Header />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/cadastroCliente" element={<CadastroCliente />} />
        <Route path="/listarRequisicoes" element={<ListarRequisicoes/>}/>
        <Route path="/buscarRequisicao" element={<BuscarRequisicao />} />
        <Route path="/statusRequisicao" element={<StatusRequisicao/>} />
      </Routes>
      <Footer />
    </BrowserRouter>
  );
}

export default App;
