import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

/**
 * The class that contains the menu used to get the simulation parameters from the user and run simulation.
 * All elements are appropriately stylized.
 */
public class InputMenu extends VBox {
    /**
     * The class that pairs Label and TextInput. 
     * It is used to get one parameter from the user. 
     */
    public class CustomTextInput extends HBox {
        /** Parameter name. */
        private Label label;

        /** Place to enter the parameter value. */
        private TextField textField;

        /**
         * Class constructor which initialize parameter getter on base parameter name.
         * @param text name of parameter to get
         */
        CustomTextInput(String text) {
            super(10);

            setAlignment(Pos.TOP_CENTER);

            label = new Label(text);
            label.setFont(Font.font ("Verdana", 14));

            textField = new TextField();
            textField.setMaxWidth(60);
            textField.setMinWidth(60);
            textField.setFont(Font.font ("Verdana", 14));

            getChildren().addAll(label, textField);
        }

        /**
         * Getter of entered value
         * @return entered value
         */
        String getText() {
            return textField.getText();
        }
    }

    /** Button used to run simulation. */
    public Button generateButton;

    /** HBox which puts everything in order. */
    private HBox inputsGroup;

    /** Inputs used in menu. */
    private CustomTextInput inputN, inputM, inputK, inputP;

    /**
     * Class constructor. It initializes all required inputs and stylized the menu.
     */
    InputMenu() {
        super(0);
        
        inputN = new CustomTextInput("rows: ");
        inputM = new CustomTextInput("columns: ");
        inputK = new CustomTextInput("k: ");
        inputP = new CustomTextInput("p: ");

        generateButton = new Button("Generate!");
        generateButton.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        generateButton.setFont(Font.font ("Verdana", 14));
        generateButton.setTextFill(Color.WHITE);
    
        inputsGroup = new HBox(10);
        inputsGroup.setAlignment(Pos.CENTER);
        inputsGroup.getChildren().addAll(inputN, inputM, inputK, inputP, generateButton);

        setPadding(new Insets(20, 20, 20, 20));
        setAlignment(Pos.TOP_CENTER);
        setFillWidth(false);

        getChildren().addAll(inputsGroup);
    }

    /**
     * Getter of entered parameters. It converts inputs to proper numbers. 
     * @return 4-element array containing, in sequence: [n - int, m - int, k - double, p - double]
     * @throws IllegalArgumentException if any of the parameters is not a number 
     */
    Object[] getInitialParameters() {
        Object[] parameters = new Object[4];
        
        try {
            parameters[0] = Integer.parseInt(inputN.getText());
            parameters[1] = Integer.parseInt(inputM.getText());
            parameters[2] = Double.parseDouble(inputK.getText());
            parameters[3] = Double.parseDouble(inputP.getText());
        }
        catch(Exception e) {
            throw new IllegalArgumentException();
        }

        return parameters;
    }
}
