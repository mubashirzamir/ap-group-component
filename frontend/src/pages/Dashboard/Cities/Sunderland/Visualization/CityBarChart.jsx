import React, { useMemo } from "react";
import { Bar } from "react-chartjs-2";
import { Skeleton, Alert } from "antd";

const CityBarChart = ({ data, loading, errored }) => {
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
        text: "Monthly Average Consumption for the City",
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
        stacked: false, // Disable stacking
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
        stacked: false, // Disable stacking
      },
    },
    elements: {
      bar: {
        borderWidth: 2,
      },
    },
  };

  // Process data for the city chart
  const chartData = useMemo(() => {
    if (!data || data.length === 0) {
      return { labels: [], datasets: [] };
    }

    const months = Array.from(new Set(data.map((item) => item.month))).sort();

    const dataset = {
      label: "City Average Consumption",
      data: months.map((month) => {
        const record = data.find((item) => item.month === month);
        return record ? record.averageConsumption : 0;
      }),
      backgroundColor: "#28a745", // A distinct color for the city chart
    };

    return {
      labels: months,
      datasets: [dataset],
    };
  }, [data]);

  return (
    <div style={{ position: "relative", height: "400px", width: "100%", marginTop: 40 }}>
      {loading ? (
        <Skeleton active />
      ) : errored ? (
        <Alert
          message="Error"
          description="Failed to load city chart data. Please try again later."
          type="error"
          showIcon
        />
      ) : chartData.labels.length === 0 ? (
        <Alert
          message="No Data"
          description="No consumption data available for the selected year."
          type="info"
          showIcon
        />
      ) : (
        <Bar data={chartData} options={chartOptions} />
      )}
    </div>
  );
};

export default CityBarChart;
