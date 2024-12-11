import { useLocation } from 'react-router-dom';

interface PaymentResult {
  status: string;
  ticketId: string;
  seat: string;
  phoneNumber: string;
  bookingUser: string;
}

export default function Result() {
  const location = useLocation();

  const resultParam = new URLSearchParams(location.search);

  const ticketId = resultParam.get('ticketId');
  const paymentStatus = resultParam.get('status');
  const seat = resultParam.get('seat');
  const phoneNumber = resultParam.get('phoneNumber');
  const bookingUser = resultParam.get('bookingUser');

  return (
    <>
      <p>{ticketId}</p>
      <p>{paymentStatus}</p>
      <p>{seat}</p>
      <p>{phoneNumber}</p>
      <p>{bookingUser}</p>
    </>
  );
}
