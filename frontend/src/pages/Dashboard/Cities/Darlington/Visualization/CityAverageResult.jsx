import {useEffect, useState} from 'react';
import DarlingtonService from '@/services/DarlingtonService.jsx';
import {genericNetworkError} from '@/helpers/utils.jsx';
import {REFRESH_INTERVAL_DARLINGTON} from '@/helpers/constants.jsx';

const CityAverageResult = (cityYear) => {
    // City state
    const [cityInformation, setCityInformation] = useState([]);
    const [cityInfoLoading, setCityInfoLoading] = useState(false);
    const [cityInfoError, setCityInfoError] = useState(null);

    const fetchCityAverageResult = async () => {
        setCityInfoLoading(true);
        setCityInfoError(null);

        try {
            const cityResponse = await DarlingtonService.getMonthlyAverageForCity(cityYear);
            setCityInformation(cityResponse || []);
        } catch (error) {
            setCityInfoError(true);
            genericNetworkError(error);
        } finally {
            setCityInfoLoading(false);
        }
    }
    // Effect to fetch data on component mount and at regular intervals
    useEffect(() => {
        fetchCityAverageResult();

        const intervalId = setInterval(() => {
            fetchCityAverageResult();
        }, REFRESH_INTERVAL_DARLINGTON);

        return () => clearInterval(cityYear);
    }, [cityYear]);
    return { cityInformation, cityInfoLoading, cityInfoError  };
}

export default CityAverageResult;