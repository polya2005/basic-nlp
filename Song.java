import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class Song {
    private ArrayList<String> lyricsWords = new ArrayList<>();

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

    public ArrayList<String> getLyricsWords(){
        return lyricsWords;
    }
}