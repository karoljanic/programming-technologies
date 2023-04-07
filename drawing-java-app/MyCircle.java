import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Scale;

/**
 * Class overriging Circle. It represents circles to be created and contains
 * functions for handling them.
 */
public class MyCircle extends Circle implements TransformationsInterface {
    /** Variable holding state of shape. */
    private boolean isActive;

    /**
     * Class constructor which initialize shape on base two points - center point
     * and point on circle.
     * 
     * @param x1 X coordinate of center point
     * @param y1 Y coordinate of center point
     * @param x2 X coordinate of point on circle
     * @param y2 Y coordinate of point on circle
     */
    MyCircle(double x1, double y1, double x2, double y2) {
        super();

        double r = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        this.setCenterX(x1);
        this.setCenterY(y1);
        this.setRadius(r);

        this.setStroke(Appearance.strokeColor);
        this.setFill(Appearance.fillColor);

        this.isActive = false;

        setOnMouseClicked(new MyMouseEventHandler());
        setOnMouseDragged(new MyMouseEventHandler());
        setOnScroll(new MyScrollEventHandler());
    }

    @Override
    /** Setting shape as active. */
    public void setAsActive() {
        isActive = true;
        setOpacity(0.5);
    }

    @Override
    public void setAsInactive() {
        isActive = false;
        setOpacity(1.0);
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public boolean includePoint(double x, double y) {
        return getBoundsInLocal().contains(sceneToLocal(x, y));
    }

    @Override
    public void translateCenterX(double dx) {
        setLayoutX(getLayoutX() + dx);
    }

    @Override
    public void translateCenterY(double dy) {
        setLayoutY(getLayoutY() + dy);
    }

    @Override
    public void resizeDimensions(double k) {
        Scale scale = new Scale();
        scale.setPivotX(getCenterX());
        scale.setPivotY(getCenterY());
        scale.setX(1 + k);
        scale.setY(1 + k);

        double newRadius = (1 + k) * getRadius();
        if (newRadius > 10 || newRadius > getRadius()) {
            getTransforms().add(scale);
        }
    }

    @Override
    public void rotateByAngle(double angle) {

    }

    @Override
    public void setFillColor(Color color) {
        setFill(color);
        setStroke(color);
    }
}
