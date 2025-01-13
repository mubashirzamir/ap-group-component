import { Alert, Skeleton, Spin } from "antd";
import PropTypes from "prop-types";

const spinStrategy = "spin";
const skeletonStrategy = "skeleton";

const DataWrapper = ({
  loading,
  errored,
  data,
  children,
  strategy = skeletonStrategy,
}) => {

  if (errored) {
    return (
      <Alert
        description="Could not fetch data. Please try again later."
        type="error"
        showIcon
      />
    );
  }

  if (strategy === spinStrategy) {
    return <Spin spinning={loading}>{data ? children : null}</Spin>;
  }

  return <Skeleton loading={loading}>{data ? children : null}</Skeleton>;
};

DataWrapper.propTypes = {
  loading: PropTypes.bool.isRequired,
  errored: PropTypes.bool,
  data: PropTypes.any,
  children: PropTypes.node.isRequired,
  strategy: PropTypes.oneOf([spinStrategy, skeletonStrategy]),
};

export default DataWrapper;
