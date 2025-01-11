import { render, screen, waitFor } from "@testing-library/react";
import { describe, expect, it, vi } from "vitest";
import All from "@/pages/Dashboard/Cities/Newcastle/View/All.jsx";
import "@/helpers/matchMedia";

// Mock the NewcastleService module
vi.mock("@/services/NewcastleService", () => ({
  default: {
    cityConsumptions: () =>
      Promise.resolve([
        {
          id: 303,
          providerId: "Provider A",
          averageConsumption: 50,
          totalConsumption: 100,
          consumptionPeriodStart: "2021-01-01T00:00:00Z",
          consumptionPeriodEnd: "2021-01-02T00:00:00Z",
        },
      ]),
  },
}));

describe("All Component", () => {
  it("displays data after fetching", async () => {
    render(<All />);

    await waitFor(() => {
      // Get the elements
      const providerElement = screen.getByText("Provider A");
      const averageConsumptionElement = screen.getByText("50");
      const totalConsumptionElement = screen.getByText("100");

      // Check if the elements exist
      expect(providerElement).toBeTruthy();
      expect(averageConsumptionElement).toBeTruthy();
      expect(totalConsumptionElement).toBeTruthy();
    });
  });
});
