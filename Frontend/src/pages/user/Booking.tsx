import { Checkbox, HR, Label } from 'flowbite-react';
import TripInfo from '../../components/user/TripInfo';
import SearchBox from '../../components/user/SearchBox';

export default function Booking() {
  return (
    <main className='w-[80vw] mx-auto py-4'>
      <SearchBox />
      <section className='w-full pt-4 my-4'>
        <h2 className='text-2xl my-4 font-semibold text-center uppercase'>Kết quả</h2>
        <div className='grid grid-cols-9 gap-8'>
          <div className='col-span-3 p-4 border border-gray-300 bg-white shadow-lg rounded'>
            <p className='uppercase font-semibold mb-4'>Bộ lọc tìm kiếm</p>
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
                <Checkbox id='morning' />
                <Label htmlFor='morning'>Buổi chiều (12:00 - 18:00)</Label>
              </div>
              <div className='flex items-center gap-2'>
                <Checkbox id='morning' />
                <Label htmlFor='morning'>Buổi tối (18:00 - 24:00)</Label>
              </div>
            </div>
            <HR />
            <div>
              <p className='mb-2'>Loại xe</p>
              <div className='flex max-w-md gap-4' id='checkbox'>
                <div className='flex items-center gap-2'>
                  <Checkbox id='seat' />
                  <Label htmlFor='seat'>Ghế</Label>
                </div>
                <div className='flex items-center gap-2'>
                  <Checkbox id='seat1' />
                  <Label htmlFor='seat1'>Giường</Label>
                </div>
                <div className='flex items-center gap-2'>
                  <Checkbox id='seat2' />
                  <Label htmlFor='seat2'>Limousine</Label>
                </div>
              </div>
            </div>
          </div>
          <div className='col-span-6 flex flex-col gap-4'>
            <TripInfo />
            <TripInfo />
            <TripInfo />
          </div>
        </div>
      </section>
    </main>
  );
}
