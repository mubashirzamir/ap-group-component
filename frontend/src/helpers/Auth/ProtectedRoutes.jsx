import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "@/helpers/Auth/AuthProvider.jsx";
import { Spin } from "antd";
import { LoadingOutlined } from "@ant-design/icons";

const ProtectedRoutes = () => {
  const { user, loading } = useAuth();

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
