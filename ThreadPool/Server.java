import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server{
private final ExecutorService threadPool;

public Server(int poolSize){
    this.threadPool = Executors.newFixedThreadPool(poolSize);
}

public void handleClient (Socket clientSocket){
    try(PrintWriter toSocket = new PrintWriter(clientSocket.getOutputStream(),true)){
        toSocket.println("hello from server"+clientSocket.getInetAddress());
    }catch(IOException e){
        e.printStackTrace();
    }
}

public static void main(String[] args) {
    int port =5050;
    int poolSize = 50;
    Server server = new Server(poolSize);

try{
    ServerSocket ServerSocket = new ServerSocket(port);
    ServerSocket.setSoTimeout(90000);
    System.out.println("server is listening on port"+ port);
  while(true){
    Socket clienSocket = ServerSocket.accept();
    server.threadPool.execute(()->server.handleClient(clienSocket));

  }
}catch(Exception e){
    e.printStackTrace();

}finally{
    server.threadPool.shutdown();
}



}}