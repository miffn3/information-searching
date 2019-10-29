import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
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

    public ArrayList<Game> search(String constant, String text, String indexDirectoryPath) throws IOException, ParseException {
        Directory indexDirectory = FSDirectory.open(new File(indexDirectoryPath).toPath());
        IndexReader indexReader = DirectoryReader.open(indexDirectory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        QueryParser queryParser = new QueryParser(constant, new StandardAnalyzer());
        Query query = queryParser.parse(text);
        System.out.println("Search '" + query + "'");

        TopDocs docs = indexSearcher.search(query, 10000);

        long num = docs.totalHits;
        System.out.println("Number of results: " + num);

        ArrayList<Game> searchResult = new ArrayList<>();

        for(ScoreDoc scoreDoc : docs.scoreDocs){
            Document doc = indexSearcher.doc(scoreDoc.doc);
            Game game = new Game(doc.get(Constant.COVER.value),
                    doc.get(Constant.NAME.value),
                    doc.get(Constant.AMOUNT_OF_ACHIEVEMENTS.value),
                    doc.get(Constant.AMOUNT_OF_POINTS.value),
                    doc.get(Constant.HREF.value));
            searchResult.add(game);
        }
        return searchResult;
    }
}
