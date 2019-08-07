package diagram;

import diagram.ui.DrawRectangle;
import diagram.ui.DrawShape;
import javafx.scene.layout.Pane;

import java.io.Serializable;

public class Rectangle implements Shape, Serializable {

  private int x;
  private int y;
  private final int width;
  private final int height;

  public Rectangle(int xCord, int yCord, int wd, int ht) {
    x = xCord;
    y = yCord;
    width = wd;
    height = ht;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  private void setX(int xCord) {
    x = xCord;
  }

  private void setY(int yCord) {
    y = yCord;
  }

  @Override
  public void moveBy(int x, int y){
    setX(x);
    setY(y);
  }

  @Override
  public boolean isPointOn(int x, int y){
    return x >= getX() && x <= getX() + getWidth() && y >= getY() && y <= getY() + getHeight();
  }

  @Override
  public boolean isShapeInRegion(int x1, int y1, int width1, int height1) {
    return ((x >= x1) && (y >= y1)
            && ((x + width) <= (x1 + width1))
            && ((y + height) <= (y1 + height1)));
  }
}

