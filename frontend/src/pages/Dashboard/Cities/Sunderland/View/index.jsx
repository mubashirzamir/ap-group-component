import { useState } from "react";
import DashboardPage from "@/components/DashboardPage/index.jsx";
import TimeRangeSelector from "@/pages/Dashboard/Cities/Sunderland/View/TimeRangeSelector.jsx";
import ProvidersTable from "@/pages/Dashboard/Cities/Sunderland/View/ProvidersTable.jsx";
import CityStatusTable from "@/pages/Dashboard/Cities/Sunderland/View/CityStatusTable.jsx";
import AggregatedDataProvider from "@/pages/Dashboard/Cities/Sunderland/View/AggregatedDataProvider.jsx"; // If using the custom hook
import AggregatedDataCity from "@/pages/Dashboard/Cities/Sunderland/View/AggregatedDataCity.jsx";

const View = () => {
  const [selectedTimeRangeProvider, setSelectedTimeRangeProvider] = useState("LAST_30_DAYS");
  const [selectedTimeRangeCity, setSelectedTimeRangeCity] = useState("LAST_30_DAYS");

  // Using the custom hook for data fetching
  const { cityData, cityLoading, cityErrored } = AggregatedDataCity(selectedTimeRangeCity);
  const { providerData, providerLoading, providerErrored } = AggregatedDataProvider(selectedTimeRangeProvider);
  return (
    <DashboardPage breadcrumbs={[{ title: "DataView Providers" }, { title: "View" }]}>
      <TimeRangeSelector selectedTimeRange={selectedTimeRangeProvider} onChange={setSelectedTimeRangeProvider} />
      <ProvidersTable data={providerData} loading={providerLoading} errored={providerErrored} />
      <TimeRangeSelector selectedTimeRange={selectedTimeRangeCity} onChange={setSelectedTimeRangeCity} />
      <CityStatusTable data={cityData} loading={cityLoading} errored={cityErrored} />
    </DashboardPage>
  );
};

export default View;
