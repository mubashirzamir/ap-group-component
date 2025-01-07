import DashboardPage from "@/components/DashboardPage/index.jsx";
import { Table } from "antd";
import { useEffect, useState } from "react";
import request from "@/request.js";
import { genericNetworkError } from "@/helpers/utils.jsx";
import ProviderInfo from "@/pages/Dashboard/Cities/Darlington/View/ProviderInfo.jsx";
import ProviderSelector from "@/pages/Dashboard/Cities/Darlington/View/ProviderSelector.jsx";
import ProviderInfoPage from "@/pages/Dashboard/Cities/Darlington/View/ProviderInfoPage.jsx";
import ElectricalProviderConsumptionSummary from "@/pages/Dashboard/Cities/Darlington/View/ElectricalProviderConsumptionSummary.jsx";
import ElectricalProviderConsumptionSummaryTable from "@/pages/Dashboard/Cities/Darlington/View/ElectricalProviderConsumptionSummaryTable.jsx";
import TimeSelector from "@/pages/Dashboard/Cities/Darlington/View/TimeSelector.jsx";
import ConsumptionProviderSummary from "@/pages/Dashboard/Cities/Darlington/View/ConsumptionProviderSummary.jsx";
import ConsumptionProviderTable from "@/pages/Dashboard/Cities/Darlington/View/ConsumptionProviderTable.jsx";
import ConsumptionCitySummary from "@/pages/Dashboard/Cities/Darlington/View/ConsumptionCitySummary.jsx";
import ConsumptionCityTable from "@/pages/Dashboard/Cities/Darlington/View/ConsumptionCityTable.jsx";

/**
 * The `View` component represents the main view for the Darlington city dashboard.
 * It includes various sub-components to display provider and city consumption data.
 */
const View = () => {
  // State to manage the selected time period for provider data
  const [providerTime, setProviderTime] = useState("LAST_30_DAYS");
  // State to manage the selected time period for city data
  const [cityTime, setCityTime] = useState("LAST_30_DAYS");
  // State to manage the selected provider choice
  const [ProviderChoice, setProviderChoice] = useState("All");

  // Fetch provider information
  const { providerData, providerLoading, providerError } = ProviderInfo();
  // Fetch electrical provider consumption summary based on the selected provider choice
  const { EPCPInformation, EPCPLoading, EPCPError } = ElectricalProviderConsumptionSummary(ProviderChoice);
  // Fetch consumption provider summary based on the selected time period
  const { CProviderInformation, CProviderLoading, CProviderError } = ConsumptionProviderSummary(providerTime);
  // Fetch city consumption summary based on the selected time period
  const { CCityInformation, CCityLoading, CCityError } = ConsumptionCitySummary(cityTime);

  return (
    <DashboardPage breadcrumbs={[{ title: "Darlington" }, { title: "View" }]}>
      {/* Display provider information */}
      <ProviderInfoPage data={providerData} loading={providerLoading} errored={providerError} />
      {/* Selector for choosing a provider */}
      <ProviderSelector selectedProvider={ProviderChoice} data={providerData} onChange={setProviderChoice} />
      {/* Table displaying electrical provider consumption summary */}
      <ElectricalProviderConsumptionSummaryTable providerData={providerData} data={EPCPInformation} loading={EPCPLoading} errored={EPCPError} />
      {/* Selector for choosing the time period for provider data */}
      <TimeSelector data={providerTime} onChange={setProviderTime} />
      {/* Table displaying consumption provider data */}
      <ConsumptionProviderTable data={CProviderInformation} loading={CProviderLoading} error={CProviderError} />
      {/* Selector for choosing the time period for city data */}
      <TimeSelector data={cityTime} onChange={setCityTime} />
      {/* Table displaying city consumption data */}
      <ConsumptionCityTable data={CCityInformation} loading={CCityLoading} error={CCityError} />
    </DashboardPage>
  );
};

export default View;