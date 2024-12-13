import DashboardPage from "@/components/DashboardPage/index.jsx";
import { Table } from "antd";
import { useEffect, useState } from "react";
import NewcastleService from "@/services/NewcastleService.jsx";
import { genericNetworkError, renderDateTime } from "@/helpers/utils.jsx";

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

  const fetchData = () => {
    setLoading(true);
    NewcastleService.cityConsumptions()
      .then((response) => setData(response))
      .catch(genericNetworkError)
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <DashboardPage breadcrumbs={[{ title: "Newcastle" }, { title: "View" }]}>
      <Table
        {...tableProps}
        dataSource={data}
        columns={columns}
        rowKey="id"
        loading={loading}
      />
    </DashboardPage>
  );
};

export default View;
