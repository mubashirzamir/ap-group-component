import { Avatar } from "antd";
import React from "react";
import { useAuth } from "@/helpers/Auth/AuthProvider.jsx";
import { generateColor } from "@/helpers/utils.jsx";

const AvatarIcon = () => {
  const { user } = useAuth();

  const initial = user.email.charAt(0);

  return (
    <Avatar style={{ backgroundColor: generateColor(initial) }}>
      {initial}
    </Avatar>
  );
};

export default AvatarIcon;
