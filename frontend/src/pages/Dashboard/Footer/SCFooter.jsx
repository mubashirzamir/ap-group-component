import React from "react";
import { Layout } from "antd";

const { Footer} = Layout;

const footerStyle = {
  textAlign: "center",
};

const SCFooter = () => {
  return (
    <Footer style={footerStyle}>
      Northumbria University © {new Date().getFullYear()}
    </Footer>
  );
};

export default SCFooter;
