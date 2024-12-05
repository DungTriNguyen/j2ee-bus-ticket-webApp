export default function ResetPassWord() {
  return (
    <>
      <div className='flex flex-col mb-7 justify-between'>
        <h1 className='font-bold'>Đặt lại mật khẩu</h1>
        <h2>Để bảo mật tài khoản, vui lòng không chia sẻ mật khẩu cho người khác</h2>
      </div>

      {/* <div className="flex flex-col gap-4"> */}
      {/* Filter Options */}
      <div className='flex flex-col justify-center items-center bg-gray-200 shadow'>
        <form className='flex flex-col border rounded-2xl justify-center items-center m-4 p-10 w-1/2'>
          <div className='mb-5'>
            <h1>(+84) 9233 244 91</h1>
          </div>
          <div className='flex flex-col mb-5 w-full'>
            <label htmlFor=''>
              <span className='text-red-500 ml-1'>*</span>Nhập mật khẩu cũ
            </label>
            <input type='password' className='rounded-2xl w-full' />
          </div>
          <div className='flex flex-col mb-5 w-full'>
            <label htmlFor=''>
              <span className='text-red-500 ml-1'>*</span>Mật khẩu mới
            </label>
            <input type='password' className='rounded-2xl' />
          </div>
          <div className='flex flex-col mb-5 w-full'>
            <label htmlFor=''>
              <span className='text-red-500 ml-1'>*</span>Nhập lại mật khẩu mới
            </label>
            <input type='password' className='rounded-2xl' />
          </div>
          <div className='flex gap-2 w-full justify-center'>
            <button className='border border-teal-600 hover:border-teal-900 p-4 text-teal-600  rounded-2xl'>
              Hủy
            </button>
            <button className='bg-teal-600 hover:bg-teal-800 rounded-2xl p-4 text-white'>
              Xác nhận
            </button>
          </div>
        </form>
      </div>
    </>
  );
}
