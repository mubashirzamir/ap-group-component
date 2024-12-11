import { message } from "antd";

export const genericNetworkError = (error) => {
  console.error(error);
  message.error("Network error");
};
