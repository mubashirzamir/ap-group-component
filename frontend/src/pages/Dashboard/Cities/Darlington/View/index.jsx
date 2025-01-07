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

const columns = [
  {
    title: "ID",
    dataIndex: "id",
    key: "id",
  },
  {
    title: "Title",
    dataIndex: "title",
    key: "title",
  },
  {
    title: "Body",
    dataIndex: "body",
    key: "body",
  },
];

const View = () => {
  const [loading, setLoading] = useState(false);
  const [posts, setPosts] = useState([]);

  const fetchPosts = () => {
    setLoading(true);
    request
      .get("https://jsonplaceholder.typicode.com/posts")
      .then((response) => setPosts(response.data))
      .catch(genericNetworkError)
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    fetchPosts();
  }, []);
  const [providerTime, setProviderTime] = useState("LAST_30_DAYS");
  const [cityTime, setcityTime] = useState("LAST_30_DAYS");
  const [ProviderChoice,setProviderChoice] = useState("All");


  const {providerData, providerLoading, providerError} = ProviderInfo();
  const {EPCPInformation, EPCPLoading, EPCPError} = ElectricalProviderConsumptionSummary(ProviderChoice);
  const {CProviderInformation, CProviderLoading, CProviderErrored}=ConsumptionProviderSummary(providerTime);
  console.log('CProviderInformation',CProviderInformation)
  return (
    <DashboardPage breadcrumbs={[{ title: "Darlington" }, { title: "View" }]}>
    <ProviderInfoPage data={providerData} loading={providerLoading} errored={providerError} />
    <ProviderSelector selectedProvider={ProviderChoice} data={providerData} onChange={setProviderChoice} />
    {/* Represents ElectricalProviderConsumptionSummaryTable*/}
    <ElectricalProviderConsumptionSummaryTable providerData={providerData} data={EPCPInformation} loading={EPCPLoading} errored={EPCPError} />
    <TimeSelector data={providerTime} onChange={setProviderTime} />
    <ConsumptionProviderTable data={CProviderInformation} loading={CProviderLoading} error={CProviderErrored} />
    <TimeSelector data={cityTime} onChange={setcityTime} />
      <Table
        dataSource={posts}
        columns={columns}
        rowKey="id"
        loading={loading}
        pagination={{ pageSize: 10 }}
      />
    </DashboardPage>
  );
};

export default View;
