import { useEffect, useState, useCallback } from "react";
import DarlingtonService from "@/services/DarlingtonService.jsx";
import { genericNetworkError } from "@/helpers/utils.jsx";
import { REFRESH_INTERVAL_DARLINGTON } from "@/helpers/constants.jsx";

const ElectricalProviderConsumptionSummary = (ProviderChoice) => {
  const [EPCPInformation, setEPCPInformation] = useState([]);
  const [EPCPLoading, setEPCPLoading] = useState(false);
  const [EPCPError, setEPCPError] = useState(null);

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
