import api from "../../../shared/api/axios";

export const login = (username, password) =>
  api.post("/auth/login", {
    username,
    password,
  });

export const logout = (refreshToken) =>
  api.post("/auth/logout", { refreshToken });

export const signup = (username, email, password) => {
  api.post("/users/signup", { username, email, password });
};

export const getMe = () => api.get("/users/me");
