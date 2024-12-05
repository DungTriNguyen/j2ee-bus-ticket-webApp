import { FaHistory } from 'react-icons/fa';
import { useState } from 'react';
import { Button, Datepicker, Select } from 'flowbite-react';

const rows = [
  {
    name: 'BX100001',
    amount: 120000,
    content: 'Sài Gòn - Đà Lạt',
    date: '2024-10-01 08:30',
    status: 'Thành công',
  },
  {
    name: 'BX100002',
    amount: 100000,
    content: 'Hà Nội - Hải Phòng',
    date: '2024-10-02 14:45',
    status: 'Thành công',
  },
  {
    name: 'BX100003',
    amount: 150000,
    content: 'Sài Gòn - Nha Trang',
    date: '2024-10-03 10:15',
    status: 'Thất bại',
  },
  {
    name: 'BX100004',
    amount: 80000,
    content: 'Cần Thơ - Cà Mau',
    date: '2024-10-04 17:50',
    status: 'Thành công',
  },
  {
    name: 'BX100005',
    amount: 200000,
    content: 'Hà Nội - Sapa',
    date: '2024-10-05 09:20',
    status: 'Thành công',
  },
  {
    name: 'BX100006',
    amount: 180000,
    content: 'Đà Nẵng - Huế',
    date: '2024-10-06 13:35',
    status: 'Đang xử lý',
  },
  {
    name: 'BX100007',
    amount: 75000,
    content: 'Hải Phòng - Hạ Long',
    date: '2024-10-07 07:55',
    status: 'Thành công',
  },
  {
    name: 'BX100008',
    amount: 120000,
    content: 'Sài Gòn - Vũng Tàu',
    date: '2024-10-08 11:10',
    status: 'Thất bại',
  },
  {
    name: 'BX100009',
    amount: 150000,
    content: 'Đà Nẵng - Quảng Ngãi',
    date: '2024-10-09 16:40',
    status: 'Thành công',
  },
];

export default function AnhBaPay() {
  const [selectedStatus, setSelectedStatus] = useState('All');

  return (
    <>
      <div className='flex bg-teal-100 p-4 rounded-2xl mb-7 justify-between'>
        <div className='flex flex-col'>
          <h1 className='font-bold mb-3'> Số dư ví</h1>
          <h1 className='text-red-600 scale-150 m-2 font-bold'>
            10 <u>đ</u>
          </h1>
        </div>
        <div className='ml-auto flex items-center gap-2 flex-col'>
          <FaHistory className='text-[#74C0FC] text-xl' />
          <h2>Giao dịch</h2>
        </div>
      </div>

      <div className='flex flex-col gap-4'>
        <h1 className='font-bold'>Lịch sử giao dịch</h1>

        {/* Filter Options */}
        <div className='flex justify-between bg-gray-200 shadow-sm border rounded-2xl p-4'>
          <div className='flex items-center gap-2'>
            <span className='mx-2'>Từ</span>
            <Datepicker />
            <span className='mx-2'>đến</span>
            <Datepicker />
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
            <Button size={'sm'}>
              Lọc
            </Button>
          </div>
        </div>

        {/* Transaction Table */}
        <div className='overflow-x-auto'>
          <table className='min-w-full bg-white rounded-2xl shadow-md'>
            <thead>
              <tr className='bg-teal-700 text-white'>
                <th className='p-4 text-left font-bold'>Mã giao dịch</th>
                <th className='p-4 text-left font-bold'>Chuyến đi</th>
                <th className='p-4 text-left font-bold'>Số tiền</th>
                <th className='p-4 text-left font-bold'>Thời gian</th>
                <th className='p-4 text-left font-bold'>Trạng thái</th>
              </tr>
            </thead>
            <tbody>
              {rows.map((row, index) => (
                <tr
                  key={row.name}
                  className={`${index % 2 === 0 ? 'bg-gray-50' : 'bg-white'} border-b`}
                >
                  <td className='p-4'>{row.name}</td>
                  <td className='p-4 text-left'>{row.content}</td>
                  <td className='p-4 text-left'>{row.amount.toLocaleString()} đ</td>
                  <td className='p-4 text-left'>{row.date}</td>
                  <td className='p-4 text-left'>{row.status}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </>
  );
}
