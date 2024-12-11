import axios from "axios";

const request = axios.create({
  baseURL: import.meta.env.REACT_APP_API_BASE_URL || "",
  timeout: 60000,
  headers: {
    "Content-Type": "application/json",
  },
});

export default request;
