import React from "react";
import { Alert, Skeleton } from "antd";

const DataWrapper = ({ loading, errored, data, children }) => {
  if (errored) {
    return (
      <Alert
        description="Could not fetch data. Please try again later."
        type="error"
        showIcon
      />
    );
  }

  return <Skeleton loading={loading}>{data ? children : null}</Skeleton>;
};

export default DataWrapper;
