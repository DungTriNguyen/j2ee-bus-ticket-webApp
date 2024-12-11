import { useEffect, useState, ChangeEvent } from 'react';
import Table from '../../components/admin/Table';
import { Button, HR, Modal, Select, TextInput } from 'flowbite-react';
import { ToastContainer, toast } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";
import { HiPlus } from 'react-icons/hi';
import { Spinner } from "flowbite-react";
import type { TableColumn } from '@type/common/TableColumn';
import type { Discount } from '@type/model/Discount';

import {
  getDiscounts,
  createDiscount,
  updateDiscount,
  deleteDiscount,
} from '../../api/services/admin/discountService';
import { formatDate } from '../../utils/dateFormat';

export default function Discount() {
  const [data, setData] = useState<Discount[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [actionLoading, setActionLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);
  const [openModal, setOpenModal] = useState<boolean>(false);
  const [isEditMode, setIsEditMode] = useState<boolean>(false);
  const [formData, setFormData] = useState<Partial<Discount>>({});

  const columns: TableColumn<Discount>[] = [
    { name: 'ID Giảm giá', selector: (row) => row.discountId, sortable: true },
    { name: 'Phần trăm giảm giá (%)', selector: (row) => row.discountPercentage + '%', sortable: true },
    { name: 'Ngày bắt đầu', selector: (row) => formatDate(row.validFrom) , sortable: true },
    { name: 'Ngày kết thúc', selector: (row) => formatDate(row.validUntil), sortable: true },
    { name: 'Số tiền giảm', selector: (row) => row.amount, sortable: true },
    {
      name: 'Tình trạng',
      selector: (row) => (row.isDelete ? 'Không hiển thị' : 'Hiển thị'),
      sortable: true,
    },
  ];

  const fetchDiscounts = async () => {
    try {
      const discounts = await getDiscounts();
      setData(discounts);
    } catch (error: any) {
      setError(error.message);
      toast.error("Lỗi khi tải danh sách giảm giá", { autoClose: 800 });
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchDiscounts();
  },[]);

  const handleOpenModal = (item: Discount | null = null) => {
    if (item) {
      setIsEditMode(true);
      setFormData(item);
    } else {
      setIsEditMode(false);
      setFormData({
        // discount id tăng tự động trong backend
        discountId: 0,
        discountPercentage: 0,
        validFrom: '',
        validUntil: '',
        amount: 0,
        isDelete: false,
        listTicketEntities_Id: []
      });
    }
    setOpenModal(true);
  };

  const validDataCheck = (): boolean => {
    // Kiểm tra phần trăm giảm giá (discountPercentage)
    if ((formData.discountPercentage ?? 0) <= 0 || (formData.discountPercentage ?? 0) > 100) {
      toast.error("Phần trăm giảm giá phải trong khoảng 1-100", { autoClose: 800 });
      return false;
    }

    // Kiểm tra ngày bắt đầu và ngày kết thúc (validFrom và validUntil)
    if (!formData.validFrom || !formData.validUntil) {
      toast.error("Ngày bắt đầu và ngày kết thúc không được để trống", { autoClose: 800 });
      return false;
    }
    if (new Date(formData.validFrom) > new Date(formData.validUntil)) {
      toast.error("Ngày bắt đầu không được lớn hơn ngày kết thúc", { autoClose: 800 });
      return false;
    }

    // Kiểm tra số tiền giảm (amount)
    if ((formData.amount ?? 0) <= 0) {
      toast.error("Số tiền giảm phải lớn hơn 0", { autoClose: 800 });
      return false;
    }

    // Kiểm tra trạng thái xóa (isDelete)
    if (formData.isDelete === null || formData.isDelete === undefined) {
      toast.error("Trạng thái xóa không được để trống", { autoClose: 800 });
      return false;
    }

    return true;  // Trả về true nếu tất cả các kiểm tra đều hợp lệ
};


  const handleSave = async () => {
    if (!validDataCheck()) return;

    setActionLoading(true);
    try {
      if (isEditMode) {
        const result = await updateDiscount(formData as Discount);
        setData((prevData) =>
          prevData.map((item) => (item.discountId === result.discountId ? result : item))
        );
        await fetchDiscounts();
        toast.success("Cập nhật giảm giá thành công", { autoClose: 800 });
      } else {
        const result = await createDiscount(formData as Discount);
        setData((prevData) => [...prevData, result]);
        await fetchDiscounts(); // Đồng bộ lại danh sách
        toast.success("Thêm giảm giá thành công", { autoClose: 800 });
      }
      setOpenModal(false);
    } catch (error: any) {
      toast.error(error.message || "Lỗi khi lưu giảm giá", { autoClose: 800 });
    } finally {
      setActionLoading(false);
    }
  };

  const handleChange = (
    e: ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: name === 'discountPercentage' || name === 'amount'
        ? Number(value)
        : name === 'isDelete'
        ? value === '1'
        : value,
    }));
  };

  const handleDelete = async (discountId: number) => {
    if (window.confirm("Bạn có chắc muốn xóa giảm giá này không?")) {
      setActionLoading(true);
      try {
        await deleteDiscount(discountId);
        // fetchDiscounts();
        setData((prevData) => prevData.filter((item) => item.discountId !== discountId));
        toast.success("Xóa giảm giá thành công", { autoClose: 800 });
      } catch (error: any) {
        toast.error(error.message || "Lỗi khi xóa giảm giá", { autoClose: 800 });
      } finally {
        setActionLoading(false);
      }
    }
  };

  if (loading) return <div><Spinner aria-label="Default status example" /></div>;
  if (error) return toast.error(error, { autoClose: 800 });

  return (
    <div className='p-4 mx-auto'>
      <h1 className='uppercase font-semibold text-2xl tracking-wide mb-4'>Quản lý giảm giá</h1>
      <Button onClick={() => handleOpenModal(null)} size='sm'>
        <HiPlus className='mr-2 h-5 w-5' />
        Thêm giảm giá
      </Button>
      <Table rows={data} columns={columns} onEdit={handleOpenModal} onDelete={(row) => handleDelete(row.discountId)} />
      <Modal show={openModal} onClose={() => setOpenModal(false)}>
        <Modal.Header>{isEditMode ? 'Cập nhật' : 'Thêm giảm giá'}</Modal.Header>
        <Modal.Body>
          <div className='space-y-6'>
            <div className='space-y-2'>
              <label htmlFor='discountPercentage'>Phần trăm giảm giá (%)</label>
              <TextInput
                name='discountPercentage'
                type='number'
                value={formData.discountPercentage || ''}
                onChange={handleChange}
                placeholder='Nhập phần trăm giảm giá'
              />
            </div>
            <div className='space-y-2'>
              <label htmlFor='validFrom'>Ngày bắt đầu</label>
              <TextInput
                name='validFrom'
                type="datetime-local"
                value={formData.validFrom || ''}
                onChange={handleChange}
              />
            </div>
            <div className='space-y-2'>
              <label htmlFor='validUntil'>Ngày kết thúc</label>
              <TextInput
                name='validUntil'
                type="datetime-local"
                value={formData.validUntil || ''}
                onChange={handleChange}
              />
            </div>
            <div className='space-y-2'>
              <label htmlFor='amount'>Số tiền giảm</label>
              <TextInput
                name='amount'
                type='number'
                value={formData.amount || ''}
                onChange={handleChange}
                placeholder='Nhập số tiền giảm'
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
            <Button onClick={handleSave} disabled={actionLoading}>
              {actionLoading ? 'Đang lưu...' : isEditMode ? 'Cập nhật' : 'Thêm'}
            </Button>
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
