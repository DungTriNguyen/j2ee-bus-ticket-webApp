export default function AboutUs() {
  return (
    <section className="bg-gray-100 text-gray-900 min-h-screen">
      <div className="text-center py-16 px-8 bg-white shadow-lg rounded-lg mx-auto max-w-5xl">
        {/* <div className=""> */}

        <h1 className="text-4xl uppercase font-bold text-teal-600">
          ANHBA BUS
        </h1>
        <p className="text-lg font-bold text-grey-500 mt-2">
          “Chất lượng là danh dự”
        </p>
        <p className="mt-6 text-gray-800 leading-relaxed max-w-3xl mx-auto">
          Tập đoàn AnhBa Bus được thành lập năm 2001. Với hoạt động kinh doanh
          chính trong lĩnh vực mua bán xe ô tô, vận tải hành khách, bất động sản
          và kinh doanh dịch vụ...
          <br /> <br />
          Trải qua hơn 20 năm hình thành và phát triển, đặt khách hàng là trọng
          tâm, chúng tôi tự hào trở thành doanh nghiệp vận tải nòng cốt đóng góp
          tích cực vào sự phát triển chung của ngành...
        </p>
        {/* </div> */}
      </div>
      {/* Tam Nhin va Su Menh Section */}
      <div className="bg-gray-50 py-16 px-8 mt-12 max-w-6xl mx-auto flex flex-col md:flex-row items-center">
        <div className="md:w-1/2">
          <h2 className="text-3xl font-bold text-teal-600">
            TẦM NHÌN VÀ SỨ MỆNH
          </h2>
          <p className="text-gray-500 mt-4 text-lg font-bold">
            Bảo Đảm Tổ Quốc Vì Một Việt Nam Hùng Cường
          </p>
          <ul className="mt-8 list-disc list-inside text-gray-800">
            <li className="py-2">
              Tạo môi trường làm việc năng động, thân thiện.
            </li>
            <li className="py-2">Phát triển từ lòng tin của khách hàng.</li>
            <li className="py-2">Trở thành tập đoàn dẫn đầu chuyên nghiệp.</li>
          </ul>
          <p className="mt-8 text-gray-800">
            AnhBa Bus luôn phấn đấu làm việc hiệu quả nhất, để luôn cống hiến,
            đóng góp hết sức mình vì một Việt Nam hùng cường.
          </p>
        </div>
        <div className="md:w-1/2 md:ml-12 mt-8 md:mt-0">
          <img
            src="/About_Us/tam-nhin-va-su-menh.png"
            alt="Tầm nhìn và sứ mệnh"
            className="w-full"
          />
        </div>
      </div>
      {/* Gia Tri Cot Loi Section */}
      <div className="py-16 px-8 bg-white shadow-lg rounded-lg mx-auto max-w-6xl mt-12 flex flex-col md:flex-row items-center  md:space-x-12">
        <div className="md:w-1/2 md:ml-12 mt-8 md:mt-0">
          <img
            src="/About_Us/gia-tri-cot-loi.png"
            alt="Giá trị cốt lõi"
            className="w-full"
          />
        </div>
        <div className="md:w-1/2">
          <h2 className="text-3xl font-bold text-teal-600">GIÁ TRỊ CỐT LÕI</h2>
          <p className="text-gray-500 mt-4 text-lg font-bold">
            Giá trị cốt lõi – AnhBa Bus
          </p>
          <p className="mt-8 text-gray-800">
            Các giá trị cốt lõi tạo nên sự thành công và khác biệt của AnhBa
            Bus, giúp chúng tôi trở thành lựa chọn hàng đầu của khách hàng...
          </p>
        </div>
      </div>

      {/* Triet ly Section */}
      <div className="bg-gray-50 py-16 px-8 mt-12 max-w-6xl mx-auto flex flex-col md:flex-row items-center">
        <div className="md:w-1/2">
          <h2 className="text-3xl font-bold text-teal-600">TRIẾT LÝ</h2>
          <p className="text-gray-500 mt-4 text-lg font-bold">Thầy Huấn</p>

          <p className="mt-8 text-gray-800">
            Hội nhập và phát triển góp phần vào sự thịnh vượng của đất nước.
            Nguồn nhân lực chính là nhân tố then chốt, là tài sản lớn nhất của
            Công ty AnhBa Bus, chú trọng tạo ra môi trường làm việc hiện đại,
            năng động, thân thiện và trao cơ hội phát triển nghề nghiệp cho tất
            cả thành viên. Sự hài lòng của khách hàng là minh chứng cho chất
            lượng dịch vụ của AnhBa Bus. Không ngừng hoàn thiện và phát triển
            năng lực kinh doanh, AnhBa Bus thấu hiểu nhu cầu khách hàng, mang
            đến sản phẩm dịch vụ hoàn hảo, đáp ứng tối đa mong đợi của khách
            hàng.
          </p>
        </div>
        <div className="md:w-1/2 md:ml-12 mt-8 md:mt-0">
          <img src="/About_Us/triet-ly.png" alt="Triết lý" className="w-full" />
        </div>
      </div>
    </section>
  );
}
