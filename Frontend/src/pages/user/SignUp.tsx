
import { registerUser } from "../../api/services/admin/accountService";
import { useState, ChangeEvent } from 'react';
import { ToastContainer, toast } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";
interface User{
  username: string,
  pass: string,
  email: string,
  fullName: string,
  phoneNumber: string
}
export default function SignUp() {
  
  const [formData, setFormData] = useState<Partial<User>>({});
  const [repeatPass, setRepeatPass] = useState<string>(''); // State for repeat password

  const validDataCheck = (): boolean => {
    // Kiểm tra userName
    if (!formData.username || formData.username.trim() === '') {
      toast.error('Tên tài khoản không được để trống', { autoClose: 800 });
      return false;
    }
    const userNameRegex = /^.{5,20}$/;
    if (!userNameRegex.test(formData.username)) {
      toast.error('Tên tài khoản phải từ 5 đến 20 ký tự', { autoClose: 800 });
      return false;
    }
  
    // Kiểm tra passWord
    if (!formData.pass || formData.pass.trim() === '') {
      toast.error('Mật khẩu không được để trống', { autoClose: 800 });
      return false;
    }
    const passwordRegex = /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*(),.?":{}|<>])[a-zA-Z0-9!@#$%^&*(),.?":{}|<>]{5,}$/;
    if ((!passwordRegex.test(formData.pass))) {
      toast.error('Mật khẩu phải có ít nhất 5 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt', { autoClose: 800 });
      return false;
    }

     // Check repeatPass matches passWord
     if (formData.pass !== repeatPass) {
      toast.error('Mật khẩu và mật khẩu xác nhận không khớp', { autoClose: 800 });
      return false;
    }
  
    // Kiểm tra email
    if (!formData.email || formData.email.trim() === '') {
      toast.error('Email không được để trống', { autoClose: 800 });
      return false;
    }
    const emailRegex = /^[a-zA-Z0-9._%+-]+@(gmail|outlook)\.com$/;
    if (!emailRegex.test(formData.email)) {
      toast.error('Email phải là địa chỉ Gmail hoặc Outlook hợp lệ', { autoClose: 800 });
      return false;
    }
  
    // Kiểm tra fullName
    if (!formData.fullName || formData.fullName.trim() === '') {
      toast.error('Họ và tên không được để trống', { autoClose: 800 });
      return false;
    }
  
    // Kiểm tra phoneNumber
    if (!formData.phoneNumber || formData.phoneNumber.trim() === '') {
      toast.error('Số điện thoại không được để trống', { autoClose: 800 });
      return false;
    }
    const phoneNumberRegex = /^(03|05|07|08|09)[0-9]{8}$/;
    if (!phoneNumberRegex.test(formData.phoneNumber)) {
      toast.error('Số điện thoại không hợp lệ (VD: 03xxxxxxxx, 09xxxxxxxx)', { autoClose: 800 });
      return false;
    }
  
    return true; // Nếu tất cả kiểm tra đều hợp lệ
  };



  const handleSave = async (event: React.MouseEvent<HTMLButtonElement>) => {
    event.preventDefault();
    if(!validDataCheck()) return;
    
    await registerUser(formData.username, formData.pass, formData.email, formData.fullName, formData.phoneNumber);
    console.log('Dữ liệu trước khi gửi:', formData);
    toast.success('Thêm tài khoản thành công.', { autoClose: 800 });
     // Đặt lại form data và repeatPass về rỗng
     setFormData({});
     setRepeatPass('');

  };

  const handleChange = (e: ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    if (name === 'repeatPass') {
      setRepeatPass(value); // Cập nhật state cho repeatPass
    } else {
      setFormData((prev) => ({
        ...prev,
        [name]: value,
      }));
    }
  };

  return (
    <section className="bg-gray-50">
      <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto">
        <div className="w-full bg-white rounded-lg shadow md:mt-0 sm:max-w-md xl:p-0">
          <div className="p-6 space-y-4 md:space-y-6 sm:p-8">
            <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl">
              Đăng ký
            </h1>
            <form className="space-y-4 md:space-y-6" action="#">
              <div>
                <label
                  htmlFor="username"
                  className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                >
                  Tên tài khoản
                </label>
                <input
                  name="username"
                  value={formData.username || ''}
                  onChange={handleChange}
                  placeholder="Nhập tên tài khoản"
                  className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-teal-600 focus:border-teal-600 block w-full p-2.5 "
                  required
                />
              </div>
              <div>
                <label
                  htmlFor="pass"
                  className="block mb-2 text-sm font-medium text-gray-900 "
                >
                  Mật khẩu
                </label>
                <input
                  type="password"
                  name="pass"
                  id="pass"
                  placeholder="••••••••"
                  value={formData.pass || ''}
                  onChange={handleChange}
                  className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-teal-600 focus:border-teal-600 block w-full p-2.5"
                  required
                />
              </div>
              <div>
                <label
                  htmlFor="repeatPass"
                  className="block mb-2 text-sm font-medium text-gray-900 "
                >
                  Nhập lại Mật khẩu
                </label>
                <input
                  type="password"
                  name="repeatPass"
                  id="repeatPass"
                  placeholder="••••••••"
                  value={repeatPass}
                  onChange={handleChange}
                  className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-teal-600 focus:border-teal-600 block w-full p-2.5"
                  required
                />
              </div>
              <div>
                <label
                  htmlFor="fullName"
                  className="block mb-2 text-sm font-medium text-gray-900 "
                >
                  Họ và tên
                </label>
                <input
                  type="name"
                  name="fullName"
                  id="fullName"
                  placeholder="nhập họ và tên"
                  value={formData.fullName || ''}
                  onChange={handleChange}
                  className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-teal-600 focus:border-teal-600 block w-full p-2.5"
                  required
                />
              </div>
              <div>
                <label
                  htmlFor="email"
                  className="block mb-2 text-sm font-medium text-gray-900 "
                >
                  Email
                </label>
                <input
                  type="email"
                  name="email"
                  id="email"
                  placeholder="nhập email"
                  value={formData.email || ''}
                  onChange={handleChange}
                  className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-teal-600 focus:border-teal-600 block w-full p-2.5"
                  required
                />
              </div>
              <div>
                <label
                  htmlFor="phoneNumber"
                  className="block mb-2 text-sm font-medium text-gray-900 "
                >
                  Số điện thoại
                </label>
                <input
                  type="number"
                  name="phoneNumber"
                  id="phoneNumber"
                  placeholder="nhập số điện thoại"
                  value={formData.phoneNumber || ''}
                  onChange={handleChange}
                  className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-teal-600 focus:border-teal-600 block w-full p-2.5"
                  required
                />
              </div>
              <button
                onClick={handleSave}
                className="w-full text-white bg-teal-600 hover:bg-teal-700 focus:ring-4 focus:outline-none focus:ring-teal-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center"
              >
                Đăng ký
              </button>
              <p className="text-sm font-light text-gray-500 ">
                Đã có tài khoản?{" "}
                <a
                  href="/dang-nhap"
                  className="font-medium text-teal-600 hover:underline "
                >
                  Đăng nhập
                </a>
              </p>
            </form>
          </div>
        </div>
      </div>
      <ToastContainer/>
    </section>
  );
}
