import React, { useState } from "react";
import DashboardPage from "@/components/DashboardPage/index.jsx";
import ProviderInfo from "@/pages/Dashboard/Cities/Darlington/View/ProviderInfo.jsx";
import ProviderAverageResult from "@/pages/Dashboard/Cities/Darlington/Visualization/ProviderAverageResult.jsx";
import YearSelector from "@/pages/Dashboard/Cities/Darlington/Visualization/YearSelector.jsx";
import ProviderAverageBarChart from "@/pages/Dashboard/Cities/Darlington/Visualization/ProviderAverageBarChart.jsx";
import CityAverageResult from "@/pages/Dashboard/Cities/Darlington/Visualization/CityAverageResult.jsx";
import CityAverageBarChart from "@/pages/Dashboard/Cities/Darlington/Visualization/CityAverageBarChart.jsx";
import EPCPResult from "@/pages/Dashboard/Cities/Darlington/Visualization/EPCPResult.jsx";
import EPCPInformationBarChart from "@/pages/Dashboard/Cities/Darlington/Visualization/EPCPInformationBarChart.jsx";

const Visualization = () => {
  // State to manage the selected Year period for provider data
  const [providerYear, setProviderYear] = useState(2024);
  // State to manage the selected Year period for city data
  const [cityYear, setCityYear] = useState(2024);

  const { providerData, providerLoading, providerError } = ProviderInfo();
  const { providerInformation, providerInfoLoading, providerInfoError } = ProviderAverageResult(providerYear);

  const {cityInformation, cityInfoLoading, cityInfoError}=CityAverageResult(cityYear);
  const { EPCPInformation, EPCPLoading, EPCPError } = EPCPResult();
  return (
    <DashboardPage breadcrumbs={[{ title: "Darlington" }, { title: "Visualization" }]}>
    <EPCPInformationBarChart pData={providerData} data={EPCPInformation} loading={EPCPLoading} error={EPCPError} />
    {/* Selector for choosing the Year period for provider data */}
    <YearSelector selectedYear={providerYear} onChange={setProviderYear} />
    {/* Table displaying provider data */}
    <ProviderAverageBarChart pData={providerData} data={providerInformation} loading={providerInfoLoading} error={providerInfoError} />
    {/* Selector for choosing the Year period for city data */}
    <YearSelector selectedYear={cityYear} onChange={setCityYear} />
    {/* Table displaying city data */}
    <CityAverageBarChart data={cityInformation} loading={cityInfoLoading} error={cityInfoError} />
      Visualization
    </DashboardPage>
  );
};

export default Visualization;