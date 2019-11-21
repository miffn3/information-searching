package Searching.Utils;

import Searching.Model.Book;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class WordUtils {
    public static void getWordsData(String wordsPath, String booksPath){
        try {
            List<Book> books = FileUtils.readBooksList(booksPath);
            Set<String> words = new HashSet<>();
            books.stream().forEach(book-> words.addAll(WordUtils.getWords(book)));
            FileUtils.writeToFileByLine(words, wordsPath);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static Set<String> getWords(Book book){
        if (book == null){
            return new HashSet<>();
        }
        Set<String> words = getWords(book.getTitle());
        words.addAll(getWords(book.getSummary()));
        words.addAll(getWords(book.getType()));
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
