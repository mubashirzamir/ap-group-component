import { useState } from "react";
import { Button, Card, Form, Input, message, Modal } from "antd";
import { LockOutlined } from "@ant-design/icons";
import InputIcon from "@/components/InputIcon/index.jsx";
import { useNavigate } from "react-router-dom";
import AuthService from "@/services/AuthService.jsx";
import { genericNetworkError, validationError } from "@/helpers/utils.jsx";
import BackButton from "@/components/BackButton/index.jsx";
import { useAuth } from "@/helpers/Auth/AuthProvider.jsx";

const formItemLayout = {
  style: { minWidth: 300 },
  requiredMark: false,
  colon: false,
};

const ChangePassword = () => (
  <div className="flex items-center justify-center min-h-screen bg-gray-50">
    <Card title="Change Password" bordered={false}>
      <ChangePasswordForm />
    </Card>
  </div>
);

const ChangePasswordForm = () => {
  const navigate = useNavigate();
  const { logoutAction } = useAuth();
  const [loading, setLoading] = useState(false);
  const [form] = Form.useForm();

  const onFinish = (values) => {
    setLoading(true);
    AuthService.changePassword(values)
      .then((response) =>
        message.success(response.message + " Redirecting to login.", 0.5),
      )
      .then(logoutAction)
      .catch((error) => validationError(error, form))
      .catch(genericNetworkError)
      .finally(() => setLoading(false));
  };

  const onClick = () => {
    if (form.getFieldsError().some(({ errors }) => errors.length)) {
      return;
    }

    Modal.confirm({
      width: 500,
      title: "Are you sure you want to change your password?",
      content: "You will be logged out after changing your password.",
      onOk: () => form.submit(),
    });
  };

  return (
    <>
      <Form {...formItemLayout} form={form} onFinish={onFinish}>
        <Form.Item
          name="currentPassword"
          rules={[
            {
              required: true,
              message: "Current password is required.",
            },
          ]}
        >
          <Input.Password
            prefix={<InputIcon Icon={LockOutlined} />}
            placeholder="Current password"
          />
        </Form.Item>
        <Form.Item
          name="newPassword"
          hasFeedback
          rules={[
            {
              required: true,
              message: "New password is required.",
            },
            {
              min: 8,
              message: "New password must be at least 8 characters.",
            },
          ]}
        >
          <Input.Password
            prefix={<InputIcon Icon={LockOutlined} />}
            placeholder="New password"
          />
        </Form.Item>

        <Form.Item
          name="confirm"
          hasFeedback
          dependencies={["newPassword"]}
          rules={[
            {
              required: true,
              message: "Please confirm your new password.",
            },
            ({ getFieldValue }) => ({
              validator(_, value) {
                if (!value || getFieldValue("newPassword") === value) {
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
          <Button onClick={onClick} loading={loading} block type="primary">
            Submit
          </Button>
        </Form.Item>
      </Form>
      <BackButton />
    </>
  );
};

export default ChangePassword;
