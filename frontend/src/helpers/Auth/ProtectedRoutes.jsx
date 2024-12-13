import React from "react";
import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "@/helpers/Auth/AuthProvider.jsx";

const ProtectedRoutes = () => {
  const { user } = useAuth();

  if (!user) {
    return <Navigate to="/login" />;
  }

  return <Outlet />;
};

export default ProtectedRoutes;
