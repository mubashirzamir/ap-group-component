import DataWrapper from "@/components/DataWrapper/DataWrapper.jsx";
import { genericNetworkError, toDecimalPlaces } from "@/helpers/utils.jsx";
import { useEffect, useState } from "react";
import NewcastleService from "@/services/NewcastleService.jsx";
import { Card } from "antd";
import { refreshInterval } from "@/pages/Dashboard/Cities/Newcastle/View/index.jsx";
import TimeRangeFilter from "@/pages/Dashboard/Cities/Newcastle/View/TimeRangeFilter.jsx";

const { Meta } = Card;

const ByProvider = () => {
  const [loading, setLoading] = useState(false);
  const [data, setData] = useState([]);
  const [errored, setErrored] = useState(null);
  const [timeRange, setTimeRange] = useState("LAST_30_DAYS");

  const fetchData = () => {
    setLoading(true);
    NewcastleService.dataForCity({ timeRange })
      .then((response) => setData(response))
      .catch((e) => {
        setErrored(true);
        genericNetworkError(e);
      })
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    // Initial fetch on mount
    fetchData();

    // Set interval to fetch data periodically (every 5 seconds)
    const intervalId = setInterval(() => {
      fetchData();
    }, refreshInterval);

    // Cleanup: Clear interval on unmount to prevent memory leaks
    return () => clearInterval(intervalId);
  }, [timeRange]); // Empty dependency array means this effect runs only once on mount

  return (
    <div>
      <div className="flex justify-end p-4">
        <TimeRangeFilter
          timeRange={timeRange}
          setTimeRange={setTimeRange}
          loading={loading}
        />
      </div>
      <DataWrapper
        data={data}
        loading={loading}
        errored={errored}
        strategy="spin"
      >
        <div className="grid grid-cols-2 gap-4">
          <Card>
            <Meta
              title={toDecimalPlaces(data.totalConsumption, 0)}
              description="Total Consumption (Wh)"
            />
          </Card>
          <Card>
            <Meta
              title={toDecimalPlaces(data.averageConsumption, 0)}
              description="Average Consumption (Wh)"
            />
          </Card>
        </div>
      </DataWrapper>
    </div>
  );
};

export default ByProvider;
