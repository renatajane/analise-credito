import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Header from "./components/Header/Header";
import Footer from "./components/Footer/Footer";
import Home from "./pages/Home";
import CadastroCliente from './pages/CadastroCliente';
import BuscarRequisicao from './pages/BuscarRequisicao'
import ListarRequisicoes from './pages/ListarRequisicoes';
import StatusRequisicao from './pages/StatusRequisicao';
import CadastroRequisicao from './pages/CadastroRequisicao';
import Login from './pages/Login';
import AuthProvider from './contexto/AuthProvider';
import PrivateRoute from './contexto/PrivateRoute';

function App() {
  return (
    <BrowserRouter>
      <Header />
      <AuthProvider>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route element={<PrivateRoute />}>
            <Route path="/cadastroCliente" element={<CadastroCliente />} />
            <Route path="/listarRequisicoes" element={<ListarRequisicoes />} />
            <Route path="/buscarRequisicao" element={<BuscarRequisicao />} />
            <Route path="/statusRequisicao" element={<StatusRequisicao />} />
            <Route path="/cadastroRequisicao" element={<CadastroRequisicao />} />
          </Route>
        </Routes>
      </AuthProvider>
      <Footer />
    </BrowserRouter>
  );
}

export default App;
