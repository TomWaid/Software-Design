package airportinfo.driver;

import airportinfo.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AirportStatusDriver {
	
  public static void main(String[]args) {
  	AirportStatus airportStatus = new AirportStatus();
    AirportService infoService = new FAAAirportInfoService();
  	List<String> airportCodes = new ArrayList<>();
  	try {
			airportCodes = getCodes(args[0]);
		} catch (IOException e) {
			System.out.println("ERROR: Please provide an appropriate input file.");
		}
    airportStatus.setAirportService(infoService);
    AirportStatusResult airportStatusResults = airportStatus.getAirportsStatus(airportCodes);
    printAirportStatuses(airportStatusResults);  
  }

  public static List<String> getCodes(String file) throws IOException {
    	return Files.lines(Paths.get(file)).collect(Collectors.toList());
  }
  
  public static void printAirportStatuses(AirportStatusResult airportStatusResults) {
    System.out.println(String.format("%-40s%-40s%-40s%-40s%-40s\n", "Name", "City", "State", "Temperature", "Delay?"));
    for (Airport airport : airportStatusResults.airports)
      System.out.println(String.format("%-40s%-40s%-40s%-40s%-40s", airport.getName(), airport.getCity(), airport.getState(), airport.getTemperature(),airport.getDelay()));
      System.out.println("\nError getting details for:");
      for (String error : airportStatusResults.airportCodesWithError) {
      	System.out.println(error);
      }
      
  }
}
