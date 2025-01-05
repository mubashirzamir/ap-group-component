import React from "react";
import { Dropdown } from "antd";
import {
  DownCircleFilled,
  LockOutlined,
  LogoutOutlined,
  MailOutlined,
  UserDeleteOutlined,
} from "@ant-design/icons";
import AvatarIcon from "@/components/AvatarIcon/AvatarIcon.jsx";
import { useAuth } from "@/helpers/Auth/AuthProvider.jsx";
import { useNavigate } from "react-router-dom";

const downCircleStyle = {
  color: "#f3f3f3",
};

const ProfileDropdown = () => {
  const navigate = useNavigate();
  const { user, logoutAction, deleteAccountAction } = useAuth();

  const items = [
    {
      key: "email",
      label: user.email,
      icon: <MailOutlined />,
    },
    {
      key: "change-password",
      label: "Change Password",
      icon: <LockOutlined />,
      onClick: () => navigate("/profile/change-password"),
    },
    {
      key: "delete-account",
      label: "Delete Account",
      icon: <UserDeleteOutlined />,
      onClick: deleteAccountAction,
    },
    {
      key: "logout",
      label: "Logout",
      icon: <LogoutOutlined />,
      onClick: logoutAction,
    },
  ];

  return (
    <div>
      <Dropdown menu={{ items }} trigger={["click"]}>
        <a onClick={(e) => e.preventDefault()}>
          <AvatarIcon />
          <DownCircleFilled
            style={downCircleStyle}
            className="transform -translate-x-3 translate-y-3"
          />
        </a>
      </Dropdown>
    </div>
  );
};

export default ProfileDropdown;
