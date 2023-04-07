import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;

/**
 * Class overriging Polygon. It represents triangles to be created and contains
 * functions for handling them.
 */
public class MyTriangle extends Polygon implements TransformationsInterface {
    /** Variable holding state of shape. */
    boolean isActive;

    /**
     * Class constructor which initialize shape on base three points - vertices of
     * triangle.
     * 
     * @param x1 X coordinate of first vertex
     * @param y1 Y coordinate of first vertex
     * @param x2 X coordinate of second vertex
     * @param y2 Y coordinate of second vertex
     * @param x3 X coordinate of third vertex
     * @param y3 Y coordinate of third vertex
     */
    MyTriangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        super(x1, y1, x2, y2, x3, y3);

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
        Point2D scaleCenter = calculateCentroid();

        Scale scale = new Scale();

        scale.setPivotX(scaleCenter.getX());
        scale.setPivotY(scaleCenter.getY());
        scale.setX(1 + k);
        scale.setY(1 + k);

        double minimalSideLenght = Double.MAX_VALUE;
        for (int i = 0; i < 3; i++) {
            Point2D p1 = localToParent(getPoints().get(2 * i), getPoints().get(2 * i + 1));
            Point2D p2 = localToParent(getPoints().get((2 * i + 2) % 3), getPoints().get((2 * i + 3) % 3));

            double d = Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2);
            if (d < minimalSideLenght) {
                minimalSideLenght = d;
            }
        }

        if (minimalSideLenght * (1 + k) > 250) {
            getTransforms().add(scale);
        }

    }

    @Override
    public void rotateByAngle(double angle) {
        Point2D rotationCenter = calculateCentroid();

        Rotate rotation = new Rotate();

        rotation.setPivotX(rotationCenter.getX());
        rotation.setPivotY(rotationCenter.getY());
        rotation.setAngle(-angle);

        getTransforms().add(rotation);
    }

    @Override
    public void setFillColor(Color color) {
        setFill(color);
        setStroke(color);
    }

    /**
     * Calculating triangle centroid on base current verices.
     * It is center of mass if polygon is convex.
     * 
     * @return point representing triangle centeroid
     */
    private Point2D calculateCentroid() {
        double centroidX = 0, centroidY = 0, signedArea = 0;
        int n = getPoints().size();

        for (int i = 0; i < n; i += 2) {
            double x0 = getPoints().get(i);
            double y0 = getPoints().get(i + 1);
            double x1 = getPoints().get((i + 2) % n);
            double y1 = getPoints().get((i + 3) % n);

            double A = (x0 * y1) - (x1 * y0);
            signedArea += A;

            centroidX += (x0 + x1) * A;
            centroidY += (y0 + y1) * A;
        }

        signedArea *= 0.5;
        centroidX = (centroidX) / (6 * signedArea);
        centroidY = (centroidY) / (6 * signedArea);

        return new Point2D(centroidX, centroidY);

    }
}
