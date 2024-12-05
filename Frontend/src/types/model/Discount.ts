export interface Discount {
  discountId?: number;
  discountPercentage: number;
  validFrom: string;
  validUntil: string;
  amount: number;
  isDelete: boolean;
  listTicketEntities_Id: any[];
}