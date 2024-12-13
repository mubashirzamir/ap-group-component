import { createContext, useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import AuthService from "@/services/AuthService.jsx";

const AuthContext = createContext();

const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [token, setToken] = useState(localStorage.getItem("site") || "");
  const navigate = useNavigate();

  const loginAction = (values) => {
    return AuthService.login(values).then((response) => {
      setUser(response);
      navigate("/");
    });
  };

  const logOutAction = () => {
    setUser(null);
    setToken("");
    localStorage.removeItem("site");
    navigate("/login");
  };

  return (
    <AuthContext.Provider value={{ token, user, loginAction, logOutAction }}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthProvider;

export const useAuth = () => {
  return useContext(AuthContext);
};
