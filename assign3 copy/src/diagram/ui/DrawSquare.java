package diagram.ui;

import diagram.Diagram;
import diagram.Shape;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class DrawSquare implements DrawShape {

  @Override
  public void draw(Shape shape, Pane pane) {
    diagram.Square squaretoDraw = (diagram.Square) shape;

    Rectangle fxSquare = new Rectangle();
    fxSquare.setFill(Color.RED);

    fxSquare.setX(squaretoDraw.getX());
    fxSquare.setY(squaretoDraw.getY());
    fxSquare.setWidth(squaretoDraw.getLength());
    fxSquare.setHeight(squaretoDraw.getLength());

    pane.getChildren().add(fxSquare);
  }

  @Override
  public Button getButton(Diagram diagram, VBox vbox, Pane pane) throws FileNotFoundException {
    FileInputStream input = new FileInputStream("square.png");
    Image img = new Image(input);
    ImageView iw = new ImageView(img);

    Button button = new Button("", iw);
    vbox.getChildren().add(button);
    button.setOnMouseClicked((MouseEvent buttonEvent) -> {
      Shape shapeToDraw = new diagram.Square(0, 0, 25);
      createShapeIn(buttonEvent, shapeToDraw, diagram, pane);
    });


    return button;
  }

  public void createShapeIn(MouseEvent event, Shape shape, Diagram diagram, Pane pane) {
    pane.setOnMouseClicked((MouseEvent paneEvent) -> {
      shape.moveBy((int) paneEvent.getX(), (int) paneEvent.getY());
      if(!diagram.getShapes().contains(shape)) {
        diagram.addShape(shape);
        draw(shape, pane);
      }
    });
  }

  @Override
  public boolean canDraw(Shape shape) {
    if (shape.getClass() == diagram.Square.class) {
      return true;
    }
    return false;
  }

}
