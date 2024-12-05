import { useEffect, useState, ChangeEvent } from 'react';
import Table from '../../components/admin/Table';
import { Button, HR, Modal, Select, TextInput, Textarea } from 'flowbite-react';
import { ToastContainer, toast } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";
import { HiPlus } from 'react-icons/hi';
import { Spinner } from "flowbite-react";

import type { TableColumn } from '@type/common/TableColumn';
import type { Ticket } from '@type/model/Ticket';

// Import các API cần thiết
import { getTickets, createTicket, updateTicket, deleteTicket } from '../../api/services/admin/ticketService';
import { getAccounts } from '../../api/services/admin/accountService';
import { getBuses } from '../../api/services/admin/busService';
import { getBusRoutes } from '../../api/services/admin/busRouteService';
import { getPayments } from '../../api/services/admin/paymentService';
import { getDiscounts } from '../../api/services/admin/discountService';
import { formatDate } from '../../utils/dateFormat';

export default function Ticket() {
  const [data, setData] = useState<Ticket[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [actionLoading, setActionLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);
  const [openModal, setOpenModal] = useState<boolean>(false);
  const [isEditMode, setIsEditMode] = useState<boolean>(false);
  const [formData, setFormData] = useState<Partial<Ticket>>({});

  // State cho các dropdown liên quan
  // trạng thái danh sách busRouterID
  const [busRouter, setBusRouter] = useState<{ routesId: number }[]>([]);
  // trạng thái danh sách busID
  const [bus, setBus] = useState<{ busId: number }[]>([]);
  // trạng thái danh sách tài khoản
  const [accounts, setAccounts] = useState<{ userName: string }[]>([]);
  // trạng thái danh sách tài khoản
  const [payments, setPayments] = useState<{ paymentId: number }[]>([]);
  // trạng thái danh sách discounts
  const [discounts, setDiscounts] = useState<{ discountId?: number }[]>([]);

  // Cấu trúc cột bảng ticket
  const columns: TableColumn<Ticket>[] = [
    { name: 'ID Vé', selector: (row) => row.ticketId, sortable: true },
    { name: 'ID Tài khoản', selector: (row) => row.accountEnity_Id, sortable: true },
    { name: 'ID Xe buýt', selector: (row) => row.busEntity_Id, sortable: true },
    { name: 'ID Tuyến', selector: (row) => row.busRoutesEntity_Id, sortable: true },
    { name: 'Số ghế', selector: (row) => row.seatNumber, sortable: true },
    { name: 'Ngày khởi hành', selector: (row) => formatDate(row.departureDate) , sortable: true },
    { name: 'Số điện thoại', selector: (row) => row.phoneNumber, sortable: true },
    { name: 'Trạng thái', selector: (row) => row.status.toUpperCase(), sortable: true },
    { name: 'Giá tiền', selector: (row) => row.price.toLocaleString('vi-VN') + 'đ', sortable: true },
    {
      name: 'Tình trạng',
      selector: (row) => (row.isDelete ? 'Không hiển thị' : 'Hiển thị'),
      sortable: true,
    }
  ];

  // Lấy dữ liệu ticket từ API
  const fetchTickets = async () => {
    try {
      const tickets = await getTickets();
      setData(tickets);
    } catch (error: any) {
      setError(error.message);
    } finally {
      setLoading(false);
    }
  };

  // Lấy dữ liệu từ các API liên quan
  const fetchRelatedData = async () => {
    try {
      const accountData = await getAccounts();
      const busData = await getBuses();
      const routeData = await getBusRoutes();
      const paymentData = await getPayments();
      const discountData = await getDiscounts();

      setAccounts(accountData);
      setBus(busData);
      setBusRouter(routeData);
      setPayments(paymentData);
      setDiscounts(discountData);
    } catch (error: any) {
      toast.error("Lỗi khi tải dữ liệu liên quan.", { autoClose: 800 });
    }
  };

  useEffect(() => {
    fetchTickets();
    fetchRelatedData();
  }, []);

  // Mở form thêm/sửa ticket
  const handleOpenModal = (item: Ticket | null = null) => {
    if (item) {
      setIsEditMode(true);
      setFormData(item);
    } else {
      setIsEditMode(false);
      setFormData({
        ticketId: 0,
        accountEnity_Id: '',
        busEntity_Id: 0,
        busRoutesEntity_Id: 0,
        paymentEntity_Id: 0,
        discountEntity_Id: null,
        seatNumber: '',
        departureDate: '',
        price: 0,
        phoneNumber: '',
        status: '',
        listFeedbackEntities_Id: [],
        isDelete: false,
      });
    }
    setOpenModal(true);
  };

  // hàm kiểm tra dữ liệu đầu vào để ràng buộc dữ liệu gửi về backend
  const validDataCheck = (): boolean => {

    // kiểm tra dữ liệu trước khi gửi
    if (!formData.accountEnity_Id || formData.accountEnity_Id.trim().length === 0) {
      toast.error("Tên tài khoản không được để trống", { autoClose: 800 });
      return false;
    }
  
    if (!formData.busEntity_Id || formData.busEntity_Id <= 0) {
      toast.error("ID xe buýt không được để trống", { autoClose: 800 });
      return false;
    }
  
    if (!formData.busRoutesEntity_Id || formData.busRoutesEntity_Id <= 0) {
      toast.error("ID tuyến xe không được để trống", { autoClose: 800 });
      return false;
    }
  
    if (!formData.paymentEntity_Id || formData.paymentEntity_Id <= 0) {
      toast.error("ID thanh toán không hợp lệ", { autoClose: 800 });
      return false;
    }

    if (!formData.discountEntity_Id) {
      toast.error("ID giảm giá không được để trống", { autoClose: 800 });
      return false;
    }
  
    if (!formData.seatNumber || formData.seatNumber.trim().length === 0) {
      toast.error("Số ghế không được để trống", { autoClose: 800 });
      return false;
    }
  
    if (formData.seatNumber.length > 3) {
      toast.error("Số ghế không được vượt quá 3 ký tự", { autoClose: 800 });
      return false;
    }
  
    if (!formData.departureDate) {
      toast.error("Ngày khởi hành không được để trống", { autoClose: 800 });
      return false;
    }
  
    if (formData.price == null || formData.price < 0) {
      toast.error("Giá vé phải lớn hơn hoặc bằng 0", { autoClose: 800 });
      return false;
    }
  
    if (!formData.phoneNumber || !/^(03|05|07|08|09)[0-9]{8}$/.test(formData.phoneNumber)) {
      toast.error("Số điện thoại không hợp lệ", { autoClose: 800 });
      return false;
    }
  
    if (!formData.status || !/^(success|pending|failure)$/.test(formData.status)) {
      toast.error("Trạng thái chỉ có thể là success, pending hoặc failure", { autoClose: 800 });
      return false;
    }

    if (formData.isDelete == null) {
      toast.error("Trạng thái xóa không được để trống", { autoClose: 800 });
      return false;
    }
  
    return true;
  };

  // Kiểm tra form sửa hay là form thêm dữ liệu và thực hiện logic
  const handleSave = async () => {
    if (!validDataCheck()) {
      return;
    }
    setActionLoading(true); // Bắt đầu trạng thái loading
    try {
      if (isEditMode) {
        // Gọi API để cập nhật ticket
        const updatedTicket = await updateTicket(formData as Ticket);
        toast.success("Cập nhật vé thành công", { autoClose: 800 });
        // Cập nhật danh sách vé
        setData((prev) =>
          prev.map((item) =>
            item.ticketId === updatedTicket.ticketId ? updatedTicket : item
          )
        );
      } else {
        // Gọi API để thêm ticket
        const newTicket = await createTicket(formData as Ticket);
        toast.success("Thêm vé thành công", { autoClose: 800 });
        // Thêm vé mới vào danh sách
        setData((prev) => [...prev, newTicket]);
      }
      setOpenModal(false); // Đóng modal sau khi thêm/sửa
    } catch (error: any) {
      toast.error(error.message || "Lỗi khi lưu vé", { autoClose: 800 });
    } finally {
      setActionLoading(false); // Kết thúc trạng thái loading
    }
  };
  

  // Xóa một ticket
  const handleDelete = async (id: number) => {
    if (window.confirm("Bạn có chắc muốn xóa vé này không?")) {
      setActionLoading(true); // Bắt đầu trạng thái loading
      try {
        await deleteTicket(id); // Gọi API xóa
        // Loại bỏ vé khỏi danh sách
        setData((prev) => prev.filter((item) => item.ticketId !== id));
        toast.success("Xóa vé thành công", { autoClose: 800 });
      } catch (error: any) {
        toast.error(error.message || "Lỗi khi xóa vé", { autoClose: 800 });
      } finally {
        setActionLoading(false); // Kết thúc trạng thái loading
      }
    }
  };

  // Cập nhật trạng thái formData khi người dùng nhập dữ liệu + ràng buộc dữ liệu tránh sai sót
  const handleChange = (
    e: ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>
  ) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: name === 'busEntity_Id' || name === 'busRoutesEntity_Id' ||  name === 'paymentEntity_Id'
      || name === 'discountEntity_Id' ||  name === 'price'
        ? Number(value)
        : name === 'isDelete'
        ? value === '1'
        : value,
    }));
  };

  if (loading) return <div><Spinner aria-label="Default status example" /></div>;
  if (error) return toast.error(error, { autoClose: 800 });
  

  return (
    <div className='p-4 mx-auto'>
      <h1 className='uppercase font-semibold text-2xl tracking-wide mb-4'>Quản lý vé</h1>
      <Button onClick={() => handleOpenModal()} size='sm'>
        <HiPlus className='mr-2 h-5 w-5' />
        Thêm vé
      </Button>
      <Table rows={data} columns={columns} onEdit={handleOpenModal} onDelete={(row) => handleDelete(row.ticketId)}/>
      <Modal show={openModal} onClose={() => setOpenModal(false)}>
        <Modal.Header>{isEditMode ? 'Cập nhật' : 'Thêm vé'}</Modal.Header>
        <Modal.Body>
          <div className='space-y-6'>
          <div className='space-y-2 flex flex-col'>
              <label htmlFor='accountEnity_Id'>Tên tài khoản</label>
              <select id="" className="rounded-lg border-gray-200 bg-gray-50" name='accountEnity_Id'
                value={formData.accountEnity_Id || ''}
                onChange={handleChange}
                // disabled={isEditMode}
              >
                <option value="" disabled className=''>Chọn tài khoản</option>
                {accounts.map((account) => (
                  <option key={account.userName} value={account.userName}>
                    {account.userName}
                  </option>
                ))}
              </select>
            </div>
            <div className='space-y-2 flex flex-col'>
              <label htmlFor='busEntity_Id'>ID Xe buýt</label>
              <select id="" className="rounded-lg border-gray-200 bg-gray-50" 
                name='busEntity_Id'
                value={formData.busEntity_Id || ''}
                onChange={handleChange}
                // disabled={isEditMode}
               >
                <option value="" disabled className=''>Chọn ID vé xe bus</option>
                {bus.map((buses) => (
                  <option key={buses.busId} value={buses.busId}>
                    {buses.busId}
                  </option>
                ))}
              </select>
            </div>
            <div className='space-y-2 flex flex-col'>
              <label htmlFor='busRoutesEntity_Id'>ID Tuyến</label>
              <select id="" className="rounded-lg border-gray-200 bg-gray-50" 
                name='busRoutesEntity_Id'
                value={formData.busRoutesEntity_Id || ''}
                onChange={handleChange}
                // disabled={isEditMode}
              >
                <option value="" disabled className=''>Chọn ID tuyến xe bus</option>
                {busRouter.map((busR) => (
                  <option key={busR.routesId} value={busR.routesId}>
                    {busR.routesId}
                  </option>
                ))}
              </select>
            </div>
            <div className='space-y-2'>
              <label htmlFor='seatNumber'>Số ghế</label>
              <TextInput
                name='seatNumber'
                value={formData.seatNumber || ''}
                onChange={handleChange}
                placeholder='Nhập số ghế'
              />
            </div>
            <div className='space-y-2'>
              <label htmlFor='departureDate'>Ngày khởi hành</label>
              <TextInput
                name='departureDate'
                type='date'
                value={formData.departureDate || ''}
                onChange={handleChange}
                placeholder='Chọn ngày khởi hành'
              />
            </div>
            <div className='space-y-2'>
              <label htmlFor='price'>Giá tiền</label>
              <TextInput
                name='price'
                type='number'
                value={formData.price || ''}
                onChange={handleChange}
                placeholder='Nhập giá tiền'
              />
            </div>
            <div className='space-y-2 flex flex-col'>
              <label htmlFor='discountEntity_Id'>Mã Giảm giá</label>
              <select id="" className="rounded-lg border-gray-200 bg-gray-50" 
                name='discountEntity_Id'
                value={formData.discountEntity_Id || ''}
                onChange={handleChange}
                disabled={isEditMode}
              >
                <option value="" disabled className=''>Chọn ID giảm giá</option>
                <option value='null' className=''>Không giảm giá</option>
                {discounts.map((discount) => (
                  <option key={discount.discountId} value={discount.discountId}>
                    {discount.discountId}
                  </option>
                ))}
              </select>
            </div>
            <div className='space-y-2 flex flex-col'>
              <label htmlFor='paymentEntity_Id'>Phương thức thanh toán</label>
              <select id="" className="rounded-lg border-gray-200 bg-gray-50" 
                name='paymentEntity_Id'
                value={formData.paymentEntity_Id || ''}
                onChange={handleChange}
                disabled={isEditMode}>
                <option value="" disabled className=''>Chọn Phương thức</option>
                {payments.map((payment) => (
                  <option key={payment.paymentId} value={payment.paymentId}>
                    {payment.paymentId}
                  </option>
                ))}
              </select>
            </div>
            <div className='space-y-2'>
              <label htmlFor='phoneNumber'>Số điện thoại</label>
              <TextInput
                name='phoneNumber'
                type="phonenumber"
                value={formData.phoneNumber || ''}
                onChange={handleChange}
                placeholder='Nhập số điện thoại'
              />
            </div>
            <div className='space-y-2'>
              <label htmlFor='status'>Trạng thái</label>
              <Select
                name='status'
                value={formData.status || ''}
                onChange={handleChange}
              >
                <option value='' disabled>Chọn trạng thái</option>
                <option value='success'>Thêm thành công</option>
                <option value='pending'>Đang duyệt</option>
                <option value='failure'>Thất bại</option>
              </Select>
            </div>
            <div className='space-y-2'>
              <label>Tình trạng</label>
              <Select
                  name="isDelete"
                  value={formData.isDelete ? '1' : '0'} // Chuyển đổi boolean thành string
                  onChange={(e) =>
                    setFormData((prev) => ({
                      ...prev,
                      isDelete: e.target.value === '1', // Chuyển đổi sang kiểu boolean
                    }))
                  }
              >
                  <option value="1">Không hiển thị</option>
                  <option value="0">Hiển thị</option>
            </Select>
            </div>
          </div>
          <HR className='my-4' />
          <div className='flex flex-row-reverse gap-2'>
            <Button onClick={handleSave} disabled={actionLoading}>{actionLoading ? 'Đang lưu...' : isEditMode ? 'Cập nhật' : 'Thêm'}</Button>
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
