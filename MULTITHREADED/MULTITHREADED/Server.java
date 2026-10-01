package MULTITHREADED;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

    public void run() {

        try {
            int port = 5050;
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                System.out.println("Server is listening on port " + port);
                Socket client = serverSocket.accept();
                Thread thread = new Thread(() -> {
                    try {

                        PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        String line = in.readLine();
                        System.out.println(line);
                        Thread.sleep(10000);
                        String response = "HTTP/1.1 200 OK\r\n" +
                                "Content-Type: text/plain\r\n" +
                                "Content-Length: 18\r\n" +
                                "\r\n" +
                                "HELLO FROM SERVER";
                        out.println(response);

                        client.close();
                        out.close();
                        in.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
                thread.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)  {
        Server server = new Server();
        server.run();
    }

}
