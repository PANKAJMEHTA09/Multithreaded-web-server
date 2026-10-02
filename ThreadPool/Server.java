import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final ExecutorService threadPool;

    public Server(int poolSize) {
        threadPool = Executors.newFixedThreadPool(poolSize);
    }
    public void handleClient(Socket clientSocket) {
        try {
            PrintWriter out =new PrintWriter(clientSocket.getOutputStream(), true);
            String response = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: text/plain\r\n" +
                    "Content-Length: 17\r\n" +
                    "\r\n" +
                    "HELLO FROM SERVER";
            out.print(response);
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 5050;
        int poolSize = 50;
        Server server = new Server(poolSize);
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket client =serverSocket.accept();
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        server.handleClient(client);
                    }
                };

                server.threadPool.execute(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.threadPool.shutdown();
        }
    }
}