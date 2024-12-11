import { useEffect, useState } from "react";
import { toPng } from "html-to-image";
import { saveAs } from "file-saver";
import type { Ticket } from '@type/model/Ticket';
import type { TicKetFullInfo } from '@type/model/TicKetFullInfo';
import { getTickets, getTicketById, getTicketInfo } from '../../api/services/admin/ticketService';
import { useLocation } from "react-router-dom";

const ExportTicket: React.FC = () => {
  const [data, setData] = useState<Ticket[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [ticket, setTicket] = useState<TicKetFullInfo | null>(null);



  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const ticketId = searchParams.get('ticketId');
  // const ticketIdDone = ticketId[0];
  // console.log(">>>",ticketId)
  // const paymentId = searchParams.get('');
  // const ticketIdExport = getTicketById(ticketId);
  // const ticketArray = ticketId.split(","); // Tách chuỗi dựa trên dấu phẩy
  // if (ticketArray.length > 0) 
  // firstTicketId = ticketArray[0];
  let ticketIdArray: number[] = [];
  let firstTicketId: number | null = null;


  if (ticketId) {
    try {
      // Thử parse nếu ticketId là JSON
      const parsedTicketId = JSON.parse(ticketId);
      if (Array.isArray(parsedTicketId)) {
        ticketIdArray = parsedTicketId.map((id: any) => Number(id));
      } else {
        // Nếu không phải JSON, tách chuỗi dựa trên dấu phẩy
        ticketIdArray = ticketId.split(',').map(id => Number(id));
      }
    } catch (error) {
      // Nếu không phải JSON, xử lý như chuỗi thông thường
      ticketIdArray = ticketId.split(',').map(id => Number(id));
    }
  }
  firstTicketId = ticketIdArray[0];
  console.log(ticketIdArray);
  console.log(firstTicketId);
    const ticketIdExport = getTicketById(firstTicketId);
  console.log(ticketIdExport);
  // setData(ticketIdExport)
  // Lấy dữ liệu ticket từ API
  const fetchTickets = async () => {
    try {
      const tickets = await getTickets();
      setData(tickets);
    } catch (error: any) {
      setError(error.message);
    } finally {
      setLoading(false);
    }
  };

  // Gọi API để lấy thông tin ticket
  const fetchTicket = async () => {
    if (!firstTicketId) return;
    try {
      // const ticketData = await getTicketById(firstTicketId);
      const ticketData = await getTicketInfo(firstTicketId);
      setTicket(ticketData);
    } catch (error: any) {
      setError(error.message || "Failed to fetch ticket.");
    }
  };

  useEffect(() => {
    fetchTickets();
    fetchTicket();
  }, [firstTicketId]);

  const handleExport = () => {
    const ticketElement = document.getElementById("ticket");
    if (ticketElement) {
      toPng(ticketElement).then((data) => {
        const blob = dataURLToBlob(data);
        saveAs(blob, "BusTicket.png");
      });
    }
  };

  const dataURLToBlob = (dataURL: string) => {
    const byteString = atob(dataURL.split(",")[1]);
    const mimeString = dataURL.split(",")[0].split(":")[1].split(";")[0];
    const ab = new ArrayBuffer(byteString.length);
    const ia = new Uint8Array(ab);
    for (let i = 0; i < byteString.length; i++) {
      ia[i] = byteString.charCodeAt(i);
    }
    return new Blob([ab], { type: mimeString });
  };

  // Gọi API để lấy thông tin ticket
  // const fetchTicket = async () => {
  //   if (!firstTicketId) return;
  //   try {
  //     const ticketData = await getTicketById(firstTicketId);
  //     setTicket(ticketData);
  //   } catch (error: any) {
  //     setError(error.message || "Failed to fetch ticket.");
  //   }
  // };

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100 -m-32 ">
      <div
        id="ticket"
        className="border border-gray-300 bg-gradient-to-r from-blue-500 to-blue-700 text-white rounded-xl shadow-md p-6 w-96 text-center "
      >
        <h2 className="text-2xl font-bold mb-4">Vé xe AnhBaBus</h2>
        <p className="text-lg font-medium mb-2">
          <strong>Mã vé: </strong>{ticketIdArray}
        </p>
        <p className="text-lg font-medium mb-2">
          <strong>Mã khách hàng: </strong> {ticket?.accountUsername}
        </p>
        <p className="text-lg font-medium mb-2">
          <strong>Số điện thoại đặt vé: </strong>{ticket?.phoneNumber}
        </p>
        <p className="text-lg font-medium mb-2">
          <strong>From: </strong> {ticket?.departureLocation}
        </p>
        <p className="text-lg font-medium mb-2">
          <strong>To: </strong> {ticket?.destinationLocation}
        </p>
        <p className="text-lg font-medium mb-2">
          <strong>Ngày khởi hành: </strong> {ticket?.departureDate}
        </p>
        <p className="text-lg font-medium mb-2">
          <strong>Thứ: </strong> {ticket?.dayOfWeek}
        </p>
        {/* <p className="text-lg font-medium mb-2">
          <strong>Thời gian dự kiến: </strong> {ticket?.tripTime}
        </p> */}
        <p className="text-lg font-medium mb-2">
          <strong>Thời gian khởi hành: </strong> {ticket?.departureTime}
        </p>
        <p className="text-lg font-medium mb-2">
          <strong>Thời gian đến: </strong> {ticket?.distinationTime}
        </p>
        <p className="text-lg font-medium mb-2">
          <strong>Số ghế: </strong>{ticket?.seatNumber}
        </p>
        <p className="text-lg font-medium mb-2">
          <strong>Số tiền giảm: </strong>{ticket?.discountAmount}
        </p>
        <p className="text-lg font-medium mb-2">
          <strong>Khoảng cách: </strong>{ticket?.distanceKm}
        </p>
        <p className="text-lg font-medium mb-2">
          <strong>Phương thức thanh toán: </strong>{ticket?.paymentMethod}
        </p>
        
        <p className="text-xl font-bold mt-4">
          Giá: <span className="text-yellow-300">{ticket?.finalAmount}</span>
        </p>
      </div>
      <div className="flex gap-4">
      <button
        className="mt-6 font-semibold py-2 px-4 rounded-lg shadow-lg transition-transform transform hover:scale-105"
      >
        <a href="/">Quay lại</a>
      </button>
      <button
        onClick={handleExport}
        className="mt-6 bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-4 rounded-lg shadow-lg transition-transform transform hover:scale-105"
      >
        Xuất vé
      </button>
      </div>
    </div>
  );
};

export default ExportTicket;
