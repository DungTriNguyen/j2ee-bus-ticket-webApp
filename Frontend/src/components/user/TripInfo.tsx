import { Button, Timeline, Badge, HR } from 'flowbite-react';
import { GiPositionMarker } from 'react-icons/gi';
import { FaClock, FaBus } from 'react-icons/fa';
import { useNavigate } from 'react-router-dom';

export default function TripInfo() {
  const navigate = useNavigate();

  return (
    <div className='bg-white border border-gray-300 rounded shadow-lg py-2'>
      <div className='w-full flex items-center justify-around pt-6 pb-2 px-2'>
        <Timeline horizontal className=''>
          <Timeline.Item>
            <Timeline.Point icon={FaClock} />
            <Timeline.Content>
              <Timeline.Time className='text-black text-xl font-light'>5:00</Timeline.Time>
              <Timeline.Title className='font-semibold'>TP. Hồ Chí Minh</Timeline.Title>
            </Timeline.Content>
          </Timeline.Item>
          <Timeline.Item>
            <Timeline.Point icon={GiPositionMarker} />
            <Timeline.Content>
              <Timeline.Time className='text-black text-xl font-light'>7:30</Timeline.Time>
              <Timeline.Title className='font-semibold'>Vĩnh Long</Timeline.Title>
            </Timeline.Content>
          </Timeline.Item>
        </Timeline>
        <div className='flex flex-col'>
          <div className='flex gap-2 items-center'>
            <FaClock/>
            <p>2 giờ 30 phút</p>
          </div>
          <div className='flex gap-2 items-center'>
            <FaBus/>
            <p>Giường</p>
          </div>
        </div>
        <Badge color='success' className='text-lg'>69 chỗ trống</Badge>
      </div>
      <HR className='my-3'/>
      <div className='flex items-center justify-between px-10'>
      <p className='text-cyan-600 font-bold text-xl'>69.000đ</p>
      <Button size='lg' onClick={() => navigate('/dat-ve')}>Đặt vé</Button>
      </div>
    </div>
  );
}
