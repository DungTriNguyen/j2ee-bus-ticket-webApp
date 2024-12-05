import { useEffect, useState, ChangeEvent } from 'react';
import Table from '../../components/admin/Table';
import { Button, HR, Modal, Select, TextInput, Textarea } from 'flowbite-react';
import { ToastContainer, toast } from 'react-toastify';
import { HiPlus } from 'react-icons/hi';
import { Spinner } from "flowbite-react";
import type { TableColumn } from '@type/common/TableColumn';
import type { PenaltyTicket } from '@type/model/PenaltyTicket';

import { getPenaltyTickets, createPenaltyTicket, updatePenaltyTicket, deletePenaltyTicket } from '../../api/services/admin/penaltyTicketService';
import { getBuses } from '../../api/services/admin/busService';
import { getEmployees } from '../../api/services/admin/employeeService';
import { formatDate } from '../../utils/dateFormat';

export default function PenaltyTicket() {
  const [data, setData] = useState<PenaltyTicket[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [actionLoading, setActionLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);
  const [openModal, setOpenModal] = useState<boolean>(false);
  const [isEditMode, setIsEditMode] = useState<boolean>(false);
  const [formData, setFormData] = useState<Partial<PenaltyTicket>>({});

  const [buses, setBuses] = useState<{ busId: number }[]>([]);
  const [employees, setEmployees] = useState<{ driverId: number }[]>([]);

  const columns: TableColumn<PenaltyTicket>[] = [
    { name: 'ID Biên bản', selector: (row) => row.penaltyTicketId, sortable: true },
    { name: 'ID Xe buýt', selector: (row) => row.busEntity_Id, sortable: true },
    { name: 'ID Nhân viên', selector: (row) => row.employeeEntity_Id, sortable: true },
    { name: 'Ngày xử phạt', selector: (row) => formatDate(row.penaltyDay) , sortable: true },
    { name: 'Mô tả', selector: (row) => row.description, sortable: false },
    { name: 'Trách nhiệm', selector: (row) => (row.responsibility ? 'Có' : 'Không'), sortable: true },
    { name: 'Giá tiền', selector: (row) => row.price.toLocaleString('vi-VN') + 'đ', sortable: true },
    { name: 'Tình trạng', selector: (row) => (row.isDelete ? 'Không hiển thị' : 'Hiển thị'), sortable: true },
  ];

  const fetchPenaltyTickets = async () => {
    try {
      const penaltyTickets = await getPenaltyTickets();
      setData(penaltyTickets);
    } catch (err: any) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const fetchBuses = async () => {
    try {
      const busData = await getBuses();
      setBuses(busData);
    } catch (err: any) {
      toast.error('Lỗi khi tải danh sách xe buýt', { autoClose: 800 });
    }
  };

  const fetchEmployees = async () => {
    try {
      const employeeData = await getEmployees();
      setEmployees(employeeData);
    } catch (err: any) {
      toast.error('Lỗi khi tải danh sách nhân viên', { autoClose: 800 });
    }
  };

  useEffect(() => {
    fetchPenaltyTickets();
    fetchBuses();
    fetchEmployees();
  }, []);

  const handleOpenModal = (item: PenaltyTicket | null = null) => {
    if (item) {
      setIsEditMode(true);
      setFormData(item);
    } else {
      setIsEditMode(false);
      setFormData({
        penaltyTicketId: 0,
        busEntity_Id: 0,
        employeeEntity_Id: 0,
        penaltyDay: '',
        description: '',
        responsibility: true,
        price: 0,
        isDelete: false,
      });
    }
    setOpenModal(true);
  };

  const validDataCheck = (): boolean => {
    // Check penaltyTicketId if in edit mode
    if (isEditMode && (!formData.penaltyTicketId || formData.penaltyTicketId <= 0)) {
      toast.error('ID biên bản không hợp lệ', { autoClose: 800 });
      return false;
    }
  
    // Validate busEntity_Id
    if (!formData.busEntity_Id || formData.busEntity_Id <= 0) {
      toast.error('ID xe buýt không được để trống và phải lớn hơn 0', { autoClose: 800 });
      return false;
    }
  
    // Validate employeeEntity_Id
    if (!formData.employeeEntity_Id || formData.employeeEntity_Id <= 0) {
      toast.error('ID nhân viên không được để trống và phải lớn hơn 0', { autoClose: 800 });
      return false;
    }
  
    // Validate penaltyDay
    if (!formData.penaltyDay) {
      toast.error('Ngày xử phạt không được để trống', { autoClose: 800 });
      return false;
    }
  
    // Validate description
    if (!formData.description || formData.description.trim().length === 0) {
      toast.error('Mô tả không được để trống', { autoClose: 800 });
      return false;
    }
    if (formData.description.length > 500) {
      toast.error('Mô tả không được vượt quá 500 ký tự', { autoClose: 800 });
      return false;
    }
  
    // Validate responsibility
    
  
    // Validate price
    if (formData.price === undefined || formData.price < 0) {
      toast.error('Giá tiền phải lớn hơn hoặc bằng 0', { autoClose: 800 });
      return false;
    }
  
    // Validate isDelete
    if (formData.isDelete === undefined || formData.isDelete === null) {
      toast.error('Trạng thái xóa không được để trống', { autoClose: 800 });
      return false;
    }
  
    return true;
  };
  

  const handleSave = async () => {
    if (!validDataCheck()) {
      return;
    }
    setActionLoading(true);
    try {
      if (isEditMode) {
        const result = await updatePenaltyTicket(formData as PenaltyTicket);
        setData((prevData) =>
          prevData.map((item) =>
            item.penaltyTicketId === result.penaltyTicketId ? result : item
          )
        );
        toast.success('Cập nhật biên bản thành công', { autoClose: 800 });
      } else {
        const result = await createPenaltyTicket(formData as PenaltyTicket);
        setData((prevData) => [...prevData, result]);
        toast.success('Thêm biên bản thành công', { autoClose: 800 });
      }
      setOpenModal(false);
    } catch (err: any) {
      toast.error(err.message || 'Lỗi khi lưu biên bản xử phạt', { autoClose: 800 });
    } finally {
      setActionLoading(false);
    }
  };

  const handleDelete = async (id: number) => {
    if (window.confirm('Bạn có chắc muốn xóa biên bản này không?')) {
      setActionLoading(true);
      try {
        await deletePenaltyTicket(id);
        setData((prevData) => prevData.filter((item) => item.penaltyTicketId !== id));
        toast.success('Xóa biên bản thành công', { autoClose: 800 });
      } catch (err: any) {
        toast.error(err.message || 'Lỗi khi xóa biên bản', { autoClose: 800 });
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
      [name]: name === 'price' ? Number(value) : name === 'responsibility' || name === 'isDelete' ? value === '1' : value,
    }));
  };

  if (loading) return <div><Spinner aria-label="Default status example" /></div>;
  if (error) return toast.error(error, { autoClose: 800 });

  return (
    <div className="p-4 mx-auto">
      <h1 className="uppercase font-semibold text-2xl tracking-wide mb-4">
        Quản lý biên bản xử phạt
      </h1>
      <Button onClick={() => handleOpenModal(null)} size="sm">
        <HiPlus className="mr-2 h-5 w-5" />
        Thêm biên bản
      </Button>
      <Table
        rows={data}
        columns={columns}
        onEdit={handleOpenModal}
        onDelete={(row) => handleDelete(row.penaltyTicketId)}
      />
      <Modal show={openModal} onClose={() => setOpenModal(false)}>
        <Modal.Header>{isEditMode ? 'Cập nhật' : 'Thêm biên bản'}</Modal.Header>
        <Modal.Body>
          <div className="space-y-6">
            <div className="space-y-2 flex flex-col">
              <label htmlFor="busEntity_Id">ID Xe buýt</label>
              <Select
                name="busEntity_Id"
                value={formData.busEntity_Id || ''}
                onChange={handleChange}
              >
                <option value="" disabled>
                  Chọn xe buýt
                </option>
                {buses.map((bus) => (
                  <option key={bus.busId} value={bus.busId}>
                    {bus.busId}
                  </option>
                ))}
              </Select>
            </div>
            <div className="space-y-2 flex flex-col">
              <label htmlFor="employeeEntity_Id">ID Nhân viên</label>
              <Select
                name="employeeEntity_Id"
                value={formData.employeeEntity_Id || ''}
                onChange={handleChange}
              >
                <option value="" disabled>
                  Chọn nhân viên
                </option>
                {employees.map((employee) => (
                  <option key={employee.driverId} value={employee.driverId}>
                    {employee.driverId}
                  </option>
                ))}
              </Select>
            </div>
            <div className="space-y-2">
              <label htmlFor="penaltyDay">Ngày xử phạt</label>
              <TextInput
                name="penaltyDay"
                type="datetime-local"
                value={formData.penaltyDay || ''}
                onChange={handleChange}
              />
            </div>
            <div className="space-y-2">
              <label htmlFor="description">Mô tả</label>
              <Textarea
                name="description"
                value={formData.description || ''}
                onChange={handleChange}
                placeholder='Nhập mô tả thông tin phạt'
              />
            </div>
            <div className="space-y-2">
              <label htmlFor="price">Giá tiền</label>
              <TextInput
                name="price"
                type="number"
                value={formData.price || ''}
                onChange={handleChange}
                placeholder='Nhập giá tiền phạt'
              />
            </div>
            {/* <div className="space-y-2">
              <label htmlFor="responsibility">Trách nhiệm</label>
              <Select
                name="responsibility"
                value={formData.responsibility ? '1' : '0'}
                onChange={handleChange}
              >
                <option value="0">Không</option>
                <option value="1">Có</option>
              </Select>
            </div> */}
            <div className="space-y-2">
              <label htmlFor="isDelete">Tình trạng</label>
              <Select
                name="isDelete"
                value={formData.isDelete ? '1' : '0'}
                onChange={handleChange}
              >
                <option value="0">Hiển thị</option>
                <option value="1">Không hiển thị</option>
              </Select>
            </div>
          </div>
        </Modal.Body>
        <Modal.Footer>
          <Button color="light" onClick={() => setOpenModal(false)}>
            Hủy
          </Button>
          <Button onClick={handleSave} disabled={actionLoading}>
            {actionLoading ? 'Đang xử lý...' : 'Lưu'}
          </Button>
        </Modal.Footer>
      </Modal>
      <ToastContainer />
    </div>
  );
}
