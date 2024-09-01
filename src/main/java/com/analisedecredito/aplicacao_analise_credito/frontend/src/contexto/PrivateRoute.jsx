import { Outlet, Navigate } from 'react-router-dom';
import { useAuth } from './AuthProvider';

const PrivateRoute = () => {
    const { token } = useAuth();
    if (!token) {
      console.log("Usuário não autenticado, redirecionando para login.");
      return <Navigate to="/login" replace />;
    }
  
    return <Outlet />;
};

export default PrivateRoute;