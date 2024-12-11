import Durham from "@/assets/city-icons/durham.svg";
import Darlington from "@/assets/city-icons/darlington.svg";
import Middlesbrough from "@/assets/city-icons/middlesbrough.svg";
import Newcastle from "@/assets/city-icons/newcastle.svg";
import Sunderland from "@/assets/city-icons/sunderland.svg";

const imgStyle = {
  maxWidth: "30px",
  minWidth: "30px",
  objectFit: "contain",
};

/**
 * Displays an icon for the specified city.
 *
 * @param {Object} props
 * @param {'darlington' | 'durham' | 'middlesbrough' | 'newcastle' | 'sunderland'} props.city - The city name.
 */
const CityIcon = ({ city }) => {
  const getIcon = (city) => {
    switch (city) {
      case "darlington":
        return Darlington;
      case "durham":
        return Durham;
      case "middlesbrough":
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
    <div className="relative -translate-x-[7px]">
      <img src={getIcon(city)} alt={city} style={imgStyle} />
    </div>
  );
};

export default CityIcon;
