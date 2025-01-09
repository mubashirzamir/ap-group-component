import React, { useState } from "react";
import DashboardPage from "@/components/DashboardPage/index.jsx";
import YearSelector from "@/pages/Dashboard/Overview/Visualization/YearSelector.jsx";
import CityBarChartSunderland from "@/pages/Dashboard/Overview/Visualization/CityBarChartSunderland.jsx";
import VisualizationDataSunderland from "@/pages/Dashboard/Overview/Visualization/VisualizationDataSunderland.jsx";

const Visualization = () => {
  // State for selected year
  const [selectedYearCity, setSelectedYearCity] = useState(2025); // Default year

  const {
    cityChartDataRaw: cityChartDataRawSundarland,
    cityChartLoading: cityChartLoadingSundarland,
    cityChartErrored: cityChartErroredSundarland,
  } = VisualizationDataSunderland(selectedYearCity);

  return (
    <DashboardPage breadcrumbs={[{ title: "Overview" }, { title: "Visualization" }]}>
      <h1 style={{ 
        fontSize: "2rem", 
        fontWeight: "bold", 
        marginTop: "20px", 
        marginBottom: "10px" 
      }}>
        Sunderland
      </h1>
      {/* Year Selection Dropdown */}
      <YearSelector selectedYear={selectedYearCity} onChange={setSelectedYearCity} />

      {/* City Bar Chart */}
      <CityBarChartSunderland data={cityChartDataRawSundarland} loading={cityChartLoadingSundarland} errored={cityChartErroredSundarland} />
    </DashboardPage>
  );
};

export default Visualization;
