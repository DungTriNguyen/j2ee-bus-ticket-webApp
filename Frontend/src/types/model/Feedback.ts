export interface Feedback {
  feedbackId: number;
  accountEnity_userName: string;
  ticketEntity_Id: number;
  content: string;
  rating: number;
  dateComment: string;
  isDelete: boolean;
}