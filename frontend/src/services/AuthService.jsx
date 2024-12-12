import request from "@/request.js";

const baseUrl = "/auth";

const register = ({ email, password }) => {
  return request.post(`${baseUrl}/register`, { email, password });
};

const login = (email, password) => {
  return request.post(`${baseUrl}/login`, { email, password });
};

export default { register, login };
