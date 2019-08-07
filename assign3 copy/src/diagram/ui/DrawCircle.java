package diagram.ui;

import diagram.Diagram;
import diagram.Shape;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class DrawCircle implements DrawShape {

  @Override
  public void draw(Shape shape, Pane pane) {
    diagram.Circle circleToDraw = (diagram.Circle) shape;

    Circle fxCircle = new Circle();
    fxCircle.setFill(Color.GREEN);

    fxCircle.setCenterX(circleToDraw.getX());
    fxCircle.setCenterY(circleToDraw.getY());
    fxCircle.setRadius(circleToDraw.getRadius());

    pane.getChildren().add(fxCircle);
  }

  @Override
  public Button getButton(Diagram diagram, VBox vbox, Pane pane) throws FileNotFoundException {
    FileInputStream input = new FileInputStream("circle.png");
    Image img = new Image(input);
    ImageView iw = new ImageView(img);

    Button button = new Button("", iw);
    vbox.getChildren().add(button);
    button.setOnMouseClicked((MouseEvent buttonEvent) -> {
      Shape shapeToDraw = new diagram.Circle(0, 0, 15);
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
    if (shape.getClass() == diagram.Circle.class) {
      return true;
    }
    return false;
  }

}
