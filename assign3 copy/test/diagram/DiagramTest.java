package diagram;

import diagram.Circle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class DiagramTest {
  private Diagram diagram;
  Shape circle1 = new Circle(2, 3, 2);
  Shape circle2 = new Circle(2, 3, 4);
  Shape rectangle = new Rectangle(0, 0, 1, 1);
  Shape square = new Square(1, 1, 1);


  @BeforeEach
  public void setup() {
    diagram = new Diagram();
  }

  @Test
  public void diagramHasNothingInIt() {
  	assertEquals(List.of(), diagram.getShapes());
  }

  @Test
  public void addCircleAddsACircle() {
    Shape circle = new Circle(3, 4, 5);
    diagram.addShape(circle);

    assertEquals(List.of(circle), diagram.getShapes());
  }

  @Test
  public void addTwoCirclesCreatesTwoCirclesOnDiagram() {
  	diagram.addShape(circle1);
  	diagram.addShape(circle2);

  	assertEquals(List.of(circle2, circle1), diagram.getShapes());
  }

  @Test
  public void addCircleAndRectangle() {
  	diagram.addShape(circle1);
  	diagram.addShape(rectangle);

  	assertEquals(List.of(rectangle, circle1), diagram.getShapes());
  }

  @Test
  public void addCircleRectangleAndSquare() {
  	diagram.addShape(circle1);
  	diagram.addShape(rectangle);
  	diagram.addShape(square);

  	assertEquals(List.of(square, rectangle, circle1), diagram.getShapes());
  }

  @Test
  public void emptyDiagramIgnoresDeleteAt() {
    assertEquals(List.of(), diagram.getShapes());

    diagram.deleteAt(6, 3);

    assertEquals(List.of(), diagram.getShapes());
  }

  @Test
  public void diagramWithTwoCirclesDeletesNothingIfLocationNotWithinCircle() {
    diagram.addShape(new Circle(3, 4, 2));
    diagram.addShape(new Circle(0, 0, 2));
    diagram.deleteAt(10, 10);

    assertEquals(2, diagram.getShapes().size());
  }

  @Test
  public void diagramWithTwoCirclesSameLocationDeleteDeletesFirstCircleForLocationInCircle() {
  	Shape circleOnBottom = new Circle(2, 3, 2);
  	Shape circleOnTop = new Circle(2, 3, 4);
    diagram.addShape(circleOnBottom);
    diagram.addShape(circleOnTop);
    diagram.deleteAt(3, 3);

    assertEquals(List.of(circleOnBottom), diagram.getShapes());
  }

  @Test
  public void diagramWithTwoDifferentCirclesDeleteDeletesSecondCircleWhenLocationPointsToSecondCircle() {
  	Shape circleOnBottom = new Circle(0, 0, 2);
    Shape circleOnTop = new Circle(2, 3, 4);

    diagram.addShape(circleOnBottom);
    diagram.addShape(circleOnTop);
    diagram.deleteAt(6, 3);

    assertEquals(List.of(circleOnBottom), diagram.getShapes());
  }

  @Test
  public void diagramWithCircleAndRectangleCircleSmallerOnTopOfRectangleCircleGetsDeletedWhenDeleteLocationInsideCircle() {
  	Shape rectangle = new Rectangle(0, 0, 10, 10);
    diagram.addShape(rectangle);
    diagram.addShape(circle2);
    diagram.deleteAt(6, 3);

    assertEquals(List.of(rectangle), diagram.getShapes());
  }

  @Test
  public void diagramWithCircleAndRectangleCircleSmallerOnTopOfRectangleRectangleGetsDeletedWhenDeleteLocationInsideRectangle() {
    Shape circle = new Circle(1, 1, 1);
    diagram.addShape(new Rectangle(0, 0, 5, 5));
    diagram.addShape(circle);
    diagram.deleteAt(4, 4);

    assertEquals(List.of(circle), diagram.getShapes());
    }

  @Test
  public void diagramWithRectangleAndSquareSquareSmallerOnTopOfRectangleSquareGetsDeletedWhenDeleteLocationInsideSquare() {
  	Shape rectangle = new Rectangle(0, 0, 5, 5);
    diagram.addShape(rectangle);
    diagram.addShape(new Square(1, 1, 2));
    diagram.deleteAt(1, 2);

    assertEquals(List.of(rectangle), diagram.getShapes());
  }

  @Test
  public void diagramWithNoShapesSelectAtReturnsNothing() {
  	assertEquals(List.of(), diagram.getShapes());

  	Optional<Shape> shape = diagram.selectAt(0, 0);

  	assertTrue(shape.isEmpty());
  }

  @Test
  public void diagramWithTwoCirclesSelectAtReturnsNothingIfLocationNotInEitherCircle() {
  	diagram.addShape(circle1);
  	diagram.addShape(circle2);
  	Optional<Shape> selectedShape = diagram.selectAt(10, 10);

  	assertTrue(selectedShape.isEmpty());
  }

  @Test
  public void diagramWithTwoCirclesAtDifferentLocationsSelectsFirstCircleWhenSelectAtPointsAtIt() {
  	Shape circleToSelect = new Circle(0, 0, 1);
  	diagram.addShape(circleToSelect);
  	diagram.addShape(new Circle(10, 10, 1));
  	Optional<Shape> selectedShape = diagram.selectAt(0, 0);

  	assertEquals(selectedShape.get(), circleToSelect);
  }

  @Test
  public void diagramWithTwoCirclesAtDifferentLocationsSelectsSecondCircleWhenSelectAtPointsAtIt() {
  	Shape circleToSelect = new Circle(10, 10, 1);
  	diagram.addShape(new Circle(0, 0, 1));
  	diagram.addShape(circleToSelect);
  	Optional<Shape> selectedShape = diagram.selectAt(10, 10);

  	assertEquals(selectedShape.get(), circleToSelect);
  }

  @Test
  public void diagramWithTwoCirclesAtSameLocationSelectAtSelectsOneOnTopWhichIsTheOneAddedLater() {
  	Shape circleOnBottom = new Circle(0, 0, 1);
  	Shape circleOnTop = new Circle(0, 0, 1);
  	diagram.addShape(circleOnBottom);
  	diagram.addShape(circleOnTop);
  	Optional<Shape> selectedShape = diagram.selectAt(0, 0);

  	assertEquals(selectedShape.get(), circleOnTop);
  }

  @Test
  public void diagramWithCircleAndRectangleCircleOnTopOfRectangleSelectAtSelectsCircleWhenLocationIsInsideCircle() {
  	Shape circle = new Circle(1, 1, 1);
    diagram.addShape(new Rectangle(0, 0, 5, 5));
    diagram.addShape(circle);
    Optional<Shape> selectedShape = diagram.selectAt(1, 1);

    assertEquals(selectedShape.get(), circle);
  }

  @Test
  public void diagramWithCircleAndRectangleCircleOnTopOfRectangleSelectAtSelectsRectangleWhenLocationIsInsideRectangle() {
  	Shape rectangle = new Rectangle(0, 0, 5, 5);
  	diagram.addShape(rectangle);
  	diagram.addShape(new Circle(1, 1, 1));
  	Optional<Shape> selectedShape = diagram.selectAt(4, 4);

    assertEquals(selectedShape.get(), rectangle);
  }

  @Test
  public void givenRectangularRegionGroupAllShapesInRegion() {
	  diagram.addShape(circle2);
	  diagram.addShape(circle1);
	  diagram.addShape(new Rectangle(11, 11, 2, 2));
	  diagram.addShape(new Square(12, 12, 2));
	  diagram.createGroup(0, 0, 10, 10);

	  assertTrue(diagram.getShapes().get(0) instanceof Group);
	}

  @Test
  public void aDiagramWithShapesBecomesDiagramWithAGroupWhenAllShapesGrouped() {
    diagram.addShape(circle2);
    diagram.addShape(circle1);
    diagram.createGroup(0, 0, 10, 10);
    Group testGroup = (Group) diagram.getShapes().get(0);

    assertEquals(List.of(circle1, circle2), testGroup.getShapes());
  }

  @Test
  public void aDiagramWithShapesBecomesDiagramWithGroupAndShapesWhenSomeShapesGrouped() {
    diagram.addShape(circle2);
    diagram.addShape(square);
    Rectangle rectangle1 = new Rectangle(8, 8, 1, 1);
    diagram.addShape(rectangle1);
    diagram.createGroup(8, 8, 9, 9);
    Group group = (Group) diagram.getShapes().get(0);

    assertEquals(List.of(group, square, circle2), diagram.getShapes());
  }

  @Test
  public void ungroupingGroupWithinDiagramReplacesGroupWithShapesInGroup() {
    diagram.addShape(circle2);
    diagram.addShape(rectangle);
    diagram.createGroup(0, 0, 10, 10);
    diagram.ungroup((Group) diagram.getShapes().get(0));

    assertEquals(List.of(rectangle, circle2), diagram.getShapes());
  }

  @Test
  public void moveGroupMovesAllMembersInGroup(){
    Rectangle rectangle1 = new Rectangle(1, 1, 2, 2);
    Rectangle rectangle2 = new Rectangle(4, 4, 3, 4);
    diagram.addShape(rectangle1);
    diagram.addShape(rectangle2);
    diagram.createGroup(0, 0, 10, 10);
    diagram.getShapes().get(0).moveBy(5, 6);

    assertEquals(5, rectangle1.getX());
    assertEquals(6, rectangle1.getY());
    assertEquals(5, rectangle2.getX());
    assertEquals(6, rectangle2.getY());
  }

  @Test
  public void groupTwoGroupsAndCircleIntoAGroup() {
  	diagram.addShape(new Rectangle(0, 0, 2, 2));
  	diagram.addShape(new Circle(1, 1, 1));
  	diagram.createGroup(0, 0, 3, 3);
  	Group group1 = (Group) diagram.getShapes().get(0);

  	diagram.addShape(new Circle(4, 4, 1));
  	diagram.createGroup(0, 0, 5, 5);
  	Group group2 = (Group) diagram.getShapes().get(0);

  	Circle circle = new Circle(11, 11, 1);
  	diagram.addShape(circle);

  	diagram.createGroup(0, 0, 10, 10);
  	Group group3 = (Group) diagram.getShapes().get(0);

  	assertEquals(List.of(group3, circle), diagram.getShapes());
  	assertTrue(group3.getShapes().contains(group2));
  	assertTrue(group2.getShapes().contains(group1));

  }

  @Test
  public void saveAndLoadADiagramWithNothingInIt() throws IOException {
    File file = new File("saveddata");
    byte[] data = diagram.saveDiagram();
    diagram.loadDiagram(data);
    assertEquals(List.of(), diagram.getShapes());
  }

  @Test
  public void saveAndLoadADiagramWithACircleInIt() throws IOException {
    diagram.addShape(circle1);
    byte[] data = diagram.saveDiagram();
    diagram.loadDiagram(data);
    Circle circ = (Circle) diagram.getShapes().get(0);

    assertEquals(2, circ.getX());
    assertEquals(3, circ.getY());
    assertEquals(2, circ.getRadius());
  }

  @Test
  public void saveAndLoadADiagramWithACircleAndRectangleInIt() throws IOException {
    diagram.addShape(circle1);
    diagram.addShape(rectangle);
    byte[] data = diagram.saveDiagram();
    diagram.loadDiagram(data);
    Rectangle rect = (Rectangle) diagram.getShapes().get(0);
    Circle circ = (Circle) diagram.getShapes().get(1);

    assertEquals(2, circ.getX());
    assertEquals(3, circ.getY());
    assertEquals(2, circ.getRadius());
    assertEquals(0, rect.getX());
    assertEquals(0, rect.getY());
  }

  @Test
  public void ungroupingADiagramWithNoGroupsDoesNothing() {
  	assertEquals(List.of(), diagram.getShapes());

  	Group testGroup = new Group(List.of(circle1, rectangle));
  	diagram.ungroup(testGroup);

  	assertEquals(List.of(), diagram.getShapes());
  }

  @Test
  public void deletingGroupWillDeleteGroupInDiagramAndAllShapesThatWereInDiagram(){
    Rectangle rectangle1 = new Rectangle(1, 1, 5, 5);
    Rectangle rectangle2 = new Rectangle(2, 2, 1, 1);
    diagram.addShape(rectangle1);
    diagram.addShape(rectangle2);
    diagram.createGroup(2, 2, 7, 7);
    diagram.deleteAt(2, 3);

    assertEquals(List.of(rectangle1), diagram.getShapes());
  }

}
