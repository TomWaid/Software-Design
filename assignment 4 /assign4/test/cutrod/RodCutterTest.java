package cutrod;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RodCutterTest {

  private RodCutter rodCutter;
  private Map<Integer, Integer> prices = Map.of(1, 1, 2, 1, 3, 2, 4, 3, 5, 4, 6, 5, 7, 5, 8, 9, 9, 9, 10, 10);

  public RodCutter makeRodCutter() {
    return new RodCutter();
  }

  @BeforeEach
  public void setUp() {
    rodCutter = makeRodCutter();
  }

  @Test
  public void canary() {
    assert (true);
  }

  @Test
  public void passLengthOfZeroReturnsZeroPriceAndZeroCuts() {
    MaxPriceAndCuts maxPriceAndCuts = rodCutter.cutRod(prices, 0);

    assertEquals(0, maxPriceAndCuts.getMaxPrice());
    assertEquals(List.of(0), maxPriceAndCuts.getCuts());
  }

  @Test
  public void passLengthOfOneReturnsMaxPriceAndCuts() {
    MaxPriceAndCuts maxPriceAndCuts = rodCutter.cutRod(prices, 1);

    assertEquals(1, maxPriceAndCuts.getMaxPrice());
    assertEquals(List.of(1), maxPriceAndCuts.getCuts());
  }

  @Test
  public void passLengthOfTwoReturnsMaxPriceAndCuts() {
    MaxPriceAndCuts maxPriceAndCuts = rodCutter.cutRod(prices, 2);

    assertEquals(2, maxPriceAndCuts.getMaxPrice());
    assertEquals(List.of(1, 1), maxPriceAndCuts.getCuts());
  }

  @Test
  public void passLengthOfFiveReturnsMaxPriceAndCuts() {
    MaxPriceAndCuts maxPriceAndCuts = rodCutter.cutRod(prices, 5);

    assertEquals(5, maxPriceAndCuts.getMaxPrice());
    assertEquals(List.of(1, 1, 1, 1, 1), maxPriceAndCuts.getCuts());
  }

  @Test
  public void passLengthOfTenReturnsMaxPriceAndCuts() {
    MaxPriceAndCuts maxPriceAndCuts = rodCutter.cutRod(prices, 10);

    assertEquals(11, maxPriceAndCuts.getMaxPrice());
    assertEquals(List.of(1, 1, 8), maxPriceAndCuts.getCuts());
  }

  @Test
  public void passLengthOfElevenReturnsMaxPriceAndCuts() {
    MaxPriceAndCuts maxPriceAndCuts = rodCutter.cutRod(prices, 11);

    assertEquals(12, maxPriceAndCuts.getMaxPrice());
    assertEquals(List.of(1, 1, 1, 8), maxPriceAndCuts.getCuts());
  }

  @Test
  public void passLengthOfFourteenReturnsMaxPriceAndCuts() {
    MaxPriceAndCuts maxPriceAndCuts = rodCutter.cutRod(prices, 14);

    assertEquals(15, maxPriceAndCuts.getMaxPrice());
    assertEquals(List.of(1, 1, 1, 1, 1, 1, 8), maxPriceAndCuts.getCuts());
  }
  
  @Test
  public void priceOf0ForALengthWhosePriceNotFoundAndNoSmallerCuts() {  
    MaxPriceAndCuts maxPriceAndCuts = rodCutter.cutRod(Map.of(2, 1), 1);

    assertEquals(0, maxPriceAndCuts.getMaxPrice());
    assertEquals(List.of(1), maxPriceAndCuts.getCuts());
  }
}