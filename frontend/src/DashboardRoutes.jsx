import { Route, Routes as RouterRoutes } from "react-router-dom";
import NotFound from "@/pages/Errors/NotFound/NotFound.jsx";
import DarlingtonView from "@/pages/Cities/Darlington/View/index.jsx";
import DarlingtonVisualization from "@/pages/Cities/Darlington/Visualization/index.jsx";
import DurhamView from "@/pages/Cities/Durham/View/index.jsx";
import DurhamVisualization from "@/pages/Cities/Durham/Visualization/index.jsx";
import MiddlesbroughView from "@/pages/Cities/Middlesbrough/View/index.jsx";
import MiddlesbroughVisualization from "@/pages/Cities/Middlesbrough/Visualization/index.jsx";
import SunderlandView from "@/pages/Cities/Sunderland/View/index.jsx";
import SunderlandVisualization from "@/pages/Cities/Sunderland/Visualization/index.jsx";
import NewcastleView from "@/pages/Cities/Newcastle/View/index.jsx";
import NewcastleVisualization from "@/pages/Cities/Newcastle/Visualization/index.jsx";
import Home from "@/pages/Home/index.jsx";

const DashboardRoutes = () => {
  return (
    <RouterRoutes>
      <Route path={"/"} element={<Home />} />

      <Route path="darlington/view" element={<DarlingtonView />} />
      <Route
        path="darlington/visualization"
        element={<DarlingtonVisualization />}
      />

      <Route path="durham/view" element={<DurhamView />} />
      <Route path="durham/visualization" element={<DurhamVisualization />} />

      <Route path="middlesbrough/view" element={<MiddlesbroughView />} />
      <Route
        path="middlesbrough/visualization"
        element={<MiddlesbroughVisualization />}
      />

      <Route path="newcastle/view" element={<NewcastleView />} />
      <Route
        path="newcastle/visualization"
        element={<NewcastleVisualization />}
      />

      <Route path="sunderland/view" element={<SunderlandView />} />
      <Route
        path="sunderland/visualization"
        element={<SunderlandVisualization />}
      />

      <Route path="/*" element={<NotFound />} />
    </RouterRoutes>
  );
};

export default DashboardRoutes;
