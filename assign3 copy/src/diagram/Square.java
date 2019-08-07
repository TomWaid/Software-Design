package diagram;

import diagram.ui.DrawShape;
import diagram.ui.DrawSquare;
import javafx.scene.layout.Pane;

import java.io.Serializable;

public class Square implements Shape , Serializable {

  private int x;
  private int y;
  private final int length;

  public Square(int xCord, int yCord, int side) {
    x = xCord;
    y = yCord;
    length = side;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getLength() {
    return length;
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
    return x >= getX() && x <= getX() + getLength() && y >= getY() && y <= getY() + getLength();
  }

  @Override
  public boolean isShapeInRegion(int x1, int y1, int width1, int height1) {
    return ((x >= x1) && (y >= y1)
            && ((x + length) <= (x1 + width1 ))
            && ((y + length) <= (y1 + height1)));
  }
}
