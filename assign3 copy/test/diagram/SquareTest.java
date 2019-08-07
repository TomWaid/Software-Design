package diagram;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class SquareTest {
	private Square square;

  @BeforeEach
  public void setup(){
    square = new Square(2, 3, 4);
  }

  @Test
  public void createSquareCreatesASquare(){
    assertEquals(2, square.getX());
    assertEquals(3, square.getY());
    assertEquals(4, square.getLength());
  }

  @Test
  public void changeLocationOfSquareSetsANewLocation(){
    square.moveBy(4, 5);

    assertEquals(4, square.getX());
    assertEquals(5, square.getY());
  }

  @Test
  public void checkIfPointOnSquare(){
    assertAll(
      () -> assertTrue(square.isPointOn(2, 5)),
      () -> assertTrue(square.isPointOn(6, 6)),
      () -> assertTrue(square.isPointOn(4, 7)),
      () -> assertTrue(square.isPointOn(4, 3)),
      () -> assertFalse(square.isPointOn(0, 4)),
      () -> assertFalse(square.isPointOn(10, 4)),
      () -> assertFalse(square.isPointOn(4, 0)),
      () -> assertFalse(square.isPointOn(4, 10)));
  }
  
  @Test
  public void checkIfSquareIsInRegion() {
  	assertAll(
			() -> assertTrue(square.isShapeInRegion(1, 2, 10, 10)),
			() -> assertFalse(square.isShapeInRegion(1, 2, 0, 10)),
			() -> assertFalse(square.isShapeInRegion(1, 2, 10, 0)),
			() -> assertFalse(square.isShapeInRegion(0, 0, 1, 1)),
			() -> assertFalse(square.isShapeInRegion(10, 10, 1, 1)),
			() -> assertFalse(square.isShapeInRegion(0, 10, 1, 1)),
			() -> assertFalse(square.isShapeInRegion(0, 10, 10, 10)),
			() -> assertFalse(square.isShapeInRegion(10, 0, 1, 1)),
			() -> assertFalse(square.isShapeInRegion(10, 0, 10, 10)));
  }
}
