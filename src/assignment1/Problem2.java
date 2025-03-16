/* Student Name: Advik Sundar, Lab Section: 17270 */

package assignment1;

import java.util.Scanner;

public class Problem2 {
    public static void solution() {
        Scanner scan = new Scanner(System.in);
        String s = scan.nextLine();
        String[] words = s.split(" ");
        for (String word : words) {
            int value = 0;
            String lowercase_word = word.toLowerCase();
            for (int i = 0; i < lowercase_word.length(); i++) {
                if (Character.isLetter(lowercase_word.charAt(i))) {
                    value += lowercase_word.charAt(i) - 'a' + 1;
                }
            }
            if (value == 100) {
                System.out.println(word);
            }
        }
    }

    public static void main(String[] args) {
        solution();
    }
}
