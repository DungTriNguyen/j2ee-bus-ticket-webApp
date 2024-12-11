export interface Bus {
  busId: number;
  busNumber: string;
  capacity: number;
  brand: string;
  isDelete: boolean;
  routesId: number | null;
  listEmployeeEntities_Id: number[];
  listBusRouteSchedules_Id: any[];
  listPenaltyTicketEntities_Id: number[];
} 