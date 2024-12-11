import { useState } from "react";
import { getStatisticsTickets, getStatisticsPenaltyTickets } from "../../api/services/admin/statictisService";
import { Line } from "react-chartjs-2";
import { Spinner } from "flowbite-react";
import { ToastContainer, toast } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";
import "chart.js/auto";

const AdminDashboard = () => {
  const [dateA, setDateA] = useState(""); // Ngày bắt đầu
  const [dateB, setDateB] = useState(""); // Ngày kết thúc
  const [ticketStats, setTicketStats] = useState<any>(null); // Dữ liệu thống kê vé
  const [penaltyStats, setPenaltyStats] = useState<any>(null); // Dữ liệu thống kê vé phạt
  const [loading, setLoading] = useState(false); // Trạng thái tải

  // Fetch thống kê vé
  const fetchTicketStatistics = async () => {
    if (!dateA || !dateB) {
      toast.error("Vui lòng nhập đầy đủ ngày bắt đầu và ngày kết thúc!", { autoClose: 800 });
      return;
    }
    setLoading(true);
    try {
      const data = await getStatisticsTickets(dateA, dateB);
      setTicketStats(data);
      console.log("Thống kê vé:", data);  
    } catch (error: any) {
      console.error("Lỗi lấy thống kê vé:", error);
      toast.error("Có lỗi xảy ra khi lấy dữ liệu thống kê vé.", { autoClose: 800 });
    } finally {
      setLoading(false);
    }
  };

  // Fetch thống kê vé phạt
  const fetchPenaltyStatistics = async () => {
    if (!dateA || !dateB) {
      toast.error("Vui lòng nhập đầy đủ ngày bắt đầu và ngày kết thúc!", { autoClose: 800 });
      return;
    }
      // Thêm T00:00:00 vào ngày
    const formattedDateA = `${dateA}T00:00:00`;
    const formattedDateB = `${dateB}T00:00:00`;
    setLoading(true);
    try {
      const data = await getStatisticsPenaltyTickets(formattedDateA, formattedDateB);
      setPenaltyStats(data);
      console.log("Thống kê vé phạt:", data);
    } catch (error) {
      console.error("Lỗi lấy thống kê vé phạt:", error);
      toast.error("Có lỗi xảy ra khi lấy dữ liệu thống kê vé phạt.", { autoClose: 800 });
    } finally {
      setLoading(false);
    }
  };

  // format tiền
  const formatCurrency = (amount: number): string => {
    return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND',
      minimumFractionDigits: 0, // Số thập phân, nếu cần
      maximumFractionDigits: 0, // Số thập phân, nếu cần
    }).format(amount);
  };


 

  // Dữ liệu biểu đồ
  const chartData = {
    labels: Object.keys(ticketStats?.revenueOfRoutes?.[0] || {}),
    datasets: [
      {
        label: "Doanh thu theo tuyến xe",
        data: Object.values(ticketStats?.revenueOfRoutes?.[0] || {}),
        backgroundColor: "rgba(75, 192, 192, 0.5)",
        borderColor: "rgba(75, 192, 192, 1)",
        borderWidth: 2,
      },
    ],
  };

  const checkEmpty = (amount: number | null | undefined): string => {
    return amount === null || amount === undefined || isNaN(amount) ? "0 VND" : `${amount.toLocaleString()} VND`;
  };
  const checkEmtyTotal = (amount: number | null | undefined): string =>{
    return amount === null || amount === undefined || isNaN(amount) ? "0 Vé" : `${amount.toLocaleString()} Vé`;
  }
  return (
    <div className="p-4 mx-auto max-w-4xl">
      <h1 className="uppercase font-semibold text-2xl tracking-wide mb-4">Thống kê</h1>

      {/* Bộ lọc ngày */}
      <div className="space-y-6 mb-6">
        <div className="space-y-2">
          <label htmlFor="dateA" className="block">Ngày bắt đầu</label>
          <input
            type="date"
            id="dateA"
            value={dateA}
            onChange={(e) => setDateA(e.target.value)}
            className="border rounded p-2 w-full"
          />
        </div>
        <div className="space-y-2">
          <label htmlFor="dateB" className="block">Ngày kết thúc</label>
          <input
            type="date"
            id="dateB"
            value={dateB}
            onChange={(e) => setDateB(e.target.value)}
            className="border rounded p-2 w-full"
          />
        </div>
        <div className="flex space-x-48 mt-5">
          <button 
            onClick={fetchTicketStatistics}
            className="bg-blue-500 text-white p-2 rounded mr-32"
          >
            Lấy thống kê vé
          </button>
          <button
            onClick={fetchPenaltyStatistics}
            className="bg-red-500 text-white p-2 rounded"
          >
            Lấy thống kê vé phạt
          </button>
        </div>
      </div>

      {/* Loading */}
      {loading && <div><Spinner aria-label="Default status example" /></div>}

      {/* Hiển thị kết quả thống kê */}
      <div className="flex space-x-40 mb-5 mt-5">
        {ticketStats && (
          <div className="space-y-6">
            <h2 className="text-xl font-bold">Kết quả thống kê vé</h2>
            <p><strong>Tổng vé:</strong> {checkEmtyTotal(ticketStats.numberTicketSuccess + ticketStats.numberTicketPending + ticketStats.numberTicketFailure)}</p>
            <p><strong>Doanh thu thành công:</strong> {checkEmpty(ticketStats.sumMoneyTicketSuccess)} </p>
            <p><strong>Số vé thành công:</strong> {checkEmtyTotal(ticketStats.numberTicketSuccess)}</p>
            <p><strong>Số vé đang xử lý:</strong> {checkEmtyTotal(ticketStats.numberTicketPending)}</p>
            <p><strong>Số vé tạo thất bại:</strong> {checkEmtyTotal(ticketStats.numberTicketFailure)}</p>
          </div>
        )}

        {penaltyStats && (
          <div className="space-y-6">
            <h2 className="text-xl font-bold">Kết quả thống kê vé phạt</h2>
            <p><strong>Tổng số vé phạt:</strong> {checkEmtyTotal(penaltyStats.size)}</p>
            <p><strong>Tổng tiền phạt:</strong> {checkEmpty(penaltyStats.sumMoneyPenalty)} </p>
            <p><strong>Tiền phạt chưa xử lý:</strong> {checkEmpty(penaltyStats.sumMoneyPenaltyNoProcess)} </p>
          </div>
        )}
      </div>

      {/* Biểu đồ */}
      {ticketStats && ticketStats.revenueOfRoutes && (
        <div className="mt-6">
          <h2 className="text-xl font-bold">Biểu đồ doanh thu theo tuyến xe</h2>
          <Line data={chartData} />
        </div>
      )}

      {/* ToastContainer for notifications */}
      <ToastContainer />
    </div>
  );
};

export default AdminDashboard;
