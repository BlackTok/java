package chat_v1.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    static final int PORT = 2302;
    static ServerSocket server;
    static Socket socket;
    static DataInputStream in;
    static DataOutputStream out;

    public static void main(String[] args) {
        try {
            server = new ServerSocket(PORT);
            System.out.println("Server started.");

            socket = server.accept();
            System.out.println("Client connected.");

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    String msg = scanner.nextLine();
                    try {
                        out.writeUTF(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            while (true) {
                if (in.available() > 0) {
                    String msg = in.readUTF();
                    System.out.println("Client: " + msg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
