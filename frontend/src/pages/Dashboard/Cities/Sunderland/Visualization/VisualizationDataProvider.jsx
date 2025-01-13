import { useEffect, useState } from "react";
import SunderlandService from "@/services/SunderlandService.jsx";
import { genericNetworkError } from "@/helpers/utils.jsx";
import { REFRESH_INTERVAL_SUNDERLAND } from "@/helpers/constants.jsx";

const VisualizationDataProvider = (selectedYear) => {
  // Providers Data State
  const [providersChartLoading, setProvidersChartLoading] = useState(false);
  const [providersChartDataRaw, setProvidersChartDataRaw] = useState([]);
  const [providersChartErrored, setProvidersChartErrored] = useState(null);

  // Fetch Providers Data
  const fetchProvidersData = async () => {
    setProvidersChartLoading(true);
    setProvidersChartErrored(null);

    try {
      const response = await SunderlandService.getMonthlyAverageConsumptionByProvider(selectedYear);
      setProvidersChartDataRaw(response);
    } catch (error) {
      setProvidersChartErrored(true);
      genericNetworkError(error);
    } finally {
      setProvidersChartLoading(false);
    }
  };


  useEffect(() => {
    // Initial fetch on mount and whenever selectedYear changes
    fetchProvidersData();

    // Set interval to fetch data periodically using the constant
    const intervalId = setInterval(() => {
      fetchProvidersData();
    }, REFRESH_INTERVAL_SUNDERLAND); // Use the imported constant

    // Cleanup: Clear interval on unmount or when selectedYear changes
    return () => clearInterval(intervalId);
  }, [selectedYear]); // Dependency array includes selectedYear

  return {
    providersChartDataRaw,
    providersChartLoading,
    providersChartErrored
  };
};

export default VisualizationDataProvider;
