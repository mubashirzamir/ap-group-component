import React from "react";
import { Layout, theme } from "antd";
import Profile from "@/pages/Dashboard/Header/Profile/index.jsx";

const { Header } = Layout;

const headerStyle = {
  padding: 0,
  display: "flex",
  alignItems: "center",
  justifyContent: "end",
};

const SCHeader = () => {
  return (
    <Header
      style={{
        ...headerStyle,
        background: "transparent",
      }}
    >
      <div className="flex justify-end p-4">
        <ul className="flex space-x-6">
          <li>
            <Profile />
          </li>
        </ul>
      </div>
    </Header>
  );
};

export default SCHeader;
