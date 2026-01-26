import api from "../../../shared/api/axios";

export const getTodo = async () => {
  const res = await api.get("/todos");
  return res.data;
};

export const createTodo = (title) => api.post("/todos", { title });

export const toggleTodo = (id) => api.patch(`/todos/${id}`);

export const updateTodo = ({ id, title, completed }) =>
  api.put(`/todos/${id}`, {
    title,
    completed,
  });

export const deleteTodo = (id) => api.delete(`/todos/${id}`);
