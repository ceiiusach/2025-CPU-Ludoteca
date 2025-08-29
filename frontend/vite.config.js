import { fileURLToPath, URL } from "node:url";
import { defineConfig, loadEnv } from "vite";
import tailwindcss from "@tailwindcss/vite";
import vue from "@vitejs/plugin-vue";

// https://vite.dev/config/
export default ({ mode }) => {
  process.env = { ...process.env, ...loadEnv(mode, process.cwd()) };

  const backendServer = process.env.VITE_BACKEND_SERVER;
  const backendPort = process.env.VITE_BACKEND_PORT;

  return defineConfig({
    plugins: [tailwindcss(), vue()],
    server: {
      allowedHosts: true,
      proxy: {
        "/api": {
          target: `http://${backendServer}:${backendPort}`,
          changeOrigin: true,
          secure: false,
        },
        "/auth": {
            target: `http://${backendServer}:${backendPort}`,
            changeOrigin: true,
            secure: false,
        },
      },
    },
    resolve: {
      alias: {
        "@": fileURLToPath(new URL("./src", import.meta.url)),
      },
    },
  });
};
