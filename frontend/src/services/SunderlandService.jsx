import axios from "axios";

const API_BASE_URL = import.meta.env.VITE_SUNDERLAND_API_BASE_URL + "/api";

const SunderlandService = {
  /**
   * Fetches aggregated consumption data for each provider within a specified time range.
   *
   * @param {string} timeRange - The predefined time range (e.g., LAST_24_HOURS, LAST_7_DAYS, LAST_30_DAYS).
   * @returns {Promise<Array>} - A promise that resolves to an array of aggregated data by provider.
   */
  getAggregatedDataByProvider: async (timeRange = "LAST_30_DAYS") => {
    try {
      const response = await axios.get(`${API_BASE_URL}/data/providers`, {
        params: { timeRange },
      });
      return response.data;
    } catch (error) {
      throw error;
    }
  },

  /**
   * Fetches aggregated consumption data for the entire city within a specified time range.
   *
   * @param {string} timeRange - The predefined time range (e.g., LAST_24_HOURS, LAST_7_DAYS, LAST_30_DAYS).
   * @returns {Promise<Object>} - A promise that resolves to the aggregated city data.
   */
  getAggregatedDataForCity: async (timeRange = "LAST_30_DAYS") => {
    try {
      const response = await axios.get(`${API_BASE_URL}/data/city`, {
        params: { timeRange },
      });
      return response.data;
    } catch (error) {
      throw error;
    }
  },
  
   /**
   * Fetches monthly average consumption data for each provider for a specified year.
   *
   * @param {number} year - The year for which to fetch data (e.g., 2024).
   * @returns {Promise<Array>} - A promise that resolves to an array of monthly average consumption data by provider.
   */
   getMonthlyAverageConsumptionByProvider: async (year = 2024) => {
    try {
      const response = await axios.get(`${API_BASE_URL}/graphs/monthly-average/providers`, {
        params: { year },
      });
      return response.data;
    } catch (error) {
      throw error;
    }
  },

  /**
   * Fetches monthly average consumption data for the entire city for a specified year.
   *
   * @param {number} year - The year for which to fetch data (e.g., 2024).
   * @returns {Promise<Array>} - A promise that resolves to an array of monthly average consumption data for the city.
   */
  getMonthlyAverageConsumptionForCity: async (year = 2024) => {
    try {
      const response = await axios.get(`${API_BASE_URL}/graphs/monthly-average/city`, {
        params: { year },
      });
      return response.data;
    } catch (error) {
      throw error;
    }
  },

  /**
   * Fetches aggregated consumption data based on the selected time range.
   * @param {string} timeRange - One of 'LAST_24_HOURS', 'LAST_7_DAYS', 'LAST_30_DAYS'
   * @returns {Promise<Object>} - The aggregated consumption data
   */
  getAggregatedConsumptionData: async (timeRange) => {
    try {
      const response = await axios.get(`${API_BASE_URL}/data/consumption`, {
        params: { timeRange },
      });
      return response.data;
    } catch (error) {
      throw error;
    }
  },
};

export default SunderlandService;
