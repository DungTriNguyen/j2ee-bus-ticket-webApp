import { Routes, Route, Navigate } from 'react-router-dom';

import { useSelector } from 'react-redux';
import { RootState } from '../redux/store';

import AdminSidebar from '../components/admin/AdminSidebar';

import AdminDashboard from '../pages/admin/AdminDashboard';
import Trip from '../pages/admin/Trip';
import TripRoute from '../pages/admin/TripRoute';
import BusRouteSchedule  from '../pages/admin/BusRouteSchedule';
import Bus from '../pages/admin/Bus';
import Driver from '../pages/admin/Driver';
import Ticket from '../pages/admin/Ticket';
import Payment from '../pages/admin/Payment';
import Discount from '../pages/admin/Discount';
import FeedBack from '../pages/admin/FeedBack';
import UnAuthorized from '../pages/shared/UnAuthorized';
import PenaltyTicket from '../pages/admin/PenaltyTicket';
import Account from '../pages/admin/Account';
import ProtectedRoute from '../components/ProtectedRoute';

export default function AdminRoute() {
  const user = useSelector((state: RootState) => state.user);

  const userRole = user.currentUser?.role;

  if(userRole === 'ROLE_USER') {
    return <Navigate to="/unauthorized" replace />;
  }

  if (userRole !== 'ROLE_ADMIN' && userRole !== 'ROLE_MANAGER') {
    return <Navigate to="/unauthorized" replace />;
  }

  return (
    <ProtectedRoute>
      <div className='flex h-screen'>
        <AdminSidebar />

        {/* Content Area */}
        <div className='flex-grow p-4'>
          <Routes>
            <Route path='/' element={<AdminDashboard />} />
            <Route path='/chuyen-di' element={<Trip />} />
            <Route path='/phan-cong-xe-tuyen' element={<BusRouteSchedule />} />
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
    </ProtectedRoute>
  );
}
