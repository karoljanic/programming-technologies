import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Class represents one of MulticlientServer clients
 */
public class ServerClient {
    /** Socket used to communication with server */
    Socket socket;

    /** Object used to sending objects to server */
    private ObjectOutputStream out;

    /** Object used to receiving objects from server */
    private ObjectInputStream in;

    /** Data type currently stored in tree */
    private DataType currentDataType;

    /**
     * Constructor of Server Client
     * 
     * @throws Exception if during making connection occurs any error
     */
    ServerClient() throws Exception {
        currentDataType = DataType.UNDEFINED;

        try {
            socket = new Socket("localhost", 7003);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (UnknownHostException ex) {
            throw new Exception("Error With Connection.");
        } catch (IOException ex) {
            throw new Exception("Error With Connection.");
        }
    }

    /**
     * Initializing new BinaryTree
     * @param dataType type of values in creating tree
     * @throws IOException if occurs exception during initializing new BinaryTree
     */
    public void initializeTree(DataType dataType) throws IOException {
        currentDataType = dataType;

        out.writeObject("init/" + Integer.toString(dataType.value));
    }

    /**
     * Inserting value to BinaryTree
     * @param value value to insert
     * @throws NumberFormatException if vale type is incorrect
     * @throws IllegalAccessError if tree is not initialized
     * @throws IOException if during inserting element occurs exception
     */
    public void insertElement(String value) throws NumberFormatException, IllegalAccessError, IOException {
        switch (currentDataType) {
            case INTEGER:
                int i = Integer.parseInt(value);
                out.writeObject("insert/" + Integer.toString(i));
                break;
            case DOUBLE:
                double d = Double.parseDouble(value);
                out.writeObject("insert/" + Double.toString(d));
                break;
            case STRING:
                out.writeObject("insert/" + value);
                break;
            default:
                throw new IllegalAccessError();
        }
    }

    /**
     * Deleting value from BinaryTree
     * @param value vakue to delete
     * @throws NumberFormatException if vale type is incorrect
     * @throws IllegalAccessError if tree is not initialized
     * @throws IOException if during deleting element occurs exception
     */
    public void deleteElement(String value) throws NumberFormatException, IllegalAccessError, IOException {
        switch (currentDataType) {
            case INTEGER:
                int i = Integer.parseInt(value);
                out.writeObject("delete/" + Integer.toString(i));
                break;
            case DOUBLE:
                double d = Double.parseDouble(value);
                out.writeObject("delete/" + Double.toString(d));
                break;
            case STRING:
                out.writeObject("delete/" + value);
                break;
            default:
                throw new IllegalAccessError();
        }
    }

    /**
     * Searching value in BinaryTree
     * @param value value to find
     * @return true if value exist in BinaryTree, false otherwise
     * @throws NumberFormatException if vale type is incorrect
     * @throws IllegalAccessError if tree is not initialized
     * @throws IOException if during gettig data occurs exception
     */
    public boolean searchElement(String value) throws NumberFormatException, IllegalAccessError, IOException {
        try {
            switch (currentDataType) {
                case INTEGER:
                    int i = Integer.parseInt(value);
                    out.writeObject("search/" + Integer.toString(i));
                    return (boolean) in.readObject();
                case DOUBLE:
                    double d = Double.parseDouble(value);
                    out.writeObject("search/" + Double.toString(d));
                    return (boolean) in.readObject();
                case STRING:
                    out.writeObject("search/" + value);
                    return (boolean) in.readObject();
                default:
                    throw new IllegalAccessError();
            }
        } catch (Exception ex) {
            return false;
        }

    }

    /**
     * Getting Root of BinaryTree
     * @return Root of BinaryTree
     * @throws IOException if during getting data occurs exception
     */
    public Node getRootNode() throws IOException {
        out.writeObject("getnodes/all");

        try {
            Node result = (Node) in.readObject();
            return result;
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
            return null;
        }
    }

    /**
     * Closing connection with server.
     * @throws IOException if during closing onnection occurs exception
     */
    public void close() throws IOException {
        out.writeObject("close/app");
        socket.close();
    }
}
