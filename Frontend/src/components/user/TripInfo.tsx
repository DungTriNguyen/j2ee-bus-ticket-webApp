import { Button, Timeline, Badge, HR } from 'flowbite-react';
import { GiPositionMarker } from 'react-icons/gi';
import { FaClock, FaBus } from 'react-icons/fa';
import { useNavigate } from 'react-router-dom';

interface TripInfoProps {
  trip: {
    departurnTime: string;
    destinationTime: string;
    departureLocation: string;
    destinationLocation: string;
    departurnDate: string;
    tripTime: string;
    price: number;
    busId: number;
    scheduleId: number;
  };
}

function formatTime(timeString: string): string {
  const [hours, minutes] = timeString.split(':');
  const formattedHours = parseInt(hours, 10);
  return `${formattedHours}g ${minutes}p`;
}

function formatDate(dateString: string): string {
  const date = new Date(dateString);
  const day = String(date.getDate()).padStart(2, '0');
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const year = date.getFullYear();
  return `${day}/${month}/${year}`;
}

export default function TripInfo({ trip }: TripInfoProps) {
  const navigate = useNavigate();

  return (
    <div className='bg-white border border-gray-300 rounded shadow-lg py-2'>
      <div className='w-full flex items-center justify-around pt-6 pb-2 px-2'>
        <Timeline horizontal>
          <Timeline.Item>
            <Timeline.Point icon={FaClock} />
            <Timeline.Content>
              <Timeline.Time className='text-black text-xl font-light'>
                {formatTime(trip.departurnTime)}
              </Timeline.Time>
              <Timeline.Title className='font-semibold'>
                {trip.departureLocation}
              </Timeline.Title>
            </Timeline.Content>
          </Timeline.Item>
          <Timeline.Item>
            <Timeline.Point icon={GiPositionMarker} />
            <Timeline.Content>
              <Timeline.Time className='text-black text-xl font-light'>
                {formatTime(trip.destinationTime)}
              </Timeline.Time>
              <Timeline.Title className='font-semibold'>
                {trip.destinationLocation}
              </Timeline.Title>
            </Timeline.Content>
          </Timeline.Item>
        </Timeline>
        <div className='flex flex-col'>
          <div className='flex gap-2 items-center'>
            <FaClock />
            <p>{formatTime(trip.tripTime)}</p>
          </div>
          <div className='flex gap-2 items-center'>
            <FaBus />
            <p>40 chỗ</p>
          </div>
        </div>
        <Badge color='success' className='text-lg'>
          {formatDate(trip.departurnDate)}
        </Badge>
      </div>
      <HR className='my-3' />
      <div className='flex items-center justify-between px-10'>
        <p className='text-cyan-600 font-bold text-xl'>{trip.price.toLocaleString()}đ</p>
        <Button
          size='lg'
          onClick={() =>
            navigate(
              `/dat-ve?busId=${trip.busId}&scheduleId=${trip.scheduleId}&departure=${trip.departureLocation}&destination=${trip.destinationLocation}&price=${trip.price}&date=${trip.departurnDate}&time=${trip.departurnTime}`
            )
          }
        >
          Đặt vé
        </Button>
      </div>
    </div>
  );
}
