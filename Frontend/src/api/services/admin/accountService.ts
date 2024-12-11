import apiClient from '../../apiClient';
import { apiRequest } from '../apiRequest';

import { ApiResponse } from '@type/common/ApiResponse';
import { User } from '@type/model/User';

export const getAccounts = async (): Promise<User[]> => {
  return apiRequest(async () => {
    const response = await apiClient.get<ApiResponse<User[]>>('/accounts');
    return response.data.data || [];
  });
};

export const getAccountById = async (username: string): Promise<User> => {
  return apiRequest(async () => {
    const response = await apiClient.get<ApiResponse<User>>(`/accounts/${username}`);
    return response.data.data!;
  });
};

export const createAccount = async (user: Partial<User>): Promise<User> => {
  return apiRequest(async () => {
    const response = await apiClient.post<ApiResponse<User>>('/accounts/insert', user);
    return response.data.data!;
  });
};

export const updateAccount = async (user: Partial<User>): Promise<User> => {
  return apiRequest(async () => {
    const response = await apiClient.put<ApiResponse<User>>('/accounts/update', user);
    return response.data.data!;
  });
};

export const deleteAccount = async (username: string): Promise<User> => {
  return apiRequest(async () => {
    const response = await apiClient.delete<ApiResponse<User>>(`/accounts/delete/${username}`);
    return response.data.data!;
  });
};

export const hideAccount = async (username: string): Promise<User> => {
  return apiRequest(async () => {
    const response = await apiClient.delete<ApiResponse<User>>(`/accounts/hidden/${username}`);
    return response.data.data!;
  });
};


// export const registerUser = async (
//   username: string | undefined,
//   pass: string | undefined,
//   email: string | undefined,
//   fullName: string | undefined,
//   phoneNumber: string | undefined
// ): Promise<any> => {
//   return apiRequest(async () => {
//     // Sử dụng query parameters để gửi dữ liệu
//     const response = await apiClient.post('accounts/register', null, {
//       params: {
//         username,
//         pass,
//         email,
//         fullName,
//         phoneNumber
//       },
//     });
//     console.log(">>DATA:", response.data);

//     // Trả về thông điệp từ backend
//     return response.data.data;
//   });
// };



export const registerUser = async (
  username: string | undefined,
  pass: string | undefined,
  email: string | undefined,
  fullName: string | undefined,
  phoneNumber: string | undefined
): Promise<any> => {
  return apiRequest(async () => {
    // Kiểm tra các tham số đầu vào trước khi gửi yêu cầu
    if (!username || !pass || !email || !fullName || !phoneNumber) {
      throw new Error('All fields must be provided');
    }

    const response = await apiClient.post('accounts/register',null, {
      params: {
                username,
                pass,
                email,
                fullName,
                phoneNumber
              },
    });

    console.log(">>DATA:", response.data);

    return response.data.data;
  });
};