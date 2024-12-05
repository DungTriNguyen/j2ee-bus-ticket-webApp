export default function UnAuthorized() {
  return (
    <>
      <section>
        <div className='py-8 px-4 mx-auto max-w-screen-xl lg:py-16 lg:px-6'>
          <div className='mx-auto max-w-screen-sm text-center'>
            <h1 className='mb-4 text-7xl tracking-tight font-extrabold lg:text-9xl text-cyan-700'>
              401
            </h1>
            <p className='mb-4 text-3xl tracking-tight font-bold text-gray-900 md:text-4xl dark:text-white'>
              Tài khoản của bạn không được phép truy cập tài nguyên này.
            </p>
            <a
              href='/'
              className='inline-flex text-cyan-600 hover:bg-gray-200 focus:ring-4 focus:outline-none focus:ring-cyan-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center my-4'
            >
              Trở về trang chủ
            </a>
          </div>
        </div>
      </section>
    </>
  );
}
