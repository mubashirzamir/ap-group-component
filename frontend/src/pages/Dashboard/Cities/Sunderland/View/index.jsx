// src/components/View.jsx

import DashboardPage from "@/components/DashboardPage/index.jsx";
import { Table, Select, Spin } from "antd"; // Import Spin for loading
import { useEffect, useState } from "react";
import SunderlandService from "@/services/SunderlandService.jsx"; // Updated service
import { genericNetworkError, renderDateTime } from "@/helpers/utils.jsx";
import DataWrapper from "@/components/DataWrapper/DataWrapper.jsx";

const { Option } = Select;

// Define available time ranges
const TIME_RANGES = [
  { label: "Last 24 Hours", value: "LAST_24_HOURS" },
  { label: "Last 7 Days", value: "LAST_7_DAYS" },
  { label: "Last 30 Days", value: "LAST_30_DAYS" },
];

const tableProps = {
  scroll: { x: "max-content" },
  pagination: { pageSize: 50 },
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
    render: (value) => `${value.toFixed(2)} kWh`, // Optional formatting
  },
  {
    title: "Total Consumption",
    dataIndex: "totalConsumption",
    key: "totalConsumption",
    render: (value) => `${value.toFixed(2)} kWh`, // Optional formatting
  },
  // Add more columns if needed
];

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

const View = () => {
  // State for Providers
  const [providerLoading, setProviderLoading] = useState(false);
  const [providerData, setProviderData] = useState([]);
  const [providerErrored, setProviderErrored] = useState(null);

  // State for City
  const [cityLoading, setCityLoading] = useState(false);
  const [cityData, setCityData] = useState(null);
  const [cityErrored, setCityErrored] = useState(null);

  // State for selected time range
  const [selectedTimeRange, setSelectedTimeRange] = useState("LAST_30_DAYS"); // Default value

  const fetchData = async () => {
    // Fetch Providers Data
    setProviderErrored(null);
    setProviderLoading(providerData.length === 0); // Only show loading if no data

    try {
      const providersResponse = await SunderlandService.getAggregatedDataByProvider(selectedTimeRange);
      setProviderData(providersResponse);
    } catch (error) {
      setProviderErrored(true);
      genericNetworkError(error);
    } finally {
      setProviderLoading(false);
    }

    // Fetch City Data
    setCityErrored(null);
    setCityLoading(cityData === null); // Only show loading if no data

    try {
      const cityResponse = await SunderlandService.getAggregatedDataForCity(selectedTimeRange);
      setCityData(cityResponse);
    } catch (error) {
      setCityErrored(true);
      genericNetworkError(error);
    } finally {
      setCityLoading(false);
    }
  };

  useEffect(() => {
    // Initial fetch on mount and whenever selectedTimeRange changes
    fetchData();

    // Set interval to fetch data periodically (every 30 seconds)
    const intervalId = setInterval(() => {
      fetchData();
    }, 30000); // 30000ms = 30 seconds

    // Cleanup: Clear interval on unmount or when selectedTimeRange changes
    return () => clearInterval(intervalId);
  }, [selectedTimeRange]); // Dependency array includes selectedTimeRange

  return (
    <DashboardPage breadcrumbs={[{ title: "DataView Providers" }, { title: "View" }]}>
      {/* Time Range Dropdown */}
      <div style={{ marginBottom: 20, display: "flex", justifyContent: "flex-end" }}>
        <Select
          value={selectedTimeRange}
          onChange={(value) => setSelectedTimeRange(value)}
          style={{ width: 200 }}
        >
          {TIME_RANGES.map((range) => (
            <Option key={range.value} value={range.value}>
              {range.label}
            </Option>
          ))}
        </Select>
      </div>

      {/* Providers Table */}
      <DataWrapper data={providerData} loading={providerLoading} errored={providerErrored}>
        <Table
          {...tableProps}
          dataSource={providerData}
          columns={providerColumns}
          rowKey="providerId"
          loading={providerLoading && providerData.length === 0} // Show loading only if no data
          title={() => "Providers Consumption Data"}
        />
      </DataWrapper>

      {/* City Status Table */}
      <DataWrapper data={cityData} loading={cityLoading} errored={cityErrored}>
        <Table
          {...tableProps}
          dataSource={cityData ? [cityData] : []} // Wrap cityData in an array
          columns={cityColumns}
          rowKey="cityStatus" // Use a unique key
          loading={cityLoading && !cityData} // Show loading only if no data
          pagination={false} // Typically, city status might not need pagination
          title={() => "Overall City Consumption Status"}
        />
      </DataWrapper>
    </DashboardPage>
  );
};

export default View;
