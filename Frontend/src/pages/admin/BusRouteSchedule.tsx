import { useEffect, useState, ChangeEvent } from 'react';
import Table from '../../components/admin/Table';
import { Button, HR, Modal, Select, TextInput, Textarea } from 'flowbite-react';
import { ToastContainer, toast } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";
import { HiPlus } from 'react-icons/hi';
import { Spinner } from "flowbite-react";



import type { TableColumn } from '@type/common/TableColumn';
import type { BusRouteSchedule } from '@type/model/BusRouteSchedule';

// Import các API cần thiết
import { getBusRouteSchedules, createBusRouteSchedule, updateBusRouteSchedule, deleteBusRouteSchedule  } from '../../api/services/admin/busRouteScheduleService';
import { getBuses } from '../../api/services/admin/busService';
import { getBusRoutes } from '../../api/services/admin/busRouteService';
// import { formatDate } from '../../utils/dateFormat';

export default function Ticket() {
  const [data, setData] = useState<BusRouteSchedule[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [actionLoading, setActionLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);
  const [openModal, setOpenModal] = useState<boolean>(false);
  const [isEditMode, setIsEditMode] = useState<boolean>(false);
  const [formData, setFormData] = useState<Partial<BusRouteSchedule>>({});

  // State cho các dropdown liên quan
  // trạng thái danh sách busRouterID
  const [busRouter, setBusRouter] = useState<{ routesId: number }[]>([]);
  // trạng thái danh sách busID
  const [bus, setBus] = useState<{ busId: number }[]>([]);


  const formatDayOfWeek = (date: string): string => {
    const daysOfWeek = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
    const parsedDate = new Date(date); // Chuyển đổi chuỗi ngày thành đối tượng Date
    return daysOfWeek[parsedDate.getDay()]; // Lấy thứ trong tuần từ Date
  };

  // Cấu trúc cột bảng ticket
  const columns: TableColumn<BusRouteSchedule>[] = [
    { name: 'ID Lịch phân xe-tuyến', selector: (row) => row.scheduleId, sortable: true },
    { name: 'Ngày trong tuần', selector: (row) => row.dayOfWeek, sortable: true },
    { name: 'ID Xe', selector: (row) => row.busEntity_Id, sortable: true },
    { name: 'ID tuyến', selector: (row) => row.busRoutesEntity_Id, sortable: true },
    { name: 'Thời gian khởi hành', selector: (row) => row.departureTime , sortable: true },
    // { name: 'Số điện thoại', selector: (row) => row.isDelete, sortable: true },
    // { name: 'Trạng thái', selector: (row) => row.status.toUpperCase(), sortable: true },
    // { name: 'Giá tiền', selector: (row) => row.price.toLocaleString('vi-VN') + 'đ', sortable: true },
    {
      name: 'Tình trạng',
      selector: (row) => (row.isDelete ? 'Không hiển thị' : 'Hiển thị'),
      sortable: true,
    }
  ];

  // Lấy dữ liệu ticket từ API
  const fetchBusRouteSchedule = async () => {
    try {
      const busRouteSchedules = await getBusRouteSchedules();
      console.log(">>>>>",busRouteSchedules)
      setData(busRouteSchedules);
    } catch (error: any) {
      setError(error.message);
    } finally {
      setLoading(false);
    }
  };

  // Lấy dữ liệu từ các API liên quan
  const fetchRelatedData = async () => {
    try {
      const busData = await getBuses();
      const routeData = await getBusRoutes();
      setBus(busData);
      setBusRouter(routeData);
    } catch (error: any) {
      toast.error("Lỗi khi tải dữ liệu liên quan.", { autoClose: 800 });
    }
  };

  useEffect(() => {
    fetchBusRouteSchedule();
    fetchRelatedData();
  }, []);

  // Mở form thêm/sửa BusRouteSchedule
  const handleOpenModal = (item: BusRouteSchedule | null = null) => {
    if (item) {
      setIsEditMode(true);
      setFormData(item);
    } else {
      setIsEditMode(false);
      setFormData({
        scheduleId: 0,
        dayOfWeek: '',
        busEntity_Id: 0,
        busRoutesEntity_Id: 0,
        departureTime: '',
        isDelete: false,
        listTicketEntities_Id: [],
      });
    }
    setOpenModal(true);
  };

  // hàm kiểm tra dữ liệu đầu vào để ràng buộc dữ liệu gửi về backend
  const validDataCheck = (): boolean => {

  
    // Kiểm tra dayOfWeek
    if (
      !formData.dayOfWeek ||
      !/^(Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday)$/.test(formData.dayOfWeek)
    ) {
      toast.error("Thứ trong tuần không hợp lệ. Giá trị hợp lệ: Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday", { autoClose: 800 });
      return false;
    }
  
    // Kiểm tra busEntity_Id
    if (!formData.busEntity_Id || formData.busEntity_Id <= 0) {
      toast.error("ID xe buýt không được để trống", { autoClose: 800 });
      return false;
    }
  
    // Kiểm tra busRoutesEntity_Id
    if (!formData.busRoutesEntity_Id || formData.busRoutesEntity_Id <= 0) {
      toast.error("ID tuyến xe không được để trống", { autoClose: 800 });
      return false;
    }
  
    // Kiểm tra departureTime
    if (!formData.departureTime || formData.departureTime.trim().length === 0) {
      toast.error("Thời gian khởi hành không được để trống", { autoClose: 800 });
      return false;
    }
  
    // Kiểm tra isDelete
    if (formData.isDelete == null) {
      toast.error("Trạng thái xóa không được để trống", { autoClose: 800 });
      return false;
    }
  
    // Nếu tất cả các kiểm tra đều hợp lệ
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
        const updatedTicket = await updateBusRouteSchedule(formData as BusRouteSchedule);
        toast.success("Cập nhật xe - tuyến thành công", { autoClose: 800 });
        // Cập nhật danh sách vé
        setData((prev) =>
          prev.map((item) =>
            item.scheduleId === updatedTicket.scheduleId ? updatedTicket : item
          )
        );
      } else {
        // Gọi API để thêm ticket
        const newTicket = await createBusRouteSchedule(formData as BusRouteSchedule);
        toast.success("Thêm xe - tuyến thành công", { autoClose: 800 });
        // Thêm vé mới vào danh sách
        setData((prev) => [...prev, newTicket]);
      }
      setOpenModal(false); // Đóng modal sau khi thêm/sửa
    } catch (error: any) {
      toast.error(error.message || "Lỗi khi lưu xe - tuyến", { autoClose: 800 });
    } finally {
      setActionLoading(false); // Kết thúc trạng thái loading
    }
  };
  

  // Xóa một ticket
  const handleDelete = async (id: number) => {
    console.log("scheduleId:", id); // Kiểm tra giá trị id
    if (window.confirm("Bạn có chắc muốn xóa xe - tuyến này không?")) {
      setActionLoading(true); // Bắt đầu trạng thái loading
      try {
        const result = await deleteBusRouteSchedule(id); // Gọi API xóa
        console.log(">>>>", result);
        // Loại bỏ vé khỏi danh sách
        setData((prev) => prev.filter((item) => item.scheduleId !== id));
        toast.success("Xóa xe - tuyến thành công", { autoClose: 800 });
      } catch (error: any) {
        toast.error(error.message || "Lỗi khi xóa xe - tuyến", { autoClose: 800 });
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
      [name]: name === 'busEntity_Id' ||  name === 'busRoutesEntity_Id'
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
      <h1 className='uppercase font-semibold text-2xl tracking-wide mb-4'>Quản lý xe - tuyến</h1>
      <Button onClick={() => handleOpenModal()} size='sm'>
        <HiPlus className='mr-2 h-5 w-5' />
        Thêm xe - tuyến 
      </Button>
      <Table rows={data} columns={columns} onEdit={handleOpenModal} onDelete={(row) => handleDelete(row.scheduleId)}/>
      <Modal show={openModal} onClose={() => setOpenModal(false)}>
        <Modal.Header>{isEditMode ? 'Cập nhật' : 'Thêm xe - tuyến'}</Modal.Header>
        <Modal.Body>
          <div className='space-y-6'>
          {/* <div className='space-y-2 flex flex-col'>
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
            </div> */}
            <div className='space-y-2'>
              <label htmlFor='dayOfWeek'>Thứ trong tuần</label>
              <Select
                name='dayOfWeek'
                value={formData.dayOfWeek || ''}
                onChange={handleChange}
              >
                <option value="" disabled>Chọn thứ</option>
                <option value="Monday">Thứ Hai</option>
                <option value="Tuesday">Thứ Ba</option>
                <option value="Wednesday">Thứ Tư</option>
                <option value="Thursday">Thứ Năm</option>
                <option value="Friday">Thứ Sáu</option>
                <option value="Saturday">Thứ Bảy</option>
                <option value="Sunday">Chủ Nhật</option>
              </Select>
            </div>
            <div className='space-y-2 flex flex-col'>
              <label htmlFor='busEntity_Id'>ID Xe</label>
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
              <label htmlFor='departureTime'>Ngày khởi hành</label>
              <TextInput
                name='departureTime'
                type='time'
                value={formData.departureTime || ''}
                onChange={handleChange}
                placeholder='Chọn ngày khởi hành'
              />
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
