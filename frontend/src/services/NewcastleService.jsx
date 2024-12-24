import request from "@/request.js";

const baseUrl = import.meta.env.VITE_NEWCASTLE_API_BASE_URL + "/city";

const cityConsumptions = (params = {}) => {
  return request.get(`${baseUrl}/aggregated-consumptions`, params);
};

export default { cityConsumptions };
