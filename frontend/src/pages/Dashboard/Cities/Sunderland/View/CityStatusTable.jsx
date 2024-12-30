import { Table } from "antd";
import DataWrapper from "@/components/DataWrapper/DataWrapper.jsx";

const tableProps = {
  scroll: { x: "max-content" },
  pagination: { pageSize: 50 },
};

const cityColumns = [
  {
    title: "Total Consumption",
    dataIndex: "totalConsumption",
    key: "totalConsumption",
    render: (value) => `${value.toFixed(2)} kWh`,
  },
  {
    title: "Average Consumption",
    dataIndex: "averageConsumption",
    key: "averageConsumption",
    render: (value) => `${value.toFixed(2)} kWh`,
  },
];

const CityStatusTable = ({ data, loading, errored }) => (
  <DataWrapper data={data} loading={loading} errored={errored}>
    <Table
      {...tableProps}
      dataSource={data ? [data] : []} // Wrap cityData in an array
      columns={cityColumns}
      rowKey="cityStatus" // Ensure this key is unique or adjust accordingly
      loading={loading && !data}
      pagination={false} // Typically, city status might not need pagination
      title={() => "Overall City Consumption Status"}
    />
  </DataWrapper>
);

export default CityStatusTable;
