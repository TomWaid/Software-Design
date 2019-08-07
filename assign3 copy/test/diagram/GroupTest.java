package diagram;

import diagram.Circle;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GroupTest {
  Rectangle rectangle = new Rectangle(2, 3, 2, 2);
  Circle circle = new Circle(2, 3, 1);

  @Test
  public void checkIfGroupIsOnPoint() {

  	Group group = new Group(List.of(circle, rectangle));

  	assertAll(
			()-> assertTrue(group.isPointOn(2, 5)),
			()-> assertFalse(group.isPointOn(6, 6)),
			()-> assertFalse(group.isPointOn(4, 7)),
			()-> assertTrue(group.isPointOn(4, 3)),
			()-> assertFalse(group.isPointOn(0, 4)),
			()-> assertFalse(group.isPointOn(10, 4)),
			()-> assertFalse(group.isPointOn(4, 0)),
			()-> assertFalse(group.isPointOn(4, 10)));
  }

  @Test
  public void checkIfGroupIsInRegion() {

  	Group group = new Group(List.of(circle, rectangle));

  	assertAll(
			() -> assertTrue(group.isShapeInRegion(1, 2, 10, 10)),
			() -> assertFalse(group.isShapeInRegion(1, 2, 0, 10)),
			() -> assertFalse(group.isShapeInRegion(1, 2, 10, 0)),
			() -> assertFalse(group.isShapeInRegion(0, 0, 1, 1)),
			() -> assertFalse(group.isShapeInRegion(10, 10, 1, 1)),
			() -> assertFalse(group.isShapeInRegion(0, 10, 1, 1)),
			() -> assertFalse(group.isShapeInRegion(0, 10, 10, 10)),
			() -> assertFalse(group.isShapeInRegion(10, 0, 1, 1)),
			() -> assertFalse(group.isShapeInRegion(10, 0, 10, 10)));
  }

}
