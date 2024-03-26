import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

public class NLPRunner {
    private static HashSet<String> stopWords = new HashSet<>();
    public static void main(String[] args) throws FileNotFoundException {
        Scanner stopWordsReader = new Scanner(new File("stop_words.txt"));
        while(stopWordsReader.hasNextLine()){
            stopWords.add(stopWordsReader.nextLine());
        }
        stopWordsReader.close();
        SentimentValueTable afinnTable = new SentimentValueTable("AFINN-111.txt");
        Song imaginationSong = new Song("imagination.txt", afinnTable, stopWords);
        Song rosesSong = new Song("roses.txt", afinnTable, stopWords);
        printAnalysis(imaginationSong, "Imagination");
        printAnalysis(rosesSong, "Roses");
    }

    private static void printAnalysis(Song song, String songName){
        System.out.println("Analysis of " + songName);
        System.out.print("Sentiment value = ");
        System.out.println(String.format("%1$+.4f", (double)song.getSentimentSum() / song.getNonStopWordCount()));
        System.out.print("Average word length = ");
        System.out.println(String.format("%1$.2f", song.getAverageWordLength()));
        printDetailedWordCount(song.getDetailedWordCount());
        System.out.println();
    }

    private static void printDetailedWordCount(HashMap<String, Integer> details){
        System.out.println("Word frequencies");
        ArrayList<Map.Entry<String, Integer>> wordCountList = new ArrayList<Map.Entry<String, Integer>>(details.entrySet());
        Comparator<Map.Entry<String, Integer>> valueComparator = Map.Entry.comparingByValue();
        wordCountList.sort(valueComparator.reversed());
        for (Map.Entry<String,Integer> entry : wordCountList) {
            if (!stopWords.contains(entry.getKey())) {
                System.out.println(String.format("%1$-12s", entry.getKey()) + entry.getValue()); // Right pad the key with space to the length of 12 and print its number of occurrences 
            }
        }
    }
}
