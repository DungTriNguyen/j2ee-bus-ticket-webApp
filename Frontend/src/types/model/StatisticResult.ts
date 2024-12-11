import { Ticket } from "./Ticket";
import { PenaltyTicket } from "./PenaltyTicket";
export interface StatisticResult {
  totalTickets?: number; // Tổng số vé
  totalPenaltyTickets?: number; // Tổng số vé phạt
  revenueOfRoutes?: { [route: string]: number }[]; // Doanh thu theo tuyến xe
  sumMoneyPenalty?: number; // Tổng số tiền phạt
  sumMoneyPenaltyDriverId?: { [driverId: string]: number }[]; // Tiền phạt theo ID tài xế
  size: number | null | undefined; // Số lượng
  sumMoneyPenaltyNoProcess?: number; // Tiền phạt chưa xử lý
  numberTicketPending?: number; // Số vé đang chờ xử lý
  numberTicketFailure?: number; // Số vé thất bại
  numberTicketSuccess?: number; // Số vé thành công
  sumMoneyTicketSuccess?: number; // Tổng tiền vé thành công
  mess?: string; // Thông báo từ API
  data?: (PenaltyTicket | Ticket)[]; // Danh sách vé hoặc vé phạt
}
