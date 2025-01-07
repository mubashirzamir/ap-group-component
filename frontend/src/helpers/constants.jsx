const MODE_PRODUCTION = "production";
const MODE_DEVELOPMENT = "development";

export const IN_PROD = import.meta.env.MODE === MODE_PRODUCTION;
export const IN_DEV = import.meta.env.MODE === MODE_DEVELOPMENT;

export const REFRESH_INTERVAl_NEWCASTLE = 30000; // 30000ms = 30 seconds

export const REFRESH_INTERVAL_SUNDERLAND = 30000; // 30000ms = 30 seconds

export const REFRESH_INTERVAL_DARLINGTON = 30000; // 30000ms = 30 seconds

// Define a color palette for providers
export const COLORS = [
    "#3366CC",
    "#DC3912",
    "#FF9900",
    "#109618",
    "#990099",
    "#0099C6",
    "#DD4477",
    "#66AA00",
    "#B82E2E",
    "#316395",
  ];