import React from "react";
import { Navigate, Outlet, useLocation } from "react-router-dom";
import { useAuth } from "@/helpers/Auth/AuthProvider.jsx";

const redirectToHome = ["/login", "/register", "/forgot-password"];

const AuthRoutes = () => {
  const { user } = useAuth();
  const location = useLocation();

  if (user && redirectToHome.includes(location.pathname)) {
    return <Navigate to="/" />;
  }

  return <Outlet />;
};

export default AuthRoutes;
