import { Routes, Route } from 'react-router-dom';

import AdminSidebar from '../components/admin/AdminSidebar';

import AdminDashboard from '../pages/admin/AdminDashboard';
import Trip from '../pages/admin/Trip';
import TripRoute from '../pages/admin/TripRoute';
import Customer from '../pages/admin/Customer';
import Bus from '../pages/admin/Bus';
import Driver from '../pages/admin/Driver';
import Ticket from '../pages/admin/Ticket';
import Payment from '../pages/admin/Payment';
import Discount from '../pages/admin/Discount';
import FeedBack from '../pages/admin/FeedBack';
import Revenue from '../pages/admin/Revenue';

export default function AdminRoute() {
  return (
    <>
    <div className="flex h-screen">
      <AdminSidebar />

      {/* Content Area */}
      <div className="flex-grow p-4">
        <Routes>
          <Route path="/" element={<AdminDashboard />} />
          <Route path="/chuyen-di" element={<Trip />} />
          <Route path="/lich-trinh" element={<TripRoute />} />
          <Route path="/khach-hang" element={<Customer />} />
          <Route path="/xe" element={<Bus />} />
          <Route path="/tai-xe" element={<Driver />} />
          <Route path="/ve" element={<Ticket />} />
          <Route path="/thanh-toan" element={<Payment />} />
          <Route path="/khuyen-mai" element={<Discount />} />
          <Route path="/phan-hoi" element={<FeedBack />} />
          <Route path="/doanh-thu" element={<Revenue />} />
        </Routes>
      </div>
    </div>
  </>
  );
}