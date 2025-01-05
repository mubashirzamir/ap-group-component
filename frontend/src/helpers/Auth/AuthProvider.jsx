import { createContext, useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import AuthService from "@/services/AuthService.jsx";
import { genericNetworkError } from "@/helpers/utils.jsx";
import { Modal } from "antd";

const AuthContext = createContext({
  user: null,
  loading: true,
  loginAction: () => {},
  logoutAction: () => {},
  deleteAccountAction: () => {},
});

const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const savedUser = localStorage.getItem("user");
    if (savedUser) {
      setUser(JSON.parse(savedUser));
    }
    setLoading(false);
  }, []);

  const loginAction = (values) => {
    return AuthService.login(values).then((response) => {
      localStorage.setItem("token", response.token);
      localStorage.setItem("user", JSON.stringify(response.user));
      setUser(response.user);
      navigate("/");
    });
  };

  const logoutAction = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    setUser(null);
    navigate("/login");
  };

  const deleteAccountAction = () => {
    Modal.confirm({
      width: 500,
      title: "Are you sure you want to delete your account?",
      content: "This action cannot be undone.",
      onOk: () =>
        AuthService.deleteAccount(user.email)
          .then(logoutAction)
          .catch(genericNetworkError),
    });
  };

  return (
    <AuthContext.Provider
      value={{ user, loading, loginAction, logoutAction, deleteAccountAction }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export default AuthProvider;

export const useAuth = () => {
  return useContext(AuthContext);
};
