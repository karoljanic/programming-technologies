import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

/**
 * Class overriding Pane so that it functions as a 'canvas'.
 * It manages the creation of shapes(circle, triangle and rectangle) and
 * receives mouse events.
 */
public class MyCanvas extends Pane {
    /** Enum holding available types of currently creating shape. */
    private enum CreatingShape {
        NONE,
        CIRCLE,
        TRIANGLE,
        RECTANGLE
    }

    /** Variable holding currently creating shape type. */
    private CreatingShape creatingShape;

    /** Array saving history of user clicks. */
    private Point2D[] clickedPoints = new Point2D[2];

    /** Array holding shapes which are creating only for promting reasons. */
    private ArrayList<Shape> promptingShapes;

    /**
     * Class constructor which initializes canva Pane
     * with given dimensions @param width x @param height .
     * 
     * @param width  width of creating object
     * @param height height of creating object
     */
    MyCanvas(double width, double height) {
        super();

        this.setMinWidth(width);
        this.setMaxWidth(width);
        this.setPrefWidth(width);
        this.setMinHeight(height);
        this.setMaxHeight(height);
        this.setPrefHeight(height);

        creatingShape = CreatingShape.NONE;
        clickedPoints[0] = null;
        clickedPoints[1] = null;
        promptingShapes = new ArrayList<>();

        this.setOnMouseClicked(event -> {
            processMouseClick(event.getX(), event.getY());
        });

        this.setOnMouseMoved(event -> {
            processMouseMove(event.getX(), event.getY());
        });
    }

    /**
     * Function which enables creating new circle.
     * It disables creating other shapes if they was enabled.
     */
    public void startCreatingCircle() {
        deleteOldPromptingShapes();
        clickedPoints[0] = null;
        clickedPoints[1] = null;
        creatingShape = CreatingShape.CIRCLE;
    }

    /**
     * Function which enables creating new triangle.
     * It disables creating other shapes if they was enabled.
     */
    public void startCreatingTriangle() {
        deleteOldPromptingShapes();
        clickedPoints[0] = null;
        clickedPoints[1] = null;
        creatingShape = CreatingShape.TRIANGLE;
    }

    /**
     * Function which enables creating new rectangle.
     * It disables creating other shapes if they was enabled.
     */
    public void startCreatingRectangle() {
        deleteOldPromptingShapes();
        clickedPoints[0] = null;
        clickedPoints[1] = null;
        creatingShape = CreatingShape.RECTANGLE;
    }

    /**
     * Function which resolve new click on canvas.
     * Depending on the situation it creates new shape or new prompting shape or
     * ignores click.
     * 
     * @param x X coordinate of click
     * @param y Y coordinate of click
     */
    private void processMouseClick(double x, double y) {
        if (creatingShape == CreatingShape.NONE) {
            return;
        }

        if (clickedPoints[0] == null) {
            clickedPoints[0] = new Point2D(x, y);
        } else if (clickedPoints[1] == null && creatingShape == CreatingShape.TRIANGLE) {
            clickedPoints[1] = new Point2D(x, y);
        } else {
            createNewShape(x, y);

            creatingShape = CreatingShape.NONE;
            clickedPoints[0] = null;
            clickedPoints[1] = null;
        }
    }

    /**
     * Function which resolve new mouse move on canvas.
     * Depending on the situation it creates new prompting shape or ignores move.
     * 
     * @param x X coordinate of current mouse position
     * @param y Y coordinate of current mouse position
     */
    private void processMouseMove(double x, double y) {
        deleteOldPromptingShapes();

        if (creatingShape == CreatingShape.NONE) {
            return;
        }

        if (clickedPoints[0] == null) {
            return;
        }

        promptingShapes.add(createNewShape(x, y));
    }

    /**
     * Function which create new shape on base clicks history.
     * It should be called if set clickedPoints make sense.
     * 
     * @param x X coordinate of last click
     * @param y Y coordinate of last click
     * @return created shape or null if any shape was created
     */
    private Shape createNewShape(double x, double y) {
        switch (creatingShape) {
            case CIRCLE:
                MyCircle promptingCircle = new MyCircle(
                        clickedPoints[0].getX(),
                        clickedPoints[0].getY(),
                        x, y);

                this.getChildren().add(promptingCircle);
                return promptingCircle;
            case TRIANGLE:
                if (clickedPoints[1] == null) {
                    MySegment promptingSegment = new MySegment(
                            clickedPoints[0].getX(),
                            clickedPoints[0].getY(),
                            x, y);

                    this.getChildren().add(promptingSegment);
                    return promptingSegment;
                } else {
                    MyTriangle promptingTriangle = new MyTriangle(
                            clickedPoints[0].getX(), clickedPoints[0].getY(),
                            clickedPoints[1].getX(), clickedPoints[1].getY(),
                            x, y);
                    this.getChildren().add(promptingTriangle);
                    return promptingTriangle;
                }
            case RECTANGLE:
                MyRectangle promptingRectangle = new MyRectangle(
                        clickedPoints[0].getX(),
                        clickedPoints[0].getY(),
                        x, y);
                this.getChildren().add(promptingRectangle);
                return promptingRectangle;
            default:
                return null;
        }
    }

    /** Function deletes prompting shapes created in last mouse move event. */
    private void deleteOldPromptingShapes() {
        for (Shape shape : promptingShapes) {
            if (shape != null) {
                this.getChildren().remove(shape);
            }
        }
    }
}
