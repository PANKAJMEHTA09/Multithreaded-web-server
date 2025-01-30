package MULTITHREADED;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {

public Consumer<Socket>getConsumer(){
//     return (clientSocket) -> {
//         try{PrintWriter toSocket = new PrintWriter(clientSocket.getOutputStream(),true);
// toSocket.println("hello from server" + clientSocket.getInetAddress());

//         }catch(IOException e){
//             e.printStackTrace();

//         }
//     };

return new Consumer<Socket>() {
    @Override
    public void accept(Socket clientSocket) {
        try {
            PrintWriter toSocket = new PrintWriter(clientSocket.getOutputStream(), true);
            toSocket.println("hello from server" + clientSocket.getInetAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
};
 
}

    public static void main(String[] args) {
        int port = 5050;
        Server server = new Server();

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(10000);
            System.out.println("server is listening on port" + port);
            while (true) {
                Socket accepSocket = serverSocket.accept();
         Thread thread = new Thread(() -> server.getConsumer().accept(accepSocket));
                thread.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
