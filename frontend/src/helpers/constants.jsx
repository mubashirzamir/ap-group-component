const MODE_PRODUCTION = "production";
const MODE_DEVELOPMENT = "development";

export const IN_PROD = import.meta.env.MODE === MODE_PRODUCTION;
export const IN_DEV = import.meta.env.MODE === MODE_DEVELOPMENT;

export const REFRESH_INTERVAl_NEWCASTLE = 30000; // 30000ms = 30 seconds
