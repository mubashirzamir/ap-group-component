import DashboardPage from "@/components/DashboardPage/index.jsx";
import { useEffect, useState } from "react";
import { Line } from "react-chartjs-2";
import NewcastleService from "@/services/NewcastleService.jsx";
import { genericNetworkError, randomColor } from "@/helpers/utils.jsx";
import DataWrapper from "@/components/DataWrapper/DataWrapper.jsx";
import { DatePicker } from "antd";
import { REFRESH_INTERVAl_NEWCASTLE } from "@/helpers/constants.jsx";
import {
  CategoryScale,
  Chart as ChartJS,
  Legend,
  LinearScale,
  LineElement,
  Title,
  Tooltip,
} from "chart.js";
import dayjs from "dayjs";
import MiddleData from "../View/Test";
import { list } from "postcss";

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
      text: "Monthly Average Consumption per Provider",
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
        text: "Average Consumption",
      },
    },
  },
};

const Visualization = () => {
  const [loading, setLoading] = useState(false);
  const [data, setData] = useState([]);
  const [errored, setErrored] = useState(false);
  const [date, setDate] = useState(dayjs());

  const fetchData = () => {
    setLoading(true);
    const data = MiddleData();
    var listDate = [];
    var listMonthlyAverage = [];
    data.forEach(element => {
      var mydate = new Date(element['ConsumptionPeriodEnd']);
      listDate.push(mydate.getFullYear())
      listMonthlyAverage.push(element['AverageConsumption'])

    });
    // console.log(listDate);
    console.log(listMonthlyAverage);
    setData(listMonthlyAverage)
    setLoading(false)
  };

  useEffect(() => {
    // Initial fetch on mount
    fetchData();

    const intervalId = setInterval(() => {
      fetchData();
    }, REFRESH_INTERVAl_NEWCASTLE);

    // Cleanup: Clear interval on unmount to prevent memory leaks
    return () => clearInterval(intervalId);
  }, [date]); // Include year as a dependency to fetch data when year changes

  const onDateChange = (date) => setDate(date);

  return (
    <DashboardPage
      breadcrumbs={[{ title: "Middlesbrough" }, { title: "Visualization" }]}
    >

      <div className="flex justify-end p-4">
        <DatePicker
          disabled={loading}
          value={date}
          onChange={onDateChange}
          picker="year"
        />
      </div>
      <DataWrapper data={data} loading={loading} errored={errored}>
        <MonthlyConsumptionChart data={data} />
      </DataWrapper>
    </DashboardPage>
  );
};

export default Visualization;



const MonthlyConsumptionChart = ({ data }) => {
  return <Line data={makeChartData(data)} options={chartOptions} />;
};

const makeChartData = (data) => {
  const providers = ["A", "B", "C"];
  const months = Array.from({ length: 12 }, (_, index) => index + 1);

  // Initialize chart datasets
  const datasets = providers.map((provider) => {
    const providerData = data.filter((item) => {
      // Map providerId to provider letter
      const providerMap = { 1: "A", 2: "B", 3: "C" };
      return providerMap[item.providerId] === provider;
    });

    // Create an array for each month
    const monthlyConsumption = months.map((month) => {
      const monthData = providerData.find((item) => item.month === month);
      return monthData ? monthData.averageConsumption : 0; // Default to 0 if no data for that month
    });

    return {
      label: provider,
      data: monthlyConsumption,
      borderColor: randomColor(), // Random color for each provider
      fill: false, // No fill under the line
      tension: 0.1,
    };
  });

  return {
    labels: months,
    datasets: datasets,
  };
};

