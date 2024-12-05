import { useEffect, useState, ChangeEvent } from 'react';
import Table from '../../components/admin/Table';
import { Button, HR, Modal, Select, TextInput } from 'flowbite-react';
import { ToastContainer, toast } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";
import { HiPlus } from 'react-icons/hi';
import { Spinner } from "flowbite-react";
import type { TableColumn } from '@type/common/TableColumn';
import type { Bus } from '@type/model/Bus';

import { getBuses, createBus, updateBus, deleteBus } from '../../api/services/admin/busService';
import { getBusRoutes } from '../../api/services/admin/busRouteService';

export default function Bus() {
  const [data, setData] = useState<Bus[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [actionLoading, setActionLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);
  const [openModal, setOpenModal] = useState<boolean>(false);
  const [isEditMode, setIsEditMode] = useState<boolean>(false);
  const [formData, setFormData] = useState<Partial<Bus>>({});

  const [routers, setRouters] = useState<{ routesId: number }[]>([]);

  const columns: TableColumn<Bus>[] = [
    { name: 'Mã buýt', selector: (row) => row.busId, sortable: true },
    { name: 'Biển kiểm soát', selector: (row) => row.busNumber, sortable: true },
    { name: 'Sức chứa', selector: (row) => row.capacity, sortable: true },
    { name: 'Hãng xe', selector: (row) => row.brand, sortable: true },
    {
      name: 'Tình trạng',
      selector: (row) => (row.isDelete ? 'Không hiển thị' : 'Hiển thị'),
      sortable: true,
    },
    { name: 'Mã tuyến', selector: (row) => row.routesId, sortable: true },
  ];

  const fetchBuses = async () => {
    try {
      const busesData = await getBuses();
      console.log(busesData);
      setData(busesData);
    } catch (error: any) {
      setError(error.message);
    } finally {
      setLoading(false);
    }
  };
  const fetchBusRoutes = async () => {
    try {
      const busesRouterData = await getBusRoutes();
      setRouters(busesRouterData);
    } catch (error: any) {
      toast(error.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchBuses();
    fetchBusRoutes();
  }, []);

  const handleOpenModal = (item: Bus | null = null) => {
    if (item) {
      setIsEditMode(true);
      setFormData(item);
    } else {
      setIsEditMode(false);
      setFormData({ 
        busId: 0, 
        busNumber: '', 
        capacity: 0, 
        brand: '', 
        isDelete: false,  
        routesId: 0,
        listEmployeeEntities_Id: [],
        listTicketEntities_Id: [],
        listPenaltyTicketEntities_Id: []
      });
    }
    setOpenModal(true);
  };

  const validDataCheck = (): boolean => {
    // Kiểm tra busNumber
    if (!formData.busNumber || formData.busNumber.trim() === '') {
      toast.error('Biển kiểm soát không được để trống', { autoClose: 800 });
      return false;
    }
    const busNumberRegex = /^[0-9]{2}[A-Z]{1}-[0-9]{4,5}$/;
    if (!busNumberRegex.test(formData.busNumber)) {
      toast.error('Biển kiểm soát phải theo định dạng hợp lệ (VD: 29A-12345)', { autoClose: 800 });
      return false;
    }

    if (!formData.routesId) {
      toast.error('Mã tuyến không để trống', { autoClose: 800 });
      return false;
    }
  
    // Kiểm tra capacity
    if (!formData.capacity || formData.capacity < 5) {
      toast.error('Sức chứa phải lớn hơn hoặc bằng 5', { autoClose: 800 });
      return false;
    }
    if (!Number(formData.capacity)) {
      toast.error('sức chứa phải là số', { autoClose: 800 });
      return false;
    }

    // Kiểm tra brand
    if (!formData.brand || formData.brand.trim() === '') {
      toast.error('Hãng xe không được để trống', { autoClose: 800 });
      return false;
    }
    if (formData.brand.length > 50) {
      toast.error('Tên hãng xe phải nhỏ hơn hoặc bằng 50 ký tự', { autoClose: 800 });
      return false;
    }
  
    // Kiểm tra isDelete
    if (formData.isDelete === null || formData.isDelete === undefined) {
      toast.error('Trạng thái xoá không được để trống', { autoClose: 800 });
      return false;
    }  
    return true;
  };
  

  const handleSave = async () => {
    if (!validDataCheck()) return;

    setActionLoading(true);
    try {
      if (isEditMode) {
        const updatedBus = await updateBus(formData as Bus);
        setData((prevData) =>
          prevData.map((item) =>
          item.busId === updatedBus.busId ? updatedBus : item
        )
      );
      // fetchBuses
        toast.success('Cập nhật buýt thành công', { autoClose: 800 });
      } else {
        const newBus = await createBus(formData as Bus);
        toast.success('Thêm buýt thành công', { autoClose: 800 });
        setData((prevData) => [...prevData, newBus]);
      }
      setOpenModal(false);
    } catch (error: any) {
      toast.error(error.message || 'Lỗi khi lưu thông tin buýt', { autoClose: 800 });
    } finally {
      setActionLoading(false);
    }
  };

  const handleDelete = async (id: number) => {
    if (window.confirm('Bạn có chắc muốn xóa buýt này không?')) {
      setActionLoading(true);
      try {
        await deleteBus(id);
        setData((prev) => prev.filter((item) => item.busId !== id));
        toast.success('Xóa buýt thành công', { autoClose: 800 });
      } catch (error: any) {
        toast.error(error.message || 'Lỗi khi xóa buýt', { autoClose: 800 });
      } finally {
        setActionLoading(false);
      }
    }
  };

  const handleChange = (e: ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
        ...prev,
        [name]: name === 'capacity' || name === 'routesId'
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
      <h1 className='uppercase font-semibold text-2xl tracking-wide mb-4'>
        Quản lý buýt
      </h1>
      <Button onClick={() => handleOpenModal(null)} size='sm'>
        <HiPlus className='mr-2 h-5 w-5' />
        Thêm buýt
      </Button>
      <Table rows={data} columns={columns} onEdit={handleOpenModal} onDelete={(row) => handleDelete(row.busId)} />
      <Modal show={openModal} onClose={() => setOpenModal(false)}>
        <Modal.Header>{isEditMode ? 'Cập nhật' : 'Thêm buýt'}</Modal.Header>
        <Modal.Body>
          <div className='space-y-6'>
            <div className='space-y-2'>
              <label htmlFor='busNumber'>Biển kiểm soát</label>
              <TextInput
                name='busNumber'
                value={formData.busNumber || ''}
                onChange={handleChange}
                placeholder='Nhập biển kiểm soát'
              />
            </div>
            <div className='space-y-2'>
              <label htmlFor='capacity'>Sức chứa</label>
              <TextInput
                name='capacity'
                type='number'
                value={formData.capacity || ''}
                onChange={handleChange}
                placeholder='Nhập sức chứa'
              />
            </div>
            <div className='space-y-2'>
              <label htmlFor='brand'>Hãng xe</label>
              <TextInput
                name='brand'
                value={formData.brand || ''}
                onChange={handleChange}
                placeholder='Nhập hãng xe'
              />
            </div>
            <div className='space-y-2'>
              <label>Tình trạng</label>
              <Select
                name='isDelete'
                value={formData.isDelete ? '1' : '0'}
                onChange={handleChange}
              >
                <option value='0'>Hiển thị</option>
                <option value='1'>Không hiển thị</option>
              </Select>
            </div>
            <div className='space-y-2 flex flex-col'>
              <label htmlFor='routesId'>Mã tuyến xe</label>
              <select id="" className="rounded-lg border-gray-200 bg-gray-50" name='routesId'
                value={formData.routesId || ''}
                onChange={handleChange}
                // disabled={isEditMode}
              >
                <option value="" disabled className=''>Chọn mã tuyến</option>
                {routers.map((router) => (
                  <option key={router.routesId} value={router.routesId}>
                    {router.routesId}
                  </option>
                ))}
              </select>
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