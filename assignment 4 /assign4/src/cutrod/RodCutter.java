package cutrod;

import java.util.*;

public class RodCutter {
  public MaxPriceAndCuts cutRod(Map<Integer, Integer> prices, int length) {
    MaxPriceAndCuts maxPriceAndCuts = new MaxPriceAndCuts(prices.getOrDefault(length, 0), List.of(length));

    for(int i = 1; i < length; i++) {
      MaxPriceAndCuts maxPriceAndCutsSplit1 = cutRod(prices, i);
      MaxPriceAndCuts maxPriceAndCutsSplit2 = cutRod(prices, length - i);

      List<Integer> combinedCuts = new ArrayList<>(maxPriceAndCutsSplit1.getCuts());
      combinedCuts.addAll(maxPriceAndCutsSplit2.getCuts());

      MaxPriceAndCuts maxPriceAndCutsSplit = new MaxPriceAndCuts(
        maxPriceAndCutsSplit1.getMaxPrice() + maxPriceAndCutsSplit2.getMaxPrice(), combinedCuts);

      if(maxPriceAndCutsSplit.getMaxPrice() > maxPriceAndCuts.getMaxPrice()) {
        maxPriceAndCuts = maxPriceAndCutsSplit;
      }
    }
    return maxPriceAndCuts;
  }
}
