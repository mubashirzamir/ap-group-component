// src/hooks/useAggregatedData.jsx

import { useEffect, useState } from "react";
import SunderlandService from "@/services/SunderlandService.jsx";
import { genericNetworkError } from "@/helpers/utils.jsx";
import { REFRESH_INTERVAL_SUNDERLAND } from "@/helpers/constants.jsx";

const useAggregatedData = (selectedTimeRange) => {
    // Providers State
    const [providerLoading, setProviderLoading] = useState(false);
    const [providerData, setProviderData] = useState([]);
    const [providerErrored, setProviderErrored] = useState(null);
  
    // City State
    const [cityLoading, setCityLoading] = useState(false);
    const [cityData, setCityData] = useState(null);
    const [cityErrored, setCityErrored] = useState(null);
  
    const fetchProviderData = async () => {
      // Fetch Providers Data
      setProviderErrored(null);
      setProviderLoading(true);
  
      try {
        const providersResponse = await SunderlandService.getAggregatedDataByProvider(selectedTimeRange);
        setProviderData(providersResponse);
      } catch (error) {
        setProviderErrored(true);
        genericNetworkError(error);
      } finally {
        setProviderLoading(false);
      }
    };

    const fetchCityData = async () => {
      // Fetch City Data
      setCityErrored(null);
      setCityLoading(true);
  
      try {
        const cityResponse = await SunderlandService.getAggregatedDataForCity(selectedTimeRange);
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
      fetchProviderData();
      fetchCityData();
  
      // Set interval to fetch data periodically using the constant
      const intervalId = setInterval(() => {
        fetchProviderData();
        fetchCityData();
      }, REFRESH_INTERVAL_SUNDERLAND); // Use the imported constant
  
      // Cleanup: Clear interval on unmount or when selectedTimeRange changes
      return () => clearInterval(intervalId);
    }, [selectedTimeRange]); // Dependency array includes selectedTimeRange
  
    return { providerData, providerLoading, providerErrored, cityData, cityLoading, cityErrored };
  };
  
  export default useAggregatedData;