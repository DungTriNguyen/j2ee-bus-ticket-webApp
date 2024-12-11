import { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import TripInfo from '../../components/user/TripInfo';
import SearchBox from '../../components/user/SearchBox';
import { searchBuses } from '../../api/services/user/customerService';
import { Checkbox, Label } from 'flowbite-react';

export default function Booking() {
  const [schedules, setSchedules] = useState<any[]>([]);
  const [loading, setLoading] = useState(false);

  const location = useLocation();

  const searchParams = new URLSearchParams(location.search);
  const departureLocation = searchParams.get('departureLocation');
  const destinationLocation = searchParams.get('destinationLocation');

  const handleSearch = async (fromLocation: string, toLocation: string) => {
    setLoading(true);
    try {
      const data = await searchBuses(fromLocation, toLocation);
      console.log(data.data);
      setSchedules(data.data);
    } catch (error) {
      setSchedules([]);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (departureLocation && destinationLocation) {
      handleSearch(departureLocation, destinationLocation);
    }
  }, [departureLocation, destinationLocation]);

  return (
    <main className='w-[80vw] mx-auto py-4'>
      <SearchBox
        departureLocationFromParent={departureLocation!}
        destinationLocationFromParent={destinationLocation!}
      />
      <section className='w-full pt-4 my-4'>
        <h2 className='text-2xl my-4 font-semibold text-center uppercase'>Kết quả</h2>
        <div className='grid grid-cols-9 gap-8'>
          <div className='col-span-3 p-4 border border-gray-300 bg-white shadow-lg rounded'>
            <div className='flex max-w-md flex-col gap-4' id='checkbox'>
              <p>Giờ đi</p>
              <div className='flex items-center gap-2'>
                <Checkbox id='morning' />
                <Label htmlFor='morning'>Sáng sớm (00:00 - 6:00)</Label>
              </div>
              <div className='flex items-center gap-2'>
                <Checkbox id='noon' />
                <Label htmlFor='noon'>Buổi sáng (06:00 - 12:00)</Label>
              </div>
              <div className='flex items-center gap-2'>
                <Checkbox id='afternoon' />
                <Label htmlFor='afternoon'>Buổi chiều (12:00 - 18:00)</Label>
              </div>
              <div className='flex items-center gap-2'>
                <Checkbox id='evening' />
                <Label htmlFor='evening'>Buổi tối (18:00 - 24:00)</Label>
              </div>
            </div>
          </div>
          <div className='col-span-6 flex flex-col gap-4'>
            {loading ? (
              <p>Loading...</p>
            ) : schedules.length === 0 ? (
              <p className='text-lg mt-8'>Không tìm thấy chuyến xe phù hợp</p>
            ) : (
              schedules.map((schedule, index) => (
                <TripInfo key={index} trip={schedule} />
              ))
            )}
          </div>
        </div>
      </section>
    </main>
  );
}
