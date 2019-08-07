package airportinfo;

public class Airport {
  private final String code;
  private final String name;
  private final String city;
  private final String state;
  private final String temperature;
  private final String delay; 

  public Airport(String airportName){ 
    this("", airportName, "", "", "", "");    
  }

  public Airport(String airportCode, String airportName, String airportCity,
    String airportState, String airportTemperature, String airportDelay) {
      code = airportCode;
      name = airportName;
      city = airportCity;
      state = airportState;
      temperature = airportTemperature;
      delay = airportDelay;
  }

  public String getName() {
    return name;
  }

  public String getCode() {
    return code;
  }

  public String getCity() {
    return city;
  }

  public String getState() {
    return state;
  }

  public String getTemperature() {
    return temperature;
  }

  public String getDelay() {
    return delay;
  }
}
