import React, { useState } from "react";
import DashboardPage from "@/components/DashboardPage/index.jsx";
import YearSelector from "@/pages/Dashboard/Cities/Sunderland/Visualization/YearSelector.jsx";
import ProvidersBarChart from "@/pages/Dashboard/Cities/Sunderland/Visualization/ProvidersBarChart.jsx";
import CityBarChart from "@/pages/Dashboard/Cities/Sunderland/Visualization/CityBarChart.jsx";
import VisualizationDataProvider from "@/pages/Dashboard/Cities/Sunderland/Visualization/VisualizationDataProvider.jsx";
import VisualizationDataCity from "@/pages/Dashboard/Cities/Sunderland/Visualization/VisualizationDataCity.jsx";

const Visualization = () => {
  // State for selected year
  const [selectedYearProvider, setSelectedYearProvider] = useState(2024); // Default year
  const [selectedYearCity, setSelectedYearCity] = useState(2024); // Default year

  // Using the custom hook for data fetching
  const {
    providersChartDataRaw,
    providersChartLoading,
    providersChartErrored
  } = VisualizationDataProvider(selectedYearProvider);

  const {
    cityChartDataRaw,
    cityChartLoading,
    cityChartErrored,
  } = VisualizationDataCity(selectedYearCity);

  return (
    <DashboardPage breadcrumbs={[{ title: "Sunderland" }, { title: "Visualization" }]}>
      {/* Year Selection Dropdown */}
      <YearSelector selectedYear={selectedYearProvider} onChange={setSelectedYearProvider} />

      {/* Providers Bar Chart */}
      <ProvidersBarChart
        data={providersChartDataRaw}
        loading={providersChartLoading}
        errored={providersChartErrored}
      />

      {/* Year Selection Dropdown */}
      <YearSelector selectedYear={selectedYearCity} onChange={setSelectedYearCity} />

      {/* City Bar Chart */}
      <CityBarChart data={cityChartDataRaw} loading={cityChartLoading} errored={cityChartErrored} />
    </DashboardPage>
  );
};

export default Visualization;
