import { Table } from "antd";
import DataWrapper from "@/components/DataWrapper/DataWrapper.jsx";
import { genericNetworkError, renderDateTime } from "@/helpers/utils.jsx";
import { useEffect, useState } from "react";
import NewcastleService from "@/services/NewcastleService.jsx";
import { REFRESH_INTERVAl_NEWCASTLE } from "@/helpers/constants.jsx";

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
    title: "Average Consumption (kWh)",
    dataIndex: "averageConsumption",
    key: "averageConsumption",
  },
  {
    title: "Total Consumption (kWh)",
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


    const intervalId = setInterval(() => {
      fetchData();
    }, REFRESH_INTERVAl_NEWCASTLE);

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
