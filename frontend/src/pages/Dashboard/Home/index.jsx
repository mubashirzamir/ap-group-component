import DashboardPage from "@/components/DashboardPage/index.jsx";
import { useEffect } from "react";
import AuthService from "@/services/AuthService.jsx";
import Northumbria from "@/assets/northumbria.png";
import { genericNetworkError } from "@/helpers/utils.jsx";

const imgStyle = {
  maxWidth: "50%",
};

const Home = () => {
  useEffect(() => {
    AuthService.test().catch(genericNetworkError);
  }, []);

  return (
    <DashboardPage breadcrumbs={[{ title: "" }]}>
      <div className="flex justify-center items-center h-screen">
        <img src={Northumbria} alt="northumbria" style={imgStyle} />
      </div>
    </DashboardPage>
  );
};

export default Home;
