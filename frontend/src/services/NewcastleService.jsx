import request from "@/request.js";

const baseUrl = import.meta.env.VITE_NEWCASTLE_API_BASE_URL + "/city";

const cityConsumptions = (params = {}) => {
  return request.get(`${baseUrl}/aggregated-consumptions`, { params });
};

const dataByProvider = (params = {}) => {
  return request.get(`${baseUrl}/data/providers`, { params });
};

const dataForCity = (params = {}) => {
  return request.get(`${baseUrl}/data/city`, { params });
};

const monthlyAverageProviders = (params = {}) => {
  return request.get(`${baseUrl}/graphs/monthly-average/providers`, { params });
};

const monthlyAverageCity = (params = {}) => {
  return request.get(`${baseUrl}/graphs/monthly-average/city`, { params });
};

const dataByProviderForOverview = (params = {}) => {
  return request.get(`${baseUrl}/data/providers`, { params }).then((res) => {
    return res.map((item) => {
      return {
        ...item,
        providerId: providerIdMap[item.providerId],
      };
    });
  });
};

const providerIdMap = {
  1: "a",
  2: "b",
  3: "c",
};

export default {
  cityConsumptions,
  dataByProvider,
  dataForCity,
  monthlyAverageProviders,
  monthlyAverageCity,
  dataByProviderForOverview,
};
