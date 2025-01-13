import axios from "axios";

const request = axios.create({
  timeout: 60000,
  headers: {
    "Content-Type": "application/json",
    "ngrok-skip-browser-warning": true,
  },
});

request.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");

    if (token) {
      config.headers["Authorization"] = `Bearer ${token}`;
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  },
);

request.interceptors.response.use(
  (response) => {
    /**
     * Unfortunately, the Newcastle API does not return a proper error code when the service is unavailable.
     * Hence, we need to check the response data.
     */
    if (response.data === "Service is currently unavailable") {
      throw new Error("Service is currently unavailable");
    }

    return response.data;
  },
  (error) => {
    return Promise.reject(error);
  },
);

export default request;
