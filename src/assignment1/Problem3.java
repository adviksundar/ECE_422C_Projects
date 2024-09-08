/* Student Name: Advik Sundar, Lab Section: 17270 */

package assignment1;

import java.util.Scanner;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class Problem3 {
    public static void solution() {
        Scanner s = new Scanner(System.in);
        String input = s.nextLine();
        MaxentTagger tagger = new MaxentTagger("assignment1/english-bidirectional-distsim.tagger");
        String taggedString = tagger.tagString(input);
        System.out.println(taggedString);
    }

    public static void main(String[] args) {
        solution();
    }
}
