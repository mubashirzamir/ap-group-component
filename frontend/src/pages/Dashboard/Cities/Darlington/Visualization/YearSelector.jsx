import React from "react";
import { Select } from "antd";

const { Option } = Select;

export const YEARS = [
    { label: "2023", value: 2023 },
    { label: "2024", value: 2024 },
    { label: "2025", value: 2025 },
];

const YearSelector = ({ selectedYear, onChange }) => (
  <div style={{  marginTop: 20, marginBottom: 20, display: "flex", justifyContent: "flex-end" }}>
    <Select
      value={selectedYear}
      onChange={onChange}
      style={{ width: 200 }}
    >
      {YEARS.map((year) => (
        <Option key={year.value} value={year.value}>
          {year.label}
        </Option>
      ))}
    </Select>
  </div>
);

export default YearSelector;
