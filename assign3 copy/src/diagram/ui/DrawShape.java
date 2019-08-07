package diagram.ui;

import diagram.Diagram;
import diagram.Shape;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;

public interface DrawShape {
  void draw(Shape shape, Pane pane);
  Button getButton(Diagram diagram, VBox vbox, Pane pane) throws FileNotFoundException;
  boolean canDraw(Shape shape);
}
