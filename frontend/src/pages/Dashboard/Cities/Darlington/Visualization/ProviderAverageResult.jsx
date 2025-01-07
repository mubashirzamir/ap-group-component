

import { useEffect, useState } from "react";
import DarlingtonService from "@/services/DarlingtonService.jsx";
import { genericNetworkError } from "@/helpers/utils.jsx";
import { REFRESH_INTERVAL_DARLINGTON } from "@/helpers/constants.jsx";

const ProviderAverageResult = (providerTime) => {
    // Provider state
    const [providerInformation, setProviderInformation] = useState([]);
    const [providerInfoLoading, setProviderLoading] = useState(false);
    const [providerInfoError, setProviderError] = useState(null);

    const fetchProviderAverageResult = async () => {
        setProviderLoading(true);
        setProviderError(null);

        try {
            const providerResponse = await DarlingtonService.getMonthlyAverageByProvider(providerTime);
            setProviderInformation(providerResponse || []);
        } catch (error) {
            setProviderError(true);
            genericNetworkError(error);
        } finally {
            setProviderLoading(false);
        }
    }

    // Effect to fetch data on component mount and at regular intervals
    useEffect(() => {
        fetchProviderAverageResult();

        const intervalId = setInterval(() => {
            fetchProviderAverageResult();
        }, REFRESH_INTERVAL_DARLINGTON);

        return () => clearInterval(intervalId);
    }, [providerTime]);

    return { providerInformation, providerInfoLoading, providerInfoError  };
}

export default ProviderAverageResult;