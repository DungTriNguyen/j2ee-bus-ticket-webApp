import { useState } from "react";
import { login } from "../../api/services/user/authService";

import { useDispatch, useSelector } from "react-redux";
import { userStart, userSuccess, userFailed } from "../../redux/userSlice";
import { RootState } from "../../redux/store";
import { useLocation, useNavigate } from "react-router-dom";
import { Alert } from "flowbite-react";

interface LoginForm {
  username: string;
  password: string;
}

export default function Login() {
  const user = useSelector((state: RootState) => state.user);
  const dispatch = useDispatch();
  const location = useLocation();

  const navigate = useNavigate();

  const [formData, setFormData] = useState<LoginForm>({ username: '', password: '' });
  const [loading, setLoading] = useState<boolean>(false);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);

    const { username, password } = formData;

    try {
        dispatch(userStart());
        const res = await login(username, password);

        const data = res;
        if(res.status !== 'success') {
          return dispatch(userFailed(data.message.mess));
        }
        dispatch(userSuccess(data.data));
        const redirectTo = (location.state as { from: string })?.from || '/';
        navigate(redirectTo);
    } catch (error) {
      dispatch(userFailed((error as Error).message));
    } finally {
      setLoading(false);
    }
  };

  return (
    <section className="bg-gray-50">
      <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto">
        <div className="w-full bg-white rounded-lg shadow md:mt-0 sm:max-w-md xl:p-0">
          <div className="p-6 space-y-4 md:space-y-6 sm:p-8">
            <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl">
              Đăng nhập
            </h1>
            <form className="space-y-4 md:space-y-6" onSubmit={handleSubmit}>
              {user.error &&
              <Alert color='failure'>{user.error}</Alert>
              }
              <div>
                <label
                  htmlFor="username"
                  className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                >
                  Tên tài khoản
                </label>
                <input
                  type="text"
                  name="username"
                  id="username"
                  value={formData.username}
                  onChange={handleChange}
                  className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-teal-600 focus:border-teal-600 block w-full p-2.5"
                  placeholder="Nhập tên tài khoản"
                  required
                />
              </div>
              <div>
                <label
                  htmlFor="password"
                  className="block mb-2 text-sm font-medium text-gray-900"
                >
                  Mật khẩu
                </label>
                <input
                  type="password"
                  name="password"
                  id="password"
                  value={formData.password}
                  onChange={handleChange}
                  placeholder="••••••••"
                  className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-teal-600 focus:border-teal-600 block w-full p-2.5"
                  required
                />
              </div>
              <div className="flex items-center justify-between">
                <a
                  href="#"
                  className="text-sm font-medium text-teal-600 hover:underline"
                >
                  Quên mật khẩu?
                </a>
              </div>
              <button
                type="submit"
                className="w-full text-white bg-teal-600 hover:bg-teal-700 focus:ring-4 focus:outline-none focus:ring-teal-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center"
                disabled={loading}
              >
                {loading ? 'Đang đăng nhập...' : 'Đăng nhập'}
              </button>
              <p className="text-sm font-light text-gray-500 ">
                Chưa có tài khoản?{" "}
                <a
                  href="/dang-ky"
                  className="font-medium text-teal-600 hover:underline "
                >
                  Đăng ký
                </a>
              </p>
            </form>
          </div>
        </div>
      </div>
    </section>
  );
}
