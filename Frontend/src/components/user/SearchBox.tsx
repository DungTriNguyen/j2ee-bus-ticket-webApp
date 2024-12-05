import { Button, Datepicker, TextInput } from 'flowbite-react';
import DropdownSearch from './DropdownSearch';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

export default function SearchBox() {
  const navigate = useNavigate();

  const [seatQuantity, setSeatQuantity] = useState(1);

  return (
    <section className='w-full bg-blue-100 p-3 my-8 rounded-xl'>
      <div className='w-full grid grid-cols-2 lg:grid-cols-4 gap-4 bg-white rounded-xl border border-blue-500 mx-auto px-4 pt-4 pb-8 shadow-sm'>
        <DropdownSearch label='Điểm đi' helper='Chọn điểm đi' />
        <DropdownSearch label='Điểm đến' helper='Chọn điểm đến' />
        <div>
          <label htmlFor=''>Ngày đi</label>
          <Datepicker />
        </div>
        <div>
          <label htmlFor=''>Số lượng</label>
          <TextInput type='number' value={seatQuantity} min={1} onChange={ (e) => setSeatQuantity(e.target.value)}/>
        </div>
      </div>
      <div className='relative flex items-center justify-center'>
        <Button size={'lg'} className='absolute'  onClick={() => navigate('/chon-ve')}>
          Tìm kiếm
        </Button>
      </div>
    </section>
  );
}
