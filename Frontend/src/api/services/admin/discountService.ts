import apiClient from "../../apiClient";
import { apiRequest } from '../apiRequest';

import { ApiResponse } from '@type/common/ApiResponse';
import { Discount } from '@type/model/Discount';

export const getDiscounts = async (): Promise<Discount[]> => {
  return apiRequest(async () => {
    const response = await apiClient.get<ApiResponse<Discount[]>>('/discounts');
    return response.data.data || [];
  });
};

export const getDiscountById = async (discountId: number): Promise<Discount> => {
  return apiRequest(async () => {
    const response = await apiClient.get<ApiResponse<Discount>>(`/discounts/${discountId}`);
    return response.data.data!;
  });
};

export const createDiscount = async (discount: Partial<Discount>): Promise<Discount> => {
  return apiRequest(async () => {
    const response = await apiClient.post<ApiResponse<Discount>>('/discounts/insert', discount);
    return response.data.data!;
  });
};

export const updateDiscount = async (discount: Partial<Discount>): Promise<Discount> => {
  return apiRequest(async () => {
    const response = await apiClient.put<ApiResponse<Discount>>('/discounts/update', discount);
    return response.data.data!;
  });
};

export const deleteDiscount = async (discountId: number): Promise<Discount> => {
  return apiRequest(async () => {
    const response = await apiClient.delete<ApiResponse<Discount>>(`/discounts/${discountId}`);
    return response.data.data!;
  });
};

export const hideDiscount = async (discountId: number): Promise<Discount> => {
  return apiRequest(async () => {
    const response = await apiClient.delete<ApiResponse<Discount>>(`/discounts/hidden/${discountId}`);
    return response.data.data!;
  });
};
