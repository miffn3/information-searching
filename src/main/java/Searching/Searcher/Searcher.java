package Searching.Searcher;

import Searching.Constant.Constant;
import Searching.Model.Book;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Searcher {

    private static final int NUM_OF_DOCS = 40;

    private ArrayList<Book> search(String indexDirectoryPath, Query query, int numOfDocs) throws IOException {
        Directory indexDirectory = FSDirectory.open(new File(indexDirectoryPath).toPath());
        IndexReader indexReader = DirectoryReader.open(indexDirectory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        System.out.println("Search '" + query + "'");
        TopDocs docs = indexSearcher.search(query, numOfDocs);
        long num = docs.totalHits;
        System.out.println("Number of results: " + num);

        ArrayList<Book> searchResult = new ArrayList<>();

        for(ScoreDoc scoreDoc : docs.scoreDocs){
            Document doc = indexSearcher.doc(scoreDoc.doc);
            long isbn = 0;
            int price = 0;
            if ( doc.get(Constant.ISBN) != null) {
                isbn = Long.valueOf(doc.get(Constant.ISBN));
            }
            if (doc.get(Constant.PRICE) != null) {
                price = Integer.valueOf(doc.get(Constant.PRICE));
            }

            Book book = new Book(doc.get(Constant.TITLE),
                    doc.get(Constant.AUTHOR),
                    doc.get(Constant.TYPE),
                    doc.get(Constant.SUMMARY),
                    price,
                    isbn,
                    doc.get(Constant.PUBLISHER),
                    doc.get(Constant.PUBLICATION_DATE));
            searchResult.add(book);
        }
        return searchResult;
    }

    public ArrayList<Book> searchByRange(String constant, String indexPathDirectory,
                                         String lowerValue, String upperValue) throws IOException {
        Query query = null;
        if (constant.equals(Constant.ISBN)) {
            long lower = Long.parseLong(lowerValue);
            long upper = Long.parseLong(upperValue);
            query = LongPoint.newRangeQuery(constant, lower, upper);
        }
        if (constant.equals(Constant.PRICE)) {
            int lower = Integer.parseInt(lowerValue);
            int upper = Integer.parseInt(upperValue);
            query = IntPoint.newRangeQuery(constant, lower, upper);
        }
        return  search(indexPathDirectory, query, NUM_OF_DOCS);
    }

    public ArrayList<Book> searchBySynonym(String constant, String indexPathDirectory,
                                           String phrase) throws ParseException, IOException {
        QueryParser queryParser = new QueryParser(constant, new SynonymAnalyzer());
        Query querySynonyms = null;
        querySynonyms = queryParser.parse(phrase);
        return search(indexPathDirectory, querySynonyms, NUM_OF_DOCS);
    }

    public ArrayList<Book> searchByText(String constant, String indexPathDirectory,
                                        String text) throws ParseException, IOException {
        Query query = null;
        if (constant.equals(Constant.ISBN)) {
            long number =  Long.parseLong(text);
            query = LongPoint.newRangeQuery(Constant.ISBN,number, number);
        } else if ( constant.equals(Constant.PRICE)) {
            int number = Integer.parseInt(text);
            query = IntPoint.newRangeQuery(Constant.PRICE,number, number);
        } else {
            QueryParser queryParser = new QueryParser(constant, new StandardAnalyzer());
            query = queryParser.parse(text);
        }
        return search(indexPathDirectory, query, NUM_OF_DOCS);
    }
}
