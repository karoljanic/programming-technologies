import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Class represents MultiClient Server.
 */
public class Server {
    /**
     * Main method of class - running server
     * 
     * @param args list of arguments
     */
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(7003)) {

            System.out.println("Server is listening on port 7003");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                new ServerThread(socket).start();
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
