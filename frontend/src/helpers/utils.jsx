import { notification } from "antd";

const notificationProps = {
  placement: "bottomRight",
  duration: 3,
};

export const validationError = (error, form) => {
  const errors = error?.response?.data?.errors;
  if (errors) {
    form.setFields(
      Object.entries(errors).map(([key, value]) => ({
        name: key,
        errors: [value],
      })),
    );
  } else {
    throw error;
  }
};

export const genericNetworkError = (error) => {
  console.error(error);

  return notification.error({
    ...notificationProps,
    message: getErrorMessage(error),
  });
};

const getErrorMessage = (error) => {
  if (error?.response?.data?.message) {
    return error.response.data.message;
  }

  switch (error?.response?.status) {
    case 400:
      return "400: Bad Request";
    case 401:
      return "401: Unauthorized";
    case 403:
      return "403: Forbidden";
    case 404:
      return "404: Not Found";
    case 405:
      return "405: Method Not Allowed";
    case 406:
      return "406: Not Acceptable";
    case 408:
      return "408: Request Timeout";
    case 409:
      return "409: Conflict";
    case 422:
      return "422: Unprocessable Entity";
    case 500:
      return "500: Internal Server Error";
    default:
      return "Something went wrong";
  }
};

export const filtersToQueryParams = (filters) => {
  try {
    if (Object.keys(filters).length === 0) {
      return "";
    }

    return "?" + new URLSearchParams(filters).toString();
  } catch (error) {
    console.error(error);
  }
};

export const renderDateTime = (value) => {
  try {
    return new Date(value).toLocaleString();
  } catch (error) {
    return "Invalid date";
  }
};

export const generateColor = (initial) => {
  let letter = initial.toUpperCase();

  if (!/[A-Z]/.test(letter)) {
    return "#000000";
  }

  const charCode = letter.charCodeAt(0) - 65;
  const hue = (charCode * (360 / 26)) % 360;
  return `hsl(${hue}, 50%, 50%)`;
};

export const randomColor = () => {
  return `hsl(${Math.random() * 360}, ${Math.random() * 100}%, ${Math.random() * 50 + 25}%)`;
};
