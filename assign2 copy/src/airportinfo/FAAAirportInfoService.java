package airportinfo;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class FAAAirportInfoService implements AirportService {
  public String getJSON(String airportCode) throws IOException {
    String faaURL = "https://soa.smext.faa.gov/asws/api/airport/status/" + airportCode; 
	
    try(Scanner scanner = new Scanner(new URL(faaURL).openStream())) {
      return scanner.nextLine();
    }
  }

  public Airport createAirport(String json) {
    try {
    	JSONParser parser = new JSONParser();
    	JSONObject airportJson = (JSONObject) parser.parse(json);
    	
    	String code = airportJson.get("IATA").toString();
    	String name = airportJson.get("Name").toString();
    	String city = airportJson.get("City").toString();
    	String state = airportJson.get("State").toString();
    	String delay = (airportJson.get("Delay").toString()=="false")?"":"\u2202";
    	JSONObject weatherJson = (JSONObject) airportJson.get("Weather");
    	String temperature = weatherJson.get("Temp").toString().replaceAll("\\[", "").replaceAll("\\]","").replace("\"", "");;
    	
    	return new Airport(code, name,city,state,temperature,delay);
  	}	catch(ParseException ex) {
  		throw new RuntimeException(ex);
  	}
  }
  
  @Override
  public Airport fetchData(String airportCode) {
		try {
			return createAirport(getJSON(airportCode));
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
  }
  
}
