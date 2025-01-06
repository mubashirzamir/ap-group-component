import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "@/helpers/Auth/AuthProvider.jsx";
import { Spin } from "antd";
import { LoadingOutlined } from "@ant-design/icons";
import { useEffect } from "react";
import AuthService from "@/services/AuthService.jsx";
import { genericNetworkError } from "@/helpers/utils.jsx";

const ProtectedRoutes = () => {
  const { user, loading } = useAuth();

  // Checks if the user is authenticated
  useEffect(() => {
    AuthService.test().catch(genericNetworkError);
  }, []);

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-screen bg-gray-50">
        <Spin size="large" indicator={<LoadingOutlined spin />} />
      </div>
    );
  }

  if (!user) {
    return <Navigate to="/login" />;
  }

  return <Outlet />;
};

export default ProtectedRoutes;
