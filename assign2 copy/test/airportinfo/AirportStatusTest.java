package airportinfo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AirportStatusTest {
  private AirportStatus airportStatus;
  private AirportService service;
  private Airport iah = new Airport("George Bush Intercontinental");
  private Airport iad = new Airport("Dulles Intl");
  private Airport ord = new Airport("Chicago Intl");

  @BeforeEach
  public void setup() {
    airportStatus = new AirportStatus();
    service = Mockito.mock(AirportService.class);
    airportStatus.setAirportService(service);
  }

  @Test
  public void canary() {
    assertTrue(true);
  }

  @Test
  public void sortListOfAirports(){
    assertAll(
      () -> assertEquals(List.of(),
        airportStatus.sortAirports(List.of())),
      () -> assertEquals(List.of(iah),
    		airportStatus.sortAirports(List.of(iah))),
      () -> assertEquals(List.of(iad, iah),
    		airportStatus.sortAirports(List.of(iad, iah))),
      () -> assertEquals(List.of(iad, iah),
    		airportStatus.sortAirports(List.of(iah, iad))),
      () -> assertEquals(List.of(ord, iad, iah),
    		airportStatus.sortAirports(List.of(iah, ord, iad))));
  }

  @Test
  public void getEmptyListOfAirportsForEmptyListOfCodes(){
  	assertEquals(List.of(),
			airportStatus.getAirportsStatus(List.of()).airports);
  }

  @Test
  public void getListOfOneAirportForListWithOneAirportCode(){
  	when(service.fetchData("IAH")).thenReturn(iah);

  	assertEquals(List.of(iah),
			airportStatus.getAirportsStatus(List.of("IAH")).airports);
  }

  @Test
  public void getListOfTwoAirportsForListWithTwoAirportCodes(){
  	when(service.fetchData("IAH")).thenReturn(iah);
  	when(service.fetchData("IAD")).thenReturn(iad);

  	assertEquals(List.of(iad,iah),
			airportStatus.getAirportsStatus(List.of("IAD", "IAH")).airports);
  }

  @Test
  public void getSortedListOfTwoAirportsForListWithTwoAirportCodes(){
  	when(service.fetchData("IAH")).thenReturn(iah);
  	when(service.fetchData("IAD")).thenReturn(iad);

  	assertEquals(List.of(iad,iah),
			airportStatus.getAirportsStatus(List.of("IAH", "IAD")).airports);
  }

  @Test
  public void getSortedListOfThreeAirportsForListWithThreeAirportCodes() {
  	when(service.fetchData("IAH")).thenReturn(iah);
  	when(service.fetchData("IAD")).thenReturn(iad);
  	when(service.fetchData("ORD")).thenReturn(ord);

  	assertEquals(List.of(ord, iad, iah),
			airportStatus.getAirportsStatus(List.of("IAH", "IAD", "ORD")).airports);
  }

  @Test
  public void oneInvalidAirportCodeGiven(){
  	when(service.fetchData("INVALID"))
  		.thenThrow(new RuntimeException("Invalid Code"));

  	assertEquals(List.of("INVALID"),
			airportStatus.getAirportsStatus(
				List.of("INVALID")).airportCodesWithError);
  }

  @Test
  public void twoCodesGivenSecondInvalid(){
    when(service.fetchData("IAH")).thenReturn(iah);
    when(service.fetchData("INVALID"))
    	.thenThrow(new RuntimeException("Invalid code"));

    assertEquals(List.of(iah),
  		airportStatus.getAirportsStatus(List.of("IAH","INVALID")).airports);

    assertEquals(List.of("INVALID"),
  		airportStatus.getAirportsStatus(
				List.of("IAH","INVALID")).airportCodesWithError);
  }

  @Test
  public void threeCodesGivenSecondInvalid() {
  	when(service.fetchData("IAH")).thenReturn(iah);
  	when(service.fetchData("INVALID"))
  		.thenThrow(new RuntimeException("Invalid code"));
  	when(service.fetchData("ORD")).thenReturn(ord);

  	assertEquals(List.of(ord, iah),
			airportStatus.getAirportsStatus(
				List.of("ORD", "INVALID", "IAH")).airports);
  	assertEquals(List.of("INVALID"),
			airportStatus.getAirportsStatus(
				List.of("ORD", "INVALID", "IAH")).airportCodesWithError);
  }

  @Test
  public void threeCodesGivenFirstInvalidThirdNetworkError(){
  	when(service.fetchData("INVALID"))
  		.thenThrow(new RuntimeException("Invalid code"));
  	when(service.fetchData("IAH")).thenReturn(iah);
  	when(service.fetchData("ORD"))
  		.thenThrow(new RuntimeException("Network error"));

  	assertEquals(List.of(iah),
			airportStatus.getAirportsStatus(
				List.of("INVALID", "IAH", "ORD")).airports);
  	assertEquals(List.of("INVALID","ORD"),
			airportStatus.getAirportsStatus(
				List.of("INVALID", "IAH", "ORD")).airportCodesWithError);
  } 
}