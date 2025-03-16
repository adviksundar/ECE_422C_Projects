package assignment5;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class OutputThread implements Runnable {
    private DataOutputStream dout;
    private Scanner scan;
    public OutputThread(Socket s) {
        try {
            scan = new Scanner(System.in);
            dout = new DataOutputStream(s.getOutputStream());
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = scan.nextLine();
                dout.writeUTF(message);
            }
        } catch(Exception e) {
            System.out.println("Server Disconnected");
        }
    }
}
