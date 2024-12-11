import React from "react";
import {
  AreaChartOutlined,
  HomeOutlined,
  UnorderedListOutlined,
} from "@ant-design/icons";
import { Link } from "react-router-dom";
import CityIcon from "@/components/CityIcon/index.jsx";

const cities = [
  "Darlington",
  "Durham",
  "Middlesbrough",
  "Newcastle",
  "Sunderland",
];

const makeItem = (key, label, icon, children) => ({
  key,
  icon,
  children,
  label,
});

export const makeItems = () => {
  const items = [
    makeItem(
      "/",
      <Link key="/" to="/">
        Home
      </Link>,
      <HomeOutlined />,
    ),
  ];

  cities.forEach((city) => {
    items.push(
      makeItem(
        city.toLowerCase(),
        city,
        <CityIcon city={city.toLowerCase()} />,
        [
          makeItem(
            `/${city.toLowerCase()}/view`,
            <Link
              key={`/${city.toLowerCase()}/view`}
              to={`/${city.toLowerCase()}/view`}
            >
              View
            </Link>,
            <UnorderedListOutlined />,
          ),
          makeItem(
            `/${city.toLowerCase()}/visualization`,
            <Link
              key={`/${city.toLowerCase()}/visualization`}
              to={`/${city.toLowerCase()}/visualization`}
            >
              Visualization
            </Link>,
            <AreaChartOutlined />,
          ),
        ],
      ),
    );
  });

  return items;
};

export const getDefaultOpenKeys = (pathname) => {
  const city = pathname.split("/")[1];
  return city ? [city] : [];
}
