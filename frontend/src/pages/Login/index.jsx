import React, { useState } from "react";
import { Button, Card, Form, Input } from "antd";
import { LockOutlined, MailOutlined } from "@ant-design/icons";
import InputIcon from "@/components/InputIcon/index.jsx";
import { Link } from "react-router-dom";
import request from "@/request.js";
import { genericNetworkError } from "@/helpers/utils.jsx";

const formItemLayout = {
  style: { maxWidth: 600 },
  requiredMark: false,
  colon: false,
};

const Login = () => (
  <div className="flex items-center justify-center min-h-screen bg-gray-50">
    <Card title="Login" bordered={false}>
      <LoginForm />
    </Card>
  </div>
);

const LoginForm = () => {
  const [loading, setLoading] = useState(false);

  const onFinish = (values) => {
    setLoading(true);
    setTimeout(() => {
      request
        .post("/login", values)
        .then((response) => {
          console.log(response);
        })
        .catch(genericNetworkError)
        .finally(() => {
          setLoading(false);
        });
    }, 3000);
  };

  return (
    <>
      <Form {...formItemLayout} onFinish={onFinish} autoComplete="off">
        <Form.Item
          name="email"
          rules={[
            {
              required: true,
              message: "Please input your email.",
            },
          ]}
        >
          <Input
            prefix={<InputIcon Icon={MailOutlined} />}
            placeholder="Email"
          />
        </Form.Item>

        <Form.Item
          name="password"
          rules={[
            {
              required: true,
              message: "Please input your password.",
            },
          ]}
        >
          <Input.Password
            prefix={<InputIcon Icon={LockOutlined} />}
            placeholder="Password"
          />
        </Form.Item>

        <Form.Item>
          <Button loading={loading} block type="primary" htmlType="submit">
            Login
          </Button>
        </Form.Item>
      </Form>
      <Button disabled={loading} block type="link">
        <Link to="/register">Register</Link>
      </Button>
    </>
  );
};

export default Login;
