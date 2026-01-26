import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import LoginPage from "../../features/auth/pages/LoginPage";
import SignupPage from "../../features/auth/pages/SignupPage";
import TodoPage from "../../features/todo/pages/TodoPage";
import { ProtectedRoute } from "./ProtectedRoute";
import { TodoLayout } from "../../features/todo/pages/TodoLayout";
import { Header } from "../../shared/ui/Header";

const AppRouter = () => {
  return (
    <BrowserRouter>
      <Header />
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/signup" element={<SignupPage />} />

        <Route
          path="/todos"
          element={
            <ProtectedRoute>
              <TodoLayout />
            </ProtectedRoute>
          }
        >
          <Route index element={<TodoPage />} />
        </Route>

        <Route path="*" element={<Navigate to={"/login"} />} />
      </Routes>
    </BrowserRouter>
  );
};

export default AppRouter;
