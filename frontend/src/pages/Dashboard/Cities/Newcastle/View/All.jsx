import { Table } from "antd";
import DataWrapper from "@/components/DataWrapper/DataWrapper.jsx";
import { genericNetworkError, renderDateTime } from "@/helpers/utils.jsx";
import { useEffect, useState } from "react";
import NewcastleService from "@/services/NewcastleService.jsx";
import { refreshInterval } from "@/pages/Dashboard/Cities/Newcastle/View/index.jsx";

const tableProps = {
  scroll: { x: "max-content" },
  pagination: { pageSize: 10 },
};

const columns = [
  {
    title: "ID",
    dataIndex: "id",
    key: "id",
  },
  {
    title: "Provider",
    dataIndex: "providerId",
    key: "providerId",
  },
  {
    title: "Average Consumption (Wh)",
    dataIndex: "averageConsumption",
    key: "averageConsumption",
  },
  {
    title: "Total Consumption (Wh)",
    dataIndex: "totalConsumption",
    key: "totalConsumption",
  },
  {
    title: "Consumption Period Start",
    dataIndex: "consumptionPeriodStart",
    key: "consumptionPeriodStart",
    render: (value) => renderDateTime(value),
  },
  {
    title: "Consumption Period End",
    dataIndex: "consumptionPeriodEnd",
    key: "consumptionPeriodEnd",
    render: (value) => renderDateTime(value),
  },
];

const All = () => {
  const [loading, setLoading] = useState(false);
  const [data, setData] = useState([]);
  const [errored, setErrored] = useState(null);

  const fetchData = () => {
    setLoading(true);
    NewcastleService.cityConsumptions()
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
  }, []); // Empty dependency array means this effect runs only once on mount

  return (
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
        rowKey="id"
        loading={loading}
      />
    </DataWrapper>
  );
};

export default All;
