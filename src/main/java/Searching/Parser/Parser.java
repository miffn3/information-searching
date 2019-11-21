package Searching.Parser;

import Searching.Model.Book;
import Searching.Utils.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Parser {

    public static void loadData(ArrayList<Book> bookList, String booksPath) throws  IOException {
        Document doc;
        Elements data = null;
        System.out.println("=== Parsing start ===");

        for (int i = 1; i <= 500; i++) {

            doc = Jsoup.connect("https://www.books.ru/proza-9001284/?page=" + i + "/")
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.120 Safari/537.36")
                    .get();
            Elements currentPageData = doc.select("table.catalog").select("tr");
            currentPageData.remove(0);
            currentPageData.remove(0);

            if (i == 1) {
                data = currentPageData;
            } else {
                data.addAll(currentPageData);
            }

            getDataFromPage(bookList, currentPageData);
            
            System.out.println("--- " + i + " page ready ---");
        }
        FileUtils.writeToFile(bookList, booksPath);
        System.out.println("Count of elements in list: " + data.size());
        System.out.println("=== Parsing finish ===\n");
    }

    private static void getDataFromPage(ArrayList<Book> bookList, Elements currentPageData) {
        for (int i = 0; i< currentPageData.size(); i++) {
            Book book = new Book();
            book.setAuthor(parseElement(currentPageData.get(i).select("p.authors")));
            book.setTitle(parseElement(currentPageData.get(i).select("p.title")));
            book.setType(parseElement(currentPageData.get(i).select("p.tip-books")));
            parseDetails(currentPageData.get(i).select("p.details").first(), book);
            book.setSummary(currentPageData.get(i).select("p").get(3).text());
            Elements price = currentPageData.get(i).select("span.price");
            if (price.size() > 0) {
                String tmp = price.get(0).text().replaceAll("\\s+|[А-Я]+|[а-я]+|[A-Z]+|[a-z]+", "");
                if (tmp.length() > 5) {
                    tmp = tmp.substring(0,5);
                }
                int tmpInt = 0;
                try {
                    tmpInt = Integer.parseInt(tmp);
                } catch (Exception ex) {

                }
                book.setPrice(tmpInt);
            } else {
                book.setPrice(0);
            }
            bookList.add(book);
        }
    }

    private static String parseElement(Elements element) {
        String value = "";
        if(element.size() == 1) {
            return element.text();
        }

        for(Element el : element) {
            value += el.text() + ',';
        }

        if(value.length() > 1) {
            value = value.substring(0, value.length() - 1);
        }
        return value;
    }

    private static void parseDetails(Element element, Book book) {
        for(Node el : element.childNodes()) {
            String elString = el.toString().replaceAll(":\\s+", "");
            if (elString.contains("ISBN")) {
                book.setIsbn(Long.parseLong(elString.substring(5).replaceAll("-|[А-Я]+|[а-я]+|[A-Z]+|[a-z]+\"", "")));
            } else if (elString.contains("Издательство")) {
                book.setPublisher(elString.substring(13));
            } else if (elString.contains("Дата выхода")) {
                book.setPublicationDate(elString.substring(12));
            }
        }
    }
}
