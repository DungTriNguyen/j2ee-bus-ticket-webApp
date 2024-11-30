import { useEffect, useState } from 'react';
import Table from '../../components/admin/Table';
import { Button, HR, Modal, Select, TextInput } from 'flowbite-react';
import { ToastContainer, toast } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";
import { HiPlus } from 'react-icons/hi';
import { Spinner } from "flowbite-react";
import type { TableColumn } from '@type/common/TableColumn';
import type { BusRoute } from '@type/model/BusRoute';

import { getBusRoutes, createBusRoute, updateBusRoute, deleteBusRoute } from '../../api/services/admin/busRouteService';

export default function BusRoute() {
  const [data, setData] = useState<BusRoute[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const [provinces, setProvinces] = useState<{ code: number; name: string }[]>([]); // Lưu danh sách tỉnh
  const [openModal, setOpenModal] = useState<boolean>(false);
  const [isEditMode, setIsEditMode] = useState<boolean>(false);
  const [formData, setFormData] = useState<Partial<BusRoute>>({});
  const [actionLoading, setActionLoading] = useState<boolean>(false);

  const columns: TableColumn<BusRoute>[] = [
    { name: 'ID Tuyến', selector: (row) => row.routesId, sortable: true },
    { name: 'Điểm khởi hành', selector: (row) => row.departureLocation, sortable: true },
    { name: 'Điểm đến', selector: (row) => row.destinationLocation, sortable: true },
    { name: 'Khoảng cách (km)', selector: (row) => row.distanceKilometer, sortable: true },
    { name: 'Giá vé', selector: (row) => row.price, sortable: true },
    { name: 'Tình trạng', selector: (row) => (row.isDelete ? 'Không hiển thị' : 'Hiển thị'), sortable: true },
  ];

  const fetchBusRoutes = async () => {
    try {
      const busRoutes = await getBusRoutes();
      setData(busRoutes);
    } catch (error: any) {
      setError(error.message || 'Có lỗi xảy ra khi tải dữ liệu tuyến xe buýt');
    } finally {
      setLoading(false);
    }
  };

  // Lấy danh sách tỉnh
  const fetchDataProvinces = async () => {
    try {
      const response = await fetch('/data/location.json');
      const text = await response.text(); // Kiểm tra nội dung thực tế nhận được
      console.log(text); // Xem nội dung để debug
      const provincesData = JSON.parse(text);
      setProvinces(provincesData);
    } catch (error) {
      console.error('Lỗi khi tải danh sách tỉnh:', error);
    }
  };

  useEffect(() => {
    fetchBusRoutes();
    fetchDataProvinces(); // Gọi hàm lấy danh sách tỉnh
  }, []);

  const handleOpenModal = (item: BusRoute | null = null) => {
    if (item) {
      setIsEditMode(true);
      setFormData(item);
    } else {
      setIsEditMode(false);
      setFormData({
        routesId: 0,
        departureLocation: '',
        destinationLocation: '',
        distanceKilometer: 0,
        departureTime: '',
        arivalTime: '',
        price: 0,
        isDelete: false,
        listBusEntities_Id: []
      });
    }
    setOpenModal(true);
  };

  const validDataCheck = (): boolean => {
    if (!formData.departureLocation || formData.departureLocation.trim().length === 0) {
      toast.error('Điểm khởi hành không được để trống', { autoClose: 800 });
      return false;
    }
    if (!formData.destinationLocation || formData.destinationLocation.trim().length === 0) {
      toast.error('Điểm đến không được để trống', { autoClose: 800 });
      return false;
    }
    if (formData.distanceKilometer === undefined || formData.distanceKilometer < 1) {
      toast.error('Khoảng cách phải lớn hơn hoặc bằng 1 km', { autoClose: 800 });
      return false;
    }
    if (!formData.departureTime) {
      toast.error('Thời gian khởi hành không được để trống', { autoClose: 800 });
      return false;
    }
    if (!formData.arivalTime) {
      toast.error('Thời gian đến không được để trống', { autoClose: 800 });
      return false;
    }
    if (new Date(formData.departureTime) >= new Date(formData.arivalTime)) {
      toast.error('Thời gian khởi hành phải trước thời gian đến', { autoClose: 800 });
      return false;
    }
    if (formData.price === undefined || formData.price < 0) {
      toast.error('Giá vé phải lớn hơn hoặc bằng 0', { autoClose: 800 });
      return false;
    }
    return true;
  };

  const handleSave = async () => {
    if (!validDataCheck()) return;

    setActionLoading(true);
    try {
      if (isEditMode) {
        const result = await updateBusRoute(formData as BusRoute);
        setData((prevData) =>
          prevData.map((item) => (item.routesId === result.routesId ? result : item))
        );
        toast.success('Cập nhật tuyến xe buýt thành công', { autoClose: 800 });
      } else {
        const result = await createBusRoute(formData as BusRoute);
        setData((prevData) => [...prevData, result]);
        toast.success('Thêm tuyến xe buýt thành công', { autoClose: 800 });
      }
      setOpenModal(false);
    } catch (error: any) {
      toast.error(error.message || 'Lỗi khi lưu dữ liệu tuyến xe buýt', { autoClose: 800 });
    } finally {
      setActionLoading(false);
    }
  };

  const handleDelete = async (id: number) => {
    if (window.confirm('Bạn có chắc muốn xóa tuyến xe buýt này không?')) {
      setActionLoading(true);
      try {
        await deleteBusRoute(id);
        setData((prevData) => prevData.filter((item) => item.routesId !== id));
        toast.success('Xóa tuyến xe buýt thành công', { autoClose: 800 });
      } catch (error: any) {
        toast.error(error.message || 'Lỗi khi xóa tuyến xe buýt', { autoClose: 800 });
      } finally {
        setActionLoading(false);
      }
    }
  };

  if (loading) return <div><Spinner aria-label="Default status example" /></div>;
  if (error) return toast.error(error, { autoClose: 800 });

  return (
    <div className='p-4 mx-auto'>
      <h1 className='uppercase font-semibold text-2xl tracking-wide mb-4'>Quản lý tuyến xe buýt</h1>
      <Button onClick={() => handleOpenModal(null)} size='sm'>
        <HiPlus className='mr-2 h-5 w-5' />
        Thêm tuyến
      </Button>
      <Table rows={data} columns={columns} onEdit={handleOpenModal} onDelete={(row) => handleDelete(row.routesId)} />
      <Modal show={openModal} onClose={() => setOpenModal(false)}>
        <Modal.Header>{isEditMode ? 'Cập nhật tuyến' : 'Thêm tuyến mới'}</Modal.Header>
        <Modal.Body>
          <div className='space-y-6'>
            <div>
              <label htmlFor='departureLocation'>Điểm khởi hành</label>
              <Select
                name='departureLocation'
                value={formData.departureLocation || ''}
                onChange={(e) =>
                  setFormData((prev) => ({ ...prev, departureLocation: e.target.value }))
                }
              >
                <option value='' disabled>Chọn điểm khởi hành</option>
                {provinces.map((province) => (
                  <option key={province.code} value={province.name}>{province.name}</option>
                ))}
              </Select>
            </div>
            <div>
              <label htmlFor='destinationLocation'>Điểm đến</label>
              <Select
                name='destinationLocation'
                value={formData.destinationLocation || ''}
                onChange={(e) =>
                  setFormData((prev) => ({ ...prev, destinationLocation: e.target.value }))
                }
              >
                <option value='' disabled>Chọn điểm đến</option>
                {provinces.map((province) => (
                  <option key={province.code} value={province.name}>{province.name}</option>
                ))}
              </Select>
            </div>
            
            <TextInput
              id='distanceKilometer'
              type='number'
              placeholder='Khoảng cách (km)'
              value={formData.distanceKilometer || ''}
              onChange={(e) =>
                setFormData((prev) => ({ ...prev, distanceKilometer: Number(e.target.value) }))
              }
            />
            {/* Các field khác tương tự */}
          </div>
        </Modal.Body>
        <Modal.Footer>
          <Button onClick={handleSave} disabled={actionLoading}>
            {actionLoading ? 'Đang lưu...' : 'Lưu'}
          </Button>
          <Button color='gray' onClick={() => setOpenModal(false)}>Hủy</Button>
        </Modal.Footer>
      </Modal>
      <ToastContainer />
    </div>
  );
}