package diagram;

import javafx.scene.layout.Pane;

import java.util.List;

public class Group implements Shape {
  private List<Shape> shapes;

  public Group(List<Shape> shapesToGroup){
    shapes = shapesToGroup;
  }

  public List<Shape> getShapes() {
    return shapes;
  }

  @Override
  public void moveBy(int x, int y) {
    for (Shape shape : shapes) {
      shape.moveBy(x, y);
    }
  }

  @Override
  public boolean isPointOn(int x, int y) {
    return shapes.stream().anyMatch(shape -> shape.isPointOn(x, y));
  }

  @Override
  public boolean isShapeInRegion(int x, int y, int width, int height) {
    return shapes.stream().allMatch(shape -> shape.isShapeInRegion(x, y, width, height));
  }
}
