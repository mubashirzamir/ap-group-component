import request from "@/request.js";
import { filtersToQueryParams } from "@/helpers/utils.jsx";

const baseUrl = import.meta.env.VITE_NEWCASTLE_API_BASE_URL + "/city";

const cityConsumptions = (filters = {}) => {
  return request.get(
    `${baseUrl}/aggregated-consumptions${filtersToQueryParams(filters)}`,
  );
};

export default { cityConsumptions };
