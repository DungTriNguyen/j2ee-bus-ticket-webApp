import { createSlice, PayloadAction } from '@reduxjs/toolkit';

import { User } from '@type/model/User';
import { AuthState } from '@type/redux/AuthState';

import { jwtDecode } from 'jwt-decode';

const initialState: AuthState = {
  user: null,
  token: null,
};

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    login: (state, action: PayloadAction<string>) => {
      const token = action.payload;
      const user: User = jwtDecode(token);
      state.token = token;
      state.user = user;
      localStorage.setItem('jwt', token);
    },
    logout: (state) => {
      state.token = null;
      state.user = null;
      localStorage.removeItem('jwt');
    },
    loadUserFromStorage: (state) => {
      const token = localStorage.getItem('jwt');
      if (token) {
        const user: User = jwtDecode(token);
        state.token = token;
        state.user = user;
      }
    },
  },
});

export const { login, logout, loadUserFromStorage } = authSlice.actions;
export default authSlice.reducer;
