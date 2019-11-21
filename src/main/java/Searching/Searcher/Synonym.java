package Searching.Searcher;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Synonym {

    private static final int SYNONYMS_COUNT = 5;

    public static void getSynonyms(String synonymsPath, String wordsPath) {
        try {
            Word2Vec w2vModel = WordVectorSerializer.readWord2VecModel("model_fast_text_Base.bin");
            List<Collection<String>> result = new ArrayList<>();
            List<String> words = Files.readAllLines(Paths.get(wordsPath));
            for (String word : words) {
                Collection<String> synonyms = w2vModel.wordsNearest(word, SYNONYMS_COUNT);
                if (!(synonyms == null || synonyms.isEmpty())) {
                    synonyms.add(word);
                    result.add(synonyms);
                }
            }
            writeToFileByLine(result, synonymsPath);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    private static void writeToFileByLine(List<Collection<String>> lines, String wordsPath) {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(new File(wordsPath))))) {
            for (Collection<String> line : lines) {
                String newLine = String.join(",", line);
                writer.write(newLine);
                writer.newLine();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}