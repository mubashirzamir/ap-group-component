import React, { useState } from "react";
import DashboardPage from "@/components/DashboardPage/index.jsx";
import YearSelector from "@/pages/Dashboard/Overview/Visualization/YearSelector.jsx";
import CityBarChartSunderland from "@/pages/Dashboard/Overview/Visualization/CityBarChartSunderland.jsx";
import VisualizationDataSunderland from "@/pages/Dashboard/Overview/Visualization/VisualizationDataSunderland.jsx";
import VisualizationDataNewcastle from "@/pages/Dashboard/Overview/Visualization/VisualizationDataNewcastle.jsx";
import CityBarChartNewcastle from "@/pages/Dashboard/Overview/Visualization/CityBarChartNewcastle.jsx";
import { CityHeading } from "@/helpers/utils.jsx";

const Visualization = () => {
  // State for selected year
  const [selectedYearCitySunderland, setSelectedYearCitySunderland] =
    useState(2025); // Default year
  const [selectedYearCityNewcastle, setSelectedYearCityNewcastle] =
    useState(2025); // Default year

  const {
    cityChartDataRaw: cityChartDataRawSunderland,
    cityChartLoading: cityChartLoadingSunderland,
    cityChartErrored: cityChartErroredSunderland,
  } = VisualizationDataSunderland(selectedYearCitySunderland);

  const {
    cityChartDataRaw: cityChartDataRawNewcastle,
    cityChartLoading: cityChartLoadingNewcastle,
    cityChartErrored: cityChartErroredNewcastle,
  } = VisualizationDataNewcastle(selectedYearCityNewcastle);

  return (
    <DashboardPage
      breadcrumbs={[{ title: "Overview" }, { title: "Visualization" }]}
    >
      <CityHeading name="Sunderland" />
      <YearSelector
        selectedYear={selectedYearCitySunderland}
        onChange={setSelectedYearCitySunderland}
      />
      <CityBarChartSunderland
        data={cityChartDataRawSunderland}
        loading={cityChartLoadingSunderland}
        errored={cityChartErroredSunderland}
      />
      <CityHeading name="Newcastle" />
      <YearSelector
        selectedYear={selectedYearCityNewcastle}
        onChange={setSelectedYearCityNewcastle}
      />
      <CityBarChartNewcastle
        data={cityChartDataRawNewcastle}
        loading={cityChartLoadingNewcastle}
        errored={cityChartErroredNewcastle}
      />
    </DashboardPage>
  );
};

export default Visualization;
