import React from "react";
import { Avatar, Dropdown } from "antd";
import {
  DownCircleFilled,
  LogoutOutlined,
  UserOutlined,
} from "@ant-design/icons";

const ProfileDropdown = () => {
  const items = [
    {
      key: "name",
      label: "Mubashir Zamir",
      icon: <UserOutlined />,
    },
    {
      key: "logout",
      label: "Logout",
      icon: <LogoutOutlined />,
    },
  ];

  return (
    <div>
      <Dropdown menu={{ items }} trigger={["click"]}>
        <a onClick={(e) => e.preventDefault()}>
          <Avatar size="large" icon={<UserOutlined />} />
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
