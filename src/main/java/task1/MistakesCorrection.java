package task1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class MistakesCorrection {

    private List<String> dictionary = new ArrayList<>();
    private Map<String, List<String>> kGrammsDictionary = new HashMap<>();
    private int k;

    public void parseDictionary(String path) throws IOException {
        Scanner sc = new Scanner(new File(path));
        String word;

        while(sc.hasNext()){
            word = sc.nextLine();
            this.dictionary.add(word);
        }
    }

    public void makeKGrammsDictionary(int size) {
        k = size;

        for(int i = 0; i < dictionary.size(); i++){
            List<String> kGramms = makeKGramms(dictionary.get(i));
            for(int j = 0; j < kGramms.size(); j++){
                if(kGrammsDictionary.containsKey(kGramms.get(j))) {
                    kGrammsDictionary.get(kGramms.get(j)).add(dictionary.get(i));
                }
                else {
                    List<String> tmp = new ArrayList<>();
                    tmp.add(dictionary.get(i));
                    kGrammsDictionary.put(kGramms.get(j), tmp);
                }
            }
        }
    }

    public Set<String> findPossible(String word) {
        Set<String> result = new HashSet<>();
        Set<String> candidates = findCandidates(word);
        int min = Integer.MAX_VALUE;

        for (String candidate : candidates) {
            int distance = getDistance(candidate, word);
            if (distance < min) {
                min = distance;
                result.clear();
            }
            if (distance == min) {
                result.add(candidate);
            }
        }

        return result;
    }

    private  List<String> makeKGramms(String word) {
        List<String> kGramms = new ArrayList<>();
        int wordLen = word.length();

        if(wordLen < k) {
            kGramms.add(word);
        }
        for (int i = 0; i < wordLen - k + 1; i++) {
            String subStr = word.substring(i, i + k);
            kGramms.add(subStr);
        }
        return kGramms;
    }

    private Set<String> findCandidates(String word){
        List<String> wordKGram = makeKGramms(word);
        Set<String> candidates = new HashSet<>();

        for(int i = 0; i < wordKGram.size(); i++) {
            if (kGrammsDictionary.containsKey(wordKGram.get(i))){
               candidates.addAll(kGrammsDictionary.get(wordKGram.get(i)));
            }
        }
        return candidates;
    }

    private int getDistance(String first, String second) {
        int[] Di_1 = new int[second.length() + 1];
        int[] Di = new int[second.length() + 1];

        for (int j = 0; j <= second.length(); j++) {
            Di[j] = j; // (i == 0)
        }

        for (int i = 1; i <= first.length(); i++) {
            System.arraycopy(Di, 0, Di_1, 0, Di_1.length);

            Di[0] = i; // (j == 0)
            for (int j = 1; j <= second.length(); j++) {
                int cost = (first.charAt(i - 1) != second.charAt(j - 1)) ? 1 : 0;
                Di[j] = min(Di_1[j] + 1, Di[j - 1] + 1, Di_1[j - 1] + cost);
            }
        }
        return Di[Di.length - 1];
    }

    private static int min(int n1, int n2, int n3) {
        return Math.min(Math.min(n1, n2), n3);
    }
}
