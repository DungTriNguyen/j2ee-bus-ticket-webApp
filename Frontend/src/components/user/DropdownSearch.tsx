import { useState } from 'react';

interface DropdownSearchProps {
  label: string;
  helper: string;
}

export default function DropdownSearch({...props}: DropdownSearchProps) {
  const [isOpen, setIsOpen] = useState(false);
  const [searchTerm, setSearchTerm] = useState('');

  const items = ['Uppercase', 'Lowercase', 'Camel Case', 'Kebab Case'];

  // Function to toggle the dropdown
  const toggleDropdown = () => {
    setIsOpen(!isOpen);
  };

  // Filter items based on the search term
  const filteredItems = items.filter((item) =>
    item.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className='flex flex-col justify-between'>
      <label htmlFor=''>{props.label}</label>
      <div className='relative group'>
        {/* Dropdown Button */}
        <button
          id='dropdown-button'
          onClick={toggleDropdown}
          className='inline-flex justify-between w-full px-4 py-[10px] text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-gray-100 focus:ring-blue-500'
        >
          <span className='mr-2'>{props.helper}</span>
          <svg
            xmlns='http://www.w3.org/2000/svg'
            className='w-5 h-5 ml-2 -mr-1'
            viewBox='0 0 20 20'
            fill='currentColor'
            aria-hidden='true'
          >
            <path
              fillRule='evenodd'
              d='M6.293 9.293a1 1 0 011.414 0L10 11.586l2.293-2.293a1 1 0 111.414 1.414l-3 3a1 1 0 01-1.414 0l-3-3a1 1 0 010-1.414z'
              clipRule='evenodd'
            />
          </svg>
        </button>

        {/* Dropdown Menu */}
        {isOpen && (
          <div
            id='dropdown-menu'
            className='absolute z-20 right-0 mt-2 rounded-md shadow-lg bg-white ring-1 ring-black ring-opacity-5 p-1 space-y-1'
          >
            {/* Search Input */}
            <input
              id='search-input'
              className='block w-full px-4 py-2 text-gray-800 border rounded-md border-gray-300 focus:outline-none'
              type='text'
              placeholder='Search items'
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              autoComplete='off'
            />

            {/* Dropdown items */}
            {filteredItems.length > 0 ? (
              filteredItems.map((item, index) => (
                <a
                  key={index}
                  href='#'
                  className='block px-4 py-2 text-gray-700 hover:bg-gray-100 active:bg-blue-100 cursor-pointer rounded-md'
                >
                  {item}
                </a>
              ))
            ) : (
              <div className='px-4 py-2 text-gray-500'>No results found</div>
            )}
          </div>
        )}
      </div>
    </div>
  );
}
