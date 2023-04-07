import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;

/**
 * Implementation of a handle on scroll movement.
 * It provides rotation and scaling of the figure.
 */
public class MyScrollEventHandler implements EventHandler<ScrollEvent> {
    /** Declaration of the type on which the event will be executed . */
    TransformationsInterface shape;

    /**
     * Performing actions.
     * Depending on the scroll direction, the action is selected.
     * -> normal scroll move -> e.getDeltaX() = 0 and e.getDeltaY() > 0
     * -> scroll move + SHIFT -> e.getDeltaX() > 0 and e.getDeltaY() = 0
     * 
     * @param event detected scroll event
     */
    private void scaleOrRotate(ScrollEvent event) {
        shape.resizeDimensions(event.getDeltaY() * 0.001);
        shape.rotateByAngle(event.getDeltaX() * 0.1);
    }

    @Override
    public void handle(ScrollEvent event) {
        shape = (TransformationsInterface) event.getSource();

        if (event.getEventType() == ScrollEvent.SCROLL && shape.isActive()) {
            scaleOrRotate(event);
        }
    }
}