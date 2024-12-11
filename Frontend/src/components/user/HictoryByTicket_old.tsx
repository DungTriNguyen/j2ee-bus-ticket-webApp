import { Datepicker, Select } from 'flowbite-react';
import { useState, useEffect, ChangeEvent, useRef } from "react";
import { FaStar } from "react-icons/fa";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import type { Ticket } from '@type/model/Ticket';
import { Button, Checkbox, Label, Modal, TextInput } from "flowbite-react";
import { getTickets, getListTicketDateAToDateB } from '../../api/services/admin/ticketService';
import { getPayments } from '../../api/services/admin/paymentService';
import { getBuses } from '../../api/services/admin/busService';
import { getBusRoutes } from '../../api/services/admin/busRouteService';


const columns = [
  // { id: 'code', label: 'Mã vé' },
  // { id: 'router', label: 'Tuyến đường' },
  // { id: 'numberof', label: 'Số vé' },
  // { id: 'date', label: 'Ngày đi' },
  // {
  //   id: 'money',
  //   label: 'Số tiền',
  //   format: (value) => value.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' }),
  // },
  // { id: 'payment', label: 'Thanh toán' },
  // { id: 'status', label: 'Trạng thái' },


  { name: 'Mã Vé', label: 'Mã Vé' },
  { name: 'Mã Tuyến', label: 'Mã Tuyến' },
  { name: 'Mã Xe buýt', label: 'Mã Xe buýt' },
  { name: 'Ngày khởi hành', label: 'Ngày khởi hành' },
  {
    name: 'Giá tiền',
    label: 'Giá tiền',
    format: (value) => value.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' }),
  },
  { name: 'Số ghế', label: 'Trạng thái' },
  { name: 'Trạng thái', label: 'Thanh toán' },

  
];

// const rows = [
//   {
//     code: 'V001',
//     numberof: 2,
//     router: 'Hà Nội - Hải Phòng',
//     date: '2024-10-30',
//     money: 300000,
//     payment: 'ZaloPay',
//     status: 'Đã thanh toán',
//   },
//   {
//     code: 'V002',
//     numberof: 1,
//     router: 'Hà Nội - Đà Nẵng',
//     date: '2024-10-31',
//     money: 800000,
//     payment: 'Chuyển khoản',
//     status: 'Đã thanh toán',
//   },
//   {
//     code: 'V003',
//     numberof: 3,
//     router: 'Hà Nội - TP.HCM',
//     date: '2024-11-01',
//     money: 1500000,
//     payment: 'AnhBaPay',
//     status: 'Chưa thanh toán',
//   },
// ];

export default function HictoryByTicket() {
  const [dateA, setDateA] = useState(""); // Ngày bắt đầu
  const [dateB, setDateB] = useState(""); // Ngày kết thúc

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
  const [selectedStatus, setSelectedStatus] = useState('All');


  // Lấy dữ liệu ticket từ API
  const fetchTickets = async () => {
    if (!dateA || !dateB) {
      toast.error("Vui lòng nhập đầy đủ ngày bắt đầu và ngày kết thúc!", { autoClose: 800 });
      return;
    }
    try {
      const tickets = await getListTicketDateAToDateB(dateA, dateB);
      setData(tickets);
      console.log("hahaha",tickets);
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
      const paymentData = await getPayments();
      setBus(busData);
      setBusRouter(routeData);
      setPayments(paymentData);
    } catch (error: any) {
      toast.error("Lỗi khi tải dữ liệu liên quan.", { autoClose: 800 });
    }
  };

  useEffect(() => {
    fetchTickets();
    fetchRelatedData();
  }, []);

  return (
    <>
      <section>
          
      <div className='flex bg-teal-100 p-4 rounded-2xl mb-7 justify-between'>
        <div className='flex flex-col'>
          <h1 className='font-bold mb-3'>Lịch sử mua vé</h1>
          <h2>Theo dõi và quản lý quá trình lịch sử mua vé của bạn</h2>
        </div>
        <div className='bg-teal-600 hover:bg-teal-800 rounded-2xl'>
          <button className='m-4 px-7 text-white'><a href="/lich-trinh" className="">Đặt vé</a></button>
        </div>
      </div>

      <div className='flex flex-col gap-4'>
        <h1 className='font-bold'>Lịch sử giao dịch</h1>

        {/* Filter Options */}
        <div className='flex justify-between bg-gray-200 shadow-sm border rounded-2xl p-4'>
          <div className='flex items-center gap-2'>
            <span className='mx-2'>Từ</span>
            <Datepicker placeholder='Chọn ngày' />
            <span className='mx-2'>đến</span>
            <Datepicker placeholder='Chọn ngày' />
          </div>
          <div className='text-center'>
            <Select value={selectedStatus} onChange={(e) => setSelectedStatus(e.target.value)}>
              <option value='' disabled>Chọn trạng thái...</option>
              <option value='success'>Đã thanh toán</option>
              <option value='pending'>Chờ thanh toán</option>
              <option value='failure'>Hủy bỏ</option>
            </Select>
          </div>
          <div className='text-center'>
            <button className='bg-teal-600 text-white hover:bg-teal-800 px-4 py-2 rounded-2xl'>
              Tìm
            </button>
          </div>
        </div>

        {/* Transaction Table */}
        <div className='overflow-x-auto'>
          <table className='min-w-full bg-white rounded-2xl shadow-md'>
            <thead>
              <tr className='bg-teal-700 text-white'>
                {columns.map((column) => (
                  <th key={column.id} className='p-4 text-left font-bold'>
                    {column.label}
                  </th>
                ))}
              </tr>
            </thead>
            <tbody>
              {rows.map((row, index) => (
                <tr
                  key={row.code}
                  className={`${index % 2 === 0 ? 'bg-gray-50' : 'bg-white'} border-b`}
                >
                  <td className='p-4'>{row.code}</td>
                  <td className='p-4'>{row.router}</td>
                  <td className='p-4'>{row.numberof}</td>
                  <td className='p-4'>{row.date}</td>
                  <td className='p-4'>{columns[4].format(row.money)}</td>
                  <td className='p-4'>{row.payment}</td>
                  <td className='p-4'>{row.status}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
      </section>
    </>
  );
}
