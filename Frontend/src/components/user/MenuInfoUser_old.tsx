
import { FaCreditCard, FaUser, FaHistory, FaMapMarkerAlt, FaKey, FaSignOutAlt } from "react-icons/fa";


export default function MenuInfoUser() {
  return (
    <div className="col-span-12 hidden rounded-2xl sm:col-span-3 lg:block h-full justify-between items-center">
      <div className="px-4 py-2 scale-125 flex flex-col gap-8 mt-10">
        <a
          href="/thong-tin/anhba-pay"
          className="flex items-center px-2 py-4 rounded hover:bg-gray-200"
        >
          <FaCreditCard className="mr-2 text-[#186477]" />
          AnhBa Pay
        </a>
        <a
          href="/thong-tin/chung"
          className="flex items-center px-2 py-4 rounded hover:bg-gray-200"
        >
          <FaUser className="mr-2 text-[#4d5b66]" />
          Thông tin tài khoản
        </a>
        <a
          href="/thong-tin/lich-su-mua-ve"
          className="flex items-center px-2 py-4 rounded hover:bg-gray-200"
        >
          <FaHistory className="mr-2 text-[#74C0FC]" />
          Lịch sử mua vé
        </a>
        {/* <a href="/thong-tin/dia-chi" className="flex items-center px-2 py-4 rounded hover:bg-gray-200">
          <FaMapMarkerAlt className="mr-2 text-[#63E6BE]" />
          Địa chỉ của bạn
        </a> */}
        <a
          href="/thong-tin/dat-lai-mat-khau"
          className="flex items-center px-2 py-4 rounded hover:bg-gray-200"
        >
          <FaKey className="mr-2 text-[#FFA726]" />
          Đặt lại mật khẩu
        </a>
        <a href="/" className="flex items-center px-2 py-4 rounded hover:bg-gray-200 mb-7">
          <FaSignOutAlt className="mr-2 text-[#fa003e]" />
          Đăng xuất
        </a>
      </div>
    </div>
  );
}
