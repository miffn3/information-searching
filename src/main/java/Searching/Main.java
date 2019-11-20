package Searching;

import Searching.Model.Game;
import Searching.Searcher.Searcher;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String indexPath = "index";

        //Indexing
//        ArrayList<Searching.Model.Game> gameList = new ArrayList<>();
//        try {
//            Searching.Parser.Parser.loadData(gameList);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//
//        Searching.Searcher.Indexer indexer = null;
//
//        try {
//            indexer = new Searching.Searcher.Indexer(indexPath);
//            indexer.createIndex(gameList);
//        } catch (IOException io) {
//            io.printStackTrace();
//        }

        //Search examples
        System.out.println("=== Searching start ===");
        Searcher searcher = new Searcher();

//        ArrayList<Searching.Model.Game> byAmountOfAchievements = null;
//        try {
//            byAmountOfAchievements = searcher.search(Searching.Constant.Constant.AMOUNT_OF_ACHIEVEMENTS, "12", indexPath, null);
//        } catch (IOException | ParseException io) {
//            io.printStackTrace();
//        }
//
//        System.out.println("Results:");
//        for (Searching.Model.Game game : byAmountOfAchievements) {
//            System.out.println(" " + game.getName());
//        }
//        System.out.println();
//
//        ArrayList<Searching.Model.Game> byName = null;
//        try {
//            byName = searcher.search(Searching.Constant.Constant.NAME, "ACA NEOGEO", indexPath, null);
//        } catch (IOException | ParseException io) {
//            io.printStackTrace();
//        }
//
//        System.out.println("Results:");
//        for (Searching.Model.Game game : byName) {
//            System.out.println(" " + game.getName());
//        }
//
//        ArrayList<Searching.Model.Game> byRange = null;
//        byRange = searcher.search(Searching.Constant.Constant.AMOUNT_OF_ACHIEVEMENTS, "10", "15");
//
//        System.out.println("Results:");
//        for (Searching.Model.Game game : byRange) {
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
//        Searching.Utils.FileUtils.writeToFile(gameList, "games.txt");
//          //Get all words from games' names and write it to txt
//        Searching.Utils.WordUtils.getWordsData(
//           //Write synonyms to txt
//        Searching.Searcher.Synonym.getSynonyms();
    }
}
