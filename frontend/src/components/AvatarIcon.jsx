import { Avatar } from "antd";
import React from "react";

const generateColor = (initial) => {
  let letter = initial.toUpperCase();

  if (!/[A-Z]/.test(letter)) {
    return "#000000";
  }

  const charCode = letter.charCodeAt(0) - 65;
  const hue = (charCode * (360 / 26)) % 360;
  return `hsl(${hue}, 50%, 30%)`;
};

const AvatarIcon = ({ user }) => {
  const initial = user.name.charAt(0);

  return (
    <Avatar style={{ backgroundColor: generateColor(initial) }}>
      {initial}
    </Avatar>
  );
};

export default AvatarIcon;
