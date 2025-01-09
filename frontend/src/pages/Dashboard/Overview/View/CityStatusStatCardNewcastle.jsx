import DataWrapper from "@/components/DataWrapper/DataWrapper.jsx";
import { Card } from "antd";
import { toDecimalPlaces } from "@/helpers/utils.jsx";

const { Meta } = Card;

const CityStatusStatCardNewcastle = ({ data, loading, errored }) => (
  <DataWrapper data={data} loading={loading} errored={errored} strategy="spin">
    <div className="grid grid-cols-2 gap-4">
      <Card>
        <Meta
          title={toDecimalPlaces(data?.totalConsumption, 0)}
          description="Total Consumption (Wh)"
        />
      </Card>
      <Card>
        <Meta
          title={toDecimalPlaces(data?.averageConsumption, 0)}
          description="Average Consumption (Wh)"
        />
      </Card>
    </div>
  </DataWrapper>
);

export default CityStatusStatCardNewcastle;
