package MULTITHREADED;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

  public Runnable getRunnable() {
    return new Runnable() {
      @Override
      public void run() {
        int port = 5050;
        try {
          InetAddress address = InetAddress.getByName("localhost");
          Socket socket = new Socket(address, port);
          try (
              PrintWriter toSocket = new PrintWriter(socket.getOutputStream(), true);
              BufferedReader fromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()))

          ) {
            toSocket.println("hello from client" + socket.getLocalSocketAddress());
            String Line = fromSocket.readLine();
            System.out.println("received from server: " + Line);

          } catch (Exception e) {
            e.printStackTrace();

          }

        } catch (Exception e) {
          e.printStackTrace();
        }
      }

    };

  }

  public static void main(String[] args) {
    Client client = new Client();
    for (int i = 0; i < 50; i++) {
      try {
        Thread thread = new Thread(client.getRunnable());
        thread.start();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

  }

}