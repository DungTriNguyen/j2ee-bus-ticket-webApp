import { Button } from 'flowbite-react';
import DropdownSearch from './DropdownSearch';
import { useNavigate } from 'react-router-dom';

export default function SearchBoxOfRoute() {
  const navigate = useNavigate();

  return (
    <section className='w-full bg-blue-100 p-3 rounded-xl'>
      <div className='w-full grid grid-cols-2 lg:grid-cols-2 gap-4 bg-white rounded-xl border border-blue-500 mx-auto px-4 pt-4 pb-8 shadow-sm'>
        <DropdownSearch label='Điểm đi' helper='Chọn điểm đi' />
        <DropdownSearch label='Điểm đến' helper='Chọn điểm đến' />
      </div>
      <div className='relative flex items-center justify-center'>
        <Button size={'lg'} className='absolute' onClick={() => navigate('')}>
          Tìm kiếm
        </Button>
      </div>
    </section>
  );
}
