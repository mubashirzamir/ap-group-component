
import { useEffect, useState } from "react";
import DarlingtonService from "@/services/DarlingtonService.jsx";
import { genericNetworkError } from "@/helpers/utils.jsx";
import { REFRESH_INTERVAL_DARLINGTON } from "@/helpers/constants.jsx";

const ConsumptionProviderSummary = (providerTime) => {

    const [CProviderInformation, setCProviderInformation] = useState([]);
    const [CProviderLoading, setCProviderLoading] = useState(false);
    const [CProviderError, setCProviderError] = useState(null);

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