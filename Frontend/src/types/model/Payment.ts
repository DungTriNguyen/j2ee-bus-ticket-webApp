export interface Payment {
  paymentId: number;
  paymentTime: string;
  originalAmount: number;
  discountAmount: number;
  finalAmount: number;
  status: string;
  paymentMethod: string;
  listTicketEntities_Id: any[];
  isDelete: boolean;
}