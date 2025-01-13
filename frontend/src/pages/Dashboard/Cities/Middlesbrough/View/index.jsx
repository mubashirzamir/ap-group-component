import DashboardPage from "@/components/DashboardPage/index.jsx";
import { Table } from "antd";
import { useEffect, useState } from "react";
import request from "@/request.js";
import { genericNetworkError } from "@/helpers/utils.jsx";
import MiddleData from "./Test";
// import { MongoClient } from "mongo";







const columns = [
  {
    "title": "ID",
    "dataIndex": "ID",
    "key": "ID"
  },
  {
    "title": "ProviderID",
    "dataIndex": "ProviderID",
    "key": "ProviderID"
  },
  {
    "title": "AverageConsumption",
    "dataIndex": "AverageConsumption",
    "key": "AverageConsumption"
  },
  {
    "title": "TotalConsumption",
    "dataIndex": "TotalConsumption",
    "key": "TotalConsumption"
  },
  {
    "title": "ConsumptionPeriodStart",
    "dataIndex": "ConsumptionPeriodStart",
    "key": "ConsumptionPeriodStart"
  },
  {
    "title": "ConsumptionPeriodEnd",
    "dataIndex": "ConsumptionPeriodEnd",
    "key": "ConsumptionPeriodEnd"
  }
]

const View = () => {
  const [loading, setLoading] = useState(true);
  const [posts, setPosts] = useState([]);


  const fetchPosts = () => {
    setLoading(true);
    request
      .get("https://jsonplaceholder.typicode.com/posts")
      .then((response) => setPosts(MiddleData()))
      .catch(genericNetworkError)
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    fetchPosts();
  }, []);

  return (
    <DashboardPage breadcrumbs={[{ title: "Middlesbrough" }, { title: "View" }]}>
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
