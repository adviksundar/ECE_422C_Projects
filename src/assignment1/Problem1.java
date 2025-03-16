/* Student Name: Advik Sundar, Lab Section: 17270 */

package assignment1;

import java.util.Scanner;

public class Problem1 {
    public static void solution() {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        scan.nextLine();
        String s = scan.nextLine();
        int greatest_product = -1;
        for (int i = 1; i <= n; i++) {
            for (int beg = 0; beg <= s.length() - i; beg++) {
                int product = 1;
                String substr = s.substring(beg, beg + i);
                for (int j = 0; j < substr.length(); j++) {
                    int num = substr.charAt(j) - '0';
                    product *= num;
                    greatest_product = (product > greatest_product) ? product : greatest_product;
                }
            }
        }
        System.out.println(greatest_product);
    }

    public static void main(String[] args) {
        solution();
    }
}
