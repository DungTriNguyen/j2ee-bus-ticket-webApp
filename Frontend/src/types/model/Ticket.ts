export interface Ticket {
  ticketId: number;
  accountEnity_Id: string;
  busRouteSchedule_Id: number;
  // busRoutesEntity_Id: number;
  paymentEntity_Id: number;
  discountEntity_Id: number | null ;
  seatNumber: string;
  departureDate: string;
  price: number;
  phoneNumber: string;
  status: string;
  listFeedbackEntities_Id: number[];
  isDelete: boolean;
}