import { Table } from "antd";
import DataWrapper from "@/components/DataWrapper/DataWrapper.jsx";
import { genericNetworkError } from "@/helpers/utils.jsx";
import { useEffect, useState } from "react";
import NewcastleService from "@/services/NewcastleService.jsx";
import TimeRangeFilter from "@/pages/Dashboard/Cities/Newcastle/View/TimeRangeFilter.jsx";
import { REFRESH_INTERVAl_NEWCASTLE } from "@/helpers/constants.jsx";

const tableProps = {
  scroll: { x: "max-content" },
  pagination: false,
};

const columns = [
  {
    title: "Provider ID",
    dataIndex: "providerId",
    key: "providerId",
  },
  {
    title: "Total Consumption (kWh)",
    dataIndex: "totalConsumption",
    key: "totalConsumption",
  },
  {
    title: "Average Consumption (kWh)",
    dataIndex: "averageConsumption",
    key: "averageConsumption",
  },
];

const ByProvider = () => {
  const [loading, setLoading] = useState(false);
  const [data, setData] = useState([]);
  const [errored, setErrored] = useState(null);
  const [timeRange, setTimeRange] = useState("LAST_30_DAYS");

  const fetchData = () => {
    setLoading(true);
    NewcastleService.dataByProvider({ timeRange })
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
        <Table
          {...tableProps}
          dataSource={data}
          columns={columns}
          rowKey="provider-id"
          loading={loading}
        />
      </DataWrapper>
    </div>
  );
};

export default ByProvider;
