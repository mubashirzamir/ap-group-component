import { Card, Popover } from "antd";
import { AlertTwoTone } from "@ant-design/icons";
import PropTypes from "prop-types";

const ServiceStatusPopover = ({ alertMessages, loading }) => {
  return (
    <Popover
      placement="top"
      content={
        <Card
          className="min-w-64"
          title={<div className="font-extrabold">Service Statuses</div>}
          loading={loading}
        >
          <ul>
            {alertMessages.map((msg, index) => (
              <li className="font-bold" key={index}>
                {msg}
              </li>
            ))}
          </ul>
        </Card>
      }
    >
      <AlertTwoTone />
    </Popover>
  );
};

ServiceStatusPopover.propTypes = {
  alertMessages: PropTypes.array.isRequired,
  loading: PropTypes.bool,
};

export default ServiceStatusPopover;
