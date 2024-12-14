import React, { useEffect, useState, useMemo } from "react";
import DashboardPage from "@/components/DashboardPage/index.jsx";
import { Select, Skeleton, Alert } from "antd";
import { Bar } from "react-chartjs-2";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title as ChartTitle,
  Tooltip,
  Legend,
} from "chart.js";
import NewcastleService from "@/services/SunderlandService.jsx";
import { genericNetworkError } from "@/helpers/utils.jsx";
import DataWrapper from "@/components/DataWrapper/DataWrapper.jsx";
import CityBarChart from "@/components/Charts/CityBarChart.jsx"; // Import the CityBarChart component

const { Option } = Select;

// Register ChartJS components
ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  ChartTitle,
  Tooltip,
  Legend
);

// Define available years (you can adjust this as needed)
const YEARS = [
  { label: "2023", value: 2023 },
  { label: "2024", value: 2024 },
  { label: "2025", value: 2025 },
  // Add more years if necessary
];

// Define a color palette for providers
const COLORS = [
  "#3366CC",
  "#DC3912",
  "#FF9900",
  "#109618",
  "#990099",
  "#0099C6",
  "#DD4477",
  "#66AA00",
  "#B82E2E",
  "#316395",
];

// Chart options with stacking enabled for providers
const providersChartOptions = {
  responsive: true,
  maintainAspectRatio: false, // Allows the chart to resize dynamically
  plugins: {
    legend: {
      position: "top", // Position of the legend
    },
    title: {
      display: true,
      text: "Monthly Average Consumption per Provider",
      font: {
        size: 18,
      },
    },
    tooltip: {
      mode: "index",
      intersect: false,
    },
  },
  interaction: {
    mode: "nearest",
    axis: "x",
    intersect: false,
  },
  scales: {
    x: {
      title: {
        display: true,
        text: "Month",
        font: {
          size: 14,
        },
      },
      stacked: true, // Enable stacking on the x-axis
      ticks: {
        autoSkip: false, // Ensure all labels are shown
      },
    },
    y: {
      title: {
        display: true,
        text: "Average Consumption (kWh)",
        font: {
          size: 14,
        },
      },
      beginAtZero: true,
      stacked: true, // Enable stacking on the y-axis
    },
  },
  elements: {
    bar: {
      borderWidth: 2,
    },
  },
};

// Visualization Component
const Visualization = () => {
  // State for Providers Data
  const [providersChartLoading, setProvidersChartLoading] = useState(false);
  const [providersChartDataRaw, setProvidersChartDataRaw] = useState([]);
  const [providersChartErrored, setProvidersChartErrored] = useState(null);

  // State for City Data
  const [cityChartLoading, setCityChartLoading] = useState(false);
  const [cityChartDataRaw, setCityChartDataRaw] = useState([]);
  const [cityChartErrored, setCityChartErrored] = useState(null);

  // State for selected year
  const [selectedYear, setSelectedYear] = useState(2024); // Default year

  // Fetch Providers Data
  const fetchProvidersData = async () => {
    setProvidersChartLoading(true);
    setProvidersChartErrored(null);

    try {
      const response = await NewcastleService.getMonthlyAverageConsumptionByProvider(selectedYear);
      setProvidersChartDataRaw(response);
    } catch (error) {
      setProvidersChartErrored(true);
      genericNetworkError(error);
    } finally {
      setProvidersChartLoading(false);
    }
  };

  // Fetch City Data
  const fetchCityData = async () => {
    setCityChartLoading(true);
    setCityChartErrored(null);

    try {
      const response = await NewcastleService.getMonthlyAverageConsumptionForCity(selectedYear);
      setCityChartDataRaw(response);
    } catch (error) {
      setCityChartErrored(true);
      genericNetworkError(error);
    } finally {
      setCityChartLoading(false);
    }
  };

  useEffect(() => {
    // Initial fetch on mount and whenever selectedYear changes
    fetchProvidersData();
    fetchCityData();

    // Set interval to fetch data periodically (every 30 seconds)
    const intervalId = setInterval(() => {
      fetchProvidersData();
      fetchCityData();
    }, 30000); // 30000ms = 30 seconds

    // Cleanup: Clear interval on unmount or when selectedYear changes
    return () => clearInterval(intervalId);
  }, [selectedYear]); // Dependency array includes selectedYear

  // Data Processing Function for Providers Chart
  const deriveProvidersChartData = (data) => {
    if (!data || data.length === 0) {
      return { labels: [], datasets: [] };
    }

    // Extract unique months and providers
    const months = Array.from(new Set(data.map(item => item.month))).sort();
    const providers = Array.from(new Set(data.map(item => item.providerId))).sort();

    // Initialize datasets
    const datasets = providers.map((providerId, index) => ({
      label: `Provider ${providerId}`,
      data: months.map(month => {
        const record = data.find(item => item.providerId === providerId && item.month === month);
        return record ? record.averageConsumption : 0;
      }),
      backgroundColor: COLORS[index % COLORS.length],
    }));

    return {
      labels: months,
      datasets,
    };
  };

  // Memoize providers chart data to prevent unnecessary recalculations
  const processedProvidersChartData = useMemo(() => {
    return deriveProvidersChartData(providersChartDataRaw);
  }, [providersChartDataRaw]);

  return (
    <DashboardPage breadcrumbs={[{ title: "Newcastle" }, { title: "Visualization" }]}>
      {/* Year Selection Dropdown */}
      <div style={{ marginBottom: 20, display: "flex", justifyContent: "flex-end" }}>
        <Select
          aria-label="Select Year"
          value={selectedYear}
          onChange={(value) => setSelectedYear(value)}
          style={{ width: 200 }}
        >
          {YEARS.map((year) => (
            <Option key={year.value} value={year.value}>
              {year.label}
            </Option>
          ))}
        </Select>
      </div>

      {/* Providers Stacked Bar Chart */}
      <DataWrapper data={providersChartDataRaw} loading={providersChartLoading} errored={providersChartErrored}>
        <div style={{ position: "relative", height: "500px", width: "100%", marginTop: 40 }}>
          {providersChartLoading ? (
            <Skeleton active />
          ) : providersChartErrored ? (
            <Alert
              message="Error"
              description="Failed to load providers chart data. Please try again later."
              type="error"
              showIcon
            />
          ) : processedProvidersChartData.labels.length === 0 ? (
            <Alert
              message="No Data"
              description="No providers consumption data available for the selected year."
              type="info"
              showIcon
            />
          ) : (
            <Bar data={processedProvidersChartData} options={providersChartOptions} />
          )}
        </div>
      </DataWrapper>

      {/* City Non-Stacked Bar Chart */}
      <DataWrapper data={cityChartDataRaw} loading={cityChartLoading} errored={cityChartErrored}>
        <CityBarChart data={cityChartDataRaw} loading={cityChartLoading} errored={cityChartErrored} />
      </DataWrapper>
    </DashboardPage>
  );
};

export default Visualization;
