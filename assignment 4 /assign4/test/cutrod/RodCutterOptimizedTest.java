package cutrod;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RodCutterOptimizedTest extends RodCutterTest {

  @Override
  public RodCutter makeRodCutter() {
    return new RodCutterOptimized();
  }
}
