import { useEffect, useState, ChangeEvent } from 'react';
import Table from '../../components/admin/Table';
import { Button, Modal, Select, TextInput, Textarea } from 'flowbite-react';
import { ToastContainer, toast } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";
import { HiPlus } from 'react-icons/hi';
import { Spinner } from "flowbite-react";

import type { TableColumn } from '@type/common/TableColumn';
import type { Payment } from '@type/model/Payment';

import { formatDate } from '../../utils/dateFormat';

// Import API services
import {
  getPayments,
  createPayment,
  updatePayment,
  deletePayment,
} from '../../api/services/admin/paymentService';

export default function Payment() {
  const [data, setData] = useState<Payment[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [actionLoading, setActionLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);
  const [openModal, setOpenModal] = useState<boolean>(false);
  const [isEditMode, setIsEditMode] = useState<boolean>(false);
  const [formData, setFormData] = useState<Partial<Payment>>({});

  const columns: TableColumn<Payment>[] = [
    { name: 'ID Thanh Toán', selector: (row) => row.paymentId, sortable: true },
    { name: 'Thời Gian Thanh Toán', selector: (row) => formatDate(row.paymentTime) , sortable: true },
    { name: 'Số Tiền Gốc', selector: (row) => row.originalAmount.toLocaleString('vi-VN') + 'đ', sortable: true },
    { name: 'Số Tiền Giảm', selector: (row) => row.discountAmount.toLocaleString('vi-VN') + 'đ', sortable: true },
    { name: 'Số Tiền Cuối Cùng', selector: (row) => row.finalAmount.toLocaleString('vi-VN') + 'đ', sortable: true },
    { name: 'Phương Thức Thanh Toán', selector: (row) => row.paymentMethod, sortable: true },
    { name: 'Trạng Thái', selector: (row) => row.status.toUpperCase(), sortable: true },
    {
      name: 'Tình Trạng',
      selector: (row) => (row.isDelete ? 'Không hiển thị' : 'Hiển thị'),
      sortable: true,
    }
  ];

  // Fetch payment data from API
  const fetchPayments = async () => {
    try {
      const payments = await getPayments();
      setData(payments);
    } catch (error: any) {
      setError(error.message);
    } finally {
      setLoading(false);
    }
  };

  // Fetch data on component load
  useEffect(() => {
    fetchPayments();
  }, []);

  // Open modal for add or edit
  const handleOpenModal = (item: Payment | null = null) => {
    if (item) {
      setIsEditMode(true);
      setFormData(item);
    } else {
      setIsEditMode(false);
      setFormData({
        paymentId: 0,
        paymentTime: '',
        originalAmount: 0,
        discountAmount: 0,
        finalAmount: 0,
        status: '',
        paymentMethod: '',
        listTicketEntities_Id: [],
        isDelete: false,
      });
    }
    setOpenModal(true);
  };

  const validDataCheck = (): boolean => {
    // Kiểm tra paymentId (không được null)
    
  
    // Kiểm tra originalAmount (>= 0)
    if (formData.originalAmount === null || formData.originalAmount === undefined || formData.originalAmount < 0) {
      toast.error("Số tiền gốc phải lớn hơn hoặc bằng 0", { autoClose: 800 });
      return false;
    }
  
    // Kiểm tra discountAmount (>= 0)
    if (formData.discountAmount === null || formData.discountAmount === undefined || formData.discountAmount < 0) {
      toast.error("Số tiền giảm giá phải lớn hơn hoặc bằng 0", { autoClose: 800 });
      return false;
    }
  
    // Kiểm tra finalAmount (>= 0)
    if (formData.finalAmount === null || formData.finalAmount === undefined || formData.finalAmount < 0) {
      toast.error("Số tiền cuối cùng phải lớn hơn hoặc bằng 0", { autoClose: 800 });
      return false;
    }
  
    // Kiểm tra status (phải là "success", "pending", hoặc "failure")
    if (!formData.status || !/^(success|pending|failure)$/.test(formData.status)) {
      toast.error("Trạng thái chỉ được phép là 'success', 'pending', hoặc 'failure'", { autoClose: 800 });
      return false;
    }
  
    // Kiểm tra paymentMethod (không được rỗng và không vượt quá 50 ký tự)
    if (!formData.paymentMethod || formData.paymentMethod.trim().length === 0) {
      toast.error("Phương thức thanh toán không được để trống", { autoClose: 800 });
      return false;
    }
    if (formData.paymentMethod.length > 50) {
      toast.error("Phương thức thanh toán không được vượt quá 50 ký tự", { autoClose: 800 });
      return false;
    }
    // Kiểm tra paymentTime (không được null)
    if (!formData.paymentTime) {
      toast.error("Thời gian thanh toán không được để trống", { autoClose: 800 });
      return false;
    }
  

  
    // Kiểm tra isDelete (không được null)
    if (formData.isDelete === null || formData.isDelete === undefined) {
      toast.error("Trạng thái hiển thị không được để trống", { autoClose: 800 });
      return false;
    }
  
    // Tất cả kiểm tra thành công
    return true;
  };
  

  const handleSave = async () => {
    console.log('Dữ liệu trước khi gửi:', formData);
    if (!validDataCheck()) {
      return;
    }
    setActionLoading(true);
    try {
      if (isEditMode) {
        const result = await updatePayment(formData as Payment);
        console.log("Kết quả trả về để sửa:", result);
        setData((prevData) =>
          prevData.map((item) =>
            item.paymentId === result.paymentId ? result : item
          )
        );
        toast.success("Cập nhật thanh toán thành công", { autoClose: 800 });
      } else {
        const result = await createPayment(formData as Payment);
        console.log(">>> Kết quả trả về để thêm:", result);
        setData((prevData) => [...prevData, result]);
        toast.success("Thêm thanh toán thành công", { autoClose: 800 });
      }
      setOpenModal(false);
    } catch (err: any) {
      toast.error(err.message || "Lỗi khi lưu thanh toán", { autoClose: 800 });
    } finally {
      setActionLoading(false);
    }
  };

  const handleDelete = async (id: number) => {
    console.log('>>>id để xóa thanh toán:', id);
    if (window.confirm("Bạn có chắc muốn xóa thanh toán này không?")) {
      setActionLoading(true);
      try {
        await deletePayment(id);
        toast.success("Xóa thanh toán thành công", { autoClose: 800 });
        fetchPayments();
      } catch (err: any) {
        toast.error(err.message || "Lỗi khi xóa thanh toán", { autoClose: 800 });
      } finally {
        setActionLoading(false);
      }
    }
  };

  if (loading) return <div><Spinner aria-label="Default status example" /></div>;
  if (error) return toast.error(error, { autoClose: 800 });

  return (
    <div className='p-4 mx-auto'>
      <h1 className='uppercase font-semibold text-2xl tracking-wide mb-4'>Quản lý thanh toán</h1>
      <Button onClick={() => handleOpenModal(null)} size='sm'>
        <HiPlus className='mr-2 h-5 w-5' />
        Thêm thanh toán
      </Button>
      <Table rows={data} columns={columns} onEdit={handleOpenModal} onDelete={(row) => handleDelete(row.paymentId)} />
      <Modal show={openModal} onClose={() => setOpenModal(false)}>
        <Modal.Header>{isEditMode ? 'Cập nhật' : 'Thêm thanh toán'}</Modal.Header>
        <Modal.Body>
          <div className='space-y-6'>
            
            <div className='space-y-2'>
              <label htmlFor='originalAmount'>Số tiền gốc</label>
              <TextInput
                name='originalAmount'
                type='number'
                value={formData.originalAmount || ''}
                onChange={(e) =>
                  setFormData((prev) => ({ ...prev, originalAmount: Number(e.target.value) }))
                }
                placeholder='Nhập số tiền gốc'
                min={0}
              />
            </div>
            <div className='space-y-2'>
              <label htmlFor='finalAmount'>Số tiền cuối cùng</label>
              <TextInput
                name='finalAmount'
                type='number'
                value={formData.finalAmount || ''}
                onChange={(e) =>
                  setFormData((prev) => ({ ...prev, finalAmount: Number(e.target.value) }))
                }
                placeholder='Nhập số tiền cuối cùng'
                min={0}
              />
            </div>
            <div className='space-y-2'>
              <label htmlFor='paymentMethod'>Phương thức thanh toán</label>
              <TextInput
                name='paymentMethod'
                type='text'
                value={formData.paymentMethod || ''}
                onChange={(e) =>
                  setFormData((prev) => ({ ...prev, paymentMethod: e.target.value }))
                }
                placeholder='Nhập phương thức thanh toán'
              />
            </div>
            <div className='space-y-2'>
              <label htmlFor='paymentTime'>Thời gian thanh toán</label>
              <TextInput
                name='paymentTime'
                type="datetime-local"
                value={formData.paymentTime || ''}
                onChange={(e) =>
                  setFormData((prev) => ({ ...prev, paymentTime: e.target.value }))
                }
              />
            </div>
            <div className='space-y-2'>
              <label htmlFor='status'>Trạng thái</label>
              <Select
                name='status'
                value={formData.status || ''}
                onChange={(e) =>
                  setFormData((prev) => ({ ...prev, status: e.target.value }))
                }
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
                name='isDelete'
                value={formData.isDelete ? '1' : '0'}
                onChange={(e) =>
                  setFormData((prev) => ({
                    ...prev,
                    isDelete: e.target.value === '1',
                  }))
                }
              >
                <option value='0'>Hiển thị</option>
                <option value='1'>Không hiển thị</option>
              </Select>
            </div>
          </div>
        </Modal.Body>
        <Modal.Footer>
          <Button onClick={handleSave}>
            {actionLoading ? <Spinner size='sm' /> : 'Lưu'}
          </Button>
          <Button color='gray' onClick={() => setOpenModal(false)}>
            Hủy
          </Button>
        </Modal.Footer>
      </Modal>
      <ToastContainer />
    </div>
  );
}
