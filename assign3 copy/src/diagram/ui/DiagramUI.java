package diagram.ui;

import diagram.Diagram;
import diagram.Shape;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.reflections.Reflections;

import java.io.*;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class DiagramUI extends Application {
  Shape shape;
  DrawShape shapeDrawer;
  Diagram diagram = new Diagram();
  Pane diagramPane;
  byte[] data;

  Reflections reflections = new Reflections("diagram.ui");
  Set<Class<? extends DrawShape>> classes = reflections.getSubTypesOf(DrawShape.class);

  @Override
  public void start(Stage primaryStage) throws Exception {

    primaryStage.setTitle("Diagramming Application");

    diagramPane = new Pane();
    diagramPane.setPrefHeight(250);
    diagramPane.setPrefWidth(500);
    VBox vbox = new VBox();

    for (Class<? extends DrawShape> temp : classes) {
      Class shapeClass = Class.forName(temp.getName());
      shapeDrawer =  (DrawShape)shapeClass.getDeclaredConstructor().newInstance();
      shapeDrawer.getButton(diagram,vbox,diagramPane);
    }

    //Button groupButton = new Button("Group Shapes");
    //executeGroupAction(diagram, diagramPane, groupButton);

    Button deleteButton = new Button("Delete Shape");
    executeDeleteAction(diagram, diagramPane, deleteButton);

    Button saveButton = new Button("Save");
    executeSaveAction(saveButton);

    Button loadButton = new Button("Load");
    executeLoadAction(diagramPane, loadButton);

    vbox.getChildren().addAll(deleteButton, saveButton, loadButton);
    vbox.setSpacing(10);

    BorderPane root = new BorderPane();
    root.setBottom(vbox);

    BorderPane border = new BorderPane();
    border.setLeft(vbox);
    border.setRight(diagramPane);

    Scene scene = new Scene(border, 800, 500);
    scene.setFill(Color.WHITE);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public void executeDeleteAction(Diagram diagram, Pane pane, Button button) throws FileNotFoundException {
    button.setOnMouseClicked((MouseEvent buttonEvent) -> {
      deleteShapeIn(diagram, pane);
    });
  }

  public void deleteShapeIn(Diagram diagram, Pane pane) {
    pane.setOnMouseClicked((MouseEvent paneEvent) -> {
      pane.getChildren().remove(paneEvent.getTarget());
      diagram.deleteAt((int)paneEvent.getX(),(int)paneEvent.getY());
    });
  }

  public void executeGroupAction(Diagram diagram, Pane pane, Button button) {
    button.setOnMouseClicked((MouseEvent buttonEvent) -> {
      groupShapes(diagram, pane);
    });
  }

  public void groupShapes(Diagram diagram, Pane pane) {
    Rectangle dragBox = new Rectangle(0, 0, 0, 0);
    dragBox.setFill(Color.GRAY);
    dragBox.setOpacity(.5);
    dragBox.setVisible(false);

    pane.setOnMousePressed((MouseEvent event) -> {
      dragBox.setVisible(true);
      dragBox.setTranslateX(event.getX());
      dragBox.setTranslateY(event.getY());
      //pane.getChildren().add(dragBox);
    });
    pane.setOnMouseDragged((MouseEvent event) -> {
      dragBox.setWidth(event.getX() - dragBox.getTranslateX());
      dragBox.setHeight(event.getY() - dragBox.getTranslateY());
      diagram.createGroup((int) dragBox.getTranslateX(), (int) dragBox.getTranslateY(), (int) event.getX()
        - (int) dragBox.getTranslateX(), (int) event.getY() - (int)dragBox.getTranslateY());
    });
    pane.setOnMouseReleased((MouseEvent event) -> {
      dragBox.setVisible(false);
    });
  }

  public void executeLoadAction(Pane diagramPane, Button loadButton) {
    loadButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        try {
          FileChooser chooser = new FileChooser();
          chooser.setTitle("Load Diagram");
          File file = chooser.showOpenDialog(new Stage());
          FileInputStream stream = new FileInputStream(file);
          byte[] data = stream.readAllBytes();
          diagram.loadDiagram(data);

          for (Shape loadedShape : diagram.getShapes()) {
            for (Class<? extends DrawShape> temp : classes) {
              Class shapeClass = Class.forName(temp.getName());
              shapeDrawer = (DrawShape)shapeClass.getDeclaredConstructor().newInstance();
              if (shapeDrawer.canDraw(loadedShape)) {
                shapeDrawer.draw(loadedShape, diagramPane);
              }
            }
          }
        } catch (Exception e) {
          System.out.println("Error while loading");
        }
      }
    });
  }


  public void executeSaveAction(Button saveButton) {
    saveButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        try {
          FileChooser chooser = new FileChooser();
          chooser.setTitle("Save Diagram");
          File file = chooser.showSaveDialog(new Stage());
          FileOutputStream stream = new FileOutputStream(file);
          byte[] data = diagram.saveDiagram();
          stream.write(data);
          stream.close();
        } catch (IOException e) {
          System.out.println("Error while saving");
        }
      }
    });
  }

  public static void main(String[] args) {
    launch(args);
  }
}
