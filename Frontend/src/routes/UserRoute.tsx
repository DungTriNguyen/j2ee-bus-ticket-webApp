import { Routes, Route } from 'react-router-dom';

import Header from '../components/user/Header';
import Footer from '../components/user/Footer';

import Home from '../pages/user/Home';
import Booking from '../pages/user/Booking';
import Login from '../pages/user/Login';
import SignUp from '../pages/user/SignUp';
import AllRoute from '../pages/user/AllRoute';
import Ticket from '../pages/user/Ticket';
import FeedBack from '../pages/user/FeedBack';
import Contact from '../pages/user/Contact';
import AboutUs from '../pages/user/AboutUs';
import AnhBaPay from '../components/user/AnhBaPay';
import InforAccount from '../components/user/AccountInfo';
import HictoryByTicket from '../components/user/HictoryByTicket';
import ResetPassWord from '../components/user/ResetPassWord';
import AddressOfAccount from '../components/user/AddressOfAccount';
import Checkout from '../pages/user/Checkout';
import Dashboard from '../pages/user/Dashboard';
import NotFound from '../pages/shared/NotFound';
import { useSelector } from 'react-redux';
import { RootState } from '../redux/store';
import UnAuthorized from '../pages/shared/UnAuthorized';

function UserRoute() {
  const { user } = useSelector((state: RootState) => state.auth);

  return (
    <>
      <Header />
      <div className='bg-gray-100 min-h-screen'>
        <Routes>
          <Route path='/' element={<Home />} />
          <Route path='/chon-ve' element={<Booking />} />
          <Route path='/dat-ve' element={<Checkout />} />
          <Route path='/dang-nhap' element={<Login />} />
          <Route path='/dang-ky' element={<SignUp />} />
          <Route path='/lich-trinh' element={<AllRoute />} />
          <Route path='/tra-cuu' element={<Ticket />} />
          <Route path='/danh-gia-gop-y' element={<FeedBack />} />
          <Route path='/lien-he' element={<Contact />} />
          <Route path='/ve-chung-toi' element={<AboutUs />} />

          <Route path='/thong-tin' element={<Dashboard />}>
            <Route path='anhba-pay' element={<AnhBaPay />} />
            <Route path='chung' element={<InforAccount />} />
            <Route path='lich-su-mua-ve' element={<HictoryByTicket />} />
            <Route path='dat-lai-mat-khau' element={<ResetPassWord />} />
            <Route path='dia-chi' element={<AddressOfAccount />} />
          </Route>

          <Route path='/unauthorized' element={<UnAuthorized />} />
          <Route path='*' element = {<NotFound />} />
        </Routes>
      </div>
      <Footer />
    </>
  );
}

export default UserRoute;
