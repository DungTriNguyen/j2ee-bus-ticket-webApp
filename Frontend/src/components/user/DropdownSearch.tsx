import { useState, useEffect } from 'react';

interface DropdownSearchProps {
  label: string;
  helper: string;
  value: string;
  onSelect: (location: string) => void;  // This prop should be a function to handle the selected location
}

// DropdownSearch.tsx
export default function DropdownSearch({ label, helper, value, onSelect }: DropdownSearchProps) {
  const [isOpen, setIsOpen] = useState(false);
  const [searchTerm, setSearchTerm] = useState('');
  const [locations, setLocations] = useState<string[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const toggleDropdown = () => {
    setIsOpen(!isOpen);
  };

  const handleSelect = (item) => {
    setIsOpen(false);
    onSelect(item);
  }

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      setError(null);

      try {
        const response = await fetch('/data/location.json');
        if (!response.ok) {
          throw new Error('Failed to fetch data');
        }
        const data = await response.json();
        const locationNames = data.map((location: { name: string }) => location.name);
        setLocations(locationNames);
      } catch (err) {
        setError('Failed to load locations');
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  const filteredLocations = locations.filter((location) =>
    location.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className='flex flex-col basis-[40%] justify-between'>
      <label>{label}</label>
      <div className='relative group'>
        <button
          onClick={toggleDropdown}
          className='inline-flex justify-between w-full px-4 py-[10px] text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md shadow-sm'
        >
          <span>{value || helper}</span>
          <svg xmlns='http://www.w3.org/2000/svg' className='w-5 h-5 ml-2 -mr-1' viewBox='0 0 20 20' fill='currentColor'>
            <path fillRule='evenodd' d='M6.293 9.293a1 1 0 011.414 0L10 11.586l2.293-2.293a1 1 0 111.414 1.414l-3 3a1 1 0 01-1.414 0l-3-3a1 1 0 010-1.414z' clipRule='evenodd' />
          </svg>
        </button>

        {isOpen && (
          <div className='absolute h-[40vh] max-w-md overflow-y-scroll z-10 mt-2 rounded-md shadow-lg bg-white ring-1 ring-black ring-opacity-5 p-1 space-y-1'>
            <input
              className='block w-full px-4 py-2 text-gray-800 border rounded-md border-gray-300'
              type='text'
              placeholder='Search locations'
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
            {loading && <div className='px-4 py-2 text-gray-500'>Loading...</div>}
            {error && <div className='px-4 py-2 text-red-500'>{error}</div>}
            {filteredLocations.map((item, index) => (
              <a
                key={index}
                className='block px-4 py-2 text-gray-700 hover:bg-gray-100'
                onClick={() => handleSelect(item)} 
              >
                {item}
              </a>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}
