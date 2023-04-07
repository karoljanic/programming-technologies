import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class PascalTriangleApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("PascalTriangle");

        VBox root = new VBox(10);
        root.setAlignment(Pos.TOP_CENTER);
        root.setFillWidth(false);
        root.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        Label textLabel = new Label("Enter number of rows: ");
        textLabel.setFont(Font.font ("Verdana", 14));

        TextField inputN = new TextField();
        inputN.setMaxWidth(50);
        inputN.setMinWidth(50);
        inputN.setFont(Font.font ("Verdana", 14));

        Button generateButton = new Button("Generate!");
        generateButton.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        generateButton.setFont(Font.font ("Verdana", 14));
        generateButton.setTextFill(Color.WHITE);
        
        Label errorLabel = new Label("");
        errorLabel.setFont(Font.font ("Verdana", 14));
        errorLabel.setTextFill(Color.RED);

        VBox triangle = new VBox(0);
        triangle.setAlignment(Pos.TOP_CENTER);
        triangle.setPadding(new Insets(0, 10, 20, 10));

        generateButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                try {
                    errorLabel.setText("");
                    triangle.getChildren().clear();

                    int n = Integer.parseInt(inputN.getText()) - 1;
                    PascalTriangle.generate(n);

                    int maxN = PascalTriangle.triangle[n + 1][(int) n / 2];
                    double size = String.valueOf(maxN).length() * 20;

                    if(n * size + 20 > Screen.getPrimary().getVisualBounds().getWidth()) {
                        throw new IllegalArgumentException();
                    }

                    for (int i = 1; i < PascalTriangle.triangle.length; i++) {
                        HBox nextRow = new HBox(0);
                        int m = (n - i + 1) / 2;

                        Label tab = new Label("");
                        tab.setMaxWidth(size * 0.5);
                        tab.setMinWidth(size * 0.5);
                        tab.setPrefWidth(size * 0.5);

                        Label space = new Label("");
                        space.setMaxWidth(m * size);
                        space.setMinWidth(m * size);
                        space.setPrefWidth(m * size);

                        if (n % 2 == i % 2) {
                            nextRow.getChildren().addAll(tab);
                        }

                        nextRow.getChildren().addAll(space);

                        for (int j = 0; j < i; j++) {
                            Label nextLabel = new Label(String.valueOf(PascalTriangle.triangle[i][j]));
                            nextLabel.setMaxWidth(size);
                            nextLabel.setMinWidth(size);
                            nextLabel.setPrefWidth(size);
                            nextLabel.setMaxHeight(30);
                            nextLabel.setMinHeight(30);
                            nextLabel.setPrefHeight(30);
                            nextLabel.setAlignment(Pos.CENTER);
                            nextLabel.setFont(Font.font ("Verdana", 14));
                            nextRow.getChildren().addAll(nextLabel);
                        }

                        triangle.getChildren().addAll(nextRow);
                    }

                } catch (NumberFormatException ex) {
                    errorLabel.setText("Input number must be integer!");
                } catch (IllegalArgumentException ex) {
                    errorLabel.setText("Input number is incorrect!");
                } catch (Exception exception) {
                    
                }

                primaryStage.sizeToScene();
                primaryStage.centerOnScreen();
            }
        });

        HBox enterDateRow = new HBox(10);
        enterDateRow.setPadding(new Insets(20, 10, 0, 10));
        enterDateRow.getChildren().addAll(textLabel, inputN, generateButton);
        enterDateRow.setAlignment(Pos.CENTER);

        root.getChildren().addAll(enterDateRow, errorLabel, triangle);

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();

        primaryStage.show();
    }
}