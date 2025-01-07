import { Table } from "antd";
import DataWrapper from "@/components/DataWrapper/DataWrapper.jsx";

const tableProps = {
  scroll: { x: "max-content" },
  pagination: { pageSize: 5 },
};

const providerColumns = [
  {
    title: "Provider ID",
    dataIndex: "providerId",
    key: "providerId",
  },
  {
    title: "Average Consumption",
    dataIndex: "averageConsumption",
    key: "averageConsumption",
    render: (value) => `${value.toFixed(2)} kWh`,
  },
  {
    title: "Total Consumption",
    dataIndex: "totalConsumption",
    key: "totalConsumption",
    render: (value) => `${value.toFixed(2)} kWh`,
  },

];

const ConsumptionProviderTable = ({ data, loading, errored }) => (
  <DataWrapper data={data} loading={loading} errored={errored} strategy="spin">
    <Table
      {...tableProps}
      dataSource={data}
      columns={providerColumns}
      rowKey="providerId"
      loading={loading && data.length === 0}
        title={() => (
          <div
            style={{
              fontWeight: "bold",
              fontSize: "20px",
              textAlign: "left",
              color: "#4A90E2",
            }}
          >
            Providers Consumption Data
          </div>
        )}
    />
  </DataWrapper>
);

export default ConsumptionProviderTable;