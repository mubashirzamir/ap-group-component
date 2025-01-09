import DashboardPage from "@/components/DashboardPage/index.jsx";
import ByProvider from "@/pages/Dashboard/Overview/View/ByProvider.jsx";

const View = () => {
  return (
    <DashboardPage breadcrumbs={[{ title: "Overview" }, { title: "View" }]}>
      <ByProvider />
    </DashboardPage>
  );
};

export default View;
