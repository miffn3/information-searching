package task1;

import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

public class Task1 {
    public static void main(String[] args) {
        String dictPath = "src/main/resources/rus_dic.txt";

        MistakesCorrection corrector = new MistakesCorrection();
        try {
            corrector.parseDictionary(dictPath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Scanner in = new Scanner(System.in);
        System.out.println("Enter word:");
        String wordToCheck = in.nextLine();
        System.out.println("Enter length:");
        int k = in.nextInt();

        corrector.makeKGrammsDictionary(k);
        long cur = System.currentTimeMillis();
        Set<String> result = corrector.findPossible(wordToCheck);
        long after = System.currentTimeMillis();
        System.out.println(after-cur);
        System.out.println(result);
    }
}
