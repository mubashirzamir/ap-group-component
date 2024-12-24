import DashboardPage from "@/components/DashboardPage/index.jsx";
import { Result } from "antd";

const NotFound = () => {
  return (
    <DashboardPage breadcrumbs={[{ title: "" }]}>
      <Result
        status="404"
        title="404"
        subTitle="Sorry, the page you visited does not exist."
      />
    </DashboardPage>
  );
};

export default NotFound;
