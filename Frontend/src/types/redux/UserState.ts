import { User } from "@type/model/User";

export interface UserState {
  currentUser: User | null;
  error: string | null;
  loading: boolean;
}