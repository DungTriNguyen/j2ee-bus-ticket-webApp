import apiClient from "../../apiClient";

import { apiRequest } from '../apiRequest'; // Import centralized API middleware
import { ApiResponse } from '@type/common/ApiResponse';
import { BusRouteSchedule } from '@type/model/BusRouteSchedule';

export const getBusRouteSchedules = async (): Promise<BusRouteSchedule[]> => {
  return apiRequest(async () => {
    const response = await apiClient.get<ApiResponse<BusRouteSchedule[]>>('/bus_routes_schedule');
    return response.data.data || [];
  });
};

export const getBusRouteScheduleById = async (scheduleId: number): Promise<BusRouteSchedule> => {
  return apiRequest(async () => {
    const response = await apiClient.get<ApiResponse<BusRouteSchedule>>(`/bus_routes_schedule/${scheduleId}`);
    return response.data.data!;
  });
};

export const createBusRouteSchedule = async (BusRouteSchedule: Partial<BusRouteSchedule>): Promise<BusRouteSchedule> => {
  return apiRequest(async () => {
    const response = await apiClient.post<ApiResponse<BusRouteSchedule>>('/bus_routes_schedule/insert', BusRouteSchedule);
    return response.data.data!;
  });
};

export const updateBusRouteSchedule = async (BusRouteSchedule: Partial<BusRouteSchedule>): Promise<BusRouteSchedule> => {
  return apiRequest(async () => {
    const response = await apiClient.put<ApiResponse<BusRouteSchedule>>('/bus_routes_schedule/update', BusRouteSchedule);
    return response.data.data!;
  });
};

export const deleteBusRouteSchedule = async (scheduleId: number): Promise<BusRouteSchedule> => {
  return apiRequest(async () => {
    const response = await apiClient.delete<ApiResponse<BusRouteSchedule>>(`/bus_routes_schedule/delete/${scheduleId}`);
    console.log("ID received: " + response.data.data?.scheduleId);
    return response.data.data!;
  });
};

export const hideBusRouteSchedule = async (scheduleId: number): Promise<BusRouteSchedule> => {
  return apiRequest(async () => {
    const response = await apiClient.delete<ApiResponse<BusRouteSchedule>>(`/bus_routes_schedule/hidden/${scheduleId}`);
    return response.data.data!;
  });
};












