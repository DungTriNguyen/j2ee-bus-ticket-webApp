import { useEffect, useState, ChangeEvent } from 'react';
import Table from '../../components/admin/Table';
import { Button, HR, Modal, Select, TextInput } from 'flowbite-react';
import { ToastContainer, toast } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";
import { HiPlus } from 'react-icons/hi';
import { Spinner } from "flowbite-react";
import type { TableColumn } from '@type/common/TableColumn';
import type { Employee } from '@type/model/Employee';
import * as XLSX from "xlsx";
import { BiExport } from 'react-icons/bi';
// Import các API dịch vụ
import {
  getEmployees,
  createEmployee,
  updateEmployee,
  deleteEmployee,
} from '../../api/services/admin/employeeService';

export default function Employee() {
  const [data, setData] = useState<Employee[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [actionLoading, setActionLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);
  const [openModal, setOpenModal] = useState<boolean>(false);
  const [isEditMode, setIsEditMode] = useState<boolean>(false);
  const [formData, setFormData] = useState<Partial<Employee>>({});

  const columns: TableColumn<Employee>[] = [
    { name: 'ID Nhân viên', selector: (row) => row.driverId, sortable: true },
    { name: 'Tên nhân viên', selector: (row) => row.driverName, sortable: true },
    { name: 'Là tài xế', selector: (row) => (row.isDriver ? 'Có' : 'Không'), sortable: true },
    { name: 'Số giấy phép lái xe', selector: (row) => row.licenseNumber, sortable: true },
    { name: 'Số điện thoại', selector: (row) => row.phoneNumber, sortable: true },
    { name: 'Tình trạng', selector: (row) => (row.isDelete ? 'Không hiển thị' : 'Hiển thị'), sortable: true },
  ];

  // Lấy danh sách nhân viên từ API
  const fetchEmployees = async () => {
    try {
      const employees = await getEmployees();
      setData(employees);
    } catch (error: any) {
      setError(error.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchEmployees();
  }, []);

  const handleOpenModal = (item: Employee | null = null) => {
    if (item) {
      setIsEditMode(true);
      setFormData(item);
    } else {
      setIsEditMode(false);
      setFormData({
        driverId: 0,
        isDriver: false,
        driverName: '',
        licenseNumber: '',
        phoneNumber: '',
        isDelete: false,
        listBusEntities_Id: [],
        listPenaltyTicketEntities_Id: []
      });
    }
    setOpenModal(true);
  };

  const validDataCheck = (): boolean => {
    // Driver name validation
    if (!formData.driverName || formData.driverName.trim().length === 0) {
      toast.error("Tên nhân viên không được để trống");
      return false;
    }
    if (formData.driverName.length > 100) {
      toast.error("Tên nhân viên không được dài hơn 100 ký tự");
      return false;
    }
  
    // License number validation (for drivers only)
    if (formData.isDriver || !formData.isDriver) {
      if (!formData.licenseNumber || formData.licenseNumber.trim().length === 0) {
        toast.error("Giấy phép lái xe không được để trống");
        return false;
      }
      if (!/^\d{12}$/.test(formData.licenseNumber)) {
        toast.error("Giấy phép lái xe phải bao gồm đúng 12 chữ số");
        return false;
      }
    }
  
    // Phone number validation
    if (!formData.phoneNumber || formData.phoneNumber.trim().length === 0) {
      toast.error("Số điện thoại không được để trống");
      return false;
    }
    if (!/^(03|05|07|08|09)\d{8}$/.test(formData.phoneNumber)) {
      toast.error("Số điện thoại phải hợp lệ và bắt đầu bằng 03, 05, 07, 08 hoặc 09, tổng cộng 10 chữ số");
      return false;
    }
  
    // isDelete validation
    if (formData.isDelete === undefined || formData.isDelete === null) {
      toast.error("Trạng thái xóa không được để trống");
      return false;
    }
  
    // listBusEntities_Id validation
    // if (!Array.isArray(formData.listBusEntities_Id) || formData.listBusEntities_Id.length === 0) {
    //   toast.error("Danh sách ID xe buýt không được để trống");
    //   return false;
    // }
  
    // // listPenaltyTicketEntities_Id validation
    // if (!Array.isArray(formData.listPenaltyTicketEntities_Id) || formData.listPenaltyTicketEntities_Id.length === 0) {
    //   toast.error("Danh sách ID phiếu phạt không được để trống");
    //   return false;
    // }
  
    return true; // All validations passed
  };
  

  const handleSave = async () => {
    if (!validDataCheck()) return;

    setActionLoading(true);
    try {
      if (isEditMode) {
        // Gọi API để cập nhật
        const updatedEmployee = await updateEmployee(formData as Employee);
        setData((prevData) =>
          prevData.map((item) =>
            item.driverId === updatedEmployee.driverId ? updatedEmployee : item
          )
        );
        toast.success("Cập nhật nhân viên thành công", { autoClose: 800 });
      } else {
        // Gọi API để thêm mới
        const newEmployee = await createEmployee(formData as Employee);
        setData((prevData) => [...prevData, newEmployee]);
        toast.success("Thêm nhân viên thành công", { autoClose: 800 });
      }
      setOpenModal(false);
    } catch (err: any) {
      toast.error(err.message || "Lỗi khi lưu nhân viên");
    } finally {
      setActionLoading(false);
    }
  };

  const handleDelete = async (id: number) => {
    if (window.confirm("Bạn có chắc muốn xóa nhân viên này không?")) {
      setActionLoading(true);
      try {
        await deleteEmployee(id);
        setData((prevData) => prevData.filter((item) => item.driverId !== id));
        toast.success("Xóa nhân viên thành công", { autoClose: 800 });
      } catch (err: any) {
        toast.error(err.message || "Lỗi khi xóa nhân viên");
      } finally {
        setActionLoading(false);
      }
    }
  };

  const handleChange = (e: ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: name === 'isDriver' || name === 'isDelete' ? value === '1' : value,
    }));
  };
  const HandleExport = () => {
    // console.log("export",data)
    var wb = XLSX.utils.book_new(),
    ws = XLSX.utils.json_to_sheet(data);
    XLSX.utils.book_append_sheet(wb, ws, "SheetEmployees")
    XLSX.writeFile(wb, "ListEmployees.xlsx")
  }


  if (loading) return <div><Spinner aria-label="Default status example" /></div>;
  if (error) return toast.error(error, { autoClose: 800 });

  return (
    <div className='p-4 mx-auto'>
      <h1 className='uppercase font-semibold text-2xl tracking-wide mb-4'>
        Quản lý nhân viên
      </h1>
      <div className="flex justify-between">
      <Button onClick={() => handleOpenModal(null)} size="sm">
        <HiPlus className="mr-2 h-5 w-5" />
        Thêm Nhân viên
      </Button>
      <Button onClick={HandleExport} size="sm">
        <BiExport className="mr-2 h-5 w-5" />
        Xuất dữ liệu
      </Button>
      </div>
      <Table rows={data} columns={columns} onEdit={handleOpenModal} onDelete={(row) => handleDelete(row.driverId)} />
      <Modal show={openModal} onClose={() => setOpenModal(false)}>
        <Modal.Header>{isEditMode ? 'Cập nhật' : 'Thêm nhân viên'}</Modal.Header>
        <Modal.Body>
          <div className='space-y-6'>
            <div className='space-y-2'>
              <label htmlFor='driverName'>Tên nhân viên</label>
              <TextInput
                name='driverName'
                value={formData.driverName || ''}
                onChange={handleChange}
                placeholder='Nhập tên nhân viên'
              />
            </div>
            <div className='space-y-2'>
              <label htmlFor='isDriver'>Là tài xế</label>
              <Select
                name='isDriver'
                value={formData.isDriver ? '1' : '0'}
                onChange={handleChange}
              >
                <option value='0'>Không</option>
                <option value='1'>Có</option>
              </Select>
            </div>
            <div className='space-y-2'>
              <label htmlFor='licenseNumber'>Số giấy phép lái xe</label>
              <TextInput
                name='licenseNumber'
                value={formData.licenseNumber || ''}
                onChange={handleChange}
                placeholder='Nhập số giấy phép lái xe'
              />
            </div>
            <div className='space-y-2'>
              <label htmlFor='phoneNumber'>Số điện thoại</label>
              <TextInput
                name='phoneNumber'
                value={formData.phoneNumber || ''}
                onChange={handleChange}
                placeholder='Nhập số điện thoại'
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
