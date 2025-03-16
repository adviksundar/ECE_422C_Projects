package assignment5;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MyClient {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 6666);
            InputThread input = new InputThread(s);
            OutputThread output = new OutputThread(s);
            Thread tin = new Thread(input);
            tin.start();
            Thread tout = new Thread(output);
            tout.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
