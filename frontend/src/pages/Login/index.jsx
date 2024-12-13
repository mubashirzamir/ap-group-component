import React, { useState } from "react";
import { Button, Card, Form, Input } from "antd";
import { LockOutlined, MailOutlined } from "@ant-design/icons";
import InputIcon from "@/components/InputIcon/index.jsx";
import { Link } from "react-router-dom";
import { genericNetworkError, validationError } from "@/helpers/utils.jsx";
import { useAuth } from "@/helpers/Auth/AuthProvider.jsx";

const formItemLayout = {
  style: { minWidth: 300 },
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
  const { loginAction } = useAuth();
  const [loading, setLoading] = useState(false);
  const [form] = Form.useForm();

  const onFinish = (values) => {
    setLoading(true);
    loginAction(values)
      .catch((error) => validationError(error, form))
      .catch(genericNetworkError)
      .finally(() => setLoading(false));
  };

  return (
    <>
      <Form {...formItemLayout} form={form} onFinish={onFinish}>
        <Form.Item
          name="email"
          rules={[
            {
              required: true,
              type: "email",
              message: "A valid email is required.",
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
              message: "Password is required.",
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
