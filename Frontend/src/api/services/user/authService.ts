import { ApiResponse } from '@type/common/ApiResponse';
import { User } from '@type/model/User';
import axios from 'axios';

export const login = async (username: string, password: string): Promise<ApiResponse<User>> => {
  const token = btoa(`${username}:${password}`);
  let response: ApiResponse<User>;

  try {
    const res = await axios.post(
      `${import.meta.env.VITE_API_URL}/accounts/info-login`,
      {},
      { headers: { Authorization: `Basic ${token}` } }
    );

    const jwtToken = res.data.message.jwtToken;
    localStorage.setItem('jwtToken', jwtToken);
    response =  res.data;
  } catch (error: any) {
    response = error.response.data;
  }
  return response;
};
