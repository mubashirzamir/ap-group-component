import { useEffect, useState, useCallback } from "react";
import DarlingtonService from "@/services/DarlingtonService.jsx";
import { genericNetworkError } from "@/helpers/utils.jsx";
import { REFRESH_INTERVAL_DARLINGTON } from "@/helpers/constants.jsx";

/**
 * Fetch and manage electrical provider consumption summary data.
 *
 * @param {string} ProviderChoice - The selected provider choice for fetching data.
 * @returns {Object} An object containing electrical provider consumption information, loading state, and error state.
 */
const ElectricalProviderConsumptionSummary = (ProviderChoice) => {
  // ElectricalProviderConsumptionSummary state
  const [EPCPInformation, setEPCPInformation] = useState([]);
  const [EPCPLoading, setEPCPLoading] = useState(false);
  const [EPCPError, setEPCPError] = useState(null);

  /**
   * Fetches electrical provider consumption information from the service.
   * Sets loading state, handles errors, and updates the state with fetched data.
   */
  const fetchEPCPInformation = useCallback(async () => {
    setEPCPLoading(true);
    setEPCPError(null);

    try {
      let providerResponse;
      if (ProviderChoice.toLowerCase() === "all") {
        providerResponse = await DarlingtonService.getSummary();
      } else {
        providerResponse = await DarlingtonService.viewIndividualProviderSummary(ProviderChoice);
      }
      setEPCPInformation(providerResponse);
    } catch (error) {
      setEPCPError(true);
      genericNetworkError(error);
    } finally {
      setEPCPLoading(false);
    }
  }, [ProviderChoice]);

  // Effect to fetch data on component mount and at regular intervals
  useEffect(() => {
    fetchEPCPInformation();

    const intervalId = setInterval(() => {
      fetchEPCPInformation();
    }, REFRESH_INTERVAL_DARLINGTON);

    return () => clearInterval(intervalId);
  }, [ProviderChoice, fetchEPCPInformation]);

  return { EPCPInformation, EPCPLoading, EPCPError };
};

export default ElectricalProviderConsumptionSummary;