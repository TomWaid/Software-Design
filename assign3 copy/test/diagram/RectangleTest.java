package diagram;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RectangleTest {
  private Rectangle rectangle;

  @BeforeEach
  public void setup(){
    rectangle = new Rectangle(2, 3, 4, 5);
  }

  @Test
  public void createRectangleCreatesARectangle(){
    assertEquals(2, rectangle.getX());
    assertEquals(3, rectangle.getY());
  }

  @Test
  public void changeLocationOfRectangleSetsANewLocation(){
    rectangle.moveBy(5, 6);

    assertEquals(5, rectangle.getX());
    assertEquals(6, rectangle.getY());
  }

  @Test
  public void checkIfPointOnRectangle(){
    assertAll(
      () -> assertTrue(rectangle.isPointOn(2, 5)),
      () -> assertTrue(rectangle.isPointOn(6, 4)),
      () -> assertTrue(rectangle.isPointOn(3, 8)),
      () -> assertTrue(rectangle.isPointOn(4, 3)),
      () -> assertTrue(rectangle.isPointOn(4, 5)),
      () -> assertFalse(rectangle.isPointOn(0, 4)),
      () -> assertFalse(rectangle.isPointOn(10, 4)),
      () -> assertFalse(rectangle.isPointOn(4, 0)),
      () -> assertFalse(rectangle.isPointOn(4, 10)));
  }

  @Test
  public void checkIfRectangleIsInRegion() {
  	assertAll(
			() -> assertTrue(rectangle.isShapeInRegion(1, 2, 10, 10)),
			() -> assertFalse(rectangle.isShapeInRegion(1, 2, 0, 10)),
			() -> assertFalse(rectangle.isShapeInRegion(1, 2, 10, 0)),
			() -> assertFalse(rectangle.isShapeInRegion(0, 0, 1, 1)),
			() -> assertFalse(rectangle.isShapeInRegion(10, 10, 1, 1)),
			() -> assertFalse(rectangle.isShapeInRegion(0, 10, 1, 1)),
			() -> assertFalse(rectangle.isShapeInRegion(0, 10, 10, 10)),
			() -> assertFalse(rectangle.isShapeInRegion(10, 0, 1, 1)),
			() -> assertFalse(rectangle.isShapeInRegion(10, 0, 10, 10)));
  }
}
