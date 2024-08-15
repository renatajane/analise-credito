import Imagem from "../assets/imagens/fototeste.png"; // Adicione a imagem na pasta assets
import { Link } from "react-router-dom";

const Footer = () => {
  return (
    <footer className="br-footer">
      <div className="container-lg">
        <div className="logo">
          <a href="https://www.gov.br/pt-br" style={{ cursor: 'pointer' }}>
            <img src={Imagem} alt="Logo do GovBR" />
          </a>
        </div>
        <div className="br-list horizontal" data-toggle="data-toggle" data-sub="data-sub">
          {[
            {
              categoria: "Navegação",
              links: [
                { label: "Home", path: "/" },
                { label: "Listar Alíquotas", path: "/aliquotas" },
                { label: "Criar Alíquotas", path: "/aliquotas/criar" },
                { label: "Deletar Alíquotas", path: "/aliquotas/deletar" },
                { label: "Consultar Contribuintes", path: "/contribuintes/consultar" },
              ],
            },
            {
              categoria: "Serviços",
              links: [
                { label: "Gestão de Contribuintes", path: "/login" },
                { label: "Gestão de Benefícios", path: "/beneficios" },
                { label: "Gestão de Empréstimos", path: "/emprestimos" },
              ],
            },
          ].map((item, index) => (
            <div className="col-2" key={index}>
              <div className="br-item header">
                <div className="content text-down-01 text-bold text-uppercase">
                  {item.categoria}
                </div>
              </div>
              <div className="br-list">
                {item.links.map((link, linkIndex) => (
                  <div className="br-item" key={linkIndex}>
                    <Link className="content" to={link.path}>
                      {link.label}
                    </Link>
                  </div>
                ))}
              </div>
            </div>
          ))}
        </div>
        <div className="d-none d-sm-block">
          <div className="row align-items-end justify-content-between py-5">
            <div className="col">
              <div className="social-network">
                <div className="social-network-title">Redes Sociais</div>
                <div className="d-flex">
                  {/* Add social network buttons here */}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className="container-lg">
        <div className="info">
          <div className="text-down-01 text-medium pb-3">
            Texto destinado a exibição de informações relacionadas à&nbsp;<strong>licença de uso.</strong>
          </div>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
