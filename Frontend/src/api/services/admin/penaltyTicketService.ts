import apiClient from "../../apiClient";
import { apiRequest } from '../apiRequest';

import { ApiResponse } from '@type/common/ApiResponse';
import { PenaltyTicket } from '@type/model/PenaltyTicket';

export const getPenaltyTickets = async (): Promise<PenaltyTicket[]> => {
  return apiRequest(async () => {
    const response = await apiClient.get<ApiResponse<PenaltyTicket[]>>('/penaltytickets');
    return response.data.data || [];
  });
};

export const getPenaltyTicketById = async (penaltyTicketId: number): Promise<PenaltyTicket> => {
  return apiRequest(async () => {
    const response = await apiClient.get<ApiResponse<PenaltyTicket>>(`/penaltytickets/${penaltyTicketId}`);
    return response.data.data!;
  });
};

export const createPenaltyTicket = async (penaltyTicket: Partial<PenaltyTicket>): Promise<PenaltyTicket> => {
  return apiRequest(async () => {
    const response = await apiClient.post<ApiResponse<PenaltyTicket>>('/penaltytickets/insert', penaltyTicket);
    return response.data.data!;
  });
};

export const updatePenaltyTicket = async (penaltyTicket: Partial<PenaltyTicket>): Promise<PenaltyTicket> => {
  return apiRequest(async () => {
    const response = await apiClient.put<ApiResponse<PenaltyTicket>>('/penaltytickets/update', penaltyTicket);
    return response.data.data!;
  });
};

export const deletePenaltyTicket = async (penaltyTicketId: number): Promise<PenaltyTicket> => {
  return apiRequest(async () => {
    const response = await apiClient.delete<ApiResponse<PenaltyTicket>>(`/penaltytickets/delete/${penaltyTicketId}`);
    return response.data.data!;
  });
};

export const hidePenaltyTicket = async (penaltyTicketId: number): Promise<PenaltyTicket> => {
  return apiRequest(async () => {
    const response = await apiClient.delete<ApiResponse<PenaltyTicket>>(`/penaltytickets/hidden/${penaltyTicketId}`);
    return response.data.data!;
  });
};
