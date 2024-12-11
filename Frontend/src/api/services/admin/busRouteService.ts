import apiClient from "../../apiClient";
import { apiRequest } from '../apiRequest';

import { ApiResponse } from '@type/common/ApiResponse';
import { BusRoute } from '@type/model/BusRoute';

export const getBusRoutes = async (): Promise<BusRoute[]> => {
  return apiRequest(async () => {
    const response = await apiClient.get<ApiResponse<BusRoute[]>>('/busroutes');
    console.log("response api busRouter: ",response.data.data);
    return response.data.data || [];
  });
};

export const getBusRouteById = async (routesId: number): Promise<BusRoute> => {
  return apiRequest(async () => {
    const response = await apiClient.get<ApiResponse<BusRoute>>(`/busroutes/${routesId}`);
    
    return response.data.data!;
  });
};

export const createBusRoute = async (busRoute: Partial<BusRoute>): Promise<BusRoute> => {
  return apiRequest(async () => {
    const response = await apiClient.post<ApiResponse<BusRoute>>('/busroutes/insert', busRoute);
    return response.data.data!;
  });
};

export const updateBusRoute = async (busRoute: Partial<BusRoute>): Promise<BusRoute> => {
  return apiRequest(async () => {
    const response = await apiClient.put<ApiResponse<BusRoute>>('/busroutes/update', busRoute);
    return response.data.data!;
  });
};

export const deleteBusRoute = async (routesId: number): Promise<BusRoute> => {
  return apiRequest(async () => {
    const response = await apiClient.delete<ApiResponse<BusRoute>>(`/busroutes/delete/${routesId}`);
    return response.data.data!;
  });
};

export const hideBusRoute = async (routesId: number): Promise<BusRoute> => {
  return apiRequest(async () => {
    const response = await apiClient.delete<ApiResponse<BusRoute>>(`/busroutes/hidden/${routesId}`);
    return response.data.data!;
  });
};
