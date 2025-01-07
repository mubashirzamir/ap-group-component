import { Table } from "antd";
import DataWrapper from "@/components/DataWrapper/DataWrapper.jsx";
import { useEffect, useState } from "react";
import TimeRangeFilter from "@/pages/Dashboard/Cities/Newcastle/View/TimeRangeFilter.jsx";
import { REFRESH_INTERVAl_OVERVIEW } from "@/helpers/constants.jsx";
import NewcastleService from "@/services/NewcastleService.jsx";
import SunderlandService from "@/services/SunderlandService.jsx";
import ServiceStatusPopover from "@/pages/Dashboard/Overview/ServiceStatusPopover.jsx";
import { toDecimalPlaces } from "@/helpers/utils.jsx";

const tableProps = {
  scroll: { x: "max-content" },
  pagination: false,
};

const columns = [
  {
    title: "Provider ID",
    dataIndex: "providerId",
    key: "providerId",
  },
  {
    title: "Total Consumption (Wh)",
    dataIndex: "totalConsumption",
    key: "totalConsumption",
  },
  {
    title: "Average Consumption (Wh)",
    dataIndex: "averageConsumption",
    key: "averageConsumption",
  },
];

const ByProvider = () => {
  const [loading, setLoading] = useState(false);
  const [data, setData] = useState([]);
  const [alertMessages, setAlertMessages] = useState([]);
  const [timeRange, setTimeRange] = useState("LAST_30_DAYS");

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      let newcastleData = [];
      let sunderlandData = [];
      let messages = [];

      try {
        newcastleData = await NewcastleService.dataByProviderForOverview({
          timeRange,
        });
        messages.push("Newcastle: ✅");
      } catch (error) {
        messages.push("Newcastle: ❌");
      }

      try {
        sunderlandData = await SunderlandService.getAggregatedDataByProvider({
          timeRange,
        });
        messages.push("Sunderland: ✅");
      } catch (error) {
        messages.push("Sunderland: ❌");
      }

      setAlertMessages(messages);
      setData(mergeData(newcastleData, sunderlandData));
      setLoading(false);
    };

    fetchData();
    const intervalId = setInterval(() => {
      fetchData();
    }, REFRESH_INTERVAl_OVERVIEW);

    return () => clearInterval(intervalId);
  }, [timeRange]);

  return (
    <div>
      <div className="flex justify-end p-4 space-x-4">
        <TimeRangeFilter
          timeRange={timeRange}
          setTimeRange={setTimeRange}
          loading={loading}
        />
        <ServiceStatusPopover alertMessages={alertMessages} loading={loading} />
      </div>

      <DataWrapper
        data={data}
        loading={loading}
        errored={false}
        strategy="spin"
      >
        <Table
          {...tableProps}
          dataSource={data}
          columns={columns}
          rowKey="providerId"
          loading={loading}
        />
      </DataWrapper>
    </div>
  );
};

const mergeData = (newcastle, sunderland) => {
  return newcastle.map((n) => {
    const s = sunderland.find(
      (s) => s.providerId.toLowerCase() === n.providerId.toLowerCase(),
    );

    return {
      providerId: n.providerId,
      totalConsumption: toDecimalPlaces(
        n.totalConsumption + (s?.totalConsumption || 0),
        2,
      ),
      averageConsumption: toDecimalPlaces(
        n.averageConsumption + (s?.averageConsumption || 0),
        2,
      ),
    };
  });
};

export default ByProvider;
