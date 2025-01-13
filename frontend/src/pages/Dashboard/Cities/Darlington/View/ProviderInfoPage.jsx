import { Table } from "antd";
import DataWrapper from "@/components/DataWrapper/DataWrapper.jsx";

// Table properties
const tableProps = {
  scroll: { x: "max-content" },
  pagination: { pageSize: 5 },
};

/**
 * Columns for the Provider information
 */
const providerColumn = [
  {
    title: "Company Name",
    dataIndex: "companyName",
    key: "companyName",
  },
  {
    title: "Company ID",
    dataIndex: "id",
    key: "id",
  },
  {
    title: "Company Address",
    dataIndex: "companyAddress",
    key: "companyAddress",
  },
  {
    title: "Company Email",
    dataIndex: "companyEmail",
    key: "companyEmail",
  },
  {
    title: "Company Phone Number",
    dataIndex: "companyPhoneNumber",
    key: "companyPhoneNumber",
  },
];

/**
 * Component to display a table of provider information.
 *
 * @param {Object} props - The component props.
 * @param {Array} props.data - The data to display in the table.
 * @param {boolean} props.loading - The loading state of the data.
 * @param {boolean} props.errored - The error state of the data.
 * @returns {JSX.Element} The rendered table component.
 */
const ProviderInfoPage = ({ data, loading, errored }) => {
  // Ensure `data` is an array for the Table component
  const dataSource = Array.isArray(data) ? data : [data];

  return (
    <DataWrapper data={data} loading={loading} errored={errored} strategy="spin">
      <Table
        {...tableProps}
        dataSource={dataSource}
        columns={providerColumn}
        rowKey="id" // Unique identifier for rows
        loading={loading && !data}
        pagination={false} // No pagination needed for single record
        title={() => (
          <div
            style={{
              fontWeight: "bold",
              fontSize: "20px",
              textAlign: "left",
              color: "#4A90E2",
            }}
          >
            Provider Information
          </div>
        )}
      />
    </DataWrapper>
  );
};

export default ProviderInfoPage;