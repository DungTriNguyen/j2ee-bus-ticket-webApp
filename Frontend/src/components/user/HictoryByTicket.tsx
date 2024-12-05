import { useState } from 'react';
import { Datepicker, Select } from 'flowbite-react';

const columns = [
  { id: 'code', label: 'Mã vé' },
  { id: 'router', label: 'Tuyến đường' },
  { id: 'numberof', label: 'Số vé' },
  { id: 'date', label: 'Ngày đi' },
  {
    id: 'money',
    label: 'Số tiền',
    format: (value) => value.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' }),
  },
  { id: 'payment', label: 'Thanh toán' },
  { id: 'status', label: 'Trạng thái' },
];

const rows = [
  {
    code: 'V001',
    numberof: 2,
    router: 'Hà Nội - Hải Phòng',
    date: '2024-10-30',
    money: 300000,
    payment: 'ZaloPay',
    status: 'Đã thanh toán',
  },
  {
    code: 'V002',
    numberof: 1,
    router: 'Hà Nội - Đà Nẵng',
    date: '2024-10-31',
    money: 800000,
    payment: 'Chuyển khoản',
    status: 'Đã thanh toán',
  },
  {
    code: 'V003',
    numberof: 3,
    router: 'Hà Nội - TP.HCM',
    date: '2024-11-01',
    money: 1500000,
    payment: 'AnhBaPay',
    status: 'Chưa thanh toán',
  },
];

export default function HictoryByTicket() {
  const [selectedStatus, setSelectedStatus] = useState('All');

  return (
    <>
      <div className='flex bg-teal-100 p-4 rounded-2xl mb-7 justify-between'>
        <div className='flex flex-col'>
          <h1 className='font-bold mb-3'>Lịch sử mua vé</h1>
          <h2>Theo dõi và quản lý quá trình lịch sử mua vé của bạn</h2>
        </div>
        <div className='bg-teal-600 hover:bg-teal-800 rounded-2xl'>
          <button className='m-4 px-7 text-white'>Đặt vé</button>
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
              <option value='All'>Chọn trạng thái...</option>
              <option value='Khởi tạo'>Khởi tạo</option>
              <option value='Chờ duyệt'>Chờ duyệt</option>
              <option value='Hủy bỏ'>Hủy bỏ</option>
              <option value='Đã duyệt'>Đã duyệt</option>
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
    </>
  );
}
