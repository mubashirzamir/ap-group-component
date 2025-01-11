import DataWrapper from "@/components/DataWrapper/DataWrapper.jsx";
import { genericNetworkError, toDecimalPlaces } from "@/helpers/utils.jsx";
import { useEffect, useState } from "react";
import NewcastleService from "@/services/NewcastleService.jsx";
import { Card } from "antd";
import TimeRangeFilter from "@/pages/Dashboard/Cities/Newcastle/View/TimeRangeFilter.jsx";
import { REFRESH_INTERVAl_NEWCASTLE } from "@/helpers/constants.jsx";

const { Meta } = Card;

const ForCity = () => {
  const [loading, setLoading] = useState(false);
  const [data, setData] = useState(null);
  const [errored, setErrored] = useState(null);
  const [timeRange, setTimeRange] = useState("LAST_30_DAYS");

  useEffect(() => {
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

    // Initial fetch on mount
    fetchData();
    
    const intervalId = setInterval(() => {
      fetchData();
    }, REFRESH_INTERVAl_NEWCASTLE);

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
              title={toDecimalPlaces(data?.totalConsumption, 0)}
              description="Total Consumption (kWh)"
            />
          </Card>
          <Card>
            <Meta
              title={toDecimalPlaces(data?.averageConsumption, 0)}
              description="Average Consumption (kWh)"
            />
          </Card>
        </div>
      </DataWrapper>
    </div>
  );
};

export default ForCity;
