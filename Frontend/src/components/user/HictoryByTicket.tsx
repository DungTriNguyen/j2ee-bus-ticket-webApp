import { useState } from "react";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import type { Ticket } from '@type/model/Ticket';
import { getListTicketDateAToDateBAndUser } from '../../api/services/admin/ticketService';
import { useSelector } from "react-redux";
import { RootState } from "../../redux/store";


const columns = [
  { id: 'ticketId', label: 'Mã Vé' },
  { id: 'busRouteSchedule_Id', label: 'Mã Xe - Tuyến' },
  // { id: 'routeId', label: 'Mã Tuyến' },
  // { id: 'busId', label: 'Mã Xe buýt' },
  { id: 'departureDate', label: 'Ngày khởi hành' },
  {
    id: 'price',
    label: 'Giá tiền',
    format: (value: number) => value.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' }),
  },
  { id: 'seatNumber', label: 'Số ghế' },
  { id: 'status', label: 'Trạng thái thanh toán' },
];

export default function HictoryByTicket() {
  const [dateA, setDateA] = useState<string>(""); // Ngày bắt đầu
  const [dateB, setDateB] = useState<string>(""); // Ngày kết thúc
  const [data, setData] = useState<Ticket[]>([]);
  const [loading, setLoading] = useState<boolean>(false);


   // Lấy username từ redux
   const username = useSelector((state: RootState) => state.user.currentUser?.userName);
   console.log(username);

  // Lấy dữ liệu ticket từ API
  const fetchTickets = async () => {
    if (!dateA || !dateB) {
      toast.error("Vui lòng nhập đầy đủ ngày bắt đầu và ngày kết thúc!", { autoClose: 800 });
      return;
    }
    setLoading(true);
    try {
      const tickets = await getListTicketDateAToDateBAndUser(username, dateA, dateB);
      setData(tickets);
      console.log(">>>> list:",tickets)
      toast.success("Dữ liệu đã được tải thành công!");
    } catch (error: any) {
      toast.error("Lỗi khi tải dữ liệu vé.", { autoClose: 800 });
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      <ToastContainer />
      <section>
        <div className='flex bg-teal-100 p-4 rounded-2xl mb-7 justify-between'>
          <div className='flex flex-col'>
            <h1 className='font-bold mb-3'>Lịch sử mua vé</h1>
            <h2>Theo dõi và quản lý quá trình lịch sử mua vé của bạn</h2>
          </div>
          <div className='bg-teal-600 hover:bg-teal-800 rounded-2xl'>
            <button className='m-4 px-7 text-white'>
              <a href="/lich-trinh" className="">Đặt vé</a>
            </button>
          </div>
        </div>

        <div className='flex flex-col gap-4'>
          <h1 className='font-bold'>Lịch sử giao dịch</h1>

          {/* Filter Options */}
          <div className='flex justify-between bg-gray-200 shadow-sm border rounded-2xl p-4'>
            <div className='flex items-center gap-2'>
              <span className='mx-2'>Từ</span>
              <input
                type="date"
                value={dateA}
                onChange={(e) => setDateA(e.target.value)}
                className="p-2 rounded-md border"
              />
              <span className='mx-2'>đến</span>
              <input
                type="date"
                value={dateB}
                onChange={(e) => setDateB(e.target.value)}
                className="p-2 rounded-md border"
              />
            </div>
            <div className='text-center'>
              <button
                className='bg-teal-600 text-white hover:bg-teal-800 px-4 py-2 rounded-2xl'
                onClick={fetchTickets}
              >
                Tìm
              </button>
            </div>
          </div>

          {/* Transaction Table */}
          <div className='overflow-x-auto'>
            {loading ? (
              <div className='text-center'>Đang tải dữ liệu...</div>
            ) : (
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
                  {data.length > 0 ? (
                    data.map((row, index) => (
                      <tr
                        key={row.ticketId}
                        className={`${index % 2 === 0 ? 'bg-gray-50' : 'bg-white'} border-b`}
                      >
                        <td className='p-4'>{row.ticketId}</td>
                        <td className='p-4'>{row.busRouteSchedule_Id}</td>
                        {/* <td className='p-4'>{row.busRoutesEntity_Id}</td>
                        <td className='p-4'>{row.busEntity_Id}</td> */}
                        <td className='p-4'>{row.departureDate}</td>
                        <td className='p-4'>{columns[3].format(row.price)}</td>
                        <td className='p-4'>{row.seatNumber}</td>
                        <td className='p-4'>{row.status}</td>
                      </tr>
                    ))
                  ) : (
                    <tr>
                      <td colSpan={columns.length} className='p-4 text-center'>
                        Không có dữ liệu.
                      </td>
                    </tr>
                  )}
                </tbody>
              </table>
            )}
          </div>
        </div>
      </section>
    </>
  );
}
