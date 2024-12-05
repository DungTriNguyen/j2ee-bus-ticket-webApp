import SearchBoxOfRoute from '../../components/user/SearchBoxOfRoute';
import { useNavigate } from 'react-router-dom';
export default function Routes() {
  const navigate = useNavigate();
  const scheduleData = [
    {
      from: 'An Nhơn',
      to: 'TP. Hồ Chí Minh',
      type: 'Giường',
      distance: '639km',
      time: '11 giờ 30 phút',
      price: '---',
    },
    {
      from: 'An Nhơn',
      to: 'TP. Hồ Chí Minh',
      type: 'Giường',
      distance: '660km',
      time: '13 giờ 46 phút',
      price: '---',
    },
    {
      from: 'An Nhơn',
      to: 'TP. Hồ Chí Minh',
      type: 'Giường',
      distance: '627km',
      time: '10 giờ 7 phút',
      price: '---',
    },
    {
      from: 'Bạc Liêu',
      to: 'TP. Hồ Chí Minh',
      type: 'Giường',
      distance: '271km',
      time: '6 giờ',
      price: '---',
    },
    {
      from: 'Bảo Lộc',
      to: 'Bình Sơn',
      type: 'Limousine',
      distance: '650km',
      time: '15 giờ 30 phút',
      price: '---',
    },
    {
      from: 'Bảo Lộc',
      to: 'Đà Nẵng',
      type: 'Giường',
      distance: '756km',
      time: '16 giờ 38 phút',
      price: '---',
    },
    {
      from: 'Bảo Lộc',
      to: 'Huế',
      type: 'Giường',
      distance: '827km',
      time: '19 giờ',
      price: '---',
    },
    {
      from: 'Bến Tre',
      to: 'TP. Hồ Chí Minh',
      type: 'Giường',
      distance: '75km',
      time: '2 giờ',
      price: '---',
    },
    {
      from: 'Bình Dương',
      to: 'Bình Dương',
      type: 'Limousine',
      distance: '260km',
      time: '5 giờ 26 phút',
      price: '---',
    },
  ];

  return (
    <main className='w-[80vw] mx-auto'>
      <SearchBoxOfRoute />
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
                    {route.from} ➔ {route.to}
                  </td>
                  <td className='px-6 py-4'>{route.type}</td>
                  <td className='px-6 py-4'>{route.distance}</td>
                  <td className='px-6 py-4'>{route.time}</td>
                  <td className='px-6 py-4'>{route.price}</td>
                  <td className='px-6 py-4'>
                    <button className='text-white bg-teal-600 hover:bg-teal-800 font-medium rounded-lg text-sm px-4 py-2' onClick={() => navigate('/dat-ve')}>
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
