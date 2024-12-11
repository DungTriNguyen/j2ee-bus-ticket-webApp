import { useEffect, useState, ChangeEvent } from 'react';
import { Button } from "flowbite-react";
import { getAccountById, updateAccount } from '../../api/services/admin/accountService';
import { ToastContainer, toast } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";
import type { User } from '@type/model/User';
import { useSelector } from "react-redux";
import { RootState } from "../../redux/store";
export default function AccountInfo() {


  const [data, setData] = useState<any>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [actionLoading, setActionLoading] = useState<boolean>(false);
  // const [formData, setFormData] = useState<any>({});
  const [error, setError] = useState<string | null>(null);
  const [formData, setFormData] = useState<Partial<User>>({});
  // const username = "exampleUser"; // Replace with the actual logged-in user's username


  // Lấy username từ redux
  const username = useSelector((state: RootState) => state.user.currentUser?.userName);
  console.log(username);

  const fetchUserData = async () => {
    setLoading(true);
    try {
      const user = await getAccountById(username); // Fetch user data by username
      setData(user);
      console.log(">>>> user", user)
      setFormData(user); // Pre-fill the form data with fetched user info
    } catch (error: any) {
      setError(error.message || 'Không thể tải dữ liệu tài khoản.');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchUserData();
  }, []);

  const validDataCheck = (): boolean => {
    if (!formData.email || formData.email.trim() === '') {
      toast.error('Email không được để trống', { autoClose: 800 });
      return false;
    }
    const emailRegex = /^[a-zA-Z0-9._%+-]+@(gmail|outlook)\.com$/;
    if (!emailRegex.test(formData.email)) {
      toast.error('Email phải là địa chỉ Gmail hoặc Outlook hợp lệ', { autoClose: 800 });
      return false;
    }

    if (!formData.fullName || formData.fullName.trim() === '') {
      toast.error('Họ và tên không được để trống', { autoClose: 800 });
      return false;
    }

    if (!formData.phoneNumber || formData.phoneNumber.trim() === '') {
      toast.error('Số điện thoại không được để trống', { autoClose: 800 });
      return false;
    }
    const phoneNumberRegex = /^(03|05|07|08|09)[0-9]{8}$/;
    if (!phoneNumberRegex.test(formData.phoneNumber)) {
      toast.error('Số điện thoại không hợp lệ (VD: 03xxxxxxxx, 09xxxxxxxx)', { autoClose: 800 });
      return false;
    }

    return true;
  };

  const handleSave = async () => {
    if (!validDataCheck()) return;
    setActionLoading(true);
    try {
      const resulr = await updateAccount(formData); // Update the user data
      console.log(">>>> user update", resulr)
      toast.success('Cập nhật tài khoản thành công.', { autoClose: 800 });
    } catch (err: any) {
      toast.error(err.message || 'Lỗi khi lưu tài khoản.', { autoClose: 800 });
    } finally {
      setActionLoading(false);
    }
  };

  const handleChange = (e: ChangeEvent<HTMLInputElement | HTMLSelectElement >) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };
  return (
    <>
      <div className='flex bg-teal-100 p-4 rounded-2xl mb-7 justify-between'>
        <div className='flex-col mb-5'>
          <h2 className='font-bold text-2xl'>Thông tin tài khoản</h2>
          <h3>Quản lý thông tin hồ sơ để bảo mật tài khoản</h3>
        </div>
      </div>
      <div className='flex justify-center items-center border rounded-2xl bg-gray-200 shadow'>
          {/* <div className='flex flex-col avater w-1/2 justify-between items-center'>
            <img
              src='../avt_user.png'
              alt='anh-dai-dien'
              className='w-44 h-44 rounded-full mb-8 -my-20'
            />
            <Button color={'gray'}>Upload file</Button>
          </div> */}
        <div className='flex flex-col information w-1/2 p-4 gap-6 mt-5'>
          <div className='flex flex-col gap-2 whitespace-nowrap shrink-0'>
            <label className='w-1/4'>Tên đăng nhập:</label>
            <input name='userName'  type='text' className='w-full border rounded-2xl px-2 py-1'value={formData.userName || " "} onChange={handleChange} disabled/>
          </div>
          <div className='flex flex-col gap-2 whitespace-nowrap'>
            <label className='w-1/4'>Họ và tên:</label>
            <input name='fullName' type='text' className='w-full border rounded-2xl px-2 py-1' value={formData.fullName || " "} onChange={handleChange}/>
          </div>
          <div className='flex flex-col gap-2 whitespace-nowrap'>
            <label className='w-1/4'>Số điện thoại:</label>
            <input name='phoneNumber'type='text' className='w-full border rounded-2xl px-2 py-1'value={formData.phoneNumber || " "} onChange={handleChange}/>
          </div>
          <div className='flex flex-col gap-2 whitespace-nowrap'>
            <label className='w-1/4'>Email:</label>
            <input name='email' type='email' className='w-full border rounded-2xl px-2 py-1' value={formData.email || " "} onChange={handleChange}/>
          </div>
          <div className='flex justify-end mt-4'>
            <Button onClick={handleSave}>
              Cập nhật
            </Button>
          </div>
        </div>
        <ToastContainer />
      </div>
    </>
  );
}
