package diagram;

import org.apache.commons.lang3.SerializationUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Diagram {
  private List<Shape> shapes = new ArrayList<>();

  public List<Shape> getShapes() {
    return shapes;
  }

  public void addShape(Shape shape) {
    shapes.add(0, shape);
  }

  public void deleteAt(int x, int y) {
    selectAt(x, y)
      .ifPresent(shape -> shapes.remove(shape));
  }

  public Optional<Shape> selectAt(int x, int y) {
    return shapes.stream()
      .filter(shape -> shape.isPointOn(x, y))
      .findFirst();
  }

  public void createGroup(int x, int y, int width, int height) {
    List<Shape> groupedShapes = shapes.stream()
      .filter(shape -> shape.isShapeInRegion(x, y, width, height))
      .collect(Collectors.toList());

    shapes.removeAll(groupedShapes);
    addShape(new Group(groupedShapes));
  }

  public void ungroup(Group group) {
    if (shapes.contains(group)) {
      shapes.addAll(group.getShapes());
      shapes.remove(group);
    }
  }

  public byte[] saveDiagram(){
    byte[] data = SerializationUtils.serialize((ArrayList)(shapes));
    return data;
  }

  @SuppressWarnings("unchecked")
  public void loadDiagram(byte[] data) {
    Object object = SerializationUtils.deserialize(data);
    shapes = (List) object;
  }

}