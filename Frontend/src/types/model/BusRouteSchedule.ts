export interface BusRouteSchedule {
    scheduleId: number;
    dayOfWeek: string;
    busEntity_Id: number;
    busRoutesEntity_Id: number;
    departureTime: string;
    isDelete: boolean;
    listTicketEntities_Id: number[];
  }