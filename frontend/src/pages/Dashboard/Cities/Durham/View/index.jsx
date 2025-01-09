import DashboardPage from "@/components/DashboardPage/index.jsx";
import { Table } from "antd"; // Removed unnecessary imports
import { useEffect, useState } from "react";
import DurhamService from "@/services/DurhamService.jsx"; // Updated service for Durham
import { genericNetworkError } from "@/helpers/utils.jsx";
import DataWrapper from "@/components/DataWrapper/DataWrapper.jsx";

const tableProps = {
  scroll: { x: "max-content" },
  pagination: { pageSize: 50 },
};

const providerColumns = [
  {
    title: "Provider Name",
    dataIndex: "providerName",
    key: "providerName",
  },
  {
    title: "Average Consumption",
    dataIndex: "average",
    key: "average",
    render: (value) => `${value.toFixed(2)} kWh`,
  },
  {
    title: "Total Consumption",
    dataIndex: "sum",
    key: "sum",
    render: (value) => `${value.toFixed(2)} kWh`,
  },
  {
    title: "Total Readings",
    dataIndex: "totalCount",
    key: "totalCount",
  },
];

const cityColumns = [
  {
    title: "Total Consumption",
    dataIndex: "sum",
    key: "sum",
    render: (value) => `${value.toFixed(2)} kWh`,
  },
  {
    title: "Average Consumption",
    dataIndex: "average",
    key: "average",
    render: (value) => `${value.toFixed(2)} kWh`,
  },
  {
    title: "Total Readings",
    dataIndex: "totalCount",
    key: "totalCount",
  },
];

const DurhamView = () => {
  // State for Providers
  const [providerLoading, setProviderLoading] = useState(false);
  const [providerData, setProviderData] = useState([]);
  const [providerErrored, setProviderErrored] = useState(null);

  // State for City
  const [cityLoading, setCityLoading] = useState(false);
  const [cityData, setCityData] = useState(null);
  const [cityErrored, setCityErrored] = useState(null);

  const fetchData = async () => {
    // Fetch Providers Data
    setProviderErrored(null);
    setProviderLoading(providerData.length === 0);

    try {
      const providersResponse = await DurhamService.getAggregatedDataByProvider();
      console.log(providersResponse)
      setProviderData(providersResponse);
    } catch (error) {
      setProviderErrored(true);
      genericNetworkError(error);
    } finally {
      setProviderLoading(false);
    }

    // Fetch City Data
    setCityErrored(null);
    setCityLoading(cityData === null);

    try {
      const cityResponse = await DurhamService.getAggregatedDataForAllProviders();
      console.log(cityResponse)
      setCityData(cityResponse);
    } catch (error) {
      setCityErrored(true);
      genericNetworkError(error);
    } finally {
      setCityLoading(false);
    }
  };

  useEffect(() => {
    // Initial fetch on mount
    fetchData();

    // Set interval to fetch data periodically (every 30 seconds)
    const intervalId = setInterval(() => {
      fetchData();
    }, 30000);

    // Cleanup: Clear interval on unmount
    return () => clearInterval(intervalId);
  }, []);

  return (
    <DashboardPage breadcrumbs={[{ title: "Durham Data View" }, { title: "View" }]}>
      {/* Providers Table */}
      <DataWrapper data={providerData} loading={providerLoading} errored={providerErrored}>
        <Table
          {...tableProps}
          dataSource={providerData}
          columns={providerColumns}
          rowKey="providerName"
          loading={providerLoading && providerData.length === 0}
          title={() => "Providers Aggregated Data"}
        />
      </DataWrapper>

      {/* City Status Table */}
      <DataWrapper data={cityData} loading={cityLoading} errored={cityErrored}>
        <Table
          {...tableProps}
          dataSource={cityData ? [cityData] : []}
          columns={cityColumns}
          rowKey="cityStatus"
          loading={cityLoading && !cityData}
          pagination={false}
          title={() => "Overall City Aggregated Data"}
        />
      </DataWrapper>
    </DashboardPage>
  );
};

export default DurhamView;
