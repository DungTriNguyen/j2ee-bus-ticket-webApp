import { useState } from "react";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { Button, Label, Modal, TextInput } from "flowbite-react";
import { getTicketByIdAndUserName } from '../../api/services/admin/ticketService';
import { useSelector } from "react-redux";
import { RootState } from "../../redux/store";
import { Ticket } from "@type/model/Ticket";

export default function TicketComponent() {
  const [ticketDetails, setTicketDetails] = useState<Ticket>();
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);
  const [ticketCode, setTicketCode] = useState<string>(""); // Lưu mã vé từ input
  const [openModal, setOpenModal] = useState(false);

  // Lấy username từ redux
  const username = useSelector((state: RootState) => state.user.currentUser?.userName);
  console.log(username);
  

  // Lấy thông tin vé từ API
  const fetchTicketDetails = async (ticketCode: string) => {
    if(username === null || username === undefined || username === ''){
        setError("Vui lòng đăng nhập để tra cứu vé!");
        toast.error("Vui lòng đăng nhập để tra cứu vé!", { autoClose: 800 });
        return;
    }
    if (!ticketCode) {
      toast.error("Vui lòng nhập mã vé để tra cứu!", { autoClose: 800 });
      return;
    }
    
    
    try {
      
      setLoading(true);
      const ticket = await getTicketByIdAndUserName(Number(ticketCode), username); // Chuyển đổi thành số
      console.log(">>>>>:",ticketCode);
      // Kiểm tra nếu vé không tồn tại
      // if(!ticket.ticketId || !ticket.accountEnity_Id ){
      //   toast.error("Mã vé không tồn tại", { autoClose: 800 });
      //   return;
      // }
        setTicketDetails(ticket);
        console.log(">>>>>:",ticket);
        toast.success("Tải vé thành công!", { autoClose: 800 });
      
      
    } catch (error: any) {
      setError("Không tìm thấy thông tin vé. Vui lòng kiểm tra lại!");
      toast.error("Lỗi khi tải vé", { autoClose: 800 });
    } finally {
      setLoading(false);
    }
  };

  // Xử lý sự kiện khi người dùng click tra cứu thông tin vé
  const handleSearchTicket = () => {
    if (!ticketCode.trim()) {
      toast.error("Vui lòng nhập mã vé!", { autoClose: 800 });
      return;
    }
    fetchTicketDetails(ticketCode);
    setOpenModal(true);
  };

  return (
    <section className="py-24">
      <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
        <div className="grid lg:grid-cols-2 grid-cols-1">
          <div className="lg:mb-0 mb-10">
            <div className="group w-full h-full">
              <div className="relative h-full">
                <img
                  src="/search-ticket.png"
                  alt="Ticket Information"
                  className="w-full h-full lg:rounded-l-2xl rounded-2xl bg-blend-multiply bg-cyan-700 object-cover"
                />
                <h1 className="font-manrope text-white text-4xl font-bold leading-10 absolute top-11 left-11">
                  Tra cứu vé
                </h1>
              </div>
            </div>
          </div>

          <div className="bg-gray-50 p-5 lg:p-11 lg:rounded-r-2xl rounded-2xl">
            <h2 className="text-cyan-600 font-manrope text-4xl font-semibold leading-10 mb-11">
              Nhập thông tin vé
            </h2>
            <input
              type="text"
              className="w-full h-12 text-gray-600 placeholder-gray-400 shadow-sm bg-transparent text-lg font-normal leading-7 rounded-full border border-gray-200 focus:outline-none pl-4 mb-10"
              placeholder="Mã vé xe"
              onChange={(e) => setTicketCode(e.target.value)}
              value={ticketCode}
            />
            <button
              onClick={handleSearchTicket}
              className="w-full h-12 text-white text-base font-semibold leading-6 rounded-full transition-all duration-700 hover:bg-cyan-800 bg-cyan-600 shadow-sm"
            >
              Tra cứu thông tin
            </button>
          </div>
        </div>
      </div>

      {/* Modal popup ticket */}
      <Modal show={openModal} size="md" popup onClose={() => setOpenModal(false)}>
        <Modal.Header />
        <Modal.Body>
          <div className="space-y-6">
            <h3 className="text-xl font-medium text-gray-900 dark:text-white">
              Thông tin tra cứu vé xe
            </h3>

            {loading && <div>Loading...</div>}
            {error && <div className="text-red-500">{error}</div>}
            {ticketDetails && (
              <>
                <div>
                  <Label htmlFor="ticketCode" value="Mã vé" />
                  <TextInput id="ticketCode" value={ticketDetails.ticketId} disabled />
                </div>
                <div>
                  <Label htmlFor="tripCode" value="Mã xe - tuyến" />
                  <TextInput id="tripCode" value={ticketDetails.busRouteSchedule_Id} disabled />
                </div>
                {/* <div>
                  <Label htmlFor="busCode" value="Mã xe" />
                  <TextInput id="busCode" value={ticketDetails.busEntity_Id} disabled />
                </div> */}
                <div>
                  <Label htmlFor="bookingTime" value="Thời gian đặt vé" />
                  <TextInput
                    id="bookingTime"
                    value={(ticketDetails.departureDate)}
                    disabled
                  />
                </div>
                <div>
                  <Label htmlFor="phoneNumber" value="Số điện thoại" />
                  <TextInput id="phoneNumber" value={ticketDetails.phoneNumber} disabled />
                </div>
                <div>
                  <Label htmlFor="seatNumber" value="Chỗ ngồi" />
                  <TextInput id="seatNumber" value={ticketDetails.seatNumber} disabled />
                </div>
                <div>
                  <Label htmlFor="status" value="Trạng thái" />
                  <TextInput id="status" value={ticketDetails.status} disabled />
                </div>
                <div>
                  <Label htmlFor="price" value="Giá tiền" />
                  <TextInput
                    id="price"
                    value={ticketDetails.price}
                    disabled
                  />
                </div>
              </>
            )}
          </div>
        </Modal.Body>
      </Modal>

      <ToastContainer />
    </section>
  );
}
