import { Route, Routes as RouterRoutes } from "react-router-dom";
import NotFound from "@/pages/Errors/NotFound/NotFound.jsx";
import DarlingtonView from "@/pages/Dashboard/Cities/Darlington/View/index.jsx";
import DarlingtonVisualization from "@/pages/Dashboard/Cities/Darlington/Visualization/index.jsx";
import DurhamView from "@/pages/Dashboard/Cities/Durham/View/index.jsx";
import DurhamVisualization from "@/pages/Dashboard/Cities/Durham/Visualization/index.jsx";
import MiddlesbroughView from "@/pages/Dashboard/Cities/Middlesbrough/View/index.jsx";
import MiddlesbroughVisualization from "@/pages/Dashboard/Cities/Middlesbrough/Visualization/index.jsx";
import SunderlandView from "@/pages/Dashboard/Cities/Sunderland/View/index.jsx";
import SunderlandVisualization from "@/pages/Dashboard/Cities/Sunderland/Visualization/index.jsx";
import NewcastleView from "@/pages/Dashboard/Cities/Newcastle/View/index.jsx";
import NewcastleVisualization from "@/pages/Dashboard/Cities/Newcastle/Visualization/index.jsx";
import ProtectedRoutes from "@/helpers/Auth/ProtectedRoutes.jsx";
import Login from "@/pages/Login/index.jsx";
import Dashboard from "@/pages/Dashboard/index.jsx";
import Home from "@/pages/Dashboard/Home/index.jsx";
import AuthRoutes from "@/helpers/Auth/AuthRoutes.jsx";
import Register from "@/pages/Register/index.jsx";

const AppRoutes = () => {
  return (
    <RouterRoutes>
      <Route element={<AuthRoutes />}>
        <Route path="/login" element={<Login />} />;
        <Route path="/register" element={<Register />} />;
      </Route>
      <Route element={<ProtectedRoutes />}>
        <Route path="/" element={<Dashboard />}>
          {DashboardRoutes()}
        </Route>
        <Route path="/*" element={<NotFound />} />
      </Route>
    </RouterRoutes>
  );
};

const DashboardRoutes = () => {
  return (
    <>
      <Route path="/" element={<Home />} />

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
    </>
  );
};

export default AppRoutes;
