import { Navigate } from "react-router-dom";
import { useAuth } from "../../features/auth/context/AuthContext";

export const ProtectedRoute = ({ children }) => {
  const { user, loading } = useAuth();

  if (loading) return null;

  return user ? children : <Navigate to="/login" />;
};
