import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { getMe, login } from "../api/userApi";
import { useAuth } from "../context/AuthContext";
import { setAccessToken } from "../../../shared/api/axios";

const LoginPage = () => {
  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const { user, setUser } = useAuth();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const res = await login(username, password);

      setAccessToken(res.data.accessToken);

      setError("");

      const resUser = await getMe();
      setUser(resUser.data);

      navigate("/todos");
    } catch (err) {
      if (err.response?.status === 401) {
        alert("로그인 실패");
        setError("로그인 실패");
      } else {
        alert("서버 오작동");
        setError("서버 오작동");
      }
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100 px-4">
      <div className="w-full max-w-sm bg-white rounded-xl shadow-lg p-6">
        <h1 className="text-2xl font-bold text-center mb-6">로그인</h1>

        <form action="" onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label
              htmlFor="username"
              className="block text-sm font-medium mb-1"
            >
              이름
            </label>
            <input
              type="text"
              name="username"
              id="username"
              required
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              placeholder="이름"
              className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
            />
          </div>
          <div>
            <label
              htmlFor="password"
              className="block text-sm font-medium mb-1"
            >
              비밀번호{" "}
            </label>
            <input
              type="password"
              name="password"
              id="password"
              required
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="비밀번호"
              className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
            />
          </div>
          <button className="w-full bg-blue-500 text-white py-2 rounded hover:bg-blue-600 transition cursor-pointer">
            로그인
          </button>
          <Link
            to="/signup"
            className="text-center block w-full bg-blue-500 text-white py-2 rounded hover:bg-blue-600 transition"
          >
            회원가입
          </Link>
        </form>
      </div>
    </div>
  );
};

export default LoginPage;
