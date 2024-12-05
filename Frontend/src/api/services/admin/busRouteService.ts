import apiClient from "../../apiClient";
import { apiRequest } from '../apiRequest';

import { ApiResponse } from '@type/common/ApiResponse';
import { BusRoute } from '@type/model/BusRoute';

export const getBusRoutes = async (): Promise<BusRoute[]> => {
  return apiRequest(async () => {
    const response = await apiClient.get<ApiResponse<BusRoute[]>>('/busroutes');
    return response.data.data || [];
  });
};

export const getBusRouteById = async (busRouteId: number): Promise<BusRoute> => {
  return apiRequest(async () => {
    const response = await apiClient.get<ApiResponse<BusRoute>>(`/busroutes/${busRouteId}`);
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

export const deleteBusRoute = async (busRouteId: number): Promise<BusRoute> => {
  return apiRequest(async () => {
    const response = await apiClient.delete<ApiResponse<BusRoute>>(`/busroutes/delete/${busRouteId}`);
    return response.data.data!;
  });
};

export const hideBusRoute = async (busRouteId: number): Promise<BusRoute> => {
  return apiRequest(async () => {
    const response = await apiClient.delete<ApiResponse<BusRoute>>(`/busroutes/hidden/${busRouteId}`);
    return response.data.data!;
  });
};
