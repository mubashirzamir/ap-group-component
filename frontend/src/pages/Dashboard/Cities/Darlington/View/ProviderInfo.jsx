
import { useEffect, useState } from "react";
import DarlingtonService from "@/services/DarlingtonService.jsx";
import { genericNetworkError } from "@/helpers/utils.jsx";
import { REFRESH_INTERVAL_DARLINGTON } from "@/helpers/constants.jsx";

const ProviderInfo = () => {

    // Provider State
    const [providerLoading, setProviderLoading] = useState(false);
    const [providerData, setProviderData] = useState(null);
    const [providerError, setProviderError] = useState(null);

    const fetchProviderInfo = async () => {
      // Fetch Provider Data
      setProviderError(null);
      setProviderLoading(true);

      try {
        const providerResponse = await DarlingtonService.getProviderInfo();
        setProviderData(providerResponse);
      } catch (error) {
        setProviderError(true);
        genericNetworkError(error);
      } finally {
        setProviderLoading(false);
      }
    }

    useEffect(() => {
      // Initial fetch on mount
      fetchProviderInfo();

      // Cleanup: No interval to clear
      return () => {};
    }, []); // Empty dependency array to run only once on mount
    return { providerData, providerLoading, providerError };
  };

  export default ProviderInfo;