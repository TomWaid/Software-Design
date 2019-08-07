package airportinfo;

import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class AirportStatus{
  private AirportService service;

  public void setAirportService(AirportService airportService) {
  	service = airportService;
  }

  public List<Airport> sortAirports(List<Airport> airports) {
  	return airports.stream()
			.sorted(comparing(Airport::getName))
			.collect(toList());
  }

  public AirportStatusResult getAirportsStatus(List<String> airportCodes){
  	List<Airport> airports = new ArrayList<>();
  	List<String> airportCodeErrors = new ArrayList<>();

  	for (String code : airportCodes) {
  		try {
  			airports.add(service.fetchData(code));
  		} catch (RuntimeException ex) {
  			airportCodeErrors.add(code);
  		}
  	}

  	return new AirportStatusResult(sortAirports(airports), airportCodeErrors); 
	}
}