package SINGLETHREADED;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    public void run() throws IOException {
        int port = 5050;
        ServerSocket socket = new ServerSocket(port);
        // socket.setSoTimeout(10000);
        // socket apna 10 sec ke liye wait kr rha hogaa client ke liye then stop hojega

        while (true) {
            try {
                System.out.println("server is listening on port" + port);
                Socket client = socket.accept(); // accept() method waits for client to connect
                System.out.println("connection accepted from client" + client.getRemoteSocketAddress());
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String line = in.readLine();
                System.out.println(line);
                String response = "HTTP/1.1 200 OK\r\n" +
                                "Content-Type: text/plain\r\n" +
                                "Content-Length: 17\r\n" +
                                "\r\n" +
                                "HELLO FROM SERVER";
                out.print(response);
                out.close();
                in.close();
                client.close();


            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }

    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
