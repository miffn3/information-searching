import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Indexer {
    private IndexWriter indexWriter;
    private IndexWriterConfig indexWriterConfig;

    public Indexer(String indexDirectoryPath) throws IOException {
        Directory index = FSDirectory.open(new File(indexDirectoryPath).toPath());
        indexWriterConfig = new IndexWriterConfig();
        indexWriter = new IndexWriter(index, indexWriterConfig);
    }

    public void close() throws IOException {
        indexWriter.close();
    }

    private static Document getDocument(Game game) {
        Document document = new Document();
        document.add(new StringField(Constant.COVER.value, game.getCover(), Field.Store.YES));
        document.add(new TextField(Constant.NAME.value, game.getName(), Field.Store.YES));
        document.add(new StringField(Constant.AMOUNT_OF_ACHIEVEMENTS.value,
                game.getAmountOfAchievements(), Field.Store.YES));
        document.add(new StringField(Constant.AMOUNT_OF_POINTS.value, game.getAmountOfPoints(), Field.Store.YES));
        document.add(new StringField(Constant.HREF.value, game.getHref(), Field.Store.YES));
        return document;
    }

    public void createIndex (ArrayList<Game> gameList) throws IOException {
        for(Game game : gameList) {
            indexWriter.addDocument(getDocument(game));
        }
        close();
    }
}
