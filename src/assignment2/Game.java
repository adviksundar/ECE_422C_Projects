/* EE422C Assignment #2 submission by
 * Advik Sundar
 * as225339
 */

package assignment2;

import java.util.Scanner;

public class Game {

    private int mode;
    private Scanner s;

    public Game(int mode) {
        this.mode = mode;
        this.s = new Scanner(System.in);
    }

    public void runGame() {
        char ready_for_game;
        gameInstructions();
        ready_for_game = s.next().charAt(0);
        while (ready_for_game == 'Y') {
            executeGame();
            System.out.print("Are you ready for another game (Y/N): ");
            ready_for_game = s.next().charAt(0);
        }
    }

    public void executeGame() {
        System.out.print("\n");
        String code = SecretCodeGenerator.getInstance().getNewSecretCode();
        generatingCodeStatement(code);
        GameTracker tracker = new GameTracker(code);
        System.out.println("You have " + tracker.getNumGuessesLeft() + " guesses left.");
        boolean first = true;
        boolean won = false;
        while (tracker.getNumGuessesLeft() > 0) {
            System.out.println("What is your next guess?\n" +
                    "Type in the characters for your guess and press enter.");
            System.out.print("Enter guess: ");
            if (first) {
                s.nextLine();
                first = false;
            }
            String guess = s.nextLine();
            if (guess.equals("HISTORY")) {
                tracker.getHistory();
                System.out.println("You have " + tracker.getNumGuessesLeft() + " guesses left.");
            }
            else if (!tracker.isGuessValid(guess)) {
                System.out.println("\n" + guess + " -> INVALID GUESS\n");
            }
            else if (guess.equals(code)) {
                System.out.println("\n" + guess + " -> Result: " + tracker.getBWPins(guess) + " - You win !!\n");
                tracker.addToHistory(guess);
                break;
            }
            else {
                tracker.subtractGuesses();
                if (tracker.getNumGuessesLeft() == 0) {
                    System.out.println("\n" + "Sorry, you are out of guesses. You lose, boo-hoo.\n");
                    break;
                }
                System.out.println("\n" + guess + " -> Result: " + tracker.getBWPins(guess));
                tracker.addToHistory(guess);
                System.out.println("\nYou have " + tracker.getNumGuessesLeft() + " guesses left.");
            }
        }
    }

    public void gameInstructions() {
        System.out.print("Welcome to Mastermind. Here are the rules.\n\n" +
                "This is a text version of the classic board game Mastermind.\n\n" +
                "The computer will think of a secret code. The code consists of " + GameConfiguration.pegNumber + " colored pegs. The pegs MUST be one of six colors: blue, green, orange, purple, red, or yellow. A color may appear more than once in the code. You try to guess what colored pegs are in the code and what order they are in. After you make a valid guess the result (feedback) will be displayed.\n\n" +
                "The result consists of a black peg for each peg you have guessed exactly correct (color and position) in your guess. For each peg in the guess that is the correct color, but is out of position, you get a white peg. For each peg, which is fully incorrect, you get no feedback.\n\n" +
                "Only the first letter of the color is displayed. B for Blue, R for Red, and so forth. When entering guesses you only need to enter the first character of each color as a capital letter.\n\n" +
                "You have " + GameConfiguration.guessNumber + " guesses to figure out the secret code or you lose the game. Are you ready to play? (Y/N): ");
    }

    public void generatingCodeStatement(String code) {
        if (mode == 1) {
            System.out.println("Generating secret code ...(for this example the secret code is " + code + ")\n");
        }
        else  {
            System.out.println("Generating secret code ...\n");
        }
    }
}
