import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class SentimentValueTable {
    private HashMap<String, Integer> sentimentValues = new HashMap<>();

    /**
     * Creates the <code>SentimentValueTable</code> from text file in the
     * format<br/>
     * word [tab] sentiment<br/>
     * word [tab] sentiment<br/>
     * ...<br/>
     * word [tab] sentiment<br/>
     * 
     * @param filename name of the file containing the lyrics
     */
    public SentimentValueTable(String filename){
        Scanner sentimentReader;
        try {
            sentimentReader = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            System.err.println("Sentiment table not found: " + filename);
            e.printStackTrace();
            return;
        }
        while (sentimentReader.hasNextLine()) {
            String line = sentimentReader.nextLine();
            sentimentValues.put(line.substring(0, line.indexOf('\t')), Integer.parseInt(line.substring(line.indexOf('\t') + 1))); // put (word (before tab), sentiment (after tab))
        }
    }

    /**
     * @param word
     * @return the sentiment of <code>word</code>
     */
    public int getSentiment(String word){
        return sentimentValues.getOrDefault(word, 0);
    }
}
