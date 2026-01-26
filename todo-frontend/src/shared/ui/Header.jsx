import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../../features/auth/context/AuthContext";
import { logout } from "../../features/auth/api/userApi";

export const Header = () => {
  const { user, setUser } = useAuth();
  const navigate = useNavigate();

  const handleLogout = async () => {
    await logout(localStorage.getItem("refreshToken"));
    localStorage.clear();
    setUser(null);
    navigate("/login");
  };

  return (
    <header className="flex justify-between p-4 bg-white shadow">
      <Link to="/">할 일 목록</Link>
      <div className="flex gap-2">
        {user ? (
          <>
            <span>{user.username}님</span>
            <button
              className="bg-red-500 text-white px-2 rounded cursor-pointer"
              onClick={handleLogout}
            >
              로그아웃
            </button>
          </>
        ) : (
          <>
            <Link to="/login">로그인</Link>
            <Link to="/signup">회원가입</Link>
          </>
        )}
      </div>
    </header>
  );
};
