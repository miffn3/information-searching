import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Parser {

    private static String findValue(String str, String start, char end) {
        String rez = "";
        int pos = str.indexOf(start);
        pos += start.length();
        while (str.charAt(pos) != end) {
            rez += str.charAt(pos);
            pos++;
        }
        return rez;
    }

    public static void loadData(ArrayList<Game> gameList) throws  IOException {
        Document doc;
        Elements data = null;
        System.out.println("=== Parsing start ===");

        for (int i = 1; i <= 1; i++) {

            doc = Jsoup.connect("https://www.xboxachievements.com/games/xbox-one/" + i + "/")
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.120 Safari/537.36")
                    .get();
            Elements currentPageData = doc.select("#col_l > div.bl_la_main > div > table:nth-child(1) > tbody > tr");
            currentPageData.remove(0);
            currentPageData.remove(0);

            if (i == 1) {
                data = currentPageData;
            } else {
                data.addAll(currentPageData);
            }

            getDataFromPage(gameList, currentPageData);
            
            System.out.println("--- " + i + " page ready ---");
        }

        System.out.println("Count of elements in list: " + data.size());
        System.out.println("=== Parsing finish ===\n");
    }

    private static void getDataFromPage(ArrayList<Game> gameList, Elements currentPageData) {
        for (Element currentGame : currentPageData) {

            String cover = findValue(currentGame.toString(),
                    "src=\"",
                    '\"');
            String name = findValue(currentGame.toString(),
                    "<strong>",
                    '<');
            String amountOfAchievements = findValue(currentGame.toString(),
                    "</a></td>\n <td align=\"center\">",
                    '<');
            String amountOfPoints = findValue(currentGame.toString(),
                    amountOfAchievements + "</td>\n <td align=\"center\">",
                    '<');
            String href = findValue(currentGame.toString(),
                    "href=\"",
                    '\"');
            
            Game game = new Game(cover, name, amountOfAchievements, amountOfPoints, href);
            gameList.add(game);
            
            System.out.println("\"" + name + "\" add in list");
        }
    }

}
