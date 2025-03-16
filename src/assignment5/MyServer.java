package assignment5;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class MyServer {
    private static ArrayList<Handler> clientHandlers = new ArrayList<>();
    private static int pegNumber = 4;
    private static int guessNumber = 2;
    private static final String[] colors = {"B","G","O","P","R","Y"};
    private static String secretCode = SecretCodeGenerator.getInstance().getNewSecretCode();
    public static volatile boolean gameEnded = false;
    public static int clientCount = 0;

    public static ServerSocket servSocket;

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(6666);
            servSocket = ss;
            System.out.println("Server started\n");

            while (true) {
                Socket client = ss.accept();
                DataOutputStream dout = new DataOutputStream(client.getOutputStream());
                dout.writeUTF("Connected to the server\n\n");
                dout.flush();

                Handler h = new Handler(client);
                clientHandlers.add(h);
                Thread t = new Thread(h);
                t.start();
                clientCount++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public synchronized static void endGame() {
        MyServer.gameEnded = true;
    }
    public synchronized static void makeCode() {
        if (gameEnded) {
            secretCode = SecretCodeGenerator.getInstance().getNewSecretCode();
            gameEnded = false;
        }
    }

    public synchronized static void sendOutMessage(String message) {
        for (Handler h : clientHandlers) {
            h.message(message);
        }
    }

    public static int getPegNumber() {
        return pegNumber;
    }

    public static int getGuessNumber() {
        return guessNumber;
    }

    public static String[] getColors() {
        return colors;
    }

    public synchronized static String getSecretCode() {
        return secretCode;
    }
}