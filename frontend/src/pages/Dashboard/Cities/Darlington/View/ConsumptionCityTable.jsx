import { Table } from "antd";
import DataWrapper from "@/components/DataWrapper/DataWrapper.jsx";

// Table properties
const tableProps = {
  scroll: { x: "max-content" },
};

/**
 * Columns for the city consumption table
 */
const cityColumns = [
  {
    title: "Total Consumption",
    dataIndex: "totalConsumption",
    key: "totalConsumption",
    render: (value) => <span style={{ fontWeight: "bold" }}>{`${value.toFixed(2)} kWh`}</span>,
  },
  {
    title: "Average Consumption",
    dataIndex: "averageConsumption",
    key: "averageConsumption",
    render: (value) => <span style={{ fontWeight: "bold" }}>{`${value.toFixed(2)} kWh`}</span>,
  },
];

/**
 * Component to display a table of city consumption data.
 *
 * @param {Object} props - The component props.
 * @param {Array} props.data - The data to display in the table.
 * @param {boolean} props.loading - The loading state of the data.
 * @param {boolean} props.error - The error state of the data.
 * @returns {JSX.Element} The rendered table component.
 */
const ConsumptionCityTable = ({ data, loading, error }) => {

  // Ensure data is an array
  const normalizedData = Array.isArray(data) ? data : [data];

  return (
    <DataWrapper data={data} loading={loading} error={error} strategy="spin">
      <Table
        {...tableProps}
        dataSource={normalizedData}
        columns={cityColumns}
        rowKey={(record) => record.totalConsumption ? `row-${record.totalConsumption}` : `index-${Math.random()}`}
        loading={loading && normalizedData.length === 0}
        pagination={false}
        title={() => (
          <div
            style={{
              fontWeight: "bold",
              fontSize: "20px",
              textAlign: "left",
              color: "#4A90E2",
            }}
          >
            Overall City Consumption Data
          </div>
        )}
      />
    </DataWrapper>
  );
};

export default ConsumptionCityTable;