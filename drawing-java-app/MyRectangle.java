import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Class overriging Rectangle. It represents rectangles to be created and
 * contains functions for handling them.
 */
public class MyRectangle extends Rectangle implements TransformationsInterface {
    /** Variable holding state of shape. */
    boolean isActive;

    /**
     * Class constructor which initialize shape on base two points - opposite
     * rectangle vertices.
     * 
     * @param x1 X coordinate of first vertex
     * @param y1 Y coordinate of first vertex
     * @param x2 X coordinate of second vertex
     * @param y2 Y coordinate of second vertex
     */
    MyRectangle(double x1, double y1, double x2, double y2) {
        super();

        double width = Math.abs(x2 - x1);
        double height = Math.abs(y2 - y1);

        this.setX(Math.min(x1, x2));
        this.setY(Math.min(y1, y2));
        this.setWidth(width);
        this.setHeight(height);

        this.setStroke(Appearance.strokeColor);
        this.setFill(Appearance.fillColor);

        this.isActive = false;

        setOnMouseClicked(new MyMouseEventHandler());
        setOnMouseDragged(new MyMouseEventHandler());
        setOnScroll(new MyScrollEventHandler());
    }

    @Override
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
        double dx = getWidth() * k;
        double dy = getHeight() * k;

        double newWidth = getWidth() + dx;
        double newHeight = getHeight() + dy;

        if (newWidth > 5 && newHeight > 5 || newWidth > getWidth() || newHeight > getHeight()) {
            setWidth(getWidth() + dx);
            setHeight(getHeight() + dy);

            setX(getX() - dx / 2);
            setY(getY() - dy / 2);
        }
    }

    @Override
    public void rotateByAngle(double angle) {
        double newRotate = getRotate() - angle;
        if (newRotate > 360.0) {
            newRotate -= 360;
        } else if (newRotate < -360) {
            newRotate += 360;
        }

        setRotate(newRotate);
    }

    @Override
    public void setFillColor(Color color) {
        setFill(color);
        setStroke(color);
    }
}
