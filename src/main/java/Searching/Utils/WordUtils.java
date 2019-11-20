package Searching.Utils;

import Searching.Model.Game;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class WordUtils {
    public static void getWordsData(){
        try {
            List<Game> games = FileUtils.readGamesList("games.txt");
            Set<String> words = new HashSet<>();
            games.stream().forEach(game-> words.addAll(WordUtils.getWords(game)));
            FileUtils.writeToFileByLine(words, "words.txt");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static Set<String> getWords(Game game){
        if (game == null){
            return new HashSet<>();
        }
        Set<String> words = getWords(game.getName());
        return words;
    }

    private static Set<String> getWords(String phrase){
        Set<String> words = Arrays.asList(phrase.split("\\s"))
                .stream()
                .map(x->x.trim()
                        .replaceAll("[.,!?;:\"()%№+-=*<>$&]", "")
                        .replaceAll("\\d+", ""))
                .filter(x->!x.isEmpty())
                .collect(Collectors.toSet());
        return words;
    }
}
