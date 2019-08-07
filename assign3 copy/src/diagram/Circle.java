package diagram;

import diagram.ui.DrawCircle;
import diagram.ui.DrawShape;
import javafx.scene.layout.Pane;

import java.io.Serializable;

public class Circle implements Shape, Serializable {

  private int x;
  private int y;
  private final int radius;

  public Circle(int xCord, int yCord, int rad) {
    x = xCord;
    y = yCord;
    radius = rad;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getRadius() {
    return radius;
  }

  private void setX(int xCord) {
    x = xCord;
  }

  private void setY(int yCord) {
    y = yCord;
  }

  @Override
  public void moveBy(int x, int y) {
    setX(x);
    setY(y);
  }

  @Override
  public boolean isPointOn(int x, int y) {
    return Math.pow(x - getX(), 2) + Math.pow(y - getY(), 2) <= Math.pow(getRadius(), 2);
  }

  @Override
  public boolean isShapeInRegion(int x1, int y1, int width, int height) {
    int deltax = x - Math.max(x1, Math.min(x, x1 + width));
    int deltay = y - Math.max(y1, Math.min(y, y1 + height));
    return (deltax * deltax + deltay * deltay) <= (radius * radius);
  }
}
