package airportinfo;

import java.util.List;

public class AirportStatusResult {
	public final List<Airport> airports;
	public final List<String> airportCodesWithError;

  public AirportStatusResult(
    List<Airport> theAirports, List<String> theAirportCodesErrors) {
    airports = theAirports;
    airportCodesWithError = theAirportCodesErrors;
  }
}