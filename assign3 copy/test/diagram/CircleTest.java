package diagram;

import diagram.Circle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CircleTest {
  private Circle circle;

  @BeforeEach
  public void setup(){
    circle = new Circle(2, 3, 4);
  }

  @Test
  public void canary() {
    assertTrue(true);
  }

  @Test
  public void createCircleCreatesACircle(){
    assertEquals(2, circle.getX());
    assertEquals(3, circle.getY());
    assertEquals(4, circle.getRadius());
  }

  @Test
  public void changeLocationOfCircleSetsANewLocation(){
    circle.moveBy(5, 6);

    assertEquals(5, circle.getX());
    assertEquals(6, circle.getY());
  }

  @Test
  public void checkIfPointIsOutsideTheCircle(){
    Circle circle = new Circle(3, 5, 2);
    
    assertFalse(circle.isPointOn(3, 8));
    assertFalse(circle.isPointOn(3, 0));
    assertFalse(circle.isPointOn(6, 7));
    assertFalse(circle.isPointOn(0, 5));
  }

  @Test
  public void checkIfPointIsInsideTheCircle(){
    assertTrue(circle.isPointOn(4, 5));
  }

  @Test
  public void checkIfPointIsOnTheCircle(){
    assertTrue(circle.isPointOn(6, 3));
  }

  @Test
  public void checkIfShapeIsInRegion(){
   assertAll(
     ()-> assertTrue(circle.isShapeInRegion(1, 2, 10, 10)),
     ()-> assertTrue(circle.isShapeInRegion(1, 2, 0, 10)),
     ()-> assertTrue(circle.isShapeInRegion(1, 2, 10, 0)),
     ()-> assertTrue(circle.isShapeInRegion(0, 0, 1, 1)),
     ()-> assertFalse(circle.isShapeInRegion(10, 10, 1, 1)),
     ()-> assertFalse(circle.isShapeInRegion(0, 10, 1, 1)),
     ()-> assertFalse(circle.isShapeInRegion(0, 10, 10, 10)),
     ()-> assertFalse(circle.isShapeInRegion(10, 0, 1, 1)),
     ()-> assertFalse(circle.isShapeInRegion(10, 0, 10, 10)));
  }
}
