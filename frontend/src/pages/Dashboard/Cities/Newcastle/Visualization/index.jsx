import DashboardPage from "@/components/DashboardPage/index.jsx";
// MonthlyConsumptionChart.js
import React, { useEffect, useState } from "react";
import { Line } from "react-chartjs-2";
import NewcastleService from "@/services/NewcastleService.jsx";
import { genericNetworkError, randomColor } from "@/helpers/utils.jsx";
import { Skeleton } from "antd";

const Visualization = () => {
  const [loading, setLoading] = useState(false);
  const [data, setData] = useState([]);

  const fetchData = () => {
    setLoading(true);
    NewcastleService.cityConsumptions()
      .then((response) => setData(response))
      .catch(genericNetworkError)
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <DashboardPage
      breadcrumbs={[{ title: "Newcastle" }, { title: "Visualization" }]}
    >
      <Skeleton loading={loading}>
        <MonthlyConsumptionChart data={data} />
      </Skeleton>
    </DashboardPage>
  );
};

export default Visualization;

const MonthlyConsumptionChart = ({ data }) => {
  return (
    <div>
      <h2>Monthly Average Consumption per Provider</h2>
      <Line
        data={deriveChartData(deriveMonthlyAverages(deriveMonthTotals(data)))}
      />
    </div>
  );
};

const deriveMonthTotals = (data) => {
  const monthTotals = {};

  data.forEach((item) => {
    const providerId = item.providerId;
    const month = new Date(item.consumptionPeriodStart).toLocaleString(
      "default",
      { month: "short" },
    );
    const year = new Date(item.consumptionPeriodStart).getFullYear();
    const key = `${month} ${year}`;

    if (!monthTotals[providerId]) {
      monthTotals[providerId] = {};
    }

    if (!monthTotals[providerId][key]) {
      monthTotals[providerId][key] = { total: 0, count: 0 };
    }

    monthTotals[providerId][key].total += item.totalConsumption;
    monthTotals[providerId][key].count += 1;
  });

  return monthTotals;
};

const deriveMonthlyAverages = (monthTotals) => {
  const monthlyAverages = {};

  Object.keys(monthTotals).forEach((providerId) => {
    monthlyAverages[providerId] = {};
    Object.keys(monthTotals[providerId]).forEach((monthKey) => {
      const { total, count } = monthTotals[providerId][monthKey];
      monthlyAverages[providerId][monthKey] = total / count;
    });
  });

  return monthlyAverages;
};

const deriveChartData = (monthlyAverages) => {
  // Prepare chart data for each provider
  const chartData = {
    labels: new Set(), // Months
    datasets: [],
  };

  // Iterate over each provider's data
  Object.keys(monthlyAverages).forEach((providerId) => {
    const providerMonthlyAverages = monthlyAverages[providerId]; // Get the data for each provider

    // Create a dataset for the current provider
    const dataset = {
      label: `Provider ${providerId}`, // Label for the dataset
      data: [], // Array to store the monthly data points
      borderColor: randomColor(), // Random color for the line
    };

    // Add monthly data points to the dataset
    Object.keys(providerMonthlyAverages).forEach((month) => {
      chartData.labels.add(month); // Add the month to the labels
      dataset.data.push(providerMonthlyAverages[month]); // Add the consumption data for that month
    });

    // Add the provider's dataset to the chart data
    chartData.datasets.push(dataset);
  });

  return {
    labels: Array.from(chartData.labels),
    datasets: chartData.datasets,
  };
};
