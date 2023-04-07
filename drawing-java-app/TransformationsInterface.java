import javafx.scene.paint.Color;

/**
 * Interface containing all necessary functions for creating and handling
 * shapes.
 * Every created shape must implement it.
 */
public interface TransformationsInterface {
    /** Setting figure as active. */
    public void setAsActive();

    /** Setting figure as inactive. */
    public void setAsInactive();

    /**
     * Checking if figure is active.
     * 
     * @return true if figure is active, otherwise false
     */
    public boolean isActive();

    /**
     * Checking if point is included in figure.
     * 
     * @param x X coordinate of point to check
     * @param y Y coordinate of point to check
     * @return true if point is included, otherwise false
     */
    public boolean includePoint(double x, double y);

    /**
     * Translating figure horizontal.
     * 
     * @param dx number of pixels to move
     */
    public void translateCenterX(double dx);

    /**
     * Translating figure vertical.
     * 
     * @param dy number of pixels to move
     */
    public void translateCenterY(double dy);

    /**
     * Scaling figure.
     * 
     * @param k scale factor
     */
    public void resizeDimensions(double k);

    /**
     * Rotating figure.
     * 
     * @param angle angle to rotation in degrees
     */
    public void rotateByAngle(double angle);

    /**
     * Setting color of figure.
     * 
     * @param color color to set
     */
    public void setFillColor(Color color);
}
