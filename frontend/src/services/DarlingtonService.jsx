import request from "@/request.js";

const API_BASE_URL = import.meta.env.VITE_DARLINGTON_API_BASE_URL + "/api/smartCity";

const SmartCityService = {
  /**
   * Fetches all electrical provider summaries for a given provider ID.
   *
   * @param {string} providerId - The ID of the provider.
   * @returns {Promise<Array>} - A promise that resolves to a list of summaries.
   */
  saveAllElectricalProviderSummary: async (providerId) => {
    try {
      const response = await request.post(
        `${API_BASE_URL}/electricalProvider/${providerId}/allSummary`
      );
      return response;
    } catch (error) {
      throw error;
    }
  },

  /**
   * Fetches all city summaries.
   *
   * @returns {Promise<Array>} - A promise that resolves to a list of city summaries.
   */
  getSummary: async () => {
    try {
      const response = await request.get(`${API_BASE_URL}/summary`);
      return response;
    } catch (error) {
      throw error;
    }
  },

  /**
   * Fetches aggregated data by provider for a specific time range.
   *
   * @param {string} timeRange - The predefined time range (e.g., LAST_24_HOURS, LAST_7_DAYS, LAST_30_DAYS).
   * @returns {Promise<Array>} - A promise that resolves to aggregated data by provider.
   */
  getAggregatedByProvider: async (timeRange = "LAST_30_DAYS") => {
    try {
      const response = await request.get(`${API_BASE_URL}/data/providers`, {
        params: { timeRange },
      });
      return response;
    } catch (error) {
      throw error;
    }
  },

  /**
   * Fetches aggregated data for the city for a specific time range.
   *
   * @param {string} timeRange - The predefined time range (e.g., LAST_24_HOURS, LAST_7_DAYS, LAST_30_DAYS).
   * @returns {Promise<Object>} - A promise that resolves to aggregated city data.
   */
  getAggregatedForCity: async (timeRange = "LAST_30_DAYS") => {
    try {
      const response = await request.get(`${API_BASE_URL}/data/city`, {
        params: { timeRange },
      });
      return response;
    } catch (error) {
      throw error;
    }
  },

  /**
   * Fetches monthly average data by provider for a specific year.
   *
   * @param {number} year - The year to fetch data for (e.g., 2024).
   * @returns {Promise<Array>} - A promise that resolves to monthly average data by provider.
   */
  getMonthlyAverageByProvider: async (year = 2024) => {
    try {
      const response = await request.get(
        `${API_BASE_URL}/graphs/monthly-average/providers`,
        {
          params: { year },
        }
      );
      return response;
    } catch (error) {
      throw error;
    }
  },

  /**
   * Fetches monthly average data for the city for a specific year.
   *
   * @param {number} year - The year to fetch data for (e.g., 2024).
   * @returns {Promise<Array>} - A promise that resolves to monthly average data for the city.
   */
  getMonthlyAverageForCity: async (year = 2024) => {
    try {
      const response = await request.get(
        `${API_BASE_URL}/graphs/monthly-average/city`,
        {
          params: { year },
        }
      );
      return response;
    } catch (error) {
      throw error;
    }
  },

  /**
   * Fetches data summary by date for a specific time range.
   *
   * @param {string} timeRange - The predefined time range (e.g., LAST_24_HOURS, LAST_7_DAYS, LAST_30_DAYS).
   * @returns {Promise<Array>} - A promise that resolves to a list of summaries.
   */
  getSummaryByDate: async (timeRange = "LAST_30_DAYS") => {
    try {
      const response = await request.get(`${API_BASE_URL}/summary/date`, {
        params: { timeRange },
      });
      return response;
    } catch (error) {
      throw error;
    }
  },

  /**
   * Fetches provider information.
   *
   * @returns {Promise<Array>} - A promise that resolves to a list of provider information.
   */
  getProviderInfo: async () => {
    try {
      const response = await request.get(`${API_BASE_URL}/providerInfo`);
      return response;
    } catch (error) {
      throw error;
    }
  },
};

export default SmartCityService;
