import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Class represents one Thread of MultiClient Server
 */
public class ServerThread extends Thread {
    /** Socket of server */
    private Socket socket;

    /** Object representing one client BinaryTree */
    private BinaryTree tree;

    /** Data type currently stored in tree */
    private DataType currentDataType;

    /**
     * Constructor of ServerThread
     * 
     * @param socket server socket on which ServerThread will be operating
     */
    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    /**
     * Running ServerThread
     */
    public void run() {
        try {
            tree = new BinaryTree<Integer>();

            ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());

            String line, command = "", argument = "";
            String[] message;
            do {
                try {
                    line = (String) objectInput.readObject();
                    System.out.println(line);
                } catch (ClassNotFoundException ex) {
                    continue;
                }

                message = line.split("/");
                command = message[0];
                argument = message[1];

                if (command.equals("init")) {
                    switch (Integer.parseInt(argument)) {
                        case 1:
                            tree = new BinaryTree<Integer>();
                            currentDataType = DataType.INTEGER;
                            break;
                        case 2:
                            tree = new BinaryTree<Double>();
                            currentDataType = DataType.DOUBLE;
                            break;
                        case 3:
                            tree = new BinaryTree<String>();
                            currentDataType = DataType.STRING;
                            break;
                        default:
                            break;
                    }
                } else if (command.equals("insert")) {
                    switch (currentDataType) {
                        case INTEGER:
                            tree.insertElement(Integer.parseInt(argument));
                            break;
                        case DOUBLE:
                            tree.insertElement(Double.parseDouble(argument));
                            break;
                        case STRING:
                            tree.insertElement(argument);
                            break;
                        default:
                            break;
                    }
                } else if (command.equals("search")) {
                    switch (currentDataType) {
                        case INTEGER:
                            boolean t1 = tree.searchElement(Integer.parseInt(argument));
                            objectOutput.writeObject(t1);
                            break;
                        case DOUBLE:
                            boolean t2 = tree.searchElement(Double.parseDouble(argument));
                            objectOutput.writeObject(t2);
                            break;
                        case STRING:
                            boolean t3 = tree.searchElement(argument);
                            objectOutput.writeObject(t3);
                            break;
                        default:
                            break;
                    }
                } else if (command.equals("delete")) {
                    switch (currentDataType) {
                        case INTEGER:
                            tree.deleteElement(Integer.parseInt(argument));
                            break;
                        case DOUBLE:
                            tree.deleteElement(Double.parseDouble(argument));
                            break;
                        case STRING:
                            tree.deleteElement(argument);
                            break;
                        default:
                            break;
                    }
                } else if (command.equals("getnodes")) {
                    objectOutput.reset();
                    objectOutput.writeObject(tree.getRepresentation());
                }

            } while (!command.equals("close"));

            socket.close();
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
