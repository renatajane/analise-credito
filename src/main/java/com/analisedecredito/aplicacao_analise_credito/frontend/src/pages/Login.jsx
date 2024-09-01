import React, { useState } from 'react';
import { useAuth } from '../contexto/AuthProvider';

function Login() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const { setToken } = useAuth();
  const { logOut } = useAuth();

  const handleLogin = async (e) => {
    e.preventDefault();

    if (!username || !password) {
      setErrorMessage('Please enter both username and password.');
      return;
    }

    try {
        const token = await authenticateUser(username, password); 
        setToken(token);      
    //   localStorage.setItem('JwtToken', token); // Armazena o token no localStorage
       window.location.href = '/'; // Redireciona para a página inicial
    } catch (error) {
        setErrorMessage('Login failed. Please try again.');
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
        client_secret: 'gr0iusw37ZTXvFXgC4Rik1vU88zelmeJ',
      }),
    });

    if (!response.ok) {
      throw new Error('Failed to authenticate');
    }

    const data = await response.json();
    return data;
  };

  return (
    <div>
      <h2>Login</h2>
      {errorMessage && <div className="alert alert-danger">{errorMessage}</div>}
      <form onSubmit={handleLogin}>
        <div className="form-group">
          <label>Username</label>
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            className="form-control"
          />
        </div>
        <div className="form-group">
          <label>Password</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="form-control"
          />
        </div>
        <button type="submit" className="btn btn-primary">Login</button>
        <button color="inherit" onClick={logOut}>LogOut</button>
      </form>
    </div>
  );
}

export default Login;
