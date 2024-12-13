import React from "react";
import { Layout } from "antd";
import SCSidebar from "@/pages/Dashboard/Sidebar/SCSidebar.jsx";
import SCFooter from "@/pages/Dashboard/Footer/SCFooter.jsx";
import SCHeader from "@/pages/Dashboard/Header/Header.jsx";
import { Outlet } from "react-router-dom";
import { Chart, registerables } from "chart.js";

Chart.register(...registerables);

const layoutStyle = {
  minHeight: "100vh",
};

const Dashboard = () => {
  return (
    <Layout style={layoutStyle}>
      <SCSidebar />
      <Layout>
        <SCHeader />
        <Outlet />
        <SCFooter />
      </Layout>
    </Layout>
  );
};

export default Dashboard;
