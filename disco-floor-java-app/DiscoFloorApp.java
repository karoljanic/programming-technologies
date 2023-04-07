import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Screen;

/**
 * The main class of simulation application. This assembles InputMenu and DiscoFloor classes.
 * This application is used to simulate random color changes of a grid of squares.
 */
public class DiscoFloorApp extends Application {
    /** Main root of scene. */
    private VBox root;

    /** Menu used to get simulation parameters. */
    private InputMenu menu;

    /** Label used to display information about incorrect parameters. */
    private Label errorLabel;

    /** Main part of application - proper simulation class. */
    private DiscoFloor discoFloor;

    /** Constant used to define the size of one tile in the grid. */
    private final static double TILE_DIMENSION = 20.0;

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
        stage.setTitle("DiscoFloorApp");

        root = new VBox(0);
        menu = new InputMenu();
        errorLabel = new Label("");
        errorLabel.setFont(Font.font ("Verdana", 14));
        errorLabel.setTextFill(Color.RED);
        errorLabel.setPadding(new Insets(0, 0, 20, 0));

        discoFloor = new DiscoFloor(0, 0, 0.0, 0.0, 0.0);

        menu.generateButton.setOnMouseClicked(event -> {
            try {
                discoFloor.destroyThreads();
                root.getChildren().remove(discoFloor);
                root.getChildren().remove(errorLabel);

                Object[] params = menu.getInitialParameters();

                if((int)params[0] < 1) {
                    throw new IllegalArgumentException();
                }
                if((int)params[1] < 1) {
                    throw new IllegalArgumentException();
                }
                if((int)params[1] * TILE_DIMENSION + 20 > Screen.getPrimary().getVisualBounds().getWidth()) {
                    throw new IllegalArgumentException();
                }
                if((int)params[0] * TILE_DIMENSION + 120 > Screen.getPrimary().getVisualBounds().getHeight()) {
                    throw new IllegalArgumentException();
                }
                if((double)params[2] < 0.0) {
                    throw new IllegalArgumentException();
                }
                if((double)params[3] < 0.0 || (double)params[3] > 1.0) {
                    throw new IllegalArgumentException();
                }

                discoFloor = new DiscoFloor((int)params[0], (int)params[1], (double)params[2], (double)params[3], TILE_DIMENSION);
                root.getChildren().add(discoFloor);
            }
            catch(IllegalArgumentException e) {
                errorLabel.setText("Bad initial parameters!");
                root.getChildren().add(errorLabel);
            }

            stage.sizeToScene();
            stage.centerOnScreen();
        });
    
        root.setAlignment(Pos.TOP_CENTER);
        root.setFillWidth(false);
        root.getChildren().add(menu);

        stage.setScene(new Scene(root));
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.setOnCloseRequest(e -> { discoFloor.destroyThreads(); });

        stage.show();
    }

    /**
     * The main function that runs the application
     * @param args parameters used in run application
    */
    public static void main(String[] args) {
        launch(args);
    }
}
