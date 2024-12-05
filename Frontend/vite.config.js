import { defineConfig } from "vite";
import react from "@vitejs/plugin-react-swc";

// https://vitejs.dev/config/
export default defineConfig(() => {
  return {
    plugins: [react()],
    server: {
      proxy: {
        "/api": {
          target: "localhost:8080",
          changeOrigin: true,
          secure: false,
        },
      },
    },
  };
});
