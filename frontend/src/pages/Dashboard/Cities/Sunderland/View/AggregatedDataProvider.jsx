// src/hooks/useAggregatedData.jsx

import { useEffect, useState } from "react";
import SunderlandService from "@/services/SunderlandService.jsx";
import { genericNetworkError } from "@/helpers/utils.jsx";
import { REFRESH_INTERVAL_SUNDERLAND } from "@/helpers/constants.jsx";

const AggregatedDataProvider = (selectedTimeRange) => {
    // Providers State
    const [providerLoading, setProviderLoading] = useState(false);
    const [providerData, setProviderData] = useState([]);
    const [providerErrored, setProviderErrored] = useState(null);

  
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

  
  
    useEffect(() => {
      // Initial fetch on mount and whenever selectedTimeRange changes
      fetchProviderData();
  
      // Set interval to fetch data periodically using the constant
      const intervalId = setInterval(() => {
        fetchProviderData();
      }, REFRESH_INTERVAL_SUNDERLAND); // Use the imported constant
  
      // Cleanup: Clear interval on unmount or when selectedTimeRange changes
      return () => clearInterval(intervalId);
    }, [selectedTimeRange]); // Dependency array includes selectedTimeRange
  
    return { providerData, providerLoading, providerErrored};
  };
  
  export default AggregatedDataProvider;