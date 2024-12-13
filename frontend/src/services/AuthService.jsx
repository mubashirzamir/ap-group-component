import request from "@/request.js";

const baseUrl = import.meta.env.VITE_AUTH_API_BASE_URL + "/auth";

const register = ({ email, password, confirm }) => {
  return request.post(`${baseUrl}/register`, { email, password, confirm });
};

const login = ({ email, password }) => {
  return request.post(`${baseUrl}/login`, { email, password });
};

const test = () => {
  return request.get(`${baseUrl}/test`);
};

export default { register, login, test };
