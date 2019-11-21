package Searching.Utils;

import Searching.Constant.Constant;
import Searching.Model.Book;
import Searching.Parser.Parser;
import Searching.Searcher.Indexer;
import Searching.Searcher.Searcher;
import Searching.Searcher.Synonym;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CLIUtils {

    private static final String INDEX_PATH = "index";
    private static final String BOOKS_PATH = "books.txt";
    private static final String WORDS_PATH = "words.txt";
    private static final String SYNONYMS_PATH = "synonyms.txt";

    Searcher searcher = new Searcher();

    public void greeting() {
        System.out.println("Hello.");
        chooseVar();
    }

    private void chooseVar() {
        int k = getK("main");

        if (k == 1) {
            ArrayList<Book> bookList = new ArrayList<>();
            try {
                Parser.loadData(bookList, BOOKS_PATH);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            chooseVar();
        }

        if (k == 2) {
            Indexer indexer = null;
            ArrayList<Book> bookList = new ArrayList<>();

            try {
                bookList = FileUtils.readBooksList(BOOKS_PATH);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                indexer = new Indexer(INDEX_PATH);
                indexer.createIndex(bookList);
            } catch (IOException io) {
                io.printStackTrace();
            }
            chooseVar();
        }

        if (k == 3) {
            WordUtils.getWordsData(WORDS_PATH, BOOKS_PATH);
            chooseVar();
        }

        if (k == 4) {
            Synonym.getSynonyms(SYNONYMS_PATH, WORDS_PATH);
            chooseVar();
        }

        if (k == 5) {
            search();
            chooseVar();
        }

        if (k == 6) {
            return;
        }
    }

    private void search() {
        int k = getK("search");

        if (k == 1) {
            searchByField();
            search();
        }

        if (k == 2) {
            searchByRange();
            search();
        }

        if (k == 3) {
            searchBySynonym();
            search();
        }

        if (k == 4) {
            return;
        }
    }

    private int getK(String usage) {
        System.out.println("\nChoose :");
        int to = 6;
        if (usage.equals("search")) {
            searchMenu();
            to = 4;
        } else if (usage.equals("main")) {
            menu();
        } else if (usage.equals("searchByField")) {
            to = 9;
            fieldsMenu();
        } else if (usage.equals("searchByRange")) {
            to = 3;
            numFieldsMenu();
        } else if (usage.equals("searchBySynonym")) {
            to = 7;
            stringFieldsMenu();
        }

        int k = 0;
        String tmp = readLine();

        if (StringUtils.isNumeric(tmp)) {
            k = Integer.parseInt(tmp);
        }


        while (k == 0 || k > to) {
            System.out.println("Incorrect, choose:");
            if (usage.equals("search")) {
                searchMenu();
            } else if (usage.equals("main")) {
                menu();
            } else if (usage.equals("searchByField")) {
                fieldsMenu();
            } else if (usage.equals("searchByRange")) {
                numFieldsMenu();
            } else if (usage.equals("searchBySynonym")) {
                stringFieldsMenu();
            }


                try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                tmp = reader.readLine();

                if (StringUtils.isNumeric(tmp)) {
                    k = Integer.parseInt(tmp);
                }
            }
            catch (IOException io) {
                io.printStackTrace();
            }
        }
        return k;
    }

    private void searchByField() {
        int k = getK("searchByField");
        ArrayList<Book> byField = null;
        String query = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        query = readQuery(query, reader);

        if (k == 1) {
            byField = byFieldString(query, Constant.TITLE);
            //printResults(byField);
            search();
        }

        if (k == 2) {
            byField = byFieldString(query, Constant.AUTHOR);
            search();
        }

        if (k == 3) {
            byField = byFieldString(query, Constant.TYPE);
            search();
        }

        if (k == 4) {
            byField = byFieldString(query, Constant.SUMMARY);
            search();
        }

        if (k == 5) {
            byField = byFieldString(query, Constant.PUBLISHER);
            search();
        }

        if (k == 6) {
            byField = byFieldString(query, Constant.PUBLICATION_DATE);
            search();
        }

        if (k == 7) {
            byField = byFieldString(query, Constant.ISBN);
            search();
        }

        if (k == 8) {
            byField = byFieldString(query, Constant.PRICE);
            search();
        }

        if (k == 9) {
            return;
        }
    }

    private void searchByRange() {
        int k = getK("searchByRange");
        ArrayList<Book> byRange = null;
        String from = "";
        String to = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        from = readQuery(from, reader);
        to = readQuery(to, reader);

        if (k == 1) {
            byRange = byRange(from, to, Constant.ISBN);
            //printResults(byRange);
            search();
        }

        if (k == 2) {
            byRange = byRange(from, to, Constant.PRICE);
            search();
        }

        if (k == 3) {
            return;
        }
    }

    private void searchBySynonym() {
        int k = getK("searchBySynonym");
        ArrayList<Book> bySynonym = null;
        String query = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        query = readQuery(query, reader);

        if (k == 1) {
            bySynonym  = bySynonym(query, Constant.TITLE);
            //printResults(byField);
            search();
        }

        if (k == 2) {
            bySynonym  = bySynonym(query, Constant.AUTHOR);
            search();
        }

        if (k == 3) {
            bySynonym  = bySynonym(query, Constant.TYPE);
            search();
        }

        if (k == 4) {
            bySynonym  = bySynonym(query, Constant.SUMMARY);
            search();
        }

        if (k == 5) {
            bySynonym  = bySynonym(query, Constant.PUBLISHER);
            search();
        }

        if (k == 6) {
            bySynonym  = bySynonym(query, Constant.PUBLICATION_DATE);
            search();
        }

        if (k == 7) {
            return;
        }
    }

    private void printResults(ArrayList<Book> books) {
        System.out.println("Results:");
        for (Book book : books) {
            System.out.println(" " + book.getTitle());
        }
    }

    private ArrayList<Book> byFieldString(String query, String constant) {
        ArrayList<Book> byField = new ArrayList<>();
        try {
            byField = searcher.searchByText(constant, INDEX_PATH, query);
        } catch (IOException | ParseException io) {
            io.printStackTrace();
        }
        return byField;
    }

    private ArrayList<Book> byRange(String from, String to, String constant) {
        ArrayList<Book> byField = new ArrayList<>();
        try {
            byField = searcher.searchByRange(constant, INDEX_PATH, from, to);
        } catch (IOException io) {
            io.printStackTrace();
        }
        return byField;
    }

    private ArrayList<Book> bySynonym(String query, String constant) {
        ArrayList<Book> byField = new ArrayList<>();
        try {
            byField = searcher.searchBySynonym(constant, INDEX_PATH, query);
        } catch (IOException | ParseException io) {
            io.printStackTrace();
        }
        return byField;
    }

    private String readQuery(String query, BufferedReader reader) {
        System.out.println("Enter query:");
        try {
            query = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return query;
    }

    private void searchMenu() {
        System.out.println("1 : Search by field");
        System.out.println("2 : Search in range");
        System.out.println("3 : Search by synonym");
        System.out.println("4 : exit");
    }

    private void menu() {
        System.out.println("1 : Parse to txt");
        System.out.println("2 : Index from txt");
        System.out.println("3 : Get words to txt");
        System.out.println("4 : Get synonyms");
        System.out.println("5 : Searcher");
        System.out.println("6 : exit");
    }

    private void fieldsMenu() {
        System.out.println("1 : Title");
        System.out.println("2 : Author");
        System.out.println("3 : Type");
        System.out.println("4 : Summary");
        System.out.println("5 : Publisher");
        System.out.println("6 : Publication date");
        System.out.println("7 : ISBN");
        System.out.println("8 : Price");
        System.out.println("9 : exit");
    }

    private void stringFieldsMenu() {
        System.out.println("1 : Title");
        System.out.println("2 : Author");
        System.out.println("3 : Type");
        System.out.println("4 : Summary");
        System.out.println("5 : Publisher");
        System.out.println("6 : Publication date");
        System.out.println("7 : exit");
    }

    private void numFieldsMenu() {
        System.out.println("1 : ISBN");
        System.out.println("2 : Price");
        System.out.println("3 : exit");
    }

    private String readLine() {
        String tmp = "";

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            tmp = reader.readLine();
        }
        catch (IOException io) {
            io.printStackTrace();
        }
        return tmp;
    }
}
