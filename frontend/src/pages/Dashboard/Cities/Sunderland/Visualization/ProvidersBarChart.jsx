import React, { useMemo } from "react";
import { Bar } from "react-chartjs-2";
import { Alert } from "antd";
import { COLORS } from "@/helpers/constants.jsx";
import DataWrapper from "@/components/DataWrapper/DataWrapper.jsx";

const ProvidersBarChart = ({ data, loading, errored }) => {
  // Define chart options (non-stacked)
  const chartOptions = {
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
        stacked: false, // Disable stacking on the x-axis
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
        stacked: false, // Disable stacking on the y-axis
      },
    },
    elements: {
      bar: {
        borderWidth: 2,
      },
    },
  };

  // Data Processing Function for Providers Chart
  const deriveChartData = (data) => {
    if (!data || data.length === 0) {
      return { labels: [], datasets: [] };
    }

    // Extract unique months and providers
    const months = Array.from(new Set(data.map((item) => item.month))).sort();
    const providers = Array.from(new Set(data.map((item) => item.providerId))).sort();

    // Initialize datasets
    const datasets = providers.map((providerId, index) => ({
      label: `Provider ${providerId}`,
      data: months.map((month) => {
        const record = data.find((item) => item.providerId === providerId && item.month === month);
        return record ? record.averageConsumption : 0;
      }),
      backgroundColor: COLORS[index % COLORS.length],
    }));

    return {
      labels: months,
      datasets,
    };
  };

  // Memoize processed chart data to prevent unnecessary recalculations
  const chartData = useMemo(() => deriveChartData(data), [data]);

  return (
    <DataWrapper data={data} loading={loading} errored={errored} strategy="spin">
      {chartData.labels.length === 0 ? (
        <Alert
          message="No Data"
          description="No providers consumption data available for the selected year."
          type="info"
          showIcon
        />
      ) : (
        <div style={{ position: "relative", height: "500px", width: "100%", marginTop: 40 }}>
          <Bar data={chartData} options={chartOptions} />
        </div>
      )}
    </DataWrapper>
  );
};

export default ProvidersBarChart;
