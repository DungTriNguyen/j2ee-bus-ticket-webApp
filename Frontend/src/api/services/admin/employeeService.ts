import apiClient from "../../apiClient";
import { apiRequest } from '../apiRequest';

import { ApiResponse } from '@type/common/ApiResponse';
import { Employee } from '@type/model/Employee';

export const getEmployees = async (): Promise<Employee[]> => {
  return apiRequest(async () => {
    const response = await apiClient.get<ApiResponse<Employee[]>>('/employees');
    return response.data.data || [];
  });
};

export const getEmployeeById = async (employeeId: number): Promise<Employee> => {
  return apiRequest(async () => {
    const response = await apiClient.get<ApiResponse<Employee>>(`/employees/${employeeId}`);
    return response.data.data!;
  });
};

export const createEmployee = async (employee: Partial<Employee>): Promise<Employee> => {
  return apiRequest(async () => {
    const response = await apiClient.post<ApiResponse<Employee>>('/employees/insert', employee);
    return response.data.data!;
  });
};

export const updateEmployee = async (employee: Partial<Employee>): Promise<Employee> => {
  return apiRequest(async () => {
    const response = await apiClient.put<ApiResponse<Employee>>('/employees/update', employee);
    return response.data.data!;
  });
};

export const deleteEmployee = async (employeeId: number): Promise<Employee> => {
  return apiRequest(async () => {
    const response = await apiClient.delete<ApiResponse<Employee>>(`/employees/delete/${employeeId}`);
    return response.data.data!;
  });
};

export const hideEmployee = async (employeeId: number): Promise<Employee> => {
  return apiRequest(async () => {
    const response = await apiClient.delete<ApiResponse<Employee>>(`/employees/hidden/${employeeId}`);
    return response.data.data!;
  });
};
