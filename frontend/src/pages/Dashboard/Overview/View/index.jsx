import { useState } from "react";
import DashboardPage from "@/components/DashboardPage/index.jsx";
import TimeRangeSelector from "@/pages/Dashboard/Overview/View/TimeRangeSelector.jsx";
import CityStatusStatCardSunderland from "@/pages/Dashboard/Overview/View/CityStatusStatCardSunderland.jsx";
import CityStatusStatCardNewcastle from "@/pages/Dashboard/Overview/View/CityStatusStatCardNewcastle.jsx";
import AggregatedDataSunderland from "@/pages/Dashboard/Overview/View/AggregatedDataSunderland.jsx";
import AggregatedDataNewcastle from "@/pages/Dashboard/Overview/View/AggregatedDataNewcastle.jsx";
import { CityHeading } from "@/helpers/utils.jsx";

const View = () => {
  const [selectedTimeRangeSunderland, setSelectedTimeRangeSunderland] =
    useState("LAST_24_HOURS");
  const [selectedTimeRangeNewcastle, setSelectedTimeRangeNewcastle] =
    useState("LAST_24_HOURS");

  // Using the custom hook for data fetching
  const {
    cityData: sunderlandData,
    cityLoading: sunderlandLoading,
    cityErrored: sunderlandErrored,
  } = AggregatedDataSunderland(selectedTimeRangeSunderland);

  const {
    data: newcastleData,
    loading: newcastleLoading,
    errored: newcastleErrored,
  } = AggregatedDataNewcastle(selectedTimeRangeNewcastle);

  return (
    <DashboardPage
      breadcrumbs={[{ title: "Cities DataView" }, { title: "View" }]}
    >
      <CityHeading name="Sunderland" />
      <TimeRangeSelector
        selectedTimeRange={selectedTimeRangeSunderland}
        onChange={setSelectedTimeRangeSunderland}
      />
      <CityStatusStatCardSunderland
        data={sunderlandData}
        loading={sunderlandLoading}
        errored={sunderlandErrored}
      />

      <CityHeading name="Newcastle" />
      <TimeRangeSelector
        selectedTimeRange={selectedTimeRangeNewcastle}
        onChange={setSelectedTimeRangeNewcastle}
      />
      <CityStatusStatCardNewcastle
        data={newcastleData}
        loading={newcastleLoading}
        errored={newcastleErrored}
      />
    </DashboardPage>
  );
};

export default View;
