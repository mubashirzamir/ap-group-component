import DashboardPage from "@/components/DashboardPage/index.jsx";
import { Table } from "antd";
import { useEffect, useState } from "react";
import request from "@/request.js";
import { genericNetworkError } from "@/helpers/utils.jsx";
import ProviderInfo from "@/pages/Dashboard/Cities/Darlington/View/ProviderInfo.jsx";
import ProviderSelector from "@/pages/Dashboard/Cities/Darlington/View/ProviderSelector.jsx";

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
  const {providerData, providerLoading, providerErrored} = ProviderInfo();
  console.log('providerData', providerData);
  return (
    <DashboardPage breadcrumbs={[{ title: "Darlington" }, { title: "View" }]}>
    <ProviderInfoPage data={providerData} loading={providerLoading} errored={providerErrored} />
    <ProviderSelector selectedProvider={ProviderChoice} data={providerData} onChange={setProviderChoice} />
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
