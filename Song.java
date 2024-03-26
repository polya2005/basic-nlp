import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.HashSet;

class Song {
    private ArrayList<String> lyricsWords = new ArrayList<>();
    private SentimentValueTable sentimentValueTable;
    private HashSet<String> stopWords = new HashSet<>();

    /**
     * Creates a <code>Song</code> object.
     * 
     * @param filename name of the file containing the lyrics
     */
    public Song(String filename) {
        Scanner songReader;
        try {
            songReader = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            System.err.println("Song not found: " + filename);
            e.printStackTrace();
            return;
        }
        String lyricsWord;
        while (songReader.hasNext()) {
            lyricsWord = songReader.next().toLowerCase(); // Get the next word in lowercase
            lyricsWord = lyricsWord.replaceAll("[^a-z']", ""); // remove punctuations except for '
            lyricsWord = lyricsWord.replaceAll("^'", ""); // remove leading '
            lyricsWords.add(lyricsWord);
        }
        songReader.close();
    }

    /**
     * Creates a <code>Song</code> object with specified sentiment table and stop
     * word list for sentiment analysis.
     * 
     * @param filename            name of the file containing the lyrics
     * @param sentimentValueTable sentiment value table for sentiment analysis
     * @param stopWords           <code>HashSet</code> containing the stop words
     */
    public Song(String filename, SentimentValueTable sentimentValueTable, HashSet<String> stopWords) {
        this(filename);
        this.sentimentValueTable = sentimentValueTable;
        this.stopWords = stopWords;
    }

    /**
     * @return the sum of the sentiment values of every word in the song
     */
    public int getSentimentSum() {
        int sentimentSum = 0;
        for (String word : lyricsWords) {
            sentimentSum += sentimentValueTable.getSentiment(word);
        }
        return sentimentSum;
    }

    /**
     * @return the number of words in the song, including duplicates
     */
    public int getWordCount() {
        return lyricsWords.size();
    }

    /**
     * @return the number of words that are not stop words in the song, including duplicates
     */
    public int getNonStopWordCount() {
        int count = 0;
        for (String word : lyricsWords) {
            if (!stopWords.contains(word)) {
                ++count;
            }
        }
        return count;
    }

    /**
     * @return average length of the words in the song
     */
    public double getAverageWordLength(){
        int lengthSum = 0;
        for (String word : lyricsWords) {
            lengthSum += word.length();
        }
        return (double)lengthSum / getWordCount();
    }

    /**
     * @return a <code>HashMap</code> with words as the keys and the number of
     *         occurrence as the values.
     */
    public HashMap<String, Integer> getDetailedWordCount() {
        HashMap<String, Integer> res = new HashMap<>();
        for (String word : lyricsWords) {
            if (res.containsKey(word)) {
                res.replace(word, res.get(word) + 1); // Increment number of occurrences
            } else {
                res.put(word, 1); // Set the number of occurrence to 1
            }
        }
        return res;
    }

    public ArrayList<String> getLyricsWords() {
        return lyricsWords;
    }
}