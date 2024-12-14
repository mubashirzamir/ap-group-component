import DashboardPage from "@/components/DashboardPage/index.jsx";
import { Table } from "antd";
import { useEffect, useState } from "react";
import NewcastleService from "@/services/NewcastleService.jsx";
import { genericNetworkError, renderDateTime } from "@/helpers/utils.jsx";
import DataWrapper from "@/components/DataWrapper/DataWrapper.jsx";

const tableProps = {
  scroll: { x: "max-content" },
  pagination: { pageSize: 50 },
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
    title: "Average Consumption",
    dataIndex: "averageConsumption",
    key: "averageConsumption",
  },
  {
    title: "Total Consumption",
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

const View = () => {
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
    }, 30000); // 30000ms = 30 seconds

    // Cleanup: Clear interval on unmount to prevent memory leaks
    return () => clearInterval(intervalId);
  }, []); // Empty dependency array means this effect runs only once on mount

  return (
    <DashboardPage breadcrumbs={[{ title: "Newcastle" }, { title: "View" }]}>
      <DataWrapper data={data} loading={loading} errored={errored}>
        <Table
          {...tableProps}
          dataSource={data}
          columns={columns}
          rowKey="id"
          loading={loading}
        />
      </DataWrapper>
    </DashboardPage>
  );
};

export default View;
