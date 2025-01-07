import { Select } from "antd";

const { Option } = Select;

const ProviderSelector = ({ selectedProvider, data, onChange }) => (
  <div style={{ marginTop:20, marginBottom: 10, display: "flex", justifyContent: "flex-end" }}>
    <Select
      value={selectedProvider}
      onChange={onChange}
      style={{ width: 200 }}
    >
      {/* Default Option for "All" */}
      <Option key="All" value="All">
        All
      </Option>
      {/* Dynamic Options from providerData */}
      {data?.map((data) => (
        <Option key={data.id} value={data.id}>
          {data.companyName}
        </Option>
      ))}
    </Select>
  </div>
);

export default ProviderSelector;
