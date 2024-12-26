import DashboardPage from "@/components/DashboardPage/index.jsx";
import All from "@/pages/Dashboard/Cities/Newcastle/View/All.jsx";
import ByProvider from "@/pages/Dashboard/Cities/Newcastle/View/ByProvider.jsx";
import ForCity from "@/pages/Dashboard/Cities/Newcastle/View/ForCity.jsx";

const View = () => {
  return (
    <DashboardPage breadcrumbs={[{ title: "Newcastle" }, { title: "View" }]}>
      <SectionHeader title="All" />
      <All />
      <SectionHeader title="By Provider" />
      <ByProvider />
      <SectionHeader title="For City" />
      <ForCity />
    </DashboardPage>
  );
};

const SectionHeader = ({ title }) => {
  return <h2 className="text-xl font-semibold mb-4 mt-8">{title}</h2>;
};

export default View;
