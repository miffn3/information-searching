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

    public ArrayList<Game> search(String constant, String text, String indexDirectoryPath, Query queryOpt) throws IOException, ParseException {
        Directory indexDirectory = FSDirectory.open(new File(indexDirectoryPath).toPath());
        IndexReader indexReader = DirectoryReader.open(indexDirectory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        Query query = null;
        if (queryOpt == null) {
            if (constant.equals(Constant.AMOUNT_OF_ACHIEVEMENTS) || constant.equals(Constant.AMOUNT_OF_POINTS)) {
                long number =  Long.parseLong(text);
                query = LongPoint.newRangeQuery(Constant.AMOUNT_OF_ACHIEVEMENTS,number, number);
            } else {
                QueryParser queryParser = new QueryParser(constant, new StandardAnalyzer());
                query = queryParser.parse(text);
            }
        } else {
            query = queryOpt;
        }
        System.out.println("Search '" + query + "'");

        TopDocs docs = indexSearcher.search(query, 10);

        long num = docs.totalHits;
        System.out.println("Number of results: " + num);

        ArrayList<Game> searchResult = new ArrayList<>();

        for(ScoreDoc scoreDoc : docs.scoreDocs){
            Document doc = indexSearcher.doc(scoreDoc.doc);
            long namOfAchievements = 0;
            long numOfPoints = 0;
            if ( doc.get(Constant.AMOUNT_OF_ACHIEVEMENTS) != null) {
                namOfAchievements = Long.valueOf(doc.get(Constant.AMOUNT_OF_ACHIEVEMENTS));
            }
            if (doc.get(Constant.AMOUNT_OF_POINTS) != null) {
                numOfPoints = Long.valueOf(doc.get(Constant.AMOUNT_OF_POINTS));
            }

            Game game = new Game(doc.get(Constant.COVER),
                    doc.get(Constant.NAME),
                    namOfAchievements,
                    numOfPoints,
                    doc.get(Constant.HREF));
            searchResult.add(game);
        }
        return searchResult;
    }

    public ArrayList<Game> search(String constant, String lowerValue, String upperValue)  {
        long achievementsLower =  Long.parseLong(lowerValue);
        long  achievementsUpper = Long.parseLong(upperValue);
        Query query = LongPoint.newRangeQuery(constant, achievementsLower, achievementsUpper);
        ArrayList<Game> games = new ArrayList<>();
        try {
            games =  search(null, null, "index", query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return games;
    }

    public ArrayList<Game> searchBySynonym(String search)  {
        QueryParser queryParser = new QueryParser(Constant.NAME, new SynonymAnalyzer());
        Query querySynonyms = null;
        try {
            querySynonyms = queryParser.parse(search);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ArrayList<Game> games = new ArrayList<>();
        try {
            games =  search(null,null,"index", querySynonyms);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return games;
    }
}
