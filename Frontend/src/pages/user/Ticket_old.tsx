

import { useState, useEffect, ChangeEvent, useRef } from "react";
import { FaStar } from "react-icons/fa";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { Button, Checkbox, Label, Modal, TextInput } from "flowbite-react";
import { getTickets, createTicket, updateTicket, deleteTicket, getTicketByIdAndUserName } from '../../api/services/admin/ticketService';
import { getPayments } from '../../api/services/admin/paymentService';
import { getBuses } from '../../api/services/admin/busService';
import { getBusRoutes } from '../../api/services/admin/busRouteService';
import { useSelector } from "react-redux";
import { RootState } from "../../redux/store";

export default function Ticket() {

    // trạng thái danh sách busRouterID
    const [busRouter, setBusRouter] = useState<{ routesId: number }[]>([]);
    // trạng thái danh sách busID
    const [bus, setBus] = useState<{ busId: number }[]>([]);
    // trạng thái danh sách tài khoản
    const [payments, setPayments] = useState<{ paymentId: number }[]>([]);
    const [error, setError] = useState<string | null>(null);

    const [openModal, setOpenModal] = useState(false);
    const emailInputRef = useRef<HTMLInputElement>(null);
    const [data, setData] = useState<Ticket[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [actionLoading, setActionLoading] = useState<boolean>(false);


  // Giả sử lấy username từ thông tin đăng nhập
  // Thay bằng cách lấy giá trị từ auth state/context.
  const username = useSelector((state: RootState) => state.user.currentUser?.userName);
   // Lấy dữ liệu ticket từ API
   const fetchTickets = async () => {
    try {
      const tickets = await getTicketByIdAndUserName();
      setData(tickets);
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
            {/* <input
              type="text"
              className="w-full h-12 text-gray-600 placeholder-gray-400 shadow-sm bg-transparent text-lg font-normal leading-7 rounded-full border border-gray-200 focus:outline-none pl-4 mb-10"
              placeholder="Số điện thoại"
            /> */}
            <input
              type="text"
              className="w-full h-12 text-gray-600 placeholder-gray-400 shadow-sm bg-transparent text-lg font-normal leading-7 rounded-full border border-gray-200 focus:outline-none pl-4 mb-10"
              placeholder="Mã vé xe"
            />
            <button onClick={() => setOpenModal(true)} className="w-full h-12 text-white text-base font-semibold leading-6 rounded-full transition-all duration-700 hover:bg-cyan-800 bg-cyan-600 shadow-sm">
              Tra cứu thông tin
            </button>
          </div>
        </div>
      </div>

              {/* modal popup ticket */}
    {/* <Button >Toggle modal</Button> */}
    <Modal show={openModal} size="md" popup onClose={() => setOpenModal(false)} initialFocus={emailInputRef}>
      <Modal.Header />
      <Modal.Body>
        <div className="space-y-6">
          <h3 className="text-xl font-medium text-gray-900 dark:text-white">Thông tin tra cứu vé xe</h3>
          <div>
            <div className="mb-2 block">
              <Label htmlFor="email" value="Your email" />
            </div>
            <TextInput id="email" ref={emailInputRef} placeholder="name@company.com" required />
          </div>
          <div>
            <div className="mb-2 block">
              <Label htmlFor="password" value="Your password" />
            </div>
            <TextInput id="password" type="password" required />
          </div>
          <div className="flex justify-between">
            <div className="flex items-center gap-2">
              <Checkbox id="remember" />
              <Label htmlFor="remember">Remember me</Label>
            </div>
            <a href="#" className="text-sm text-cyan-700 hover:underline dark:text-cyan-500">
              Lost Password?
            </a>
          </div>
          <div className="w-full">
            <Button>Log in to your account</Button>
          </div>
          <div className="flex justify-between text-sm font-medium text-gray-500 dark:text-gray-300">
            Not registered?&nbsp;
            <a href="#" className="text-cyan-700 hover:underline dark:text-cyan-500">
              Create account
            </a>
          </div>
        </div>
      </Modal.Body>
    </Modal>


      
    </section>
  );
}



