export interface BusRoute {
  routesId: number;
  departureLocation: string;
  destinationLocation: string;
  distanceKilometer: number;
  tripTime: string;
  // arivalTime: string;
  price: number;
  isDelete: boolean;
  listBusRouteSchedules_Id: number[];
}