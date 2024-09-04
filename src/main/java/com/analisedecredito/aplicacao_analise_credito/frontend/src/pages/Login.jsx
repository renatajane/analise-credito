import React, { useState } from 'react';
import { useAuth } from '../contexto/AuthProvider';
import Styles from './Login.module.css';

function Login() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const { setToken } = useAuth();
  const { logOut } = useAuth();

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  const handleLogin = async (e) => {
    e.preventDefault();

    if (!username || !password) {
      setErrorMessage('Verifique os dados de login e senha e tente novamente.');
      return;
    }

    try {
      const token = await authenticateUser(username, password);
      setToken(token);
      window.location.href = '/';
    } catch (error) {
      setErrorMessage('Login falhou. Por favor, tente novamente.');
    }
  };

  const authenticateUser = async (username, password) => {
    const response = await fetch('http://localhost:8088/realms/analise-credito-realm/protocol/openid-connect/token', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: new URLSearchParams({
        client_id: 'analise-credito-cliente',
        grant_type: 'password',
        username: username,
        password: password,
        client_secret: 'iZTmplwA1mIE5bwQT3cu0Ky9jMhbVAbn',
      }),
    });

    if (!response.ok) {
      throw new Error('Failed to authenticate');
    }

    const data = await response.json();
    return data;
  };

  const handleCloseError = () => {
    setErrorMessage('');
  };

  return (
    <form onSubmit={handleLogin}>
      <div className={Styles.container}>
        {errorMessage && (
          <div className="br-message danger mt-4">
            <div className="icon">
              <i className="fas fa-times-circle fa-lg" aria-hidden="true"></i>
            </div>
            <div className="content" role="alert">
              <span className="message-title">Erro:</span>
              <span className="message-body"> {errorMessage}</span>
            </div>
            <div className="close">
              <button className="br-button circle small" type="button" aria-label="Fechar a mensagem de alerta" onClick={handleCloseError}>
                <i className="fas fa-times" aria-hidden="true"></i>
              </button>
            </div>
          </div>
        )}
        <div className="col-sm-6 col-lg-4 mb-3">
          <div className={`${Styles['input-button']} br-input`}>
            <label htmlFor="input-icon">Login</label>
            <div className="input-group">
              <div className="input-icon">
                <i className="fas fa-user-tie" aria-hidden="true"></i>
              </div>
              <input
                id="input-icon"
                type="text"
                placeholder="Digite o login de acesso"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                className="form-control"
              />
            </div>
          </div>
        </div>
        <div className="col-sm-5 col-lg-3 mb-3">
          <div className={`${Styles['input-button']} br-input`}>
            <label htmlFor="input-password">Senha</label>
            <div className="input-group">
              <input
                id="input-password"
                type={showPassword ? 'text' : 'password'}
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="form-control"
                placeholder="Digite sua senha"
              />
              <button
                type="button"
                className="br-button"
                aria-label="Exibir senha"
                role="switch"
                aria-checked={showPassword}
                onClick={togglePasswordVisibility}
              >
                <i className={`fas fa-eye${showPassword ? '-slash' : ''}`} aria-hidden="true"></i>
              </button>
            </div>
          </div>
        </div>
        <div className="p-3">
          <button type="submit" className="br-button primary mr-3">Entrar</button>
          <button type="button" className="br-button secondary mr-3" onClick={logOut}>Sair</button>
        </div>
      </div>
    </form>
  );
}

export default Login;
