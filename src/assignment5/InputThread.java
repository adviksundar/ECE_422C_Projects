package assignment5;

import java.io.DataInputStream;
import java.net.Socket;

public class InputThread implements Runnable {
    private DataInputStream din;

    public InputThread(Socket s) {
        try {
            din = new DataInputStream(s.getInputStream());
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = din.readUTF();
                System.out.print(message);
            }
        } catch(Exception e) {
            System.out.println(e);
        }
    }
}
