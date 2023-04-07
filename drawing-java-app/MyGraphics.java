import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * The main application class that assembles all the parts into a whole and
 * displays them.
 * The application is used to draw simple shapes: circles, rectangles, triangles
 * and to edit them: move, rotate, scale, change color.
 */
public class MyGraphics extends Application {

    @Override
    public void start(Stage startStage) throws Exception {
        initApplication(startStage);
    }

    /**
     * Function to initiate an application.
     * 
     * @param stage stage to set scene and display it
     */
    private void initApplication(Stage stage) {
        stage.setTitle("MyGraphics");

        Rectangle2D targetWindowSize = Screen.getPrimary().getVisualBounds();

        VBox root = new VBox(0);
        MyMenu menu = new MyMenu();
        MyCanvas canvas = new MyCanvas(targetWindowSize.getWidth(), targetWindowSize.getHeight());
        ProgramInformation programInfo = new ProgramInformation();
        ProgramInstruction instruction = new ProgramInstruction();

        menu.createCircle.setOnMouseClicked(event -> {
            canvas.startCreatingCircle();
        });
        menu.createRectangle.setOnMouseClicked(event -> {
            canvas.startCreatingRectangle();
        });
        menu.createTriangle.setOnMouseClicked(event -> {
            canvas.startCreatingTriangle();
        });
        menu.information.setOnMouseClicked(event -> {
            programInfo.showAndWait();
        });
        menu.instruction.setOnMouseClicked(event -> {
            instruction.showAndWait();
        });

        root.setAlignment(Pos.TOP_CENTER);
        root.getChildren().addAll(menu, canvas);

        stage.setScene(new Scene(root));

        stage.setWidth(targetWindowSize.getWidth());
        stage.setHeight(targetWindowSize.getHeight());

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
