package cutrod;

import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PerformanceTest {

  @Test
  public void performanceOfMemoizedVersionIsFasterThanNormalVersion() {
    assertTrue(computeTime(new RodCutter()) > computeTime(new RodCutterOptimized()) * 10);
  }

  public long computeTime(RodCutter rodCutter) {
    Map<Integer, Integer> prices = Map.of(1, 1, 5, 4, 10, 14);
    long startTime = System.nanoTime();
    MaxPriceAndCuts maxPriceAndCutsForLengthFourteen = rodCutter.cutRod(prices, 14);
    long endTime = System.nanoTime();

    return (endTime - startTime);
  }
}