import { FaPen, FaTrashAlt } from 'react-icons/fa';

export default function AddressOfAccount() {
  return (
    <>
      <div className='flex bg-teal-100 p-4 rounded-2xl mb-7 justify-between'>
        <div className='flex flex-col'>
          <h1 className='font-bold'>Địa chỉ của bạn</h1>
          <h2>Địa chỉ của bạn sẽ được sử dụng để nhập nhanh điểm đón - trả tận nơi</h2>
        </div>
        <div className='bg-teal-600 hover:bg-teal-800 rounded-2xl'>
          <button
            className='m-4 text-white'
            // onClick={() => navigate("/")}
          >
            Thêm địa chỉ mới
          </button>
        </div>
      </div>
      <div className=''>
        {/* address1 */}
        <div className='flex bg-gray-50 p-4 rounded-2xl mb-7 justify-between bg-gray-200 shadow'>
          <div className='flex flex-col m-4'>
            <h1 className='font-bold'>TP HCM</h1>
            <h2>Loại địa chỉ</h2>
            <h2>Địa chỉ</h2>
          </div>
          <div className='flex justify-center items-center text-white'>
            <div className='bg-teal-600 hover:bg-teal-800 rounded-2xl p-2 mr-2'>
              <a href='/' className='flex items-center'>
                <FaPen className='mr-1' />
                Sửa
              </a>
            </div>
            <div className='bg-teal-600 hover:bg-teal-800 rounded-2xl p-2'>
              <a href='/' className='flex items-center'>
                <FaTrashAlt className='mr-1' /> Xóa
              </a>
            </div>
          </div>
        </div>
        {/* address2 */}
        <div className='flex bg-gray-50 p-4 rounded-2xl mb-7 justify-between bg-gray-200 shadow'>
          <div className='flex flex-col m-4'>
            <h1 className='font-bold'>TP HCM</h1>
            <h2>Loại địa chỉ</h2>
            <h2>Địa chỉ</h2>
          </div>
          <div className='flex justify-center items-center text-white'>
            <div className='bg-teal-600 hover:bg-teal-800 rounded-2xl p-2 mr-2'>
              <a href='/' className='flex items-center'>
                <FaPen className='mr-1' />
                Sửa
              </a>
            </div>
            <div className='bg-teal-600 hover:bg-teal-800 rounded-2xl p-2'>
              <a href='/' className='flex items-center'>
                <FaTrashAlt className='mr-1' /> Xóa
              </a>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
