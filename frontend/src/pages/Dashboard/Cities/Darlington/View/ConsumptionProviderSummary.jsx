import { useEffect, useState } from "react";
import DarlingtonService from "@/services/DarlingtonService.jsx";
import { genericNetworkError } from "@/helpers/utils.jsx";
import { REFRESH_INTERVAL_DARLINGTON } from "@/helpers/constants.jsx";

/**
 * fetch and manage provider consumption summary data.
 *
 * @param {string} providerTime - The selected time period for fetching data.
 * @returns {Object} An object containing provider consumption information, loading state, and error state.
 */
const ConsumptionProviderSummary = (providerTime) => {
    //Store Provider State
    const [CProviderInformation, setCProviderInformation] = useState([]);
    const [CProviderLoading, setCProviderLoading] = useState(false);
    const [CProviderError, setCProviderError] = useState(null);

    /**
     * Fetches provider consumption information from the service.
     * Sets loading state, handles errors, and updates the state with fetched data.
     */
    const fetchProviderInformation = async () => {
        setCProviderLoading(true);
        setCProviderError(null);

        try {
            const providerResponse = await DarlingtonService.getAggregatedByProvider(providerTime);
            setCProviderInformation(providerResponse);
        } catch (error) {
            setCProviderError(true);
            genericNetworkError(error);
        } finally {
            setCProviderLoading(false);
        }
    };

    // Effect to fetch data on component mount and at regular intervals
    useEffect(() => {
        fetchProviderInformation();

        const intervalId = setInterval(() => {
            fetchProviderInformation();
        }, REFRESH_INTERVAL_DARLINGTON);

        return () => clearInterval(intervalId);
    }, [providerTime]);

    return { CProviderInformation, CProviderLoading, CProviderError };
}

export default ConsumptionProviderSummary;