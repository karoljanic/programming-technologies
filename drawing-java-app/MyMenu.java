import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;

/**
 * Class overrides HBox so that it functions as a topbar menu.
 * Contains and styles menu buttons to which funcionality must be attached in
 * addition.
 */
public class MyMenu extends HBox {
    /**
     * Nested class overriding Button styling a single button.
     */
    public class MyMenuButton extends Button {
        final private static Background normalBackground = new Background(
                new BackgroundFill(Appearance.primaryAppColor, new CornerRadii(3), Insets.EMPTY));
        final private static Background onHoverBackground = new Background(
                new BackgroundFill(Appearance.secondaryAppColor, new CornerRadii(7), Insets.EMPTY));

        /**
         * Class constructor setting the button styles and initializes the button.
         * 
         * @param arg button text to set
         */
        MyMenuButton(String arg) {
            super(arg);

            this.setFont(Appearance.appFont);
            this.setBackground(normalBackground);

            this.setOnMouseEntered(event -> this.setBackground(onHoverBackground));
            this.setOnMouseExited(event -> this.setBackground(normalBackground));
        }
    }

    /** Button used to draw circle. */
    public MyMenuButton createCircle;

    /** Button used to draw triangle. */
    public MyMenuButton createTriangle;

    /** Button used to draw rectangle. */
    public MyMenuButton createRectangle;

    /** Button used to display infomations. */
    public MyMenuButton information;

    /** Button used to display instruction. */
    public MyMenuButton instruction;

    /** Class constructor initializing menu. */
    MyMenu() {
        super(10);

        createCircle = new MyMenuButton("Utwórz okrąg");
        createTriangle = new MyMenuButton("Utwórz trojkąt");
        createRectangle = new MyMenuButton("Utwórz prostokąt");
        information = new MyMenuButton("Info");
        instruction = new MyMenuButton("Instrukcja");

        setMargin(information, new Insets(0, 0, 0, 200));

        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(20, 20, 10, 20));

        this.setOnMouseEntered(event -> this.setBackground(
                new Background(new BackgroundFill(Appearance.primaryAppColor, CornerRadii.EMPTY, Insets.EMPTY))));
        this.setOnMouseExited(event -> this.setBackground(
                new Background(new BackgroundFill(Appearance.secondaryAppColor, CornerRadii.EMPTY, Insets.EMPTY))));

        this.getChildren().addAll(createCircle, createTriangle, createRectangle, information, instruction);
    }
}
