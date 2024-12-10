import { Route, Routes as RouterRoutes } from "react-router-dom";
import NotFound from "@/pages/Errors/NotFound/NotFound.jsx";
import View from "@/pages/Newcastle/View/index.jsx";
import Visualization from "@/pages/Newcastle/Visualization/index.jsx";

const DashboardRoutes = () => {
  return (
    <RouterRoutes>
      <Route path="newcastle/view" element={<View />} />
      <Route path="newcastle/visualization" element={<Visualization />} />
      <Route path="/*" element={<NotFound />} />
    </RouterRoutes>
  );
};

export default DashboardRoutes;
