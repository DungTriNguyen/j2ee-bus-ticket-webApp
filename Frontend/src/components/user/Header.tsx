import { Button, MegaMenu, Navbar } from "flowbite-react";
import { useLocation } from "react-router-dom";
import { useState } from "react";

import {
  FaCreditCard,
  FaUser,
  FaHistory,
  FaMapMarkerAlt,
  FaKey,
  FaSignOutAlt,
} from "react-icons/fa";

export default function Header() {
  const location = useLocation();
  const [isOpen, setIsOpen] = useState(false); // state to toggle the dropdown
  const toggleDropdown = () => {
    setIsOpen(!isOpen);
  };

  return (
    <MegaMenu className="bg-gray-100">
      <div className="mx-auto flex w-full flex-wrap items-center justify-between p-4 md:space-x-8">
        <Navbar.Brand href="/">
          <img src="/logo_removebg.png" alt="logo" className="w-16 h-16 ml-2" />
          <span className="self-center whitespace-nowrap text-xl font-semibold dark:text-white">
            AnhBa Bus
          </span>
        </Navbar.Brand>
        <div className="relative order-2 items-center md:flex ">
          <a
            href="/dang-nhap"
            className="mr-1 rounded-lg px-4 py-2 text-sm font-medium text-gray-800 hover:bg-gray-50 focus:outline-none focus:ring-4 focus:ring-gray-300 dark:text-white dark:hover:bg-gray-700 dark:focus:ring-gray-800 md:mr-2 md:px-5 md:py-2.5"
          >
            Đăng nhập
          </a>
          <Button href="/dang-ky">Đăng ký</Button>
        </div>
        {/* Avatar with Dropdown */}
        <div className="relative order-2 items-center">
          <img
            src="/avatar_apu_2.jpg" // Replace with actual user avatar
            alt="User Avatar"
            className=" w-12 h-12 rounded-full cursor-pointer"
            onClick={toggleDropdown} // Handle toggle on click
          />

          {isOpen && (
            <div className="absolute right-0 mt-2 w-48 rounded-md shadow-lg bg-white ring-1 ring-black ring-opacity-5 focus:outline-none z-10">
              <div className="py-1">
                <a
                  href="/thong-tin/anhba-pay"
                  className="flex items-center p-2 hover:bg-gray-100"
                >
                  <FaCreditCard className="mr-2 text-[#186477]" />
                  AnhBa Pay
                </a>
                <a
                  href="/thong-tin/chung"
                  className="flex items-center p-2 hover:bg-gray-100"
                >
                  <FaUser className="mr-2 text-[#4d5b66]" />
                  Thông tin tài khoản
                </a>
                <a
                  href="/thong-tin/lich-su-mua-ve"
                  className="flex items-center p-2 hover:bg-gray-100"
                >
                  <FaHistory className="mr-2 text-[#74C0FC]" />
                  Lịch sử mua vé
                </a>
                <a
                  href="/thong-tin/dia-chi"
                  className="flex items-center p-2 hover:bg-gray-100"
                >
                  <FaMapMarkerAlt className="mr-2 text-[#63E6BE]" />
                  Địa chỉ của bạn
                </a>
                <a
                  href="/thong-tin/dat-lai-mat-khau"
                  className="flex items-center p-2 hover:bg-gray-100"
                >
                  <FaKey className="mr-2 text-[#FFA726]" />
                  Đặt lại mật khẩu
                </a>
                <a href="/" className="flex items-center p-2 hover:bg-gray-100">
                  <FaSignOutAlt className="mr-2 text-[#fa003e]" />
                  Đăng xuất
                </a>
              </div>
            </div>
          )}
        </div>

        <Navbar.Toggle />
        <Navbar.Collapse>
          <Navbar.Link
            href="/"
            className={`${
              location.pathname === "/"
                ? "text-teal-600 overline underline-offset-8 decoration-2"
                : ""
            }`}
          >
            Trang chủ
          </Navbar.Link>
          <Navbar.Link
            href="/lich-trinh"
            className={`${
              location.pathname === "/lich-trinh"
                ? "text-teal-600 overline underline-offset-8 decoration-2"
                : ""
            }`}
          >
            Lịch trình
          </Navbar.Link>
          <Navbar.Link
            href="/tra-cuu"
            className={`${
              location.pathname === "/tra-cuu"
                ? "text-teal-600 overline underline-offset-8 decoration-2"
                : ""
            }`}
          >
            Tra cứu vé
          </Navbar.Link>
          <Navbar.Link
            href="/danh-gia-gop-y"
            className={`${
              location.pathname === "/danh-gia-gop-y"
                ? "text-teal-600 overline underline-offset-8 decoration-2"
                : ""
            }`}
          >
            Đánh giá
          </Navbar.Link>
          <Navbar.Link
            href="/lien-he"
            className={`${
              location.pathname === "/lien-he"
                ? "text-teal-600 overline underline-offset-8 decoration-2"
                : ""
            }`}
          >
            Liên hệ
          </Navbar.Link>
          <Navbar.Link
            href="/ve-chung-toi"
            className={`${
              location.pathname === "/ve-chung-toi"
                ? "text-teal-600 overline underline-offset-8 decoration-2"
                : ""
            }`}
          >
            Về chúng tôi
          </Navbar.Link>
        </Navbar.Collapse>
      </div>
    </MegaMenu>
  );
}
