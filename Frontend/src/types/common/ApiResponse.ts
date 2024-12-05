export interface ApiResponse<T> {
  status: string;
  message: 
    {
      mess: string;
    }
  data?: T;
}