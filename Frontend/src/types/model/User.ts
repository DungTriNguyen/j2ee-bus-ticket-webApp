export interface User {
  userName: string;
  fullName: string;
  passWord: string;
  email: string;
  phoneNumber: string;
  role: string;
  isBlock: boolean;
  isDelete: boolean;
  listFeedbackEntities_Id: any[];
  listTicketEntities_Id: any[];
}