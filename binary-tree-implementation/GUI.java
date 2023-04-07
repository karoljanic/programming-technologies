import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Class represents GUI of Application for initializing, inserting, deleting,
 * searching and drawing BinaryTrees using comunication multiServer-client.
 */
public class GUI extends Application {
    /** Primary stage of Application */
    private Stage stage;

    /** Root of Application scene */
    private VBox root;

    /** Pane used to drawing generated BinaryTree */
    private ScrollPane graphVisualisation;

    /** Client used for communication with server */
    ServerClient client;

    /**
     * Main function - running Application
     * @param args list of arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starting Application
     * 
     * @param primaryStage primary stage of Application
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            client = new ServerClient();
        } catch (Exception ex) {
            System.out.println("\nProblem With Connection. Check Server.");
            System.exit(0);
        }

        stage = primaryStage;
        primaryStage.setTitle("Binary Tree");

        initializeApplication();

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();

        primaryStage.show();
    }

    /**
     * Stopping Application
     */
    @Override
    public void stop() {
        try {
            client.close();
        } catch (IOException e) {
            System.out.println("\nProblem With Closing Connection With Server. Try Again.");
        }
    }

    /**
     * Initializing Application elements
     */
    private void initializeApplication() {
        root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setFillWidth(false);
        root.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        initializeInputs();

        graphVisualisation = new ScrollPane();
        graphVisualisation.setFitToWidth(true);
        graphVisualisation.setFitToHeight(true);
    
        root.getChildren().add(graphVisualisation);
    }

    /**
     * Initializing Appliucation Inputs
     */
    private void initializeInputs() {
        Label errorLabel = new Label("");
        errorLabel.setFont(Font.font("Verdana", 13));
        errorLabel.setTextFill(Color.RED);

        Label text1Label = new Label(" Tree Values Type:");
        text1Label.setFont(Font.font("Verdana", 13));

        ChoiceBox<String> valueTypeChoiceBox = new ChoiceBox<String>();
        valueTypeChoiceBox.setMinWidth(100);
        valueTypeChoiceBox.setMaxWidth(100);

        valueTypeChoiceBox.getItems().add("Integer");
        valueTypeChoiceBox.getItems().add("Double");
        valueTypeChoiceBox.getItems().add("String");
        valueTypeChoiceBox.setValue("Integer");

        Button generateButton = new Button("Generate Tree");
        generateButton.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        generateButton.setFont(Font.font("Verdana", 13));
        generateButton.setTextFill(Color.WHITE);
        generateButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                errorLabel.setText("");
                int index = valueTypeChoiceBox.getSelectionModel().getSelectedIndex();
                try {
                    switch (index) {
                        case 0:
                            client.initializeTree(DataType.INTEGER);
                            drawTree();
                            break;
                        case 1:
                            client.initializeTree(DataType.DOUBLE);
                            drawTree();
                            break;
                        case 2:
                            client.initializeTree(DataType.STRING);
                            drawTree();
                            break;
                        default:
                            break;
                    }
                } catch (IOException ex) {
                    errorLabel.setText("Problem With Initializing Binary Tree. Try Again.");
                }
            }
        });

        Button drawButton = new Button("Draw Tree");
        drawButton.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        drawButton.setFont(Font.font("Verdana", 13));
        drawButton.setTextFill(Color.WHITE);

        drawButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                try {
                    drawTree();
                } catch (Exception ex) {

                }
            }
        });

        Label text2Label = new Label("Value To Operation:");
        text2Label.setFont(Font.font("Verdana", 13));

        TextField valueInputTextField = new TextField();
        valueInputTextField.setMaxWidth(100);
        valueInputTextField.setMinWidth(100);
        valueInputTextField.setFont(Font.font("Verdana", 13));

        Button insertButton = new Button("Insert Element");
        insertButton.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        insertButton.setFont(Font.font("Verdana", 13));
        insertButton.setTextFill(Color.WHITE);

        insertButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                errorLabel.setText("");
                String value = valueInputTextField.getText();
                try {
                    client.insertElement(value);
                    drawTree();
                } catch (NumberFormatException ex) {
                    errorLabel.setText("Incorrect Value!");
                } catch (IOException ex) {
                    errorLabel.setText("Incorrect Value!");
                } catch (IllegalAccessError ex) {
                    errorLabel.setText("Tree Is Not Initialized!");
                }
            }
        });

        Button deleteButton = new Button("Delete Element");
        deleteButton.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        deleteButton.setFont(Font.font("Verdana", 13));
        deleteButton.setTextFill(Color.WHITE);

        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                errorLabel.setText("");
                String value = valueInputTextField.getText();
                try {
                    client.deleteElement(value);
                    drawTree();
                } catch (NumberFormatException ex) {
                    errorLabel.setText("Incorrect Value!");
                } catch (IOException ex) {
                    errorLabel.setText("Incorrect Value!");
                } catch (IllegalAccessError ex) {
                    errorLabel.setText("Tree Is Not Initialized!");
                }
            }
        });

        Button searchButton = new Button("Search Element");
        searchButton.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        searchButton.setFont(Font.font("Verdana", 13));
        searchButton.setTextFill(Color.WHITE);

        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                errorLabel.setText("");
                String value = valueInputTextField.getText();
                try {
                    boolean elementExist = client.searchElement(value);

                    Alert alert = new Alert(AlertType.NONE);
                    alert.setAlertType(AlertType.CONFIRMATION);
                    if (elementExist) {
                        alert.setHeaderText("Element " + value + " exist!");
                    } else {
                        alert.setHeaderText("Element " + value + " does not exist!");
                    }
                    alert.show();

                } catch (NumberFormatException ex) {
                    errorLabel.setText("Incorrect Value!");
                } catch (IOException ex) {
                    errorLabel.setText("Incorrect Value!");
                } catch (IllegalAccessError ex) {
                    errorLabel.setText("Tree Is Not Initialized!");
                }
            }
        });

        HBox line1 = new HBox(20);
        line1.setPadding(new Insets(20, 10, 0, 10));
        line1.getChildren().addAll(text1Label, valueTypeChoiceBox, generateButton, drawButton);
        line1.setAlignment(Pos.CENTER_LEFT);

        HBox line2 = new HBox(20);
        line2.setPadding(new Insets(10, 10, 0, 10));
        line2.getChildren().addAll(text2Label, valueInputTextField, insertButton, deleteButton, searchButton);
        line2.setAlignment(Pos.CENTER_LEFT);

        VBox lines = new VBox(0);
        lines.setAlignment(Pos.CENTER_LEFT);
        lines.getChildren().addAll(line1, line2);

        root.getChildren().addAll(lines, errorLabel);
    }

    /**
     * Drawing BinaryTree
     * @throws IOException if during getting data occurs exception
     */
    private void drawTree() throws IOException {
        TreeDrawer.drawTree(client.getRootNode(), graphVisualisation);

        stage.sizeToScene();
        stage.centerOnScreen();
    }
}
