import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class Song {
    private ArrayList<String> lyricsWords = new ArrayList<>();
    private SentimentValueTable sentimentValueTable;

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
            lyricsWords.add(lyricsWord);
        }
        songReader.close();
    }

    /**
     * Creates a <code>Song</code> object with specified table for sentiment
     * analysis.
     * 
     * @param filename            name of the file containing the lyrics
     * @param sentimentValueTable sentiment value table for sentiment analysis
     */
    public Song(String filename, SentimentValueTable sentimentValueTable) {
        this(filename);
        this.sentimentValueTable = sentimentValueTable;
    }

    /**
     * @return the sum of the sentiment values of every word in the song
     */
    public int getSentimentSum(){
        int sentimentSum = 0;
        for (String word : lyricsWords) {
            sentimentSum += sentimentValueTable.getSentiment(word);
        }
        return sentimentSum;
    }

    /**
     * @return the number of words in the song, with duplicates
     */
    public int getWordCount(){
        return lyricsWords.size();
    }

    public ArrayList<String> getLyricsWords() {
        return lyricsWords;
    }
}