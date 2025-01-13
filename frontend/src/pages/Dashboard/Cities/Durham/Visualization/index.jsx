import DashboardPage from "@/components/DashboardPage/index.jsx";
import { useEffect, useState } from "react";
import { Line } from "react-chartjs-2";
import DurhamService from "@/services/DurhamService.jsx";
import { genericNetworkError, randomColor } from "@/helpers/utils.jsx";
import DataWrapper from "@/components/DataWrapper/DataWrapper.jsx";
import {
  CategoryScale,
  Chart as ChartJS,
  Legend,
  LinearScale,
  LineElement,
  Title,
  Tooltip,
} from "chart.js";

// Register Chart.js components
ChartJS.register(
  LineElement,
  CategoryScale,
  LinearScale,
  Title,
  Tooltip,
  Legend,
);

const chartOptions = {
  plugins: {
    title: {
      display: true,
      text: "Monthly Consumption per Provider",
    },
    tooltip: {
      mode: "index",
      intersect: false,
    },
  },
  scales: {
    x: {
      title: {
        display: true,
        text: "Month",
      },
    },
    y: {
      title: {
        display: true,
        text: "Consumption (kWh)",
      },
    },
  },
};

const DurhamVisualization = () => {
  const [loading, setLoading] = useState(false);
  const [data, setData] = useState([]);
  const [errored, setErrored] = useState(false);

  const fetchData = () => {
    setLoading(true);
    DurhamService.getMonthlyConsumptionForAllProviders()
      .then((response) => setData(response))
      .catch((e) => {
        setErrored(true);
        genericNetworkError(e);
      })
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    // Initial fetch on mount
    fetchData();

    // Set interval to refresh data every 30 seconds
    const intervalId = setInterval(() => {
      fetchData();
    }, 30000);

    // Cleanup: Clear interval on unmount
    return () => clearInterval(intervalId);
  }, []);

  return (
    <DashboardPage
      breadcrumbs={[{ title: "Durham" }, { title: "Visualization" }]}
    >
      <DataWrapper data={data} loading={loading} errored={errored}>
        <MonthlyConsumptionChart data={data} />
      </DataWrapper>
    </DashboardPage>
  );
};

export default DurhamVisualization;

const MonthlyConsumptionChart = ({ data }) => {
  return <Line data={makeChartData(data)} options={chartOptions} />;
};

const makeChartData = (data) => {
  // Extract all unique months from the response
  const months = Array.from(
    new Set(
      Object.values(data)
        .flatMap((providerData) => Object.keys(providerData))
        .sort()
    )
  );

  // Initialize chart datasets
  const datasets = Object.keys(data).map((provider) => {
    const providerData = data[provider];

    // Map months to their corresponding values or default to 0
    const monthlyConsumption = months.map((month) =>
      providerData[month] ? providerData[month] : 0
    );

    return {
      label: provider,
      data: monthlyConsumption,
      borderColor: randomColor(), // Random color for each provider
      fill: false, // No fill under the line
      tension: 0.1,
    };
  });

  return {
    labels: months, // Use all unique months as labels
    datasets: datasets,
  };
};

