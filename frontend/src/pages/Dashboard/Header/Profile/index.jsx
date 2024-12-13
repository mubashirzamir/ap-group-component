import React from "react";
import { Dropdown } from "antd";
import {
  DownCircleFilled,
  LogoutOutlined, MailOutlined,
  UserOutlined
} from '@ant-design/icons'
import AvatarIcon from "@/components/AvatarIcon.jsx";
import { useAuth } from "@/helpers/Auth/AuthProvider.jsx";

const ProfileDropdown = () => {
  const { user, logoutAction } = useAuth();

  const items = [
    {
      key: "email",
      label: user.email,
      icon: <MailOutlined />,
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
            style={{ color: "#f3f3f3" }}
            className="transform -translate-x-3 translate-y-3"
          />
        </a>
      </Dropdown>
    </div>
  );
};

export default ProfileDropdown;
