package cutrod;

import java.util.List;

public class MaxPriceAndCuts {

  private final int maxPrice;
  private final List<Integer> cuts;

  MaxPriceAndCuts(int price, List<Integer> sizeCuts) {
    maxPrice = price;
    cuts = sizeCuts;
  }

  public int getMaxPrice() {
    return maxPrice;
  }

  public List<Integer> getCuts() {
    return cuts;
  }
}
