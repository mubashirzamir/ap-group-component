import React, { useState } from "react";
import { Layout, Menu } from "antd";
import { makeItems } from "./SCSidebarItems.jsx";
import { useLocation } from "react-router-dom";

const { Sider } = Layout;

const SCSidebar = () => {
  const [collapsed, setCollapsed] = useState(false);
  const location = useLocation();

  return (
    <Sider
      collapsible
      collapsed={collapsed}
      onCollapse={(value) => setCollapsed(value)}
    >
      <Menu
        theme="dark"
        mode="inline"
        defaultSelectedKeys={[location.pathname]}
        items={makeItems()}
      />
    </Sider>
  );
};

export default SCSidebar;
