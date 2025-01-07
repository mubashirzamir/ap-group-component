import DashboardPage from "@/components/DashboardPage/index.jsx";
import { useEffect, useState } from "react";
import { Line } from "react-chartjs-2";
import DataWrapper from "@/components/DataWrapper/DataWrapper.jsx";
import { DatePicker } from "antd";
import { REFRESH_INTERVAl_OVERVIEW } from "@/helpers/constants.jsx";
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
import NewcastleService from "@/services/NewcastleService.jsx";
import SunderlandService from "@/services/SunderlandService.jsx";
import ServiceStatusPopover from "@/pages/Dashboard/Overview/ServiceStatusPopover.jsx";

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
      text: "Monthly Average Consumption By City",
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
  const [data, setData] = useState({
    labels: [],
    datasets: [],
  });
  const [alertMessages, setAlertMessages] = useState([]);
  const [date, setDate] = useState(dayjs());

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      const year = date.clone().year();
      let newcastleData = [];
      let sunderlandData = [];
      let messages = [];

      try {
        newcastleData = await NewcastleService.monthlyAverageCity({ year });
        messages.push("Newcastle: ✅");
      } catch (error) {
        messages.push("Newcastle: ❌");
      }

      try {
        sunderlandData =
          await SunderlandService.getMonthlyAverageConsumptionForCity(year);
        messages.push("Sunderland: ✅");
      } catch (error) {
        messages.push("Sunderland: ❌");
      }

      setAlertMessages(messages);
      setData(mergeData(newcastleData, sunderlandData));
      setLoading(false);
    };

    fetchData();

    const intervalId = setInterval(() => {
      fetchData();
    }, REFRESH_INTERVAl_OVERVIEW);

    return () => clearInterval(intervalId);
  }, [date]);

  const onDateChange = (date) => setDate(date);

  return (
    <DashboardPage
      breadcrumbs={[{ title: "Overview" }, { title: "Visualization" }]}
    >
      <div className="flex justify-end p-4 space-x-4">
        <DatePicker
          disabled={loading}
          value={date}
          onChange={onDateChange}
          picker="year"
        />
        <ServiceStatusPopover alertMessages={alertMessages} loading={loading} />
      </div>
      <DataWrapper data={data} loading={loading} errored={false}>
        <MonthlyConsumptionChart data={data} />
      </DataWrapper>
    </DashboardPage>
  );
};

const MonthlyConsumptionChart = ({ data }) => {
  return <Line data={data} options={chartOptions} />;
};

// Merges the data for Newcastle and Sunderland, filling in missing months with zero consumption
const mergeData = (newcastleData, sunderlandData) => {
  const months = Array.from({ length: 12 }, (_, index) => index + 1);

  // Initialize the consumption arrays for both cities with 0s for all 12 months
  const newcastleConsumption = new Array(12).fill(0);
  const sunderlandConsumption = new Array(12).fill(0);

  // Map the consumption data from the response to the correct month
  newcastleData.forEach((data) => {
    newcastleConsumption[data.month - 1] = data.averageConsumption;
  });

  sunderlandData.forEach((data) => {
    sunderlandConsumption[data.month - 1] = data.averageConsumption;
  });

  return {
    labels: months,
    datasets: [
      {
        label: "Newcastle",
        data: newcastleConsumption,
        borderColor: "#3366CC",
        backgroundColor: "#3366CC",
        fill: false,
      },
      {
        label: "Sunderland",
        data: sunderlandConsumption,
        borderColor: "#DC3912",
        backgroundColor: "#DC3912",
        fill: false,
      },
    ],
  };
};

export default Visualization;
