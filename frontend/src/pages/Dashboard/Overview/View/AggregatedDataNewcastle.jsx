import { useEffect, useState } from "react";
import NewcastleService from "@/services/NewcastleService.jsx";
import { genericNetworkError } from "@/helpers/utils.jsx";
import { REFRESH_INTERVAl_NEWCASTLE } from "@/helpers/constants.jsx";

const AggregatedDataNewcastle = (selectedTimeRange) => {
  const [loading, setLoading] = useState(false);
  const [data, setData] = useState(null);
  const [errored, setErrored] = useState(null);

  const fetchData = async () => {
    setErrored(null);
    setLoading(true);
    try {
      const response = await NewcastleService.dataForCity({ timeRange: selectedTimeRange });
      setData(response);
    } catch (error) {
      setErrored(true);
      genericNetworkError(error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    // Initial fetch and subsequent periodic refresh
    fetchData();

    const intervalId = setInterval(() => {
      fetchData();
    }, REFRESH_INTERVAl_NEWCASTLE);

    // Cleanup on unmount or when selectedTimeRange changes
    return () => clearInterval(intervalId);
  }, [selectedTimeRange]);

  return { data, loading, errored };
};

export default AggregatedDataNewcastle;
