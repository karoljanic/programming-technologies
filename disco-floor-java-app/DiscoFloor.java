import java.util.Random;

import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * The class that contains a color grid simulation. 
 * The board is a grid of size n by m. Each tile is a separate thread, 
 * which at random intervals - between 0.5k and 1.5k with probability p changes color to a random color 
 * and with probability 1-p changes color based on its neighbors.
 * By clicking on a tile it is possible to pause it and block access to it by other tiles. 
 */
public class DiscoFloor extends GridPane {
    /**
     * The class used to describe a single grid tile. 
     */
    private class DiscoTile extends Thread {
        /** Figure which represents tile */
        private Rectangle rectangle;

        /** Coordinates of tile in grid */
        private int xCoordinate, yCoordinate;

        /** Probability of changes color to a random color */
        private double randomColorProbability;

        /** Time interval between color changes */
        private long interval;

        /** Variables used to describe the state of the thread */
        private boolean isActive, doIt;

        /** Object on which threads are synchronized */
        private DiscoFloor objectToSynchronization; 

        /**
         * Class constructor
         * @param parentObject object on which threads are synchronized
         * @param x x coordinate of tile in grid
         * @param y y coordinate of tile in grid
         * @param p probability of changes color to a random color 
         * @param k constant used to calculate time interval
         */
        DiscoTile(DiscoFloor parentObject, int x, int y, double p, double k) {
            rectangle = new Rectangle(tileDimesion, tileDimesion);
            rectangle.setFill(getRandomColor());
            
            xCoordinate = x;
            yCoordinate = y;
            randomColorProbability = p;
            interval = (long)((0.5 + random.nextDouble()) * k);
            isActive = true;
            doIt = true;
            objectToSynchronization = parentObject;

            rectangle.setOnMouseClicked(event -> {
                if(isActive) {
                    isActive = false;
                }
                else {
                    synchronized(this) {
                        isActive = true;
                        notify();
                    }
                }
            });
        }

        @Override
        public void run() {
            while(doIt) {
                try {
                    if(!isActive) {
                        synchronized(this) {
                            while(!isActive) { 
                                wait();
                            }
                        }
                    } 

                    Thread.sleep(interval);
                    synchronized(objectToSynchronization) {
                        System.out.println("Current threads count  = " + Thread.activeCount());
                        System.out.println("Changing color started for tile (" + xCoordinate + ", " + yCoordinate + ")");
                        if(random.nextDouble() <= randomColorProbability) {
                            rectangle.setFill(getRandomColor());
                        }
                        else {
                            try {
                                rectangle.setFill(getColorBasedOnNeighbors());
                            }
                            catch(IllegalArgumentException e) {}
                        }
                        System.out.println("Changing color ended for tile (" + xCoordinate + ", " + yCoordinate + ")");
                    }
                    Thread.yield();        
                }
                catch(InterruptedException e) {}
            }
        }

        /**
         * Getter of rectangle shape
         * @return shape which represents a tile
         */
        public Rectangle getRectangle() {
            return rectangle;
        }

        /**
         * Getter of rectangle color
         * @return color of rectangle which represents a tile
         */
        private Color getColor() {
            return (Color)rectangle.getFill();
        }

        /**
         * Getting random color
         * @return random color
         */
        private Color getRandomColor() {
            return Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble());
        }

        /**
         * Getting random color calculated on based neighbors
         * @return color calculated on based neighbors 
         * @throws IllegalArgumentException if all neighbors are blocked for color reading 
         */
        private Color getColorBasedOnNeighbors() throws IllegalArgumentException {
            DiscoTile[] tiles = new DiscoTile[4];
            tiles[0] = nodesHashMap[calculateHash((xCoordinate + 1) % columnsCount, yCoordinate)];
            tiles[1] = nodesHashMap[calculateHash((xCoordinate - 1 + columnsCount) % columnsCount, yCoordinate)];
            tiles[2] = nodesHashMap[calculateHash(xCoordinate, (yCoordinate + 1) % rowsCount)];
            tiles[3] = nodesHashMap[calculateHash(xCoordinate, (yCoordinate - 1 + rowsCount) % rowsCount)];
            
            int counter = 0;
            double newR = 0.0, newG = 0.0, newB = 0.0;
           
            for(int i = 0; i<4; i++) {
                if(tiles[i].getState() != State.WAITING) {
                    newR += tiles[i].getColor().getRed();
                    newG += tiles[i].getColor().getGreen();
                    newB += tiles[i].getColor().getBlue();

                    counter++;
                }
            }
            
            if(counter == 0) {
                throw new IllegalArgumentException();
            }

            return Color.color(newR / counter, newG / counter, newB / counter);
        }
    }

    /** The random number generator used in the all simulation. */
    private Random random;

    /** Dimensions of the board. */
    private int columnsCount, rowsCount;

    /** Hashmap containing the created tiles. */
    private DiscoTile[] nodesHashMap;

    /** Length and width of one tile's side. */
    private double tileDimesion;

    /**
     * Class constructor
     * @param n number of grid rows
     * @param m number of grid columns
     * @param k number used to calculate interval between color changes
     * @param p probability of changing color to random
     * @param d length and width of one tile
     */
    DiscoFloor(int n, int m, double k, double p, double d) {
        super();

        random = new Random();
        columnsCount = m;
        rowsCount = n;
        nodesHashMap = new DiscoTile[n * m];
        tileDimesion = d;
        
        for(int i = 0; i<n; i++) {
            for(int j = 0; j<m; j++) {                
                DiscoTile nextTile = new DiscoTile(this, j, i, p, k);
                setColumnIndex(nextTile.getRectangle(), j);
                setRowIndex(nextTile.getRectangle(), i);
                getChildren().add(nextTile.getRectangle());
                nodesHashMap[calculateHash(j, i)] = nextTile;
            }
        }

        for(int i = 0; i<n*m; i++) {
            nodesHashMap[i].start();
        }
    }

    /**
     * Destroying all created threads
     */
    public void destroyThreads() {
        for(int i = 0; i<columnsCount*rowsCount; i++) {
            nodesHashMap[i].doIt = false;
        }
    }

    /**
     * Calculating tile position in nodesHashMap
     * @param columnNumber column number of tile
     * @param rowNumber row number of tile
     * @return index of tile in nodesHashMap
     */
    private int calculateHash(int columnNumber, int rowNumber) {
        return rowNumber * columnsCount + columnNumber;
    }
}
