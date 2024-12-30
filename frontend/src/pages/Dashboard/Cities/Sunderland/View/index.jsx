import { useState } from "react";
import DashboardPage from "@/components/DashboardPage/index.jsx";
import TimeRangeSelector from "@/pages/Dashboard/Cities/Sunderland/View/TimeRangeSelector.jsx";
import ProvidersTable from "@/pages/Dashboard/Cities/Sunderland/View/ProvidersTable.jsx";
import CityStatusTable from "@/pages/Dashboard/Cities/Sunderland/View/CityStatusTable.jsx";
import useAggregatedData from "@/pages/Dashboard/Cities/Sunderland/View/useAggregatedData.jsx"; // If using the custom hook

const View = () => {
  const [selectedTimeRange, setSelectedTimeRange] = useState("LAST_30_DAYS");

  // Using the custom hook for data fetching
  const { providerData, providerLoading, providerErrored, cityData, cityLoading, cityErrored } = useAggregatedData(selectedTimeRange);

  return (
    <DashboardPage breadcrumbs={[{ title: "DataView Providers" }, { title: "View" }]}>
      <TimeRangeSelector selectedTimeRange={selectedTimeRange} onChange={setSelectedTimeRange} />
      <ProvidersTable data={providerData} loading={providerLoading} errored={providerErrored} />
      <CityStatusTable data={cityData} loading={cityLoading} errored={cityErrored} />
    </DashboardPage>
  );
};

export default View;
