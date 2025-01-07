import { Select } from "antd";
import PropTypes from "prop-types";

const timeRangeOptions = [
  { value: "LAST_24_HOURS", label: "Last 24 Hours" },
  { value: "LAST_7_DAYS", label: "Last 7 Days" },
  { value: "LAST_30_DAYS", label: "Last 30 Days" },
];

const timeRangeFilterStyle = {
  width: 200,
};

const TimeRangeFilter = ({ timeRange, setTimeRange, loading }) => {
  return (
    <Select
      disabled={loading}
      loading={loading}
      style={timeRangeFilterStyle}
      defaultValue={timeRange}
      options={timeRangeOptions}
      onChange={(value) => setTimeRange(value)}
    />
  );
};

TimeRangeFilter.propTypes = {
  timeRange: PropTypes.string.isRequired,
  setTimeRange: PropTypes.func.isRequired,
  loading: PropTypes.bool.isRequired,
};

export default TimeRangeFilter;
