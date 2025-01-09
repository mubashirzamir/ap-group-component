import { Select } from "antd";

const { Option } = Select;

// Define available time ranges
const TIME_RANGES = [
  { label: "Last 24 Hours", value: "LAST_24_HOURS" },
  { label: "Last 7 Days", value: "LAST_7_DAYS" },
  { label: "Last 30 Days", value: "LAST_30_DAYS" },
];

/**
 * Used to select a time range from a dropdown list.
 *
 * @param {Object} props - The component props.
 * @param {string} props.data - The currently selected time range.
 * @param {Function} props.onChange - The function to call when the selected time range changes.
 * @returns {JSX.Element} The rendered time selector component.
 */
const TimeSelector = ({ data, onChange }) => (
  <div style={{ marginTop:20, marginBottom: 20, display: "flex", justifyContent: "flex-end" }}>
    <Select
      value={data}
      onChange={onChange}
      style={{ width: 200 }}
    >
      {TIME_RANGES.map((range) => (
        <Option key={range.value} value={range.value}>
          {range.label}
        </Option>
      ))}
    </Select>
  </div>
);

export default TimeSelector;