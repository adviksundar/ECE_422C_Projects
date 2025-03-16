
package assignment4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class GraphPoet {

    private Map<String, Vertex<String>> graph;

    /**
     *
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */

    public GraphPoet(File corpus) throws IOException {

        /* Read in the File and place into graph here */
        graph = new HashMap<>();
        BufferedReader input = new BufferedReader(new FileReader(corpus));
        String line = input.readLine();
        String prevWord = null;
        while (line != null) {
            if (line.trim().isEmpty()) {
                line = input.readLine();
                continue;
            }
            String[] words = line.toLowerCase().split("\\s+");
            for (String word : words) {
                if (!graph.containsKey(word)) {
                    graph.put(word, new Vertex<>(word));
                }
                if (prevWord != null) {
                    graph.get(prevWord).appendEdge(word);
                }
                prevWord = word;
            }
            line = input.readLine();
        }
    }

    /**
     * Generate a poem.
     *
     * @param input File from which to create the poem
     * @return poem (as described above)
     */
    public String poem(File input) throws IOException {

        /* Read in input and use graph to complete poem */
        String poem = "";
        BufferedReader reader = new BufferedReader(new FileReader(input));
        String line = reader.readLine();
        while (line != null) {
            if (line.trim().isEmpty()) {
                poem = poem + "\n";
                line = reader.readLine();
                continue;
            }
            String[] words = line.toLowerCase().split("\\s+");
            boolean isFirstWord = true;
            if (words.length == 1) {
                poem = poem + words[0] + "\n";
                line = reader.readLine();
                continue;
            }
            for (int i = 0; i < words.length - 1; i++) {
                String firstWord = words[i];
                String secondWord = words[i+1];
                String mostWeightWord = null;
                if (graph.containsKey(firstWord) && graph.containsKey(secondWord)) {
                    ArrayList<String> bridgeWords = new ArrayList<>();
                    Set<String> keySet = graph.get(firstWord).getEdges().keySet();
                    for (String key : keySet) {
                        Set<String> keySet2 = graph.get(key).getEdges().keySet();
                        for (String key2 : keySet2) {
                            if (key2.equals(secondWord)) {
                                bridgeWords.add(key);
                                break;
                            }
                        }
                    }
                    int mostWeight = 0;
                    for (String word : bridgeWords) {
                        int curWeight = graph.get(word).getEdgeWeight(secondWord);
                        if (curWeight > mostWeight) {
                            mostWeight = curWeight;
                            mostWeightWord = word;
                        }
                    }
                }
                if (isFirstWord) {
                    if (mostWeightWord != null) {
                        poem = poem + firstWord + " " + mostWeightWord + " " + secondWord + " ";
                    }
                    else {
                        poem = poem + firstWord + " " + secondWord + " ";
                    }
                    isFirstWord = false;
                }
                else {
                    if (mostWeightWord != null) {
                        poem = poem + mostWeightWord + " " + secondWord + " ";
                    }
                    else {
                        poem = poem + secondWord + " ";
                    }
                }
            }
            poem = poem.substring(0, poem.length() - 1);
            poem = poem + "\n";
            line = reader.readLine();
        }
        poem = poem.substring(0, poem.length() - 1);
        return poem;
    }
}
