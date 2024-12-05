import SearchBox from "../../components/user/SearchBox";

export default function Home() {
  return (
    <main className='w-[80vw] mx-auto'>
      <img src='/banner.png' className='h-[40vh] object-cover mx-auto rounded-2xl shadow-xl' />
      <SearchBox />
      <section>
        <div className='grid max-w-screen-xl px-4 py-8 mx-auto lg:gap-8 xl:gap-0 lg:py-16 lg:grid-cols-12'>
          <div className='col-span-12 flex flex-col items-center justify-between mb-8'>
            <h2 className='text-2xl font-bold lg:text-4xl text-teal-700'>
              ANH BA BUS - CHẤT LƯỢNG LÀ DANH DỰ
            </h2>
            <h3 className='text-lg font-semibold lg:text-2xl'>
              Được khách hàng tin tưởng và lựa chọn
            </h3>
          </div>
          <div className='mr-auto place-self-center lg:col-span-7'>
            <div className='my-2'>
              <h2 className='max-w-2xl mb-4 text-xl font-extrabold tracking-tight leading-none md:text-2xl xl:text-3xl'>
                Hơn 10 Triệu Lượt khách
              </h2>
              <p className='max-w-2xl mb-6 text-gray-600 lg:mb-8 md:text-md lg:text-lg'>
                Nhà xe Anh Ba phục vụ hơn 10 triệu lượt khách bình quân mỗi năm trên toàn quốc.
              </p>
            </div>
            <div className='my-2'>
              <h2 className='max-w-2xl mb-4 text-xl font-extrabold tracking-tight leading-none md:text-2xl xl:text-3xl'>
                Hơn 200 Phòng vé - Bưu cục
              </h2>
              <p className='max-w-2xl mb-6 text-gray-600 lg:mb-8 md:text-md lg:text-lg'>
                Nhà xe Anh Ba có hơn 200 phòng vé, trạm trung chuyển, và bến xe trên toàn hệ
                thống.
              </p>
            </div>
            <div className='my-2'>
              <h2 className='max-w-2xl mb-4 text-xl font-extrabold tracking-tight leading-none md:text-2xl xl:text-3xl'>
                Hơn 800 Chuyến xe
              </h2>
              <p className='max-w-2xl mb-6 text-gray-600 lg:mb-8 md:text-md lg:text-lg'>
                Nhà xe Anh Ba vận hành hơn 800 chuyến xe đường dài và liên tỉnh mỗi ngày.
              </p>
            </div>
          </div>
          <div className='hidden lg:mt-0 lg:col-span-5 lg:flex'>
            <img src='/hero.jpg' alt='mockup' />
          </div>
        </div>
      </section>
    </main>
  );
}
