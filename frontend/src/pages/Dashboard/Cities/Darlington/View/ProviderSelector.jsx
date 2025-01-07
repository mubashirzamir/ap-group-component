import { Select } from "antd";

const { Option } = Select;

/**
 * Component to select a provider from a dropdown list.
 *
 * @param {Object} props - The component props.
 * @param {string} props.selectedProvider - The currently selected provider.
 * @param {Array} props.data - The list of provider data to populate the dropdown.
 * @param {Function} props.onChange - The function to call when the selected provider changes.
 * @returns {JSX.Element} The rendered provider selector component.
 */
const ProviderSelector = ({ selectedProvider, data, onChange }) => (
  <div style={{ marginTop: 20, marginBottom: 10, display: "flex", justifyContent: "flex-end" }}>
    <Select
      value={selectedProvider}
      onChange={onChange}
      style={{ width: 200 }}
    >
      {/* Default Option for "All" */}
      <Option key="All" value="All">
        All
      </Option>
      {data?.map((data) => (
        <Option key={data.id} value={data.id}>
          {data.companyName}
        </Option>
      ))}
    </Select>
  </div>
);

export default ProviderSelector;