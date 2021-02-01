package chat_v1.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    static final String IP_ADDRESS = "localhost";
    static final int PORT = 2302;

    static Socket socket;
    static DataInputStream in;
    static DataOutputStream out;

    public static void main(String[] args) {
        try {
            socket = new Socket(IP_ADDRESS, PORT);
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
                    System.out.println("Server: " + msg);
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
        }
    }
}
