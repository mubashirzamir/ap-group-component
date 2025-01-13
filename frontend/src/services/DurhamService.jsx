import request from "@/request.js";

const API_BASE_URL = import.meta.env.VITE_DURHAM_API_BASE_URL + "/sc/api";

const DurhamService = {
  /**
   * Fetches aggregation of power consumption grouped by provider.
   *
   * @returns {Promise<Array>} - A promise that resolves to a list of ProviderAggregationDTO objects.
   */
  getAggregatedDataByProvider: async () => {
    try {
      const response = await request.get(`${API_BASE_URL}/aggregations/provider`);
      console.log(response)
      return response;
    } catch (error) {
      throw error;
    }
  },

  /**
   * Fetches aggregated power consumption data for all providers combined.
   *
   * @returns {Promise<Object>} - A promise that resolves to a single ProviderAggregationDTO object.
   */
  getAggregatedDataForAllProviders: async () => {
    try {
      const response = await request.get(`${API_BASE_URL}/aggregations/all`);
      return response;
    } catch (error) {
      throw error;
    }
  },

  /**
   * Fetches monthly power consumption aggregated across all providers.
   *
   * @returns {Promise<Array>} - A promise that resolves to a list of MonthlyConsumptionDTO objects.
   */
  getMonthlyConsumptionForCity: async () => {
    try {
      const response = await request.get(`${API_BASE_URL}/aggregations/monthly-city`);
      return response;
    } catch (error) {
      throw error;
    }
  },

  /**
   * Fetches monthly power consumption data for all providers in a nested JSON format.
   *
   * @returns {Promise<Object>} - A promise that resolves to a nested JSON structure.
   */
  getMonthlyConsumptionForAllProviders: async () => {
    try {
      const response = await request.get(
        `${API_BASE_URL}/aggregations/monthly-all-providers`
      );
      return response;
    } catch (error) {
      throw error;
    }
  },
};

export default DurhamService;
