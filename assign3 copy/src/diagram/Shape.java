package diagram;

import javafx.scene.layout.Pane;

public interface Shape {
  void moveBy(int x, int y);
  boolean isPointOn(int x, int y);
  boolean isShapeInRegion(int x1, int y1, int x2, int y2);
}
