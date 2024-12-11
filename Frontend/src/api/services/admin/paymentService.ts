import apiClient from "../../apiClient";
import { apiRequest } from '../apiRequest';

import { ApiResponse } from '@type/common/ApiResponse';
import { Payment } from '@type/model/Payment';

export const getPayments = async (): Promise<Payment[]> => {
  return apiRequest(async () => {
    const response = await apiClient.get<ApiResponse<Payment[]>>('/payments');
    return response.data.data || [];
  });
};

export const getPaymentById = async (paymentId: number): Promise<Payment> => {
  return apiRequest(async () => {
    const response = await apiClient.get<ApiResponse<Payment>>(`/payments/${paymentId}`);
    return response.data.data!;
  });
};

export const createPayment = async (payment: Partial<Payment>): Promise<Payment> => {
  return apiRequest(async () => {
    const response = await apiClient.post<ApiResponse<Payment>>('/payments/insert', payment);
    return response.data.data!;
  });
};

export const updatePayment = async (payment: Partial<Payment>): Promise<Payment> => {
  return apiRequest(async () => {
    const response = await apiClient.put<ApiResponse<Payment>>('/payments/update', payment);
    return response.data.data!;
  });
};

export const deletePayment = async (paymentId: number): Promise<Payment> => {
  return apiRequest(async () => {
    const response = await apiClient.delete<ApiResponse<Payment>>(`/payments/delete/${paymentId}`);
    return response.data.data!;
  });
};

export const hidePayment = async (paymentId: number): Promise<Payment> => {
  return apiRequest(async () => {
    const response = await apiClient.delete<ApiResponse<Payment>>(`/payments/hidden/${paymentId}`);
    return response.data.data!;
  });
};
