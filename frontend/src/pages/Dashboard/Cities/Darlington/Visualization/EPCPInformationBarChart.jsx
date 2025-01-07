import React, { useMemo } from "react";
import { Line } from "react-chartjs-2";
import { Card, Alert } from "antd";
import DataWrapper from "@/components/DataWrapper/DataWrapper.jsx";
import { COLORS } from "@/helpers/constants.jsx";

const EPCPInformationLineChart = ({ pData, data, loading, errored }) => {
  const chartOptions = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        position: "bottom",
        labels: {
          font: { size: 14 },
          color: "#4A4A4A"
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
          text: "Peak Hourly Consumption (kWh)",
          font: {
            size: 16,
            weight: "bold",
          },
        },
        ticks: {
          beginAtZero: true,
        },
      },
    },
  };

  const chartData = useMemo(() => {
    if (!data || !pData) return { labels: [], datasets: [] };

    const providersMap = pData.reduce((acc, provider) => {
      acc[provider.id] = provider.companyName;
      return acc;
    }, {});

    const allDataSorted = [...data].sort((a, b) => {
      const dateA = new Date(a.date);
      const dateB = new Date(b.date);
      return dateA - dateB;
    });

    const months = Array.from(
      new Set(
        allDataSorted.map((item) => {
          const date = new Date(item.date);
          return `${date.getMonth() + 1}/${date.getFullYear()}`;
        })
      )
    );

    const datasets = pData.map((provider, index) => {
      const providerData = allDataSorted.filter(
        (item) => item.providerId === provider.id
      );

      const providerConsumption = months.map((month) => {
        const matchingData = providerData.find((item) => {
          const date = new Date(item.date);
          return `${date.getMonth() + 1}/${date.getFullYear()}` === month;
        });
        return matchingData ? matchingData.peakHourlyConsumption : 0;
      });

      return {
        label: providersMap[provider.id],
        data: providerConsumption,
        borderColor: COLORS[index % COLORS.length],
        backgroundColor: COLORS[index % COLORS.length], // Fill legend with color
        tension: 0.4,
      };
    });

    return {
      labels: months,
      datasets,
    };
  }, [data, pData]);

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
        <div
          style={{
            padding: "10px 20px",
            fontSize: "20px",
            fontWeight: "bold",
            color: "#4A90E2",
          }}
        >
          Peak Hourly Consumption by Provider
        </div>
        {chartData.labels.length === 0 ? (
          <div
            style={{
              height: "200px",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
            }}
          >
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
                description="No consumption data available."
                type="info"
                showIcon
              />
            </div>
          </div>
        ) : (
          <div style={{ position: "relative", height: "500px", width: "100%" }}>
            <Line data={chartData} options={chartOptions} />
          </div>
        )}
      </Card>
    </DataWrapper>
  );
};

export default EPCPInformationLineChart;
