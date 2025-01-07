import { Table } from "antd";
import DataWrapper from "@/components/DataWrapper/DataWrapper.jsx";

const tableProps = {
  scroll: { x: "max-content" },
  pagination: { pageSize: 5 }, // Set page size to 10
};

const cityColumns = [
  {
    title: "Company Name",
    dataIndex: "companyName",
    key: "companyName",
    render: (value) => value || "Unknown", // Show "Unknown" if no match is found
  },
  {
    title: "Provider Id",
    dataIndex: "providerId",
    key: "providerId",
  },
  {
    title: "Total Monthly Consumption",
    dataIndex: "totalMonthlyConsumption",
    key: "totalMonthlyConsumption",
    render: (value) => `${value.toFixed(2)} kWh`,
  },
  {
    title: "Daily Average Consumption",
    dataIndex: "dailyAverageConsumption",
    key: "dailyAverageConsumption",
    render: (value) => `${value.toFixed(2)} kWh`,
  },
  {
    title: "Average Consumption Per Citizen",
    dataIndex: "averageConsumptionPerCitizen",
    key: "averageConsumptionPerCitizen",
    render: (value) => `${value.toFixed(2)} kWh`,
  },
  {
    title: "Peak Hourly Consumption",
    dataIndex: "peakHourlyConsumption",
    key: "peakHourlyConsumption",
    render: (value) => `${value.toFixed(2)} kWh`,
  },
  {
    title: "Citizen Count",
    dataIndex: "citizenCount",
    key: "citizenCount",
    render: (value) => value.toString(),
  },
  {
    title: "Date",
    dataIndex: "date",
    key: "date",
  },
];

const ElectricalProviderConsumptionSummaryTable = ({
  providerData,
  data,
  loading,
  errored,
}) => {
  // Preprocess data to include companyName from providerData
  const enhancedData =
    data?.map((item) => {
      const provider = providerData?.find(
        (provider) => provider.id === item.providerId
      );
      return {
        ...item,
        companyName: provider?.companyName || "Unknown", // Add companyName if matched
      };
    }) || [];

  return (
    <DataWrapper data={data} loading={loading} errored={errored} strategy="spin">
      <Table
        {...tableProps}
        dataSource={enhancedData}
        columns={cityColumns}
        rowKey="id"
        loading={loading && !data}
        title={() => (
          <div
            style={{
              fontWeight: "bold",
              fontSize: "20px",
              textAlign: "left",
              color: "#4A90E2",
            }}
          >
            Overall City Consumption Status
          </div>
        )}
      />
    </DataWrapper>
  );
};

export default ElectricalProviderConsumptionSummaryTable;
