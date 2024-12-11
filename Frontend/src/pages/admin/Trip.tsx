import { useEffect, useState, ChangeEvent } from 'react';
import Table from '../../components/admin/Table';
import { Button, HR, Modal, Select, TextInput, Spinner } from 'flowbite-react';
import { ToastContainer, toast } from 'react-toastify';
import { HiPlus } from 'react-icons/hi';
import type { TableColumn } from '@type/common/TableColumn';
import type { EmployeeBus } from '@type/model/EmployeeBus';
import { updateEmployeeBus, deleteEmployeeBus, getEmployeeBus } from '../../api/services/admin/tripService';
import { getBuses } from '../../api/services/admin/busService';
import { getEmployees } from '../../api/services/admin/employeeService';

export default function Trip() {
  const [data, setData] = useState<EmployeeBus[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [actionLoading, setActionLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);

  const [openModal, setOpenModal] = useState<boolean>(false);
  const [isEditMode, setIsEditMode] = useState<boolean>(false);
  const [formData, setFormData] = useState<Partial<EmployeeBus>>({});

  const [bus, setBus] = useState<{ busId: number }[]>([]);
  const [employees, setEmployees] = useState<{ driverId: number }[]>([]);

  const columns: TableColumn<EmployeeBus>[] = [
    { name: 'Mã xe', selector: (row) => row.busId, sortable: true },
    { name: 'Mã nhân viên', selector: (row) => row.driverId, sortable: true },
    {
      name: 'Hành động',
      cell: (row) => (
        <>
          {/* <Button size="xs" onClick={() => handleOpenModal(row)}>
            Sửa
          </Button> */}
          <Button size="xs" color="red" onClick={() => handleDelete(row.busId, row.driverId)}>
            Xóa
          </Button>
        </>
      ),
    },
  ];

//   const fetchEmployeeBuses = async () => {
//     try {
//       const busEmployeeData = await getEmployeeBus();
//       // Chuyển đổi object thành array:
//     //     const formattedData = Object.entries(busEmployeeData).map(([key, value]) => ({
//     //     key: Number(key),
//     //     value: Number(value),
//     // }));
//       console.log("Formatted Data:", busEmployeeData);
//       setData(busEmployeeData);
//     } catch (error: any) {
//       setError(error.message);
//     } finally {
//       setLoading(false);
//     }
//   };

  const fetchEmployeeBuses = async () => {
    try {
      const busEmployeeData = await getEmployeeBus();
      const formattedData: EmployeeBus[] = Object.entries(busEmployeeData).map(([key, value]) => ({
        busId: Number(key),  // key sẽ là busId
        driverId: Number(value), // value sẽ là driverId
      }));
      setData(formattedData);
    } catch (error: any) {
      setError(error.message);
    } finally {
      setLoading(false);
    }
  };

  const fetchBuses = async () => {
    try {
      const busData = await getBuses();
      setBus(busData);
    } catch (error: any) {
      setError(error.message);
    }
  };

  const fetchEmployees = async () => {
    try {
      const employeeData = await getEmployees();
      setEmployees(employeeData);
    } catch (error: any) {
      setError(error.message);
    }
  };

  useEffect(() => {
    fetchEmployeeBuses();
    fetchBuses();
    fetchEmployees();
  }, []);

  const handleOpenModal = (item: EmployeeBus | null = null) => {
    setIsEditMode(item !== null);
    setFormData(item || { busId: 0, driverId: 0 });
    setOpenModal(true);
  };

  const validDataCheck = (): boolean => {
    if (!formData.driverId || !formData.busId) {
      toast.error('Vui lòng chọn cả mã nhân viên và mã xe đầy đủ', { autoClose: 800 });
      return false;
    }
    return true;
  };

  const handleSave = async () => {
    if (!validDataCheck()) return;
    setActionLoading(true);

    try {
      if (isEditMode) {
        // Sửa lịch phân công
        const result = await updateEmployeeBus(formData.busId!, formData.driverId!);
        setData((prevData) =>
          prevData.map((item) =>
            item.driverId === formData.driverId && item.busId === formData.busId ? result : item
          )
        );
        toast.success('Cập nhật lịch phân công thành công', { autoClose: 800 });
      } else {
        // Thêm mới lịch phân công
        const result = await updateEmployeeBus(formData.busId!, formData.driverId!);
        setData((prevData) => [...prevData, result]);
        toast.success('Thêm lịch phân công thành công', { autoClose: 800 });
      }
      setOpenModal(false);
    } catch (err: any) {
      const errorMessage = err.response?.data?.message || 'Đã xảy ra lỗi khi lưu lịch phân công';
      toast.error(errorMessage, { autoClose: 800 });
    } finally {
      setActionLoading(false);
    }
  };

  const handleDelete = async (busId: number, driverId: number) => {
    if (window.confirm('Bạn có chắc muốn xóa lịch phân công này không?')) {
      setActionLoading(true);
      try {
        await deleteEmployeeBus(busId, driverId);
        setData((prevData) =>
          prevData.filter((item) => !(item.driverId === driverId && item.busId === busId))
        );
        toast.success('Xóa lịch phân công thành công', { autoClose: 800 });
      } catch (err: any) {
        const errorMessage = err.response?.data?.message || 'Đã xảy ra lỗi khi xóa lịch phân công';
        toast.error(errorMessage, { autoClose: 800 });
      } finally {
        setActionLoading(false);
      }
    }
  };

  const handleChange = (e: ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: name === 'driverId' || name === 'busId' ? Number(value) : value,
    }));
  };

  if (loading) return <div><Spinner aria-label="Loading" /></div>;
  if (error) return toast.error(error, { autoClose: 800 });

  return (
    <div className="p-4 mx-auto">
      <h1 className="uppercase font-semibold text-2xl tracking-wide mb-4">Quản lý lịch phân công</h1>
      <Button onClick={() => handleOpenModal()} size="sm">
        <HiPlus className="mr-2 h-5 w-5" />
        Thêm lịch phân công
      </Button>
      <Table rows={data} columns={columns} />
      <Modal show={openModal} onClose={() => setOpenModal(false)}>
        <Modal.Header>{isEditMode ? 'Chỉnh sửa' : 'Thêm mới'} lịch phân công</Modal.Header>
        <Modal.Body>
          <div className="space-y-6">
            
            <div className="space-y-2 flex flex-col">
              <label htmlFor="busId">Mã xe</label>
              <select
                id="busId"
                className="rounded-lg border-gray-200 bg-gray-50"
                name="busId"
                value={formData.busId || ''}
                onChange={handleChange}
              >
                <option value="" disabled>
                  Chọn mã xe
                </option>
                {bus.map((b) => (
                  <option key={b.busId} value={b.busId}>
                    {b.busId}
                  </option>
                ))}
              </select>
            </div>
            <div className="space-y-2 flex flex-col">
              <label htmlFor="driverId">Mã nhân viên</label>
              <select
                id="driverId"
                className="rounded-lg border-gray-200 bg-gray-50"
                name="driverId"
                value={formData.driverId || ''}
                onChange={handleChange}
              >
                <option value="" disabled>
                  Chọn mã nhân viên
                </option>
                {employees.map((employee) => (
                  <option key={employee.driverId} value={employee.driverId}>
                    {employee.driverId}
                  </option>
                ))}
              </select>
            </div>
          </div>
          <HR className="my-4" />
          <div className="flex flex-row-reverse gap-2">
            <Button onClick={handleSave} disabled={actionLoading}>
              {actionLoading ? 'Đang xử lý...' : 'Lưu'}
            </Button>
            <Button color="gray" onClick={() => setOpenModal(false)}>
              Hủy
            </Button>
          </div>
        </Modal.Body>
      </Modal>
      <ToastContainer />
    </div>
  );
}
