import { User } from "@type/model/User";

export interface AuthState {
  user : User | null;
  token : string | null;
}

