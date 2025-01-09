import request from "@/request.js";

const baseUrl = import.meta.env.VITE_AUTH_API_BASE_URL + "/users";

const deleteUser = (email) => {
  return request.delete(`${baseUrl}/${email}`);
};

export default { deleteUser };
