import React, { useMemo } from "react";
import { Bar } from "react-chartjs-2";
import { Alert, Card } from "antd";
import { COLORS } from "@/helpers/constants.jsx";
import DataWrapper from "@/components/DataWrapper/DataWrapper.jsx";

/**
 * Component to display a bar chart of the city's average consumption data.
 *
 * @param {Object} props - The component props.
 * @param {Array} props.data - The data to display in the chart.
 * @param {boolean} props.loading - The loading state of the data.
 * @param {boolean} props.errored - The error state of the data.
 * @returns {JSX.Element} The rendered bar chart component.
 */
const CityAverageBarChart = ({ data, loading, errored }) => {
  // Chart options configuration
  const chartOptions = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        position: "bottom",
        labels: {
          font: { size: 14 },
          color: "#4A4A4A",
        },
      },
      tooltip: {
        mode: "index",
        intersect: false,
        backgroundColor: "#f5f5f5",
        titleColor: "#333",
        bodyColor: "#333",
        borderWidth: 1,
        borderColor: "#ddd",
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
            size: 16,
            weight: "bold",
          },
        },
        ticks: {
          autoSkip: false,
          font: { size: 12 },
        },
      },
      y: {
        title: {
          display: true,
          text: "Average Consumption (kWh)",
          font: {
            size: 16,
            weight: "bold",
          },
        },
        ticks: {
          beginAtZero: true,
          stepSize: 50,
        },
      },
    },
    elements: {
      bar: {
        borderWidth: 2,
        borderRadius: 5,
      },
    },
  };

  // Memoized chart data to avoid unnecessary recalculations
  const chartData = useMemo(() => {
    if (!data || data.length === 0) {
      return { labels: [], datasets: [] };
    }

    // Sort months numerically
    const months = Array.from(new Set(data.map((item) => item.month))).sort((a, b) => a - b);

    const dataset = {
      label: "City Average Consumption",
      data: months.map((month) => {
        const record = data.find((item) => item.month === month);
        return record ? record.averageConsumption : 0;
      }),
      backgroundColor: "#28a745",
      borderColor: "#28a745",
    };

    return {
      labels: months.map((month) => `Month ${month}`),
      datasets: [dataset],
    };
  }, [data]);

  return (
    <DataWrapper data={data} loading={loading} errored={errored} strategy="spin">
      <Card
        style={{
          marginTop: 20,
          borderRadius: "10px",
          border: "1px solid #ddd",
          boxShadow: "0px 4px 10px rgba(0, 0, 0, 0.1)",
        }}
      >
        <div style={{ padding: "10px 20px", fontSize: "20px", fontWeight: "bold", color: "#4A90E2" }}>
          Monthly Average Consumption for the City
        </div>
        {chartData.labels.length === 0 ? (
          <div style={{ height: "200px", display: "flex", alignItems: "center", justifyContent: "center" }}>
            <div
              style={{
                textAlign: "center",
                padding: "20px",
                border: "1px dashed #ddd",
                borderRadius: "10px",
                backgroundColor: "#fafafa",
              }}
            >
              <Alert
                message="No Data"
                description="No consumption data available for the selected year."
                type="info"
                showIcon
              />
            </div>
          </div>
        ) : (
          <>
            <div style={{ position: "relative", height: "500px", width: "100%" }}>
              <Bar data={chartData} options={chartOptions} />
            </div>
            <div style={{ marginTop: 20 }}>
              {chartData.datasets.map((dataset, index) => (
                <div
                  key={index}
                  style={{
                    padding: "10px",
                    borderRadius: "5px",
                    marginBottom: "10px",
                    background: `linear-gradient(135deg, #28a745 50%, #f5f5f5 50%)`, // Changed color to green
                    color: "#333",
                    fontSize: "14px",
                  }}
                >
                  <strong>{dataset.label}</strong>
                  <ul style={{ paddingLeft: "20px" }}>
                    {dataset.data.map((value, idx) => (
                      <li
                        key={`${index}-${idx}`}
                        style={{
                          margin: "5px 0",
                          color: "#333", // Ensure text inside the list is readable
                        }}
                      >
                        {chartData.labels[idx]}: <strong>{value.toFixed(2)} kWh</strong>
                      </li>
                    ))}
                  </ul>
                </div>
              ))}
            </div>
          </>
        )}
      </Card>
    </DataWrapper>
  );
};

export default CityAverageBarChart;