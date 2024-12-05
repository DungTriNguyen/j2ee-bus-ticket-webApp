export interface PenaltyTicket {
  penaltyTicketId: number;
  busEntity_Id: number;
  employeeEntity_Id: number;
  penaltyDay: string;
  description: string;
  responsibility: boolean;
  price: number;
  isDelete: boolean;
}