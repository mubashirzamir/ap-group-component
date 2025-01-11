import react from "@vitejs/plugin-react";

export default {
  plugins: [react()],
  test: {
    environment: "jsdom",
  },
  resolve: {
    alias: [{ find: "@", replacement: "/src" }],
  },
};