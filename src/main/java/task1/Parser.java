package task1;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Parser {

    public void parse() {
        Document doc = null;
        try {
            doc = Jsoup.connect("https://yandex.ru/")
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(doc);
    }
}
