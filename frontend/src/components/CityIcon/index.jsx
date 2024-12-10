import Newcastle from "@/assets/city-icons/newcastle.svg";
import Sunderland from "@/assets/city-icons/sunderland.svg";
import Durham from "@/assets/city-icons/durham.svg";
import Darlington from "@/assets/city-icons/darlington.svg";
import Middlesbrough from "@/assets/city-icons/middlesbrough.svg";

const imgStyle = {
  maxWidth: "30px",
  minWidth: "30px",
  objectFit: "contain",
};

const CityIcon = ({ city }) => {
  const getIcon = (city) => {
    switch (city) {
      case "darlington":
        return Darlington;
      case "durham":
        return Durham;
      case "middlessbrough":
        return Middlesbrough;
      case "newcastle":
        return Newcastle;
      case "sunderland":
        return Sunderland;
      default:
        return Darlington;
    }
  };

  return (
    <div className="pr-2">
      <img src={getIcon(city)} alt={city} style={imgStyle} />
    </div>
  );
};

export default CityIcon;
