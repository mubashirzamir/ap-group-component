import React from "react";
import { Layout, theme } from "antd";

const { Header } = Layout;

const headerStyle = {
  padding: 0,
};

const SCHeader = () => {
  const {
    token: { colorBgContainer },
  } = theme.useToken();

  return (
    <Header
      style={{
        ...headerStyle,
        background: colorBgContainer,
      }}
    />
  );
};

export default SCHeader;
