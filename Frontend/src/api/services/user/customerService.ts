import { Schedule } from '@type/model/Schedule';
import apiClient from '../../apiClient';
import { apiRequest } from '../apiRequest';
import { User } from '@type/model/User';

export const register = async (data: any): Promise<any> => {
  return apiRequest(async () => {
    const response = await apiClient.post<User>('/accounts/register', null, {
      params: {
        ...data
      }
    });
    return response.data;
  });
}

export const searchBuses = async (departure: string, destination: string): Promise<any> => {
  return apiRequest(async () => {
    const response = await apiClient.get<Schedule[]>(
      `/bus_routes_schedule/departureLocation_destinationLocation?departureLocation=${departure}&destinationLocation=${destination}`
    );
    return response.data || [];
  });
};

export const getSeatList = async (data: any): Promise<any> => {
  return apiRequest(async () => {
    const response = await apiClient.get<any>(`/buses/list_empty_seat?departureDate=${data.departureDate}&scheduleId=${data.scheduleId}`);
    return response.data || [];
  });
}

export const createPayment = async (data: any): Promise<any> => {
  return apiRequest(async () => {
    const response = await apiClient.post<any>('/create_payment', null, {
      params: {
        ...data
      }
    });
    return response.data;
  });
}

export const getDiscountById = async (id: number): Promise<any> => {
  return apiRequest(async () => {
    const response = await apiClient.get<any>(`/discounts/${id}`);
    return response.data;
  });
}
