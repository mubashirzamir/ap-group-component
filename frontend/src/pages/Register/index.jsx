import React, { useState } from "react";
import { Button, Card, Form, Input } from "antd";
import { LockOutlined, MailOutlined } from "@ant-design/icons";
import InputIcon from "@/components/InputIcon/index.jsx";
import { Link } from "react-router-dom";
import AuthService from "@/services/AuthService.jsx";
import { genericNetworkError } from "@/helpers/utils.jsx";

const formItemLayout = {
  style: { minWidth: 300 },
  requiredMark: false,
  colon: false,
};

const Register = () => (
  <div className="flex items-center justify-center min-h-screen bg-gray-50">
    <Card title="Register" bordered={false}>
      <RegisterForm />
    </Card>
  </div>
);

const RegisterForm = () => {
  const [loading, setLoading] = useState(false);

  const onFinish = (values) => {
    setLoading(true);
    AuthService.register(values)
      .then((response) => {
        console.log(response);
      })
      .catch(genericNetworkError)
      .finally(() => setLoading(false));
  };

  return (
    <>
      <Form {...formItemLayout} onFinish={onFinish} autoComplete="off">
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
          hasFeedback
          rules={[
            {
              required: true,
              message: "Password is required.",
            },
            {
              min: 8,
              message: "Password must be at least 8 characters.",
            },
          ]}
        >
          <Input.Password
            prefix={<InputIcon Icon={LockOutlined} />}
            placeholder="Password"
          />
        </Form.Item>

        <Form.Item
          name="confirm"
          hasFeedback
          dependencies={["password"]}
          rules={[
            {
              required: true,
              message: "Please confirm your password.",
            },
            ({ getFieldValue }) => ({
              validator(_, value) {
                if (!value || getFieldValue("password") === value) {
                  return Promise.resolve();
                }
                return Promise.reject(new Error("Passwords do not match."));
              },
            }),
          ]}
        >
          <Input.Password
            prefix={<InputIcon Icon={LockOutlined} />}
            placeholder="Confirm Password"
          />
        </Form.Item>

        <Form.Item>
          <Button loading={loading} block type="primary" htmlType="submit">
            Register
          </Button>
        </Form.Item>
      </Form>
      <Button disabled={loading} block type="link">
        <Link to="/login">Login</Link>
      </Button>
    </>
  );
};

export default Register;
