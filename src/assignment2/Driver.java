/* EE422C Assignment #2 submission by
 * Advik Sundar
 * as225339
 */

package assignment2;

public class Driver {
    public static void main(String[] args) {
        int mode = 0;
        if (args.length > 0) {
            mode = Integer.parseInt(args[0]);
        }
        Game g = new Game(mode);
        g.runGame();
    }
}
