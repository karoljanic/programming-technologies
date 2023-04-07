import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Class holding all the parameters related to the appearance of the application.
 */
public class Appearance {
    /** Font and size of text used in application. */
    final public static Font appFont = Font.font("Verdana", 14);

    /** Color of application background. */
    final public static Color primaryAppColor = Color.LIGHTGRAY;

    /** Color of application top bar. */
    final public static Color secondaryAppColor = Color.WHITESMOKE;

    /** Color of all shapes outlines. 
     * This value is base value. User can change it for each figure individually. */
    final public static Color strokeColor = Color.BLACK;

    /** Color of all shapes fills.
     * This value is base value. User can change it for each figure individually. */
    final public static Color fillColor = Color.TRANSPARENT;
}
