import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String indexPath = "index";

        //Indexing
//        ArrayList<Game> gameList = new ArrayList<>();
//        try {
//            Parser.loadData(gameList);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//
//        Indexer indexer = null;
//
//        try {
//            indexer = new Indexer(indexPath);
//            indexer.createIndex(gameList);
//        } catch (IOException io) {
//            io.printStackTrace();
//        }

        //Search examples
        System.out.println("=== Searching start ===");
        Searcher searcher = new Searcher();

//        ArrayList<Game> byAmountOfAchievements = null;
//        try {
//            byAmountOfAchievements = searcher.search(Constant.AMOUNT_OF_ACHIEVEMENTS, "12", indexPath, null);
//        } catch (IOException | ParseException io) {
//            io.printStackTrace();
//        }
//
//        System.out.println("Results:");
//        for (Game game : byAmountOfAchievements) {
//            System.out.println(" " + game.getName());
//        }
//        System.out.println();
//
//        ArrayList<Game> byName = null;
//        try {
//            byName = searcher.search(Constant.NAME, "ACA NEOGEO", indexPath, null);
//        } catch (IOException | ParseException io) {
//            io.printStackTrace();
//        }
//
//        System.out.println("Results:");
//        for (Game game : byName) {
//            System.out.println(" " + game.getName());
//        }
//
//        ArrayList<Game> byRange = null;
//        byRange = searcher.search(Constant.AMOUNT_OF_ACHIEVEMENTS, "10", "15");
//
//        System.out.println("Results:");
//        for (Game game : byRange) {
//            System.out.println(" " + game.getName());
//        }

        ArrayList<Game> bySynonym = null;
        bySynonym = searcher.searchBySynonym("gang");

        System.out.println("Results:");
        for (Game game : bySynonym) {
            System.out.println(" " + game.getName());
        }

        System.out.println("=== Searching finished ===\n");


            //  Write all games to txt
//        FileUtils.writeToFile(gameList, "games.txt");
//          //Get all words from games' names and write it to txt
//        WordUtils.getWordsData(
//           //Write synonyms to txt
//        Synonym.getSynonyms();
    }
}
