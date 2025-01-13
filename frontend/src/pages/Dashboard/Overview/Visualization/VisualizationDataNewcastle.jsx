import { useEffect, useState } from "react";
import NewcastleService from "@/services/NewcastleService.jsx";
import { genericNetworkError } from "@/helpers/utils.jsx";
import { REFRESH_INTERVAl_NEWCASTLE } from "@/helpers/constants.jsx";

const VisualizationDataNewcastle = (selectedYear) => {
  // City Data State
  const [cityChartLoading, setCityChartLoading] = useState(false);
  const [cityChartDataRaw, setCityChartDataRaw] = useState([]);
  const [cityChartErrored, setCityChartErrored] = useState(null);

  // Fetch City Data
  const fetchCityData = async () => {
    setCityChartLoading(true);
    setCityChartErrored(null);

    try {
      const response = await NewcastleService.monthlyAverageCity({
        year: selectedYear,
      });
      setCityChartDataRaw(response);
    } catch (error) {
      setCityChartErrored(true);
      genericNetworkError(error);
    } finally {
      setCityChartLoading(false);
    }
  };

  useEffect(() => {
    // Initial fetch on mount and whenever selectedYear changes
    fetchCityData();

    // Set interval to fetch data periodically using the constant
    const intervalId = setInterval(() => {
      fetchCityData();
    }, REFRESH_INTERVAl_NEWCASTLE); // Use the imported constant

    // Cleanup: Clear interval on unmount or when selectedYear changes
    return () => clearInterval(intervalId);
  }, [selectedYear]); // Dependency array includes selectedYear

  return {
    cityChartDataRaw,
    cityChartLoading,
    cityChartErrored,
  };
};

export default VisualizationDataNewcastle;
