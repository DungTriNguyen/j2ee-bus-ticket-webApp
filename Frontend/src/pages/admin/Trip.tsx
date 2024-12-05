import { useEffect, useState, ChangeEvent } from 'react';
import Table from '../../components/admin/Table';
import { Button, DrawerItems, HR, Modal, Select, TextInput } from 'flowbite-react';
import { ToastContainer, toast } from 'react-toastify';
import { HiPlus } from 'react-icons/hi';
import { Spinner } from "flowbite-react";
import type { TableColumn } from '@type/common/TableColumn';
import type { EmployeeBus } from '@type/model/EmployeeBus';

import { updateEmployeeBus, deleteEmployeeBus } from '../../api/services/admin/tripService';
import { getBuses } from '../../api/services/admin/busService';
import { getEmployees } from '../../api/services/admin/employeeService';

export default function Trip() {
  const [data, setData] = useState<EmployeeBus[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [actionLoading, setActionLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const [openModal, setOpenModal] = useState<boolean>(false);
  const [isEditMode, setIsEditMode] = useState<boolean>(false);
  const [formData, setFormData] = useState<Partial<EmployeeBus>>({});

  // trạng thái danh sách busID
  const [bus, setBus] = useState<{ busId: number }[]>([]);
  // trạng thái danh sách employees
  const [employees, setEmployees] = useState<{ driverId: number }[]>([]);


  const columns: TableColumn<EmployeeBus>[] = [
    { name: 'Mã nhân viên', selector: (row) => row.driverId, sortable: true },
    { name: 'Mã xe', selector: (row) => row.busId, sortable: true },
  ];  

  // lấy dữ liệu id buses
  const fetchEmployeeBuses = async () => {
    try {
      const busData = await getBuses();
      console.log(busData); // Log the full data to verify
  
      // Map the busData to extract listEmployeeEntities_Id for each bus
      const employeeData = busData.map((bus: any) => ({
        busId: bus.busId,
        driverId: bus.listEmployeeEntities_Id,
      }));
  
      console.log(employeeData); // Log the processed data to verify
  
      // Set the data
      setData(employeeData);
    } catch (error: any) {
      setError(error.message);
    } finally {
      setLoading(false);
    }
  };


  // lấy dữ liệu bảng employee
  const fetchBuses= async () => {
    try {
      const busData = await getBuses();
      setBus(busData);
    } catch (error: any){
      setError(error.message);
    } finally {
      setLoading(false);
    }
  };
  
  // lấy dữ liệu của employees
  const fetchEmployees = async () => {
    try {
      const employeeData = await getEmployees();
      setEmployees(employeeData);
    } catch (error: any) {
      setError(error.message);
    } finally {
      setLoading(false);
    }
  };
  
  useEffect(() => {
    fetchEmployeeBuses();
    fetchBuses();
    fetchEmployees();
  }, []);

  const handleOpenModal = (item: EmployeeBus | null = null) => {
    if (item) {
      setIsEditMode(true);
      setFormData(item);
    } else {
      setIsEditMode(false);
      setFormData({
        driverId: 0,
        busId: 0
      });
    }
    setOpenModal(true);
  };

  const validDataCheck = (): boolean => {
    if (!formData.driverId || !formData.busId) {
      toast.error("Vui lòng chọn cả Mã nhân viên và Mã xe", { autoClose: 800 });
      return false;
    }
    return true;
  }

  const handleSave = async () => {
    if (!validDataCheck()) return; // Kiểm tra dữ liệu đầu vào
    setActionLoading(true);

    try {
        if (isEditMode) {
            // Chế độ chỉnh sửa
            const result = await updateEmployeeBus(formData.driverId!, formData.busId!);

            // Cập nhật trực tiếp state `data`
            setData((prevData) =>
                prevData.map((item) =>
                    item.driverId === result.driverId && item.busId === result.busId ? result : item
                )
            );
            fetchEmployeeBuses();
            toast.success("Cập nhật lịch phân công thành công", { autoClose: 800 });
        } else {
            // Chế độ thêm mới
            const result1 = await updateEmployeeBus(formData.driverId!, formData.busId!);
            // const newAssignment: EmployeeBus = {
            //     driverId: formData.driverId!,
            //     busId: formData.busId!,
            // };

            setData((prevData) => [...prevData, result1]);
            toast.success("Thêm lịch phân công thành công", { autoClose: 800 });
            fetchEmployeeBuses();
        }
        setOpenModal(false); // Đóng modal sau khi thêm/sửa thành công
    } catch (err: any) {
        const errorMessage =
            err.response?.data?.message || // Lấy thông báo từ server nếu có
            "Đã xảy ra lỗi khi lưu lịch phân công"; // Thông báo mặc định
        toast.error(errorMessage, { autoClose: 800 });
    } finally {
        // Đảm bảo trạng thái actionLoading luôn được đặt lại về false
        setActionLoading(false);
    }
};

  

  // hàm để xóa một employee_bus
  const handleDelete = async (driverId: number, busId: number) => {
    if (window.confirm("Bạn có chắc muốn xóa lịch phân công này không?")) {
      setActionLoading(true);
  
      try {
        await deleteEmployeeBus(driverId, busId); // Gọi API xóa
        // setData((prevData) =>
        //   prevData.filter((item) => !(item.driverId === driverId && item.busId === busId))
        // );
  
        toast.success("Xóa lịch phân công thành công", { autoClose: 800 });
        fetchEmployeeBuses();
      } catch (err: any) {
        const errorMessage =
          err.response?.data?.message || // Lấy thông báo từ server nếu có
          "Đã xảy ra lỗi khi xóa lịch phân công"; // Thông báo mặc định
  
        toast.error(errorMessage, { autoClose: 800 });
      } finally {
        setActionLoading(false);
      }
    }
  };
  

  const handleChange = (
    e: ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>
  ) => {
    const { name, value } = e.target;
  
    setFormData((prev) => ({
      ...prev,
      [name]: name === 'driverId' || name === 'busId'
        ? Number(value) : value,
    }));
  };

  if (loading) return <div><Spinner aria-label="Default status example" /></div>;
  if (error) return toast.error(error, { autoClose: 800 });

  return (
    <div className='p-4 mx-auto'>
      <h1 className='uppercase font-semibold text-2xl tracking-wide mb-4'>
        Quản lý tuyến xe buýt
      </h1>
      <Button onClick={() => handleOpenModal()} size='sm'>
        <HiPlus className='mr-2 h-5 w-5' />
        Thêm tuyến
      </Button>
      <Table rows={data} columns={columns} onEdit={handleOpenModal} onDelete={(row) => handleDelete(row.driverId, row.busID)} />
      <Modal show={openModal} onClose={() => setOpenModal(false)}>
        <Modal.Header>{isEditMode ? 'Cập nhật' : 'Thêm lịch phân công'}</Modal.Header>
        <Modal.Body>
          <div className='space-y-6'>
          <div className='space-y-2 flex flex-col'>
              <label htmlFor='driverId'>Mã nhân viên</label>
              <select id="" className="rounded-lg border-gray-200 bg-gray-50" 
                name='driverId'
                value={formData.driverId || ''}
                onChange={handleChange}
              >
                <option value="" disabled className=''>Chọn mã nhân viên</option>
                {employees.map((employee) => (
                  <option key={employee.driverId} value={employee.driverId}>
                    {employee.driverId}
                  </option>
                ))}
              </select>
            </div>
            <div className='space-y-2 flex flex-col'>
              <label htmlFor='busId'>Mã xe</label>
              <select id="" className="rounded-lg border-gray-200 bg-gray-50" 
                name='busID'
                value={formData.busId || ''}
                onChange={handleChange}
              >
                <option value="" disabled className=''>Chọn mã xe</option>
                {bus.map((buses) => (
                  <option key={buses.busId} value={buses.busId}>
                    {buses.busId}
                  </option>
                ))}
              </select>
            </div>
          </div>
          <HR className='my-4' />
          <div className='flex flex-row-reverse gap-2'>
            <Button onClick={handleSave} >{ isEditMode ? 'Cập nhật' : 'Thêm'}</Button>
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