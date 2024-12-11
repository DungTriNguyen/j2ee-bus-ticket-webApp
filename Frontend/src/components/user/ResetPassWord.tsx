import { useEffect, useState, ChangeEvent } from 'react';
import type { User } from '@type/model/User';
import { ToastContainer, toast } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";
import { Spinner } from "flowbite-react";
import {
  updateAccount,
} from '../../api/services/admin/accountService';


export default function ResetPassWord() {
  const [loading, setLoading] = useState<boolean>(true);
  const [formData, setFormData] = useState<Partial<User>>({
        userName: '',
        fullName: '',
        passWord: '',
        phoneNumber: '',
        email: '',
        role: '',
        isBlock: false,
        isDelete: false,
        listFeedbackEntities_Id: [],
        listTicketEntities_Id: [],
  });
  const [repeatPass, setRepeatPass] = useState<string>(''); // State for repeat password

  // Giả sử lấy username từ thông tin đăng nhập
  const username= 'phong';
  const fullname= 'Truong Coong Phúc';
  const phonenumber= '0986493887';
  const emailTest= 'congphuc@gmail.com';
  const roleTest= 'ROLE_USER';

  const handleChange = (e: ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    if (name === 'repeatPass') {
      setRepeatPass(value); // Update repeatPass state
    } else {
      setFormData((prev) => ({
        ...prev,
        [name]: name === 'isBlock' || name === 'isDelete' ? value === '1' : value,
      }));
    }
  };


  const validDataCheck = (): boolean => {

    // Kiểm tra passWord
    if (!formData.passWord || formData.passWord.trim() === '') {
      toast.error('Mật khẩu không được để trống', { autoClose: 800 });
      return false;
    }
    const passwordRegex = /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*(),.?":{}|<>])[a-zA-Z0-9!@#$%^&*(),.?":{}|<>]{5,}$/;
    if ((!passwordRegex.test(formData.passWord))) {
      toast.error('Mật khẩu phải có ít nhất 5 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt', { autoClose: 800 });
      return false;
    }

     // Check repeatPass matches passWord
     if (formData.passWord !== repeatPass) {
      toast.error('Mật khẩu và mật khẩu xác nhận không khớp', { autoClose: 800 });
      return false;
    }
  
    return true; // Nếu tất cả kiểm tra đều hợp lệ
  };


  const handleSubmit = async (e: React.FormEvent) => { 
    e.preventDefault(); // Ngăn chặn reload trang
    // Kiểm tra điều kiện các trường bắt buộc không được để trống
    if(!validDataCheck()) return;

    const accountData: User = {
        ...formData,
        userName: username,
        fullName: fullname,
        phoneNumber: phonenumber,
        email: emailTest,
        role: roleTest,
        isBlock: false,
        isDelete: false,
        listFeedbackEntities_Id: [],
        listTicketEntities_Id: [],
      
    } as User;


    try {
      setLoading(true);
      const response = await updateAccount(accountData);
      console.log("Payload gửi đến API:", response);
      toast.success("Cập nhật mật khẩu thành công!",{autoClose: 800});
      setFormData({ passWord: '', isBlock: false, isDelete: false, listFeedbackEntities_Id: [], listTicketEntities_Id:[]});
      setRepeatPass('');
    } catch (error) {
      console.error(error);
      toast.error("Đã có lỗi vui lòng thử lại!",{autoClose:800});
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
    <section>
      <div className='flex bg-teal-100 p-4 rounded-2xl mb-7 justify-between'>
        <div className='flex flex-col'>
          <h1 className='font-bold'>Đặt lại mật khẩu</h1>
          <h2>Để bảo mật tài khoản, vui lòng không chia sẻ mật khẩu cho người khác</h2>
        </div>
      </div>

      {/* <div className="flex flex-col gap-4"> */}
      {/* Filter Options */}
      <div className='flex flex-col justify-center items-center bg-gray-200 shadow'>
        <form className='flex flex-col border rounded-2xl justify-center items-center m-4 p-10 w-1/2'>
          <div className='mb-5'>
            <h1>(+84) 9233 244 91</h1>
          </div>
          <div className='flex flex-col mb-5 w-full'>
            <label htmlFor=''>
              <p className="">Nếu người dùng bị khóa tài khoản liên hệ với admin để giải quyết</p>
            </label>
            {/* <input type='password' className='rounded-2xl w-full' /> */}
          </div>
          <div className='flex flex-col mb-5 w-full'>
            <label htmlFor=''>
              <span className='text-red-500 ml-1'>*</span>Mật khẩu mới
            </label>
            {/* <input type='password' className='rounded-2xl' /> */}
            <input
              type="password"
              name="passWord"
              value={formData.passWord || ""}
              onChange={handleChange}
              className="rounded-2xl"
              placeholder="Mật khẩu"
            />
          </div>
          <div className='flex flex-col mb-5 w-full'>
            <label htmlFor=''>
              <span className='text-red-500 ml-1'>*</span>Nhập lại mật khẩu mới
            </label>
            {/* <input type='password' className='rounded-2xl' /> */}
            <input
              type="password"
              name="repeatPass"
              value={repeatPass}
              onChange={handleChange}
              className="rounded-2xl  p-2"
              placeholder="Nhập lại mật khẩu"
            />
          </div>
          <div className='flex gap-2 w-full justify-center'>
            <button className='border border-teal-600 hover:border-teal-900 p-4 text-teal-600  rounded-2xl'>
              Hủy
            </button>
            {/* <button className='bg-teal-600 hover:bg-teal-800 rounded-2xl p-4 text-white'>
              Xác nhận
            </button> */}
            <button
              onClick={handleSubmit}
              // disabled={loading}
              className="bg-teal-600 hover:bg-teal-800 rounded-2xl p-4 text-white"
            >
              Xác nhận đổi mật khẩu
            </button>
          </div>
        </form>
      </div>
      <ToastContainer />
      </section>
    </>
  );
}
