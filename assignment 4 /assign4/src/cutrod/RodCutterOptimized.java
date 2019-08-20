package cutrod;

import java.util.*;

public class RodCutterOptimized extends RodCutter {

  private final Map<Integer, MaxPriceAndCuts> cacheResults = new HashMap<>();

  public MaxPriceAndCuts cutRod(Map<Integer, Integer> prices, int length) {

    if (!cacheResults.containsKey(length)) {
      cacheResults.put(length, super.cutRod(prices, length));
    } 
	
    return cacheResults.get(length);
  }
}