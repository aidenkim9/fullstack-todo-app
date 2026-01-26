import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080",
  withCredentials: true,
  headers: {
    "Content-Type": "application/json",
  },
});

let accessToken = null;

export const setAccessToken = (token) => {
  accessToken = token;
};

api.interceptors.request.use(
  (config) => {
    if (accessToken) {
      config.headers.Authorization = `Bearer ${accessToken}`;
    }

    return config;
  },
  (error) => Promise.reject(error),
);

api.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;
    const isAuthApi =
      originalRequest.url.includes("/auth/login") ||
      originalRequest.url.includes("/auth/signup") ||
      originalRequest.url.includes("/auth/refresh");

    if (
      error.response?.status === 401 &&
      !originalRequest._retry &&
      !isAuthApi
    ) {
      try {
        originalRequest._retry = true;

        const res = await axios.post(
          "http://localhost:8080/auth/refresh",
          {},
          { withCredentials: true },
        );

        localStorage.setItem("accessToken", res.data.accessToken);

        originalRequest.headers.Authorization = `Bearer ${res.data.accessToken}`;

        return api(originalRequest);
      } catch (error) {
        localStorage.clear();
        return Promise.reject(error);
      }
    }

    return Promise.reject(error);
  },
);

export default api;
