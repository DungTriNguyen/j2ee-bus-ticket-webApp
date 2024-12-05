import { toast } from 'react-toastify';

/**
 * A utility function to handle API requests with centralized error handling.
 * @param request A function that performs the API request (e.g., apiClient.get, apiClient.post)
 * @returns The resolved data from the API response
 */
export const apiRequest = async <T>(request: () => Promise<T>): Promise<T> => {
  try {
    return await request();
  } catch (error: any) {
    handleError(error); // Centralized error handler
    throw error; // Re-throw if further handling is required
  }
};

/**
 * Centralized error handler function.
 * You can extend this to log errors, format error messages, etc.
 */
const handleError = (error: any): void => {
  const message = error?.response?.data?.message || 'An unexpected error occurred';
  console.error('API Error:', error);
  toast.error("Xóa dữ liệu không thành công. Lỗi vi phạm khóa ngoại"); // Show a toast notification
  // throw new Error(message); // Optional, re-throw if necessary
};
