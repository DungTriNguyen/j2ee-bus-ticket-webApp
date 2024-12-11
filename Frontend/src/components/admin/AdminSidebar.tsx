import { HR, Sidebar } from "flowbite-react";

import { FaHome, FaBus, FaTicketAlt, FaMoneyBill, FaSignOutAlt } from "react-icons/fa";
import { MdFeedback } from "react-icons/md";
import { HiShoppingBag, HiUser } from "react-icons/hi";
import { useDispatch } from "react-redux";
import { userClear } from "../../redux/userSlice";
import { useNavigate } from "react-router-dom";

export default function AdminSidebar() {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  function handleLogout() {
    localStorage.removeItem('jwtToken')
    dispatch(userClear());
    navigate('/');
  }

  return (
    <Sidebar>
      <Sidebar.Items>
        <Sidebar.ItemGroup>
          <Sidebar.Item href="/admin" icon={FaHome}>
            Trang chủ
          </Sidebar.Item>
          <Sidebar.Collapse icon={FaBus} label="Quản lý nhà xe">
            <Sidebar.Item href="/admin/chuyen-di">Chuyến đi</Sidebar.Item>
            <Sidebar.Item href="/admin/phan-cong-xe-tuyen">Xe - tuyến</Sidebar.Item>
            <Sidebar.Item href="/admin/tuyen-xe">Tuyến Xe</Sidebar.Item>
            <Sidebar.Item href="/admin/xe">Xe</Sidebar.Item>
            <Sidebar.Item href="/admin/tai-xe">Tài xế</Sidebar.Item>
            <Sidebar.Item href="/admin/ve-phat">Vé phạt</Sidebar.Item>
          </Sidebar.Collapse>
          <Sidebar.Item href="/admin/ve" icon={FaTicketAlt}>
            Vé xe
          </Sidebar.Item>
          <Sidebar.Item href="/admin/khach-hang" icon={HiUser}>
            Tài khoản
          </Sidebar.Item>
          <Sidebar.Item href="/admin/khuyen-mai" icon={HiShoppingBag}>
            Khuyến mãi
          </Sidebar.Item>
          <Sidebar.Item href="/admin/phan-hoi" icon={MdFeedback}>
            Phản hồi
          </Sidebar.Item>
          <Sidebar.Item href="/admin/thanh-toan" icon={FaMoneyBill}>
            Thanh toán
          </Sidebar.Item>
          <HR />
          <Sidebar.Item icon={FaSignOutAlt} onClick={handleLogout}>
            Đăng xuất
          </Sidebar.Item>
        </Sidebar.ItemGroup>
      </Sidebar.Items>
    </Sidebar>
  );
}
