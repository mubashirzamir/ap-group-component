import React, { useState } from "react";
import DashboardPage from "@/components/DashboardPage/index.jsx";
import ProviderInfo from "@/pages/Dashboard/Cities/Darlington/View/ProviderInfo.jsx";
import ProviderAverageResult from "@/pages/Dashboard/Cities/Darlington/Visualization/ProviderAverageResult.jsx";
import YearSelector from "@/pages/Dashboard/Cities/Darlington/Visualization/YearSelector.jsx";
import ProviderAverageBarChart from "@/pages/Dashboard/Cities/Darlington/Visualization/ProviderAverageBarChart.jsx";
import CityAverageResult from "@/pages/Dashboard/Cities/Darlington/Visualization/CityAverageResult.jsx";

const Visualization = () => {
  // State to manage the selected Year period for provider data
  const [providerYear, setProviderYear] = useState(2024);
  // State to manage the selected Year period for city data
  const [cityDate, setCityDate] = useState(2024);

  const { providerData, providerLoading, providerError } = ProviderInfo();
  const { providerInformation, providerInfoLoading, providerInfoError } = ProviderAverageResult(providerYear);

  const {cityInformation, cityInfoLoading, cityInfoError}=CityAverageResult(cityDate);
  return (
    <DashboardPage breadcrumbs={[{ title: "Darlington" }, { title: "Visualization" }]}>
    {/* Selector for choosing the Date period for provider data */}
    <YearSelector selectedYear={providerYear} onChange={setProviderYear} />
    {/* Table displaying provider data */}
    <ProviderAverageBarChart pData={providerData} data={providerInformation} loading={providerInfoLoading} error={providerInfoError} />
      Visualization
    </DashboardPage>
  );
};

export default Visualization;