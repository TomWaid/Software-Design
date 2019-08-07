package airportinfo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FAAAirportInfoServiceTest {
  private FAAAirportInfoService infoService;
  private final String validJSON = "{\"Name\":\"George Bush Intercontinental/houston\",\"City\":\"Houston\",\"State\":\"TX\",\"ICAO\":\"KIAH\",\"IATA\":\"IAH\",\"SupportedAirport\":false,\"Delay\":false,\"DelayCount\":0,\"Status\":[{\"Reason\":\"No known delays for this airport\"}],\"Weather\":{\"Weather\":[{\"Temp\":[\"Partly Cloudy\"]}],\"Visibility\":[10.00],\"Meta\":[{\"Credit\":\"NOAA's National Weather Service\",\"Url\":\"http://weather.gov/\",\"Updated\":\"Last Updated on Feb 24 2019, 6:53 am CST\"}],\"Temp\":[\"49.0 F (9.4 C)\"],\"Wind\":[\"North at 11.5\"]}}";
  private final String invalidJSON = "{\"SupportedAirport\":false,\"Delay\":false,\"DelayCount\":0,\"Status\":[{\"Type\":\"\",\"AvgDelay\":\"\",\"ClosureEnd\":\"\",\"ClosureBegin\":\"\",\"MinDelay\":\"\",\"Trend\":\"\",\"MaxDelay\":\"\",\"EndTime\":\"\"}]}";
  private final String validJSONWithDelay="{\"Name\":\"San Francisco Intl\",\"City\":\"San Francisco\",\"State\":\"CA\",\"ICAO\":\"KSFO\",\"IATA\":\"SFO\",\"SupportedAirport\":true,\"Delay\":true,\"DelayCount\":1,\"Status\":[{\"Type\":\"Ground Delay\",\"Reason\":\"WEATHER / WIND\",\"AvgDelay\":\"2 hours and 45 minutes\"}],\"Weather\":{\"Weather\":[{\"Temp\":[\"Rain Fog/Mist\"]}],\"Visibility\":[6.00],\"Meta\":[{\"Credit\":\"NOAA's National Weather Service\",\"Url\":\"http://weather.gov/\",\"Updated\":\"Last Updated on Feb 26 2019, 11:56 pm PST\"}],\"Temp\":[\"54.0 F (12.2 C)\"],\"Wind\":[\"Southeast at 18.4\"]}}";
 

  @BeforeEach
  public void setup() {
  	infoService = new FAAAirportInfoService();
  }

  @Test
  public void getJSONFromServiceURLReturnsJSON() throws Exception {
  	String airportJSON = infoService.getJSON("IAH");
  	
  	assertTrue(airportJSON.contains("\"City\":\"Houston\""));
  }

  @Test
  public void verifyAirportHasAllParametersRequired() throws Exception {
  	Airport airportJSON = infoService.createAirport(validJSON);
	Airport airportJSONWithDelay = infoService.createAirport(validJSONWithDelay);
  	
  	assertEquals("George Bush Intercontinental/houston", airportJSON.getName());
  	assertEquals("Houston", airportJSON.getCity());
  	assertEquals("TX", airportJSON.getState());
  	assertEquals("49.0 F (9.4 C)", airportJSON.getTemperature()); 
  	assertEquals("", airportJSON.getDelay());
  	assertEquals("\u2202", airportJSONWithDelay.getDelay());
  	assertEquals("IAH", airportJSON.getCode());
  }

  @Test
  public void createAirportThrowsExceptionWithNoAirportName() {
    assertThrows(RuntimeException.class,() -> infoService.createAirport(invalidJSON));
  }
  
  @Test
  public void fetchDataCallsGetJSON() throws IOException {
  	FAAAirportInfoService infoServiceMock = Mockito.mock(FAAAirportInfoService.class);
  	when(infoServiceMock.getJSON("ORD")).thenReturn(validJSON);
  	when(infoServiceMock.fetchData("ORD")).thenCallRealMethod();
  	
  	infoServiceMock.fetchData("ORD");
  	
    verify(infoServiceMock).getJSON("ORD");
  }
	
  @Test
  public void fetchDataCallsCreateAirportWithResponseFromGetJSON() throws IOException {
  	FAAAirportInfoService infoServiceMock = Mockito.mock(FAAAirportInfoService.class);
  	when(infoServiceMock.getJSON("ORD")).thenReturn(validJSON);
  	when(infoServiceMock.createAirport(validJSON)).thenReturn(new Airport("test"));
  	when(infoServiceMock.fetchData("ORD")).thenCallRealMethod();
  	
  	infoServiceMock.fetchData("ORD");
  	
  	verify(infoServiceMock).createAirport(validJSON);
  }
  
  @Test
  public void fetchDataReturnsTheAirportCreatedByCreateAirport() throws IOException {
  	Airport expectedAirport = new Airport("test");
  	FAAAirportInfoService infoServiceMock = Mockito.mock(FAAAirportInfoService.class);
    when(infoServiceMock.getJSON("ORD")).thenReturn(validJSON);
    when(infoServiceMock.createAirport(validJSON)).thenReturn(expectedAirport);
    when(infoServiceMock.fetchData("ORD")).thenCallRealMethod();

    assertEquals(expectedAirport, infoServiceMock.fetchData("ORD"));
  }
  
  @Test
  public void fetchDataPropagatesExceptionFromCreateAirport() throws Exception {
  	FAAAirportInfoService infoServiceMock = Mockito.mock(FAAAirportInfoService.class);
  	when(infoServiceMock.getJSON("ORD")).thenReturn("error");
  	when(infoServiceMock.createAirport("error")).thenCallRealMethod().thenThrow(new RuntimeException("Error from createAirport"));
  	when(infoServiceMock.fetchData("ORD")).thenCallRealMethod();

  	assertThrows(RuntimeException.class,
			() -> infoServiceMock.fetchData("ORD"));
  }
  
  @Test
  public void fetchDataPropagatesExceptionFromGetJSON() throws Exception {
    FAAAirportInfoService infoServiceMock = Mockito.mock(FAAAirportInfoService.class);
    when(infoServiceMock.getJSON("ORD")).thenThrow(new IOException("Error from getJSON"));
    when(infoServiceMock.fetchData("ORD")).thenCallRealMethod();

    assertThrows(RuntimeException.class,
			() -> infoServiceMock.fetchData("ORD"));
	}
  
}
