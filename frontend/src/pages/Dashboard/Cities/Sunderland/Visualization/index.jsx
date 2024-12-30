import React, { useState } from "react";
import DashboardPage from "@/components/DashboardPage/index.jsx";
import YearSelector from "@/pages/Dashboard/Cities/Sunderland/Visualization/YearSelector.jsx";
import ProvidersBarChart from "@/pages/Dashboard/Cities/Sunderland/Visualization/ProvidersBarChart.jsx";
import CityBarChart from "@/pages/Dashboard/Cities/Sunderland/Visualization/CityBarChart.jsx";
import useVisualizationData from "@/pages/Dashboard/Cities/Sunderland/Visualization/useVisualizationData.jsx";

const Visualization = () => {
  // State for selected year
  const [selectedYear, setSelectedYear] = useState(2024); // Default year

  // Using the custom hook for data fetching
  const {
    providersChartDataRaw,
    providersChartLoading,
    providersChartErrored,
    cityChartDataRaw,
    cityChartLoading,
    cityChartErrored,
  } = useVisualizationData(selectedYear);

  return (
    <DashboardPage breadcrumbs={[{ title: "Sunderland" }, { title: "Visualization" }]}>
      {/* Year Selection Dropdown */}
      <YearSelector selectedYear={selectedYear} onChange={setSelectedYear} />

      {/* Providers Bar Chart */}
      <ProvidersBarChart
        data={providersChartDataRaw}
        loading={providersChartLoading}
        errored={providersChartErrored}
      />

      {/* City Bar Chart */}
      <CityBarChart data={cityChartDataRaw} loading={cityChartLoading} errored={cityChartErrored} />
    </DashboardPage>
  );
};

export default Visualization;
