import { useEffect, useState } from "react";
import DarlingtonService from "@/services/DarlingtonService.jsx";
import { genericNetworkError } from "@/helpers/utils.jsx";
import { REFRESH_INTERVAL_DARLINGTON } from "@/helpers/constants.jsx";

/**
 * Custom hook to fetch and manage city consumption summary data.
 *
 * @param {string} providerTime - The selected time period for fetching data.
 * @returns {Object} An object containing city consumption information, loading state, and error state.
 */
const ConsumptionCitySummary = (providerTime) => {
    // City state
    const [CCityInformation, setCCityInformation] = useState([]);
    const [CCityLoading, setCCityLoading] = useState(false);
    const [CCityError, setCCityError] = useState(null);

    /**
     * Fetches city consumption information from the service.
     * Sets loading state, handles errors, and updates the state with fetched data.
     */
    const fetchProviderConsumptionInformation = async () => {
        setCCityLoading(true);
        setCCityError(null);

        try {
            const providerResponse = await DarlingtonService.getAggregatedForCity(providerTime);
            setCCityInformation(providerResponse || []);
        } catch (error) {
            setCCityError(true);
            genericNetworkError(error);
        } finally {
            setCCityLoading(false);
        }
    };

    // Effect to fetch data on component mount and at regular intervals
    useEffect(() => {
        fetchProviderConsumptionInformation();

        const intervalId = setInterval(() => {
            fetchProviderConsumptionInformation();
        }, REFRESH_INTERVAL_DARLINGTON);

        return () => clearInterval(intervalId);
    }, [providerTime]);

    return { CCityInformation, CCityLoading, CCityError };
}

export default ConsumptionCitySummary;