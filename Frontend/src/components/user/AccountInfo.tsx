import { Button } from "flowbite-react";

export default function AccountInfo() {
  return (
    <>
      <div className='flex-col mb-5'>
        <h2 className='font-bold text-2xl'>Thông tin tài khoản</h2>
        <h3>Quản lý thông tin hồ sơ để bảo mật tài khoản</h3>
      </div>
      <div className='flex justify-center items-center border rounded-2xl bg-gray-200 shadow'>
        <div className='flex flex-col avater w-1/2 justify-between items-center'>
          <img
            src='../avatar_apu.png'
            alt='anh-dai-dien'
            className='w-44 h-44 rounded-full mb-8 -my-20'
          />
          <Button color={'gray'}>Upload file</Button>
        </div>
        <div className='flex flex-col information w-1/2 p-4 gap-6 mt-5'>
          <div className='flex flex-col gap-2 whitespace-nowrap shrink-0'>
            <label className='w-1/4'>Tên đăng nhập:</label>
            <input type='text' className='w-full border rounded-2xl px-2 py-1' />
          </div>
          <div className='flex flex-col gap-2 whitespace-nowrap'>
            <label className='w-1/4'>Họ và tên:</label>
            <input type='text' className='w-full border rounded-2xl px-2 py-1' />
          </div>
          <div className='flex flex-col gap-2 whitespace-nowrap'>
            <label className='w-1/4'>Số điện thoại:</label>
            <input type='text' className='w-full border rounded-2xl px-2 py-1' />
          </div>
          <div className='flex flex-col gap-2 whitespace-nowrap'>
            <label className='w-1/4'>Email:</label>
            <input type='email' className='w-full border rounded-2xl px-2 py-1' />
          </div>
          <div className='flex justify-end mt-4'>
            <Button>
              Cập nhật
            </Button>
          </div>
        </div>
      </div>
    </>
  );
}
