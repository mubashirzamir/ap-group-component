import DashboardPage from "@/components/DashboardPage/index.jsx";
import { useEffect } from "react";
import AuthService from "@/services/AuthService.jsx";

const Home = () => {
  useEffect(() => {
    AuthService.test()
      .then((response) => console.log(response))
      .catch((error) => console.log(error));
  }, []);

  return <DashboardPage breadcrumbs={[{ title: "Home" }]}>Home</DashboardPage>;
};

export default Home;
