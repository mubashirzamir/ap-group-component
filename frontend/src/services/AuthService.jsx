import request from "@/request.js";

const baseUrl = "/auth";

const register = ({ email, password, confirm }) => {
  return request.post(`${baseUrl}/register`, { email, password, confirm });
};

const login = ({ email, password }) => {
  return request.post(`${baseUrl}/login`, { email, password }, { withCredentials: true });
};

const test = () => {
  return request.get(`${baseUrl}/test`, { withCredentials: true });
};

export default { register, login, test };
