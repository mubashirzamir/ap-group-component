import { Breadcrumb, Layout, theme } from "antd";
import React from "react";

const { Content } = Layout;

const contentStyle = {
  margin: "0 16px",
};

const breadcrumbStyle = {
  margin: "16px 0",
};

const divStyle = {
  padding: 24,
  minHeight: "75vh",
};

const DashboardPage = ({ children, breadcrumbs }) => {
  const {
    token: { colorBgContainer, borderRadiusLG },
  } = theme.useToken();

  return (
    <Content style={contentStyle}>
      <Breadcrumb separator="|" style={breadcrumbStyle} items={breadcrumbs} />
      <div
        style={{
          ...divStyle,
          background: colorBgContainer,
          borderRadius: borderRadiusLG,
        }}
      >
        {children}
      </div>
    </Content>
  );
};

export default DashboardPage;
