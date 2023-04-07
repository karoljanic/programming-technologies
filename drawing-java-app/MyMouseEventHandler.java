import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Window;

/**
 * Implementation of a handle on mouse click.
 * It provides movement, changing activity and changing color of the figure.
 */
public class MyMouseEventHandler implements EventHandler<MouseEvent> {
    /** Declaration of the type on which the event will be executed . */
    TransformationsInterface shape;

    /** X coordinate of last mouse click. */
    private double x;

    /** Y coordinate of last mouse click. */
    private double y;

    /**
     * Performing move of figure.
     * 
     * @param event detected mouse event
     */
    private void move(MouseEvent event) {
        double dx = event.getSceneX() - x;
        double dy = event.getSceneY() - y;

        if (shape.includePoint(x, y)) {
            shape.translateCenterX(dx);
            shape.translateCenterY(dy);
        }

        x += dx;
        y += dy;
    }

    /**
     * Performing change of figure color fill.
     * A popup appears with Color Picker to choose new fill color.
     * 
     * @param event detected mouse event
     */
    private void changeColor(MouseEvent event) {
        Popup colorPickerPopup = new Popup();

        ColorPicker myColorPicker = new ColorPicker();
        myColorPicker.getStyleClass().add("button");
        myColorPicker.setOnAction(e -> {
            Color chosenColor = myColorPicker.getValue();
            shape.setFillColor(chosenColor);
            colorPickerPopup.hide();
        });

        colorPickerPopup.getContent().addAll(myColorPicker);
        colorPickerPopup.sizeToScene();
        colorPickerPopup.setX(event.getX());
        colorPickerPopup.setY(event.getY());

        colorPickerPopup.show(Window.getWindows().get(0));
    }

    @Override
    public void handle(MouseEvent event) {
        shape = (TransformationsInterface) event.getSource();

        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            if (event.getButton() == MouseButton.PRIMARY) {
                x = event.getSceneX();
                y = event.getSceneY();

                if (shape.isActive()) {
                    shape.setAsInactive();
                } else {
                    shape.setAsActive();
                }
            } else if (event.getButton() == MouseButton.SECONDARY) {
                changeColor(event);
            }
        }
        if (event.getEventType() == MouseEvent.MOUSE_DRAGGED && shape.isActive()) {
            move(event);
        }
    }
}