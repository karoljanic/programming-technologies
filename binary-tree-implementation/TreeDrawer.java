import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Screen;

/**
 * Class used to draw BinaryTree on base its Nodes
 */
public class TreeDrawer {
    /** Radius of circle representing one Node */
    private final static double RADIUS = 17.0;

    /** Color of circle representing Node */
    private final static Color CIRCLE_COLOR = Color.GREEN;
    
    /** Colot of line repreenting BinaryTree Edge */
    private final static Color LINE_COLOR = Color.NAVY;

    /** Color of line representing BinaryTree edges */
    private final static Color TEXT_COLOR = Color.BLACK;

    /**
     * Drawing BinaryTree on given ScrollPane
     * @param tree BinaryTree root
     * @param pane ScrollPane on which BinaryTree will be drawn
     */
    public static void drawTree(Node tree, ScrollPane pane) {
        if(tree == null) {
            Pane drawingPane = new Pane();
            drawingPane.setMinWidth(50);
            drawingPane.setMinHeight(50);
            drawingPane.setMaxWidth(50);
            drawingPane.setMaxHeight(50);

            pane.setContent(drawingPane);
        }
        else {
            int levelsCount = findTreeHeight(tree);
            int d = (int) Math.pow(2, levelsCount);

            double canvasWidth = 2 * RADIUS * d;
            double canvasHeight = 4 * RADIUS * levelsCount;

            Pane drawingPane = new Pane();
            drawingPane.setMinWidth(canvasWidth);
            drawingPane.setMinHeight(canvasHeight);
            drawingPane.setMaxWidth(canvasWidth);
            drawingPane.setMaxHeight(canvasHeight);

            drawTree(canvasWidth / 2, 2 * RADIUS, (int) Math.pow(2, levelsCount - 2), tree, drawingPane);

            pane.setContent(drawingPane);

            if(canvasWidth <= Screen.getPrimary().getVisualBounds().getWidth() - 100) {
                pane.setMinWidth(canvasWidth);
                pane.setMaxWidth(canvasWidth);
            }

            if(canvasHeight <= Screen.getPrimary().getVisualBounds().getHeight() - 125) {
                pane.setMinHeight(canvasHeight);
                pane.setMaxHeight(canvasHeight);
            }
        }
    }

    /**
     * Drawing BinaryTree Nodes on given ScrollPane
     * @param x X coordinate of next Node to draw
     * @param y Y coordinate of next Node to draw
     * @param q distance multiplier between following Nodes
     * @param node BinaryTree root
     * @param drawingPane pane to add Node
     */
    private static void drawTree(double x, double y, int q, Node node, Pane drawingPane) {
        if (node.right != null) {
            drawEdge(x, y, x + q * 2 * RADIUS, y + 4 * RADIUS, drawingPane);
            drawTree(x + q * 2 * RADIUS, y + 4 * RADIUS, q / 2, node.right, drawingPane);
        }
        if (node.left != null) {
            drawEdge(x, y, x - q * 2 * RADIUS, y + 4 * RADIUS, drawingPane);
            drawTree(x - q * 2 * RADIUS, y + 4 * RADIUS, q / 2, node.left, drawingPane);
        }

        drawNode(x, y, node.key.toString(), drawingPane);
    }

    /**
     * Finding BinaryTree height
     * @param node BinaryTree root
     * @return height of BinaryTree
     */
    private static int findTreeHeight(Node node) {
        if(node == null) {
            return 0;
        }

        int leftHeight = findTreeHeight(node.right);
        int rightHeight = findTreeHeight(node.left);

        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * Drawing one Node
     * @param x X coordinate of Node
     * @param y Y coordinate of Node
     * @param value value of Node
     * @param drawingPane pane to add Node
     */
    private static void drawNode(double x, double y, String value, Pane drawingPane) {
        Circle circle = new Circle(x, y, RADIUS);
        circle.setFill(CIRCLE_COLOR);

        Text text = new Text(x, y, value);
        text.setBoundsType(TextBoundsType.VISUAL); 
        text.setFill(TEXT_COLOR);
        text.setTextAlignment(TextAlignment.CENTER);
        text.relocate(text.getX() - text.getBoundsInLocal().getWidth() / 2, 
                      text.getY() - text.getBoundsInLocal().getHeight() / 2);

        drawingPane.getChildren().add(circle);
        drawingPane.getChildren().add(text);
    }

    /**
     * Drawing one Edge
     * @param x1 X coordinate of first end
     * @param y1 Y coordinate of first end
     * @param x2 X coordinate of second end
     * @param y2 Y coordinate of second end
     * @param drawingPane pane to add Edge
     */
    private static void drawEdge(double x1, double y1, double x2, double y2, Pane drawingPane) {
        Line line = new Line(x1, y1, x2, y2);
        line.setStroke(LINE_COLOR);

        drawingPane.getChildren().addAll(line);
    }
}
