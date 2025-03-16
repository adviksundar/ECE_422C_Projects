package assignment5;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Handler implements Runnable {

    private Socket socket;
    private GameTracker tracker;
    private DataInputStream din;
    private DataOutputStream dout;
    private Scanner s;

    public Handler(Socket socket) {
        this.socket = socket;
        try {
            this.din = new DataInputStream(socket.getInputStream());
            this.dout = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            System.out.println(e);
        }
        this.tracker = new GameTracker(din, dout);
        this.s = new Scanner(System.in);
    }

    public void run1() {
        while(true) {
            try {
                String msg = din.readUTF();
                System.out.println(msg);
                dout.writeUTF("Server received the message: " + msg);
                dout.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // @Override
    public void run() {
        try {
            char ready_for_game;
            gameInstructions();
            ready_for_game = din.readUTF().charAt(0);
            while (ready_for_game == 'Y') {
                MyServer.makeCode();
                executeGame();
                dout.writeUTF("The game will automatically restart again for you.");
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Client Disconnected");
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                System.out.println("Can't close client socket");
            }
        }
    }

    public void executeGame() throws IOException {
        generatingCodeStatement(MyServer.getSecretCode(), 1);
        tracker.setCode(MyServer.getSecretCode());
        dout.writeUTF("You have " + tracker.getNumGuessesLeft() + " guesses left.");
        while (tracker.getNumGuessesLeft() > 0) {
            dout.writeUTF("\nWhat is your next guess?\n" +
                    "Type in the characters for your guess and press enter.\n");
            dout.writeUTF("Enter guess: ");
            String guess = din.readUTF();
            if (MyServer.gameEnded == true) {
                break;
            }
            if (guess.equals("HISTORY")) {
                tracker.getHistory();
                dout.writeUTF("\nYou have " + tracker.getNumGuessesLeft() + " guesses left.");
            }
            else if (!tracker.isGuessValid(guess)) {
                dout.writeUTF("\n" + guess + " -> INVALID GUESS\n");
            }
            // If the client wins
            else if (guess.equals(MyServer.getSecretCode())) {
                dout.writeUTF("\n" + guess + " -> Result: " + tracker.getBWPins(guess) + " - You win !!\n");
                tracker.addToHistory(guess);
                MyServer.endGame();
                tracker.clearHistory();
                MyServer.sendOutMessage("\n\nA client has won. Game over.");
                break;
            }
            else {
                tracker.subtractGuesses();
                // If the client loses
                if (tracker.getNumGuessesLeft() == 0) {
                    MyServer.clientCount--;
                    tracker.clearHistory();
                    dout.writeUTF("\n" + "Sorry, you are out of guesses. You lose, boo-hoo.\n");
                    break;
                }
                dout.writeUTF("\n" + guess + " -> Result: " + tracker.getBWPins(guess) + "\n");
                tracker.addToHistory(guess);
                dout.writeUTF("\nYou have " + tracker.getNumGuessesLeft() + " guesses left.");
            }
        }
        tracker.clearHistory();
    }

    public void message(String message) {
        try {
            dout.writeUTF(message);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void gameInstructions() {
        try {
            dout.writeUTF("Welcome to Mastermind. Here are the rules.\n\n" +
                    "This is a text version of the classic board game Mastermind.\n\n" +
                    "The computer will think of a secret code. The code consists of " + MyServer.getPegNumber() + " colored pegs. The pegs MUST be one of six colors: blue, green, orange, purple, red, or yellow. A color may appear more than once in the code. You try to guess what colored pegs are in the code and what order they are in. After you make a valid guess the result (feedback) will be displayed.\n\n" +
                    "The result consists of a black peg for each peg you have guessed exactly correct (color and position) in your guess. For each peg in the guess that is the correct color, but is out of position, you get a white peg. For each peg, which is fully incorrect, you get no feedback.\n\n" +
                    "Only the first letter of the color is displayed. B for Blue, R for Red, and so forth. When entering guesses you only need to enter the first character of each color as a capital letter.\n\n" +
                    "You have " + MyServer.getGuessNumber() + " guesses to figure out the secret code or you lose the game. Are you ready to play? (Y/N): ");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void generatingCodeStatement(String code, int mode) throws IOException {
        if (mode == 1) {
            dout.writeUTF("\nGenerating secret code ...(for this example the secret code is " + code + ")\n\n");
        }
        else  {
            dout.writeUTF("Generating secret code ...\n\n");
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public GameTracker getTracker() {
        return tracker;
    }
}
