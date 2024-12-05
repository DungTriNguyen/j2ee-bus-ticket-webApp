export interface Employee {
  driverId: number;
  isDriver: boolean;
  driverName: string;
  licenseNumber: string;
  phoneNumber: string;
  isDelete: boolean;
  listBusEntities_Id: number[];
  listPenaltyTicketEntities_Id: number[];
}