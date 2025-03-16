/* EE422C Assignment #2 submission by
 * Advik Sundar
 * as225339
 */

package assignment5;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class GameTracker {
    private ArrayList<String> history;
    private ArrayList<String> history_pins;
    private String code;
    private int num_guesses_left;
    private DataInputStream din;
    private DataOutputStream dout;

    public GameTracker(DataInputStream din, DataOutputStream dout) {
        this.history = new ArrayList<>();
        this.history_pins = new ArrayList<>();
        this.num_guesses_left = MyServer.getGuessNumber();
        this.din = din;
        this.dout = dout;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void clearHistory() {
        history.clear();
        history_pins.clear();
        num_guesses_left = MyServer.getGuessNumber();
    }

    public int getNumGuessesLeft() {
        return num_guesses_left;
    }

    public void subtractGuesses() {
        num_guesses_left--;
    }

    public boolean isGuessValid(String guess) {
        if (guess.length() != MyServer.getPegNumber()) {
            return false;
        }
        for (char c : guess.toCharArray()) {
            String letter = Character.toString(c);
            boolean found = false;
            for (String s: MyServer.getColors()) {
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

    public void getHistory() throws IOException {
        if (history.size() > 0) {
            dout.writeUTF("\n");
        }
        for (int i = 0; i < history.size(); i++) {
            dout.writeUTF(history.get(i) + "\t\t" + history_pins.get(i));
        }
        dout.writeUTF("\n");
    }
}
