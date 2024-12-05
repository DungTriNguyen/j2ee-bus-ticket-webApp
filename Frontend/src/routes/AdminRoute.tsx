import { Routes, Route, Navigate } from 'react-router-dom';

import { useSelector } from 'react-redux';
import { RootState } from '../redux/store';

import AdminSidebar from '../components/admin/AdminSidebar';

import AdminDashboard from '../pages/admin/AdminDashboard';
import Trip from '../pages/admin/Trip';
import TripRoute from '../pages/admin/TripRoute';
import Bus from '../pages/admin/Bus';
import Driver from '../pages/admin/Driver';
import Ticket from '../pages/admin/Ticket';
import Payment from '../pages/admin/Payment';
import Discount from '../pages/admin/Discount';
import FeedBack from '../pages/admin/FeedBack';
import UnAuthorized from '../pages/shared/UnAuthorized';
import PenaltyTicket from '../pages/admin/PenaltyTicket';
import Account from '../pages/admin/Account';

export default function AdminRoute() {
  const { user } = useSelector((state: RootState) => state.auth);

  // if (!user) {
  //   return <Navigate to="/dang-nhap" replace />;
  // }

  // if(user.role !== 'ADMIN') {
  //   return <Navigate to="/unauthorized" replace />;
  // }

  return (
    <>
      <div className='flex h-screen'>
        <AdminSidebar />

        {/* Content Area */}
        <div className='flex-grow p-4'>
          <Routes>
            <Route path='/' element={<AdminDashboard />} />
            <Route path='/chuyen-di' element={<Trip />} />
            <Route path='/tuyen-xe' element={<TripRoute />} />
            <Route path='/khach-hang' element={<Account />} />
            <Route path='/xe' element={<Bus />} />
            <Route path='/tai-xe' element={<Driver />} />
            <Route path='/ve' element={<Ticket />} />
            <Route path='/ve-phat' element={<PenaltyTicket />} />
            <Route path='/thanh-toan' element={<Payment />} />
            <Route path='/khuyen-mai' element={<Discount />} />
            <Route path='/phan-hoi' element={<FeedBack />} />
            <Route path='/thanh-toan' element={<Payment />} />

            <Route path='/unauthorized' element={<UnAuthorized />} />
          </Routes>
        </div>
      </div>
    </>
  );
}
