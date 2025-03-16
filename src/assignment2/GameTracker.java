/* EE422C Assignment #2 submission by
 * Advik Sundar
 * as225339
 */

package assignment2;

import java.util.ArrayList;

public class GameTracker {
    private ArrayList<String> history;
    private ArrayList<String> history_pins;
    private String code;
    private int num_guesses_left;


    public GameTracker(String code) {
        this.history = new ArrayList<>();
        this.history_pins = new ArrayList<>();
        this.code = code;
        this.num_guesses_left = GameConfiguration.guessNumber;
    }

    public int getNumGuessesLeft() {
        return num_guesses_left;
    }

    public void subtractGuesses() {
        num_guesses_left--;
    }

    public boolean isGuessValid(String guess) {
        if (guess.length() != GameConfiguration.pegNumber) {
            return false;
        }
        for (char c : guess.toCharArray()) {
            String letter = Character.toString(c);
            boolean found = false;
            for (String s: GameConfiguration.colors) {
                if (s.equals(letter)) {
                    found = true;
                }
            }
            if (found == false) {
                return false;
            }
        }
        return true;
    }

    public String getBWPins(String guess) {
        int black = 0;
        int white = 0;
        ArrayList<String> guessList = new ArrayList<>();
        ArrayList<String> codeList = new ArrayList<>();
        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) != code.charAt(i)) {
                guessList.add(String.valueOf(guess.charAt(i)));
                codeList.add(String.valueOf(code.charAt(i)));
            }
            else {
                black++;
            }
        }
        for (String s : codeList) {
            if (guessList.contains(s)) {
                guessList.remove(s);
                white++;
            }
        }
        return black + "B_" + white + "W";
    }

    public void addToHistory(String guess) {
        history.add(guess);
        history_pins.add(getBWPins(guess));
    }

    public void getHistory() {
        if (history.size() > 0) {
            System.out.println("");
        }
        for (int i = 0; i < history.size(); i++) {
            System.out.println(history.get(i) + "\t\t" + history_pins.get(i));
        }
        System.out.println("");
    }
}
