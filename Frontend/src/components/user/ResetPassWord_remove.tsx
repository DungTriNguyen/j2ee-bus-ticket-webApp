import { useState, ChangeEvent } from "react";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { updateAccount } from "../../api/services/admin/accountService"; // API cập nhật tài khoản

export default function ChangePassword() {
  const [formData, setFormData] = useState({
    newPassword: "",
    confirmPassword: "",
  });
  const [loading, setLoading] = useState(false);

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const validatePassword = (): boolean => {
    const passwordRegex =
      /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*(),.?":{}|<>])[a-zA-Z0-9!@#$%^&*(),.?":{}|<>]{5,}$/;

    if (!formData.newPassword) {
      toast.error("Mật khẩu không được để trống!", { autoClose: 800 });
      return false;
    }

    if (!passwordRegex.test(formData.newPassword)) {
      toast.error(
        "Mật khẩu phải có ít nhất 5 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt.",
        { autoClose: 800 }
      );
      return false;
    }

    if (formData.newPassword !== formData.confirmPassword) {
      toast.error("Mật khẩu xác nhận không khớp!", { autoClose: 800 });
      return false;
    }

    return true;
  };
    // Giả sử lấy username từ thông tin đăng nhập
    const username = "phong";
    const userName= 'phuc';
    const fullName= 'Truong Coong Phúc';
    const phoneNumber= '0986493887';
    const email= 'congphuc@gmail.com';
    const role= 'ROLE_USER';

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!validatePassword()) return;

    try {
      setLoading(true);

      const payload = {
        userName: userName,
        fullName: fullName,
        phoneNumber: phoneNumber,
        email: email,
        role: role,
        isBlock: false,
        isDelete: false,
        listFeedbackEntities_Id: [],
        listTicketEntities_Id: [],
      };

      const response = await updateAccount(payload);
      console.log(response);
      if (response.status === 200) {
        toast.success("Đổi mật khẩu thành công!", { autoClose: 800 });
        setFormData({ newPassword: "", confirmPassword: "" });
      } else {
        toast.error("Có lỗi xảy ra, vui lòng thử lại!", { autoClose: 800 });
      }
    } catch (error) {
      console.error(error);
      toast.error("Đã xảy ra lỗi khi đổi mật khẩu!", { autoClose: 800 });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100">
      <div className="w-full max-w-md p-6 bg-white shadow-md rounded-2xl">
        <h2 className="text-2xl font-bold text-center text-gray-700 mb-6">
          Đổi Mật Khẩu
        </h2>
        <form onSubmit={handleSubmit}>
          <div className="flex flex-col gap-2 mb-5">
            <label className="text-gray-600">
              <span className="text-red-500">*</span> Mật khẩu mới
            </label>
            <input
              type="password"
              name="newPassword"
              value={formData.newPassword}
              onChange={handleChange}
              className="w-full px-4 py-2 border border-gray-300 rounded-2xl focus:outline-none focus:ring focus:ring-blue-200"
              placeholder="Nhập mật khẩu mới"
              required
            />
          </div>
          <div className="flex flex-col gap-2 mb-5">
            <label className="text-gray-600">
              <span className="text-red-500">*</span> Xác nhận mật khẩu mới
            </label>
            <input
              type="password"
              name="confirmPassword"
              value={formData.confirmPassword}
              onChange={handleChange}
              className="w-full px-4 py-2 border border-gray-300 rounded-2xl focus:outline-none focus:ring focus:ring-blue-200"
              placeholder="Nhập lại mật khẩu"
              required
            />
          </div>
          <div className="flex items-center justify-center">
            <button
              type="submit"
              disabled={loading}
              className={`w-full px-4 py-3 text-white bg-teal-600 hover:bg-teal-700 rounded-2xl transition-all duration-200 ${
                loading ? "bg-gray-400 cursor-not-allowed" : ""
              }`}
            >
              {loading ? "Đang xử lý..." : "Xác nhận"}
            </button>
          </div>
        </form>
      </div>
      <ToastContainer />
    </div>
  );
}
