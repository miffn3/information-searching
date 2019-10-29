import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Game> gameList = new ArrayList<>();
        String indexPath = "index";

        try {
            Parser.loadData(gameList);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Indexer indexer = null;

        try {
            indexer = new Indexer(indexPath);
            indexer.createIndex(gameList);
        } catch (IOException io) {
            io.printStackTrace();
        }

        System.out.println("=== Searching start ===");
        Searcher searcher = new Searcher();

        ArrayList<Game> byAmountOfAchievements = null;
        try {
            byAmountOfAchievements = searcher.search(Constant.AMOUNT_OF_ACHIEVEMENTS.value, "20", indexPath);
        } catch (IOException | ParseException io) {
            io.printStackTrace();
        }

        System.out.println("Results:");
        for (Game game : byAmountOfAchievements) {
            System.out.println(" " + game.getName());
        }
        System.out.println();

        ArrayList<Game> byName = null;
        try {
            byName = searcher.search(Constant.NAME.value, "ACA NEOGEO", indexPath);
        } catch (IOException | ParseException io) {
            io.printStackTrace();
        }

        System.out.println("Results:");
        for (Game game : byName) {
            System.out.println(" " + game.getName());
        }

        System.out.println("=== Searching finish ===\n");
    }
}
