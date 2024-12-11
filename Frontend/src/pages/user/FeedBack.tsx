import { useState, useEffect, ChangeEvent } from "react";
import { FaStar } from "react-icons/fa";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { Feedback } from "@type/model/Feedback";
import { createFeedback, sendToEmailFeedback } from "../../api/services/admin/feedbackService";
import { useSelector } from "react-redux";
import { RootState } from "../../redux/store";

export default function FeedBack() {
  const [rating, setRating] = useState(0);
  const [formData, setFormData] = useState<Partial<Feedback>>({
    ticketEntity_Id: 0,
    content: "",
    rating: 0,
    isDelete: false,  // Default value for isDelete
  });
  const [loading, setLoading] = useState(false);
  

  // Giả sử lấy username từ thông tin đăng nhập
  // Thay bằng cách lấy giá trị từ auth state/context.
  const username = useSelector((state: RootState) => state.user.currentUser?.userName);
  const email_online = useSelector((state: RootState) => state.user.currentUser?.email);
  const email_default = email_online || 'default@example.com';
  console.log(">>>email",email_default);

  const handleChange = (
    e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    const { name, value } = e.target;

    setFormData((prev) => ({
      ...prev,
      [name]: name === "ticketEntity_Id" || name === "rating" ? Number(value) : value,
    }));
  };

  const handleStarClick = (index: number) => {
    setRating(index);
    setFormData((prev) => ({ ...prev, rating: index }));
  };

  const handleSubmit = async () => {
    // Kiểm tra điều kiện các trường bắt buộc không được để trống
    // if (username === null || username === undefined) {
    //   toast.error("Bạn chưa đăng nhập, Vui lòng đăng nhập!",{autoClose:1000});
    //   return;
    // }
    if (!formData.ticketEntity_Id || !formData.content || !rating) {
      toast.error("Vui lòng nhập đầy đủ thông tin form feedback!",{autoClose:1000});
      return;
    }
    if (!Number(formData.ticketEntity_Id)) {
      toast.error("Vui lòng nhập mã vé xe (chuyến đi) là một số !",{autoClose:1000});
      return;
    }

    // Chuyển đổi thời gian hiện tại sang định dạng 'yyyy-MM-ddThh:mm'
    const currentDateTime = new Date();
    const dateComment = currentDateTime.toISOString().slice(0, 16); // Định dạng: 'yyyy-MM-ddThh:mm'

    const feedbackData: Feedback = {
      ...formData,
      feedbackId: 0, // Chưa có id khi tạo mới
      accountEnity_userName: username, // Lấy từ người dùng đăng nhập
      // dateComment: new Date().toISOString(), // Tự động tạo thời gian gửi
      dateComment: dateComment, // Sử dụng định dạng datetime-local
      isDelete: false, // Giả định ban đầu chưa xoá
      
    } as Feedback;


    // const toMailsend = await sendToEmailFeedback(email_online,'Feedback','Chúng tôi sẽ phản hồi lại đánh giá của bạn trong thời gian sớm nhất. AnhBa Bus xin chân thành cảm ơn!!!')
    // console.log(">>send to mail: ",toMailsend);


    try {

      const toMailSend = await sendToEmailFeedback(
        email_default,
        "Feedback",
        "Chúng tôi sẽ phản hồi lại đánh giá của bạn trong thời gian sớm nhất. AnhBa Bus xin chân thành cảm ơn!!!"
      );
      console.log(">> send to mail: ", toMailSend);
      console.log(">>>email trc khi gui",email_default);
  
      setLoading(true);
      await createFeedback(feedbackData);
      toast.success("Gửi đánh giá thành công!",{autoClose: 800});
      setFormData({ ticketEntity_Id: 0, content: "", rating: 0, isDelete: false });
      setRating(0);
    } catch (error) {
      console.error(error);
      toast.error("Mã vé không tồn tại hoặc bạn chưa đăng nhập, xin vui lòng thử lại!",{autoClose:900});
    } finally {
      setLoading(false);
    }
  };

  return (
    <section className="py-24">
      <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
        <div className="grid lg:grid-cols-2 grid-cols-1">
          <div className="lg:mb-0 mb-10">
            <div className="group w-full h-full">
              <div className="relative h-full">
                <img
                  src="/feedback.png"
                  alt="Ticket Information"
                  className="w-full h-full lg:rounded-l-2xl rounded-2xl bg-blend-multiply bg-cyan-700 object-cover"
                />
                <h1 className="font-manrope text-white text-4xl font-bold leading-10 absolute top-11 left-11">
                  Đánh giá / góp ý
                </h1>
              </div>
            </div>
          </div>

          <div className="bg-gray-50 p-5 lg:p-11 lg:rounded-r-2xl rounded-2xl">
            <h2 className="text-cyan-600 font-manrope text-4xl font-semibold leading-10 mb-11">
              Đánh giá và góp ý
            </h2>
            <input
              type="number"
              name="ticketEntity_Id"
              value={formData.ticketEntity_Id || ""}
              onChange={handleChange}
              className="w-full h-12 text-gray-600 placeholder-gray-400 shadow-sm bg-transparent text-lg font-normal leading-7 rounded-full border border-gray-200 focus:outline-none pl-4 mb-10"
              placeholder="Mã chuyến đi (mã vé)"
            />
            <textarea
              name="content"
              value={formData.content || ""}
              onChange={handleChange}
              className="w-full h-24 text-gray-600 placeholder-gray-400 shadow-sm bg-transparent text-lg font-normal leading-7 rounded-2xl border border-gray-200 focus:outline-none pl-4 mb-10"
              placeholder="Nội dung cần góp ý đánh giá trong chuyến đi đó"
            ></textarea>

            <div className="flex mb-10 scale-150 pl-24">
              {[1, 2, 3, 4, 5].map((index) => (
                <FaStar
                  key={index}
                  className={`mr-2 cursor-pointer ${
                    index <= rating ? "text-[#ffb02f]" : "text-[#e4e5e9]"
                  }`}
                  onClick={() => handleStarClick(index)}
                />
              ))}
            </div>

            <button
              onClick={handleSubmit}
              disabled={loading}
              className={`w-full h-12 text-white text-base font-semibold leading-6 rounded-full transition-all duration-700 ${
                loading
                  ? "bg-gray-400 cursor-not-allowed"
                  : "hover:bg-cyan-800 bg-cyan-600"
              } shadow-sm`}
            >
              {loading ? "Đang gửi..." : "Xác nhận đánh giá"}
            </button>
          </div>
        </div>
      </div>
      <ToastContainer />
    </section>
  );
}
