import javafx.scene.shape.Line;

/**
 * Class overriging Line. It represents segment to be created.
 * Used only as prompting shape so does not need functions implementation.
 */
public class MySegment extends Line {
    /**
     * Class constructor which initialize shape on base two points - ends of
     * segment.
     * 
     * @param x1 X coordinate of first end
     * @param y1 Y coordinate of first end
     * @param x2 X coordinate of second end
     * @param y2 Y coordinate of second end
     */
    MySegment(double x1, double y1, double x2, double y2) {
        super(x1, y1, x2, y2);

        this.setStroke(Appearance.strokeColor);
        this.setFill(Appearance.fillColor);
    }

}
