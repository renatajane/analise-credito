import React, {
    createContext,
    useContext,
    useEffect,
    useState,
    ReactNode,
  } from "react";
  import { useNavigate } from "react-router-dom";
  import jwtDecode from "jwt-decode";
  
  const AuthContext = createContext({
    token: null,
    cpf: null,
    nome: null,
    setToken: () => {},
    logOut: () => {},
  });
  
  export const useAuth = () => useContext(AuthContext);
  
  export default function AuthProvider({ children }) {
    const token_key = "__token";
    const cpf_key = "__cpf";
    const nome_key = "__nome";
    const navigate = useNavigate();   
  
    const [cpfLogado, setcpfLogado] = useState(() => {
      const storedCpf = localStorage.getItem(cpf_key);
      return storedCpf ? storedCpf : null;
    });

    const [nomeLogado, setnomeLogado] = useState(() => {
      const storedNome = localStorage.getItem(nome_key);
      return storedNome ? storedNome : null;
    });
  
    const [token, setToken] = useState(() => {
      const storedToken = localStorage.getItem(token_key);
      return storedToken ? storedToken : null;
    });
  
    useEffect(() => {
      if (token) {
        try {
          const decodedToken = jwtDecode(token);
          setcpfLogado(decodedToken.cpf);
          setnomeLogado(decodedToken.nome);
          // Agendar logout para quando o token expirar
          scheduleTokenExpiry(decodedToken.exp);
  
        } catch (error) {
          console.error("Error decoding token:", error);
          handleLogOut();
        }
      }
    }, [token]);
  
    const handleSetToken = (newToken) => {
      const tokenValue = newToken.access_token;
      setToken(tokenValue);
      if (newToken) {
        localStorage.setItem(token_key, tokenValue);
      } else {
        localStorage.removeItem(token_key);
      }
  
      const decodedToken = jwtDecode(tokenValue);
      setcpfLogado(decodedToken.cpf);
      setnomeLogado(decodedToken.nome);
      if (decodedToken.cpf) {
        localStorage.setItem(cpf_key, decodedToken.cpf);
      } else {
        localStorage.removeItem(cpf_key);
      }
      if (decodedToken.nome) {
        localStorage.setItem(nome_key, decodedToken.nome);
      } else {
        localStorage.removeItem(nome_key);
      }
    };
  
     // Função para realizar o logout automaticamente quando o token expirar
     const scheduleTokenExpiry = (expiresAt) => {
      const currentTime = Date.now();
      const timeUntilExpiry = expiresAt * 1000 - currentTime;
  
      if (timeUntilExpiry > 0) {
        setTimeout(() => {        
          handleLogOut();
        }, timeUntilExpiry);
      } else {
        handleLogOut();
      }
    };
  
    const handleLogOut = () => {
      setToken(null);
      setcpfLogado(null); // Clear CPF on logout
      setnomeLogado(null);
      localStorage.removeItem(token_key);
      navigate("/");
    };
  
    return (
      <AuthContext.Provider
        value={{ token, cpfLogado, nomeLogado, setToken: handleSetToken, logOut: handleLogOut }}
      >
        {children}
      </AuthContext.Provider>
    );
  }
  