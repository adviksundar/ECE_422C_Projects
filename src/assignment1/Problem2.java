/* Student Name: Advik Sundar, Lab Section: 17270 */

package assignment1;

import java.util.Scanner;

public class Problem2 {
    public static void solution() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter s: ");
        String s = scan.nextLine().toLowerCase();
        String[] words = s.split(" ");
        for (String word : words) {
            int value = 0;
            for (int i = 0; i < word.length(); i++) {
                if (Character.isLetter(word.charAt(i))) {
                    value += word.charAt(i) - 'a' + 1;
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
