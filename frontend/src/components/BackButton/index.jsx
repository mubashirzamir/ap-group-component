import { Button } from "antd";
import { useNavigate } from "react-router-dom";

const BackButton = (props) => {
  const navigate = useNavigate();
  const onClick = () => {
    navigate(-1);
  };

  return (
    <Button block type="link" onClick={onClick} {...props}>
      {props?.title || "Back"}
    </Button>
  );
};

export default BackButton;
