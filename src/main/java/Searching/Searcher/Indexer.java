package Searching.Searcher;

import Searching.Constant.Constant;
import Searching.Model.Book;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.LongPoint;
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

    private static Document getDocument(Book book) {
        Document document = new Document();
        document.add(new TextField(Constant.TITLE, book.getTitle(), Field.Store.YES));
        document.add(new TextField(Constant.AUTHOR, book.getAuthor(), Field.Store.YES));
        document.add(new TextField(Constant.TYPE, book.getType(), Field.Store.YES));
        document.add(new TextField(Constant.SUMMARY, book.getSummary(), Field.Store.YES));
        document.add(new IntPoint(Constant.PRICE, book.getPrice()));
        long isbn = 0;
        if ( book.getIsbn() != null ) {
            isbn =  book.getIsbn();
        }
        document.add(new LongPoint(Constant.ISBN, isbn));
        String publisher = "неизвестно";
        String date = "неизвестно";
        if (book.getPublisher() != null) {
            publisher = book.getPublisher();
        }
        if (book.getPublicationDate() != null) {
            date = book.getPublicationDate();
        }
        document.add(new TextField(Constant.PUBLISHER, publisher , Field.Store.YES));
        document.add(new TextField(Constant.PUBLICATION_DATE, date, Field.Store.YES));
        return document;
    }

    public void createIndex (ArrayList<Book> bookList) throws IOException {
        for(Book book : bookList) {
            indexWriter.addDocument(getDocument(book));
        }
        close();
    }
}
