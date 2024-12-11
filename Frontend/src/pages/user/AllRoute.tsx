import SearchBox from '../../components/user/SearchBox';
import { useNavigate } from 'react-router-dom';
export default function Routes() {
  const navigate = useNavigate();
  const scheduleData = [
    {
      distance_location: 102.5,
      price: 150000,
      trip_time: '2g 30p',
      departureLocation: 'Hà Nội',
      destinationLocation: 'Hải Phòng',
    },
    {
      distance_location: 90,
      price: 120000,
      trip_time: '2g 00p',
      departureLocation: 'Hà Nội',
      destinationLocation: 'Ninh Bình',
    },
    {
      distance_location: 75.5,
      price: 100000,
      trip_time: '1g 45p',
      departureLocation: 'Hải Phòng',
      destinationLocation: 'Quảng Ninh',
    },
    {
      distance_location: 298,
      price: 250000,
      trip_time: '5g 30p',
      departureLocation: 'Hà Nội',
      destinationLocation: 'Vinh',
    },
    {
      distance_location: 30.5,
      price: 50000,
      trip_time: '1g 00p',
      departureLocation: 'Đà Nẵng',
      destinationLocation: 'Hội An',
    },
    {
      distance_location: 125,
      price: 180000,
      trip_time: '3g 00p',
      departureLocation: 'Hồ Chí Minh',
      destinationLocation: 'Vũng Tàu',
    },
    {
      distance_location: 60.5,
      price: 80000,
      trip_time: '1g 30p',
      departureLocation: 'Cần Thơ',
      destinationLocation: 'Sóc Trăng',
    },
    {
      distance_location: 132,
      price: 200000,
      trip_time: '3g 15p',
      departureLocation: 'Lâm Đồng',
      destinationLocation: 'Nha Trang',
    },
    {
      distance_location: 180.5,
      price: 220000,
      trip_time: '4g 00p',
      departureLocation: 'Hà Nội',
      destinationLocation: 'Thanh Hóa',
    },
    {
      distance_location: 300,
      price: 350000,
      trip_time: '6g 30p',
      departureLocation: 'Hồ Chí Minh',
      destinationLocation: 'Lâm Đồng',
    },
    {
      distance_location: 105,
      price: 150000,
      trip_time: '2g 45p',
      departureLocation: 'Huế',
      destinationLocation: 'Đà Nẵng',
    },
    {
      distance_location: 102.5,
      price: 150000,
      trip_time: '2g 30p',
      departureLocation: 'Hải Phòng',
      destinationLocation: 'Hà Nội',
    },
    {
      distance_location: 75.5,
      price: 100000,
      trip_time: '1g 45p',
      departureLocation: 'Quảng Ninh',
      destinationLocation: 'Hải Phòng',
    },
    {
      distance_location: 298,
      price: 250000,
      trip_time: '5g 30p',
      departureLocation: 'Vinh',
      destinationLocation: 'Hà Nội',
    },
    {
      distance_location: 30.5,
      price: 50000,
      trip_time: '1g 00p',
      departureLocation: 'Hội An',
      destinationLocation: 'Đà Nẵng',
    },
    {
      distance_location: 125,
      price: 180000,
      trip_time: '3g 00p',
      departureLocation: 'Vũng Tàu',
      destinationLocation: 'Hồ Chí Minh',
    },
    {
      distance_location: 60.5,
      price: 80000,
      trip_time: '1g 30p',
      departureLocation: 'Sóc Trăng',
      destinationLocation: 'Cần Thơ',
    },
    {
      distance_location: 132,
      price: 200000,
      trip_time: '3g 15p',
      departureLocation: 'Nha Trang',
      destinationLocation: 'Lâm Đồng',
    },
    {
      distance_location: 180.5,
      price: 220000,
      trip_time: '4g 00p',
      departureLocation: 'Thanh Hóa',
      destinationLocation: 'Hà Nội',
    },
    {
      distance_location: 300,
      price: 350000,
      trip_time: '6g 30p',
      departureLocation: 'Lâm Đồng',
      destinationLocation: 'Hồ Chí Minh',
    },
    {
      distance_location: 105,
      price: 150000,
      trip_time: '2g 45p',
      departureLocation: 'Đà Nẵng',
      destinationLocation: 'Huế',
    },
  ];

  return (
    <main className='w-[80vw] mx-auto'>
      <SearchBox />
      <section>
        <div className='grid max-w-screen-xl px-4 py-8 mx-auto lg:gap-8 xl:gap-0 lg:py-16 lg:grid-cols-12'>
          <div className='col-span-12 flex flex-col items-center justify-between mb-8'>
            <h2 className='text-2xl font-bold lg:text-4xl text-teal-700'>
              Lịch Trình Xe - Hành trình của bạn bắt đầu từ đây
            </h2>
          </div>
        </div>
        <div className='overflow-x-auto shadow-md sm:rounded-lg'>
          <table className='min-w-full text-sm text-left text-gray-500'>
            <thead className='text-xs text-gray-700 uppercase bg-gray-200'>
              <tr>
                <th scope='col' className='px-6 py-3'>
                  Tuyến xe
                </th>
                <th scope='col' className='px-6 py-3'>
                  Loại xe
                </th>
                <th scope='col' className='px-6 py-3'>
                  Quãng đường
                </th>
                <th scope='col' className='px-6 py-3'>
                  Thời gian hành trình
                </th>
                <th scope='col' className='px-6 py-3'>
                  Giá vé
                </th>
                <th scope='col' className='px-6 py-3'>
                  Hành động
                </th>
              </tr>
            </thead>
            <tbody>
              {scheduleData.map((route, index) => (
                <tr key={index} className='bg-white border-b'>
                  <td className='px-6 py-4 whitespace-nowrap'>
                    {route.departureLocation} ➔ {route.destinationLocation}
                  </td>
                  <td className='px-6 py-4'>40 chỗ</td>
                  <td className='px-6 py-4'>{route.distance_location} km</td>
                  <td className='px-6 py-4'>{route.trip_time}</td>
                  <td className='px-6 py-4'>{route.price.toLocaleString('vi-vn')} VNĐ</td>
                  <td className='px-6 py-4'>
                    <button
                      className='text-white bg-teal-600 hover:bg-teal-800 font-medium rounded-lg text-sm px-4 py-2'
                      onClick={() =>
                        navigate(
                          `/chon-ve?departureLocation=${route.departureLocation}&destinationLocation=${route.destinationLocation}`
                        )
                      }
                    >
                      Tìm tuyến xe
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </section>
    </main>
  );
}
