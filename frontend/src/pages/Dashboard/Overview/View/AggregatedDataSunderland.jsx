import { useEffect, useState } from "react";
import SunderlandService from "@/services/SunderlandService.jsx";
import { genericNetworkError } from "@/helpers/utils.jsx";
import { REFRESH_INTERVAL_SUNDERLAND } from "@/helpers/constants.jsx";

const AggregatedDataSunderland = (selectedTimeRange) => {
  
    // City State
    const [cityLoading, setCityLoading] = useState(false);
    const [cityData, setCityData] = useState(null);
    const [cityErrored, setCityErrored] = useState(null);

    const fetchCityData = async () => {
      // Fetch City Data
      setCityErrored(null);
      setCityLoading(true);
  
      try {
        const cityResponse = await SunderlandService.getAggregatedDataForCity(selectedTimeRange);
        console.log(cityResponse);
        setCityData(cityResponse);
      } catch (error) {
        setCityErrored(true);
        genericNetworkError(error);
      } finally {
        setCityLoading(false);
      }
    }
  
    useEffect(() => {
      // Initial fetch on mount and whenever selectedTimeRange changes
      fetchCityData();
  
      // Set interval to fetch data periodically using the constant
      const intervalId = setInterval(() => {
        fetchCityData();
      }, REFRESH_INTERVAL_SUNDERLAND); // Use the imported constant
  
      // Cleanup: Clear interval on unmount or when selectedTimeRange changes
      return () => clearInterval(intervalId);
    }, [selectedTimeRange]); // Dependency array includes selectedTimeRange
  
    return { cityData, cityLoading, cityErrored };
  };
  
  export default AggregatedDataSunderland;