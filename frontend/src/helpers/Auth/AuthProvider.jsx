import { createContext, useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import AuthService from "@/services/AuthService.jsx";

const AuthContext = createContext({
  user: null,
  loginAction: () => {},
  logoutAction: () => {},
});

const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const navigate = useNavigate();

  const loginAction = (values) => {
    return AuthService.login(values).then((response) => {
      localStorage.setItem("token", response.token);
      setUser(response.user);
      navigate("/");
    });
  };

  const logoutAction = () => {
    localStorage.removeItem("token");
    setUser(null);
    navigate("/login");
  };

  return (
    <AuthContext.Provider value={{ user, loginAction, logoutAction }}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthProvider;

export const useAuth = () => {
  return useContext(AuthContext);
};
