import { useEffect, useState, ChangeEvent } from 'react';
import Table from '../../components/admin/Table';
import { Button, HR, Modal, Select, TextInput } from 'flowbite-react';
import { ToastContainer, toast } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";
import { HiPlus } from 'react-icons/hi';
import { Spinner } from "flowbite-react";
import type { TableColumn } from '@type/common/TableColumn';
import type { User } from '@type/model/User';

import {
  createAccount,
  getAccounts,
  updateAccount,
  deleteAccount,
} from '../../api/services/admin/accountService';


export default function Account() {
  const [data, setData] = useState<User[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [actionLoading, setActionLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);

  const [openModal, setOpenModal] = useState<boolean>(false);
  const [isEditMode, setIsEditMode] = useState<boolean>(false);
  const [formData, setFormData] = useState<Partial<User>>({});
  const [repeatPass, setRepeatPass] = useState<string>(''); // State for repeat password

  const columns: TableColumn<User>[] = [
    { name: 'Tài khoản', selector: (row) => row.userName, sortable: true },
    { name: 'Họ tên', selector: (row) => row.fullName, sortable: true },
    { name: 'SĐT', selector: (row) => row.phoneNumber, sortable: true },
    { name: 'Email', selector: (row) => row.email, sortable: true },
    { name: 'Quyền hạn', selector: (row) => row.role, sortable: true },
    { name: 'Tình trạng', selector: (row) => (row.isBlock ? 'Khóa' : 'Mở khóa'), sortable: true },
    { name: 'Đã xóa', selector: (row) => (row.isDelete ? 'Đã xóa' : 'Không'), sortable: true },
  ];

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    setLoading(true);
    try {
      const accounts = await getAccounts();
      setData(accounts);
    } catch (error: any) {
      setError(error.message || 'Không thể tải dữ liệu tài khoản.');
    } finally {
      setLoading(false);
    }
  };

  const handleOpenModal = (item: User | null = null) => {
    setFormData(
      item || {
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
      }
    );
    setIsEditMode(!!item);
    setOpenModal(true);
  };

  const validDataCheck = (): boolean => {
    // Kiểm tra userName
    if (!formData.userName || formData.userName.trim() === '') {
      toast.error('Tên tài khoản không được để trống', { autoClose: 800 });
      return false;
    }
    const userNameRegex = /^.{5,20}$/;
    if (!userNameRegex.test(formData.userName)) {
      toast.error('Tên tài khoản phải từ 5 đến 20 ký tự', { autoClose: 800 });
      return false;
    }
  
    // Kiểm tra passWord
    if (!formData.passWord || formData.passWord.trim() === '') {
      toast.error('Mật khẩu không được để trống', { autoClose: 800 });
      return false;
    }
    const passwordRegex = /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*(),.?":{}|<>])[a-zA-Z0-9!@#$%^&*(),.?":{}|<>]{5,}$/;
    if ((!passwordRegex.test(formData.passWord)) && !isEditMode) {
      toast.error('Mật khẩu phải có ít nhất 5 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt', { autoClose: 800 });
      return false;
    }

     // Check repeatPass matches passWord
     if (formData.passWord !== repeatPass && !isEditMode) {
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
  
    // Kiểm tra role
    if (!formData.role || formData.role.trim() === '') {
      toast.error('Vai trò không được để trống', { autoClose: 800 });
      return false;
    }
    const roleRegex = /^(ROLE_ADMIN|ROLE_USER|ROLE_MANAGER|ROLE_STAFF)$/;
    if (!roleRegex.test(formData.role)) {
      toast.error('Vai trò phải là một trong các giá trị: ROLE_ADMIN, ROLE_USER, ROLE_MANAGER, ROLE_STAFF', { autoClose: 800 });
      return false;
    }
  
    // Kiểm tra isBlock
    if (formData.isBlock === null || formData.isBlock === undefined) {
      toast.error('Trạng thái khóa không được để trống', { autoClose: 800 });
      return false;
    }
  
    // Kiểm tra isDelete
    if (formData.isDelete === null || formData.isDelete === undefined) {
      toast.error('Trạng thái xoá không được để trống', { autoClose: 800 });
      return false;
    }
  
    return true; // Nếu tất cả kiểm tra đều hợp lệ
  };
  
  const handleSave = async () => {
    if(!validDataCheck()) return;
    console.log('Dữ liệu trước khi gửi:', formData);
    setActionLoading(true);
    try {
      if (isEditMode) {
        await updateAccount(formData as User);
        toast.success('Cập nhật tài khoản thành công.', { autoClose: 800 });
        // const result = await updateAccount(formData);
        // console.log('Kết quả trả về:', result);
      } else {
        await createAccount(formData as User);
        toast.success('Thêm tài khoản thành công.', { autoClose: 800 });
      }
      console.log(">>> Data: ", formData);
      // fetchUsers();
      setOpenModal(false);
    } catch (err: any) {
      toast.error(err.message || 'Lỗi khi lưu tài khoản.', { autoClose: 800 });
    } finally {
      setActionLoading(false);
    }
  };

  // hàm để xóa một account
  const handleDelete = async (userName: string) => {
    console.log('>>>id de xoa account',userName)
    if (window.confirm("Bạn có chắc muốn xóa tài khoản này không?")) {
      setActionLoading(true);
      try {
        await deleteAccount(userName); // Gọi API xóa
        toast.success("Xóa tài khoản thành công", {autoClose:800});
        // fetchUsers(); // Tải lại danh sách phản hồi sau khi xóa
      } catch (err: any) {
        toast.error(err.message || "Lỗi khi xóa tài khoản", { autoClose: 800 });
        const errorMessage =
        err.response?.data?.message || // Thông báo từ API
        "Đã xảy ra lỗi khi xóa tài khoản"; // Thông báo mặc định
        toast.error(errorMessage, { autoClose: 800 });
      } finally {
        setActionLoading(false);
      }
    }
  };

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

  if (loading) return <div><Spinner aria-label="Default status example" /></div>;
  if (error) return toast.error(error, { autoClose: 800 });

  return (
    <div className="p-4 mx-auto">
      <h1 className="uppercase font-semibold text-2xl tracking-wide mb-4">Quản lý tài khoản</h1>
      <Button onClick={() => handleOpenModal()} size="sm">
        <HiPlus className="mr-2 h-5 w-5" />
        Thêm tài khoản
      </Button>
      <Table rows={data} columns={columns} onEdit={handleOpenModal} onDelete={(row) => handleDelete(row.userName) }/>
      <Modal show={openModal} onClose={() => setOpenModal(false)}>
        <Modal.Header>{isEditMode ? 'Cập nhật' : 'Thêm tài khoản'}</Modal.Header>
        <Modal.Body>
          <div className="space-y-6">
            <div>
              <label>Tên tài khoản</label>
              <TextInput
                name="userName"
                value={formData.userName || ''}
                onChange={handleChange}
                disabled={isEditMode}
                placeholder="Nhập tên tài khoản"
              />
            </div>
            <div>
              <label>Password</label>
              <TextInput
                id="password"
                name="passWord"
                type='password'
                value={formData.passWord || ''}
                disabled={isEditMode}
                onChange={handleChange}
                placeholder="Nhập mật khẩu"
              />
            </div>
            <div>
            {!isEditMode && (
              <div>
                <label>Xác nhận password</label>
                <TextInput
                  id="repeatpass"
                  name="repeatPass"
                  type="password"
                  value={repeatPass}
                  onChange={handleChange}
                  placeholder="Nhập lại mật khẩu"
                />
              </div>
            )}
            </div>
            <div>
              <label>Họ và tên</label>
              <TextInput
                name="fullName"
                value={formData.fullName || ''}
                onChange={handleChange}
                placeholder="Nhập họ và tên"
              />
            </div>
            <div>
              <label>Số điện thoại</label>
              <TextInput
                name="phoneNumber"
                type="text"
                value={formData.phoneNumber || ''}
                onChange={handleChange}
                placeholder="Nhập SĐT"
              />
            </div>
            <div>
              <label>Email</label>
              <TextInput
                name="email"
                type="email"
                value={formData.email || ''}
                onChange={handleChange}
                placeholder="Nhập email"
              />
            </div>
            <div>
              <label>Quyền hạn</label>
              <Select name="role" value={formData.role || ''} onChange={handleChange}>
                <option value="" disabled>Chọn quyền hạn</option>
                <option value="ROLE_USER">Người dùng</option>
                <option value="ROLE_ADMIN">Quản trị viên</option>
                <option value="ROLE_MANAGER">Quản lý</option>
                <option value="ROLE_STAFF">Nhân viên</option>
              </Select>
            </div>
            <div>
              <label>Tình trạng</label>
              <Select name="isBlock" value={formData.isBlock ? '1' : '0'} onChange={handleChange}>
                <option value="0">Mở khóa</option>
                <option value="1">Khóa</option>
              </Select>
            </div>
            <div>
              <label>Đã xóa</label>
              <Select name="isDelete" value={formData.isDelete ? '1' : '0'} onChange={handleChange}>
                <option value="0">Không</option>
                <option value="1">Đã xóa</option>
              </Select>
            </div>
          </div>
          <HR className="my-4" />
          <div className="flex justify-end gap-2">
          <Button onClick={handleSave} disabled={actionLoading}>{actionLoading ? 'Đang lưu...' : isEditMode ? 'Cập nhật' : 'Thêm'}  </Button>
            <Button color="gray" onClick={() => setOpenModal(false)} disabled={actionLoading}>
              Hủy
            </Button>
          </div>
        </Modal.Body>
      </Modal>
      <ToastContainer />
    </div>
  );
}
