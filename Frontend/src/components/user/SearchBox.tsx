import { Button, TextInput } from 'flowbite-react';
import DropdownSearch from './DropdownSearch';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

interface SearchBoxProps {
  departureLocationFromParent?: string;
  destinationLocationFromParent?: string;
}

export default function SearchBox({ departureLocationFromParent, destinationLocationFromParent }: SearchBoxProps) {
  const [departureLocation, setDepartureLocation] = useState<string>(departureLocationFromParent || '');
  const [destinationLocation, setDestinationLocation] = useState<string>(destinationLocationFromParent || '');

  const navigate = useNavigate();

  return (
    <section className='w-full bg-blue-100 p-3 my-8 rounded-xl'>
      <div className='w-full flex justify-around gap-4 bg-white rounded-xl border border-blue-500 mx-auto px-4 pt-4 pb-8 shadow-sm'>
        <DropdownSearch
          label='Điểm đi'
          helper='Chọn điểm đi'
          value={departureLocation}
          onSelect={setDepartureLocation}
        />
        <DropdownSearch
          label='Điểm đến'
          helper='Chọn điểm đến'
          value={destinationLocation}
          onSelect={setDestinationLocation}
        />
      </div>
      <div className='relative flex items-center justify-center'>
        <Button
          size={'lg'}
          className='absolute'
          onClick={() => navigate(`/chon-ve?departureLocation=${departureLocation}&destinationLocation=${destinationLocation}`)}
        >
          Tìm kiếm
        </Button>
      </div>
    </section>
  );
}
