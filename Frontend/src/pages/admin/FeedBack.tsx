import { useEffect, useState, ChangeEvent } from 'react';
import Table from '../../components/admin/Table';
import { Button, HR, Modal, Select, TextInput, Textarea } from 'flowbite-react';
import { ToastContainer, toast } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";
import { HiPlus } from 'react-icons/hi';
import { Spinner } from "flowbite-react";

import type { TableColumn } from '@type/common/TableColumn';
import type { Feedback } from '@type/model/Feedback';



// import api
import {
  getFeedbacks,
  createFeedback,
  updateFeedback,
  deleteFeedback,
} from '../../api/services/admin/feedbackService';
import {
  getAccounts
} from '../../api/services/admin/accountService';
import {
  getTickets
} from '../../api/services/admin/ticketService';
import { formatDate } from '../../utils/dateFormat';



export default function Feedback() {
  const [data, setData] = useState<Feedback[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [actionLoading, setActionLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);
  const [openModal, setOpenModal] = useState<boolean>(false);
  const [isEditMode, setIsEditMode] = useState<boolean>(false);
  const [formData, setFormData] = useState<Partial<Feedback>>({});
  // trạng thái danh sách tài khoản
  const [accounts, setAccounts] = useState<{ userName: string }[]>([]);
  // trạng thái danh sách ID tickets
  const [tickets, setTickets] = useState<{ ticketId: number }[]>([]);

  const columns: TableColumn<Feedback>[] = [
    { name: 'ID Phản hồi', selector: (row) => row.feedbackId, sortable: true },
    { name: 'Tên tài khoản', selector: (row) => row.accountEnity_userName, sortable: true },
    { name: 'ID Vé', selector: (row) => row.ticketEntity_Id, sortable: true },
    { name: 'Nội dung', selector: (row) => row.content, sortable: false },
    { name: 'Đánh giá', selector: (row) => row.rating, sortable: true },
    { name: 'Ngày bình luận', selector: (row) => formatDate(row.dateComment) , sortable: true },
    {
      name: 'Tình trạng',
      selector: (row) => (row.isDelete ? 'Không hiển thị' : 'Hiển thị'),
      sortable: true,
    }
  ];

  // lấy danh sách feedback từ api
  const fetchFeedbacks = async () => {
    try {
      const feedbacks = await getFeedbacks();
      setData(feedbacks);
    } catch (error: any) {
      setError(error.message);
    } finally {
      setLoading(false);
    }
  };
  // gọi danh sách tài khoản từ api
  const fetchAccounts = async () => {
    try {
      const accountData = await getAccounts();
      setAccounts(accountData);
    } catch (error: any) {
      toast.error("Lỗi khi tải danh sách tài khoản", { autoClose: 800 });
    }
  };
  // gọi danh sách Tickets từ api
  const fetchTickets = async () => {
    try {
      const ticketData = await getTickets();
      setTickets(ticketData);
    } catch (error: any) {
      toast.error("Lỗi khi tải danh sách vé", { autoClose: 800 });
    }
  };
  // hook fetch data feedback và account
  useEffect(() => {
    fetchFeedbacks();
    fetchAccounts();
    fetchTickets();
  }, []);


  // Mở model thêm hoặc sửa 1 feedback
  const handleOpenModal = (item: Feedback | null = null) => {
    if (item) {
      setIsEditMode(true);
      setFormData(item);
    } else {
      setIsEditMode(false);
      setFormData({
        // khi thêm thì id_feedback sẽ được tạo tự động trong database
        feedbackId: 0,
        accountEnity_userName: '',
        ticketEntity_Id: 0,
        content: '',
        rating: 0,
        dateComment: '',
        isDelete: false,
      });
    }
    setOpenModal(true);
  };


  const validDataCheck = (): boolean => {
    if (!formData.accountEnity_userName || formData.accountEnity_userName.trim().length === 0) {
      toast.error("Tên tài khoản không được để trống", { autoClose: 800 });
      return false;
    }
    
    if (formData.accountEnity_userName.length > 20) {
      toast.error("Tên tài khoản không được dài hơn 20 ký tự", { autoClose: 800 });
      return false;
    }
  
    if (!formData.ticketEntity_Id || formData.ticketEntity_Id <= 0) {
      toast.error("ID vé không hợp lệ", { autoClose: 800 });
      return false;
    }
  
    if (!formData.content || formData.content.trim().length === 0) {
      toast.error("Nội dung phản hồi không được để trống", { autoClose: 800 });
      return false;
    }
  
    if (formData.content.length > 500) {
      toast.error("Nội dung phản hồi không được dài hơn 500 ký tự", { autoClose: 800 });
      return false;
    }
  
    if ((formData.rating ?? 0) < 1 || (formData.rating ?? 0) > 5) {
      toast.error("Đánh giá phải nằm trong khoảng từ 1 đến 5", { autoClose: 800 });
      return false;
    }
  
    if (!formData.dateComment) {
      toast.error("Ngày bình luận không được để trống", { autoClose: 800 });
      return false;
    }
  
    if (formData.isDelete === undefined || formData.isDelete === null) {
      toast.error("Tình trạng không được để trống", { autoClose: 800 });
      return false;
    }
  
    return true;
  };

  // toast.configure();
  // Kiểm tra form sửa hay là form thêm dữ liệu và thực hiện logic
  const handleSave = async () => {
    console.log('Dữ liệu trước khi gửi:', formData);
    // Kiểm tra dữ liệu trước khi gửi
    if (!validDataCheck()) {
      return; // Dừng xử lý nếu dữ liệu không hợp lệ
    }
    setActionLoading(true);
    try{
      // gọi api sửa
      if(isEditMode){
        const result = await updateFeedback(formData as Feedback);   
        console.log("Kết quả trả về để sửa:", result);
        // Cập nhật trực tiếp vào state `data`
          setData((prevData) =>
              prevData.map((item) =>
              item.feedbackId === result.feedbackId ? result : item
            )
          );
          fetchFeedbacks();
      toast.success("Cập nhật Feedback thành công", {autoClose: 800});
      
      }else{
        // gọi api thêm
        const result = await createFeedback(formData as Feedback);
        console.log(">>> Kết quả trả về để thêm:", result);
        toast.success("Thêm Feedback thành công", {autoClose: 800});


        // Thêm dữ liệu mới vào state `data`
      setData((prevData) => [...prevData, result]);


      }
      // đóng form sau khi thực hiện xong hàm này
      setOpenModal(false)
      
    }catch (err: any){
      toast.error(err.message || "lỗi khi lưu Feedback", { autoClose: 800 });
    }finally{
      setActionLoading(false);
    }
  };

  // Cập nhật trạng thái formData khi người dùng nhập dữ liệu + ràng buộc dữ liệu tránh sai sót
  const handleChange = (
    e: ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>
  ) => {
    const { name, value } = e.target;
  
    setFormData((prev) => ({
      ...prev,
      [name]: name === 'rating' || name === 'ticketEntity_Id'
        ? Number(value)
        : name === 'isDelete'
        ? value === '1'
        : value,
    }));
  };
  

  // hàm để xóa một feedback
  const handleDelete = async (id: number) => {
    console.log('>>>id de xoa feedback',id)
    if (window.confirm("Bạn có chắc muốn xóa feedback này không?")) {
      setActionLoading(true);
      try {
        await deleteFeedback(id); // Gọi API xóa
        toast.success("Xóa feedback thành công", {autoClose:800});
        fetchFeedbacks(); // Tải lại danh sách phản hồi sau khi xóa
      } catch (err: any) {
        toast.error(err.message || "Lỗi khi xóa feedback", { autoClose: 800 });
        const errorMessage =
        err.response?.data?.message || // Thông báo từ API
        "Đã xảy ra lỗi khi xóa feedback"; // Thông báo mặc định
        toast.error(errorMessage, { autoClose: 800 });
      } finally {
        setActionLoading(false);
      }
    }
  };
  
  if (loading) return <div><Spinner aria-label="Default status example" /></div>;
  if (error) return toast.error(error, { autoClose: 800 });

  return (
    <div className='p-4 mx-auto'>
      <h1 className='uppercase font-semibold text-2xl tracking-wide mb-4'>Quản lý phản hồi</h1>
      <Button onClick={() => handleOpenModal(null)} size='sm'>
        <HiPlus className='mr-2 h-5 w-5' />
        Thêm phản hồi
      </Button>
      <Table rows={data} columns={columns} onEdit={handleOpenModal} onDelete={(row) => handleDelete(row.feedbackId)} />
      <Modal show={openModal} onClose={() => setOpenModal(false)}>
        <Modal.Header>{isEditMode ? 'Cập nhật' : 'Thêm phản hồi'}</Modal.Header>
        <Modal.Body>
          <div className='space-y-6'>
            <div className='space-y-2 flex flex-col'>
              <label htmlFor='accountEnity_userName'>Tên tài khoản</label>
              <select id="" className="rounded-lg border-gray-200 bg-gray-50" name='accountEnity_userName'
                value={formData.accountEnity_userName || ''}
                onChange={handleChange}
                disabled={isEditMode}>
                <option value="" disabled className=''>Chọn tài khoản</option>
                {accounts.map((account) => (
                  <option key={account.userName} value={account.userName}>
                    {account.userName}
                  </option>
                ))}
              </select>
            </div>
            <div className='space-y-2 flex flex-col'>
              <label htmlFor='ticketEntity_Id'>ID Vé</label>
              <select id="" className="rounded-lg border-gray-200 bg-gray-50" 
                name='ticketEntity_Id'
                value={formData.ticketEntity_Id || ''}
                disabled={isEditMode}
                onChange={handleChange}>
                <option value="" disabled className=''>ID Vé</option>
                {tickets.map((ticket) => (
                  <option key={ticket.ticketId} value={ticket.ticketId}>
                    {ticket.ticketId}
                  </option>
                ))}
              </select>
            </div>
            <div className='space-y-2'>
              <label htmlFor='content'>Nội dung</label>
              <Textarea
                name='content'
                value={formData.content || ''}
                // onChange={handleChange}
                onChange={(e) =>
                  setFormData((prev) => ({ ...prev, content: e.target.value }))
                }
                placeholder='Nhập nội dung phản hồi'
              />
            </div>
            
            <div className='space-y-2'>
              <label htmlFor='rating'>Đánh giá</label>
              <TextInput
                name='rating'
                type='number'
                value={formData.rating || ''}
                onChange={handleChange}
                placeholder='Nhập điểm đánh giá (1-5)'
                min={1}
                max={5}
              />
            </div>
            <div className='space-y-2'>
              <label htmlFor='dateComment'>Ngày bình luận</label>
              <TextInput
                name='dateComment'
                // type='date'
                type="datetime-local"
                value={formData.dateComment || ''}
                onChange={handleChange}
              />
            </div>
            <div className='space-y-2'>
              <label>Tình trạng</label>
              <Select
                
                name='isDelete'
                value={formData.isDelete ? '1' : '0'}
                onChange={handleChange}
              >
                <option value='1'>Không hiển thị</option>
                <option value='0'>Hiển thị</option>
              </Select>
            </div>
          </div>
          <HR className='my-4' />
          <div className='flex flex-row-reverse gap-2'>
            <Button onClick={handleSave} disabled={actionLoading}>{actionLoading ? 'Đang lưu...' : isEditMode ? 'Cập nhật' : 'Thêm'}  </Button>
            <Button color='gray' onClick={() => setOpenModal(false)}>
              Hủy
            </Button>
          </div>
        </Modal.Body>
      </Modal>
      <ToastContainer />
    </div>
  );
}
