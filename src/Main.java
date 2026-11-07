import algorithms.*;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        System.out.println("================================");
        System.out.println("       TEXT ANALYSIS");
        System.out.println("================================");

        // LCS
        String first = "AGGTAB";
        String second = "GXTXAYB";

        System.out.println("\n1. LONGEST COMMON SUBSEQUENCE");

        System.out.println(
                "Result: " + LCS.findLCS(first, second)
        );

        // Edit Distance
        System.out.println("\n2. EDIT DISTANCE");

        System.out.println(
                "Result: "
                        + EditDistance.calculate("kitten", "sitting")
        );

        // String Matching
        String text = "ABABCABAB";
        String pattern = "AB";

        System.out.println("\n3. NAIVE SEARCH");

        System.out.println(
                NaiveSearch.search(text, pattern)
        );

        System.out.println("\n4. KMP SEARCH");

        System.out.println(
                KMP.search(text, pattern)
        );

        System.out.println("\n5. RABIN-KARP SEARCH");

        System.out.println(
                RabinKarp.search(text, pattern)
        );

        // Kasai
        System.out.println("\n6. KASAI ALGORITHM");

        String sample = "banana";

        int[] suffixArray =
                Kasai.buildSuffixArray(sample);

        int[] lcp =
                Kasai.buildLCP(sample, suffixArray);

        System.out.println(
                "Suffix Array: "
                        + Arrays.toString(suffixArray)
        );

        System.out.println(
                "LCP Array: "
                        + Arrays.toString(lcp)
        );

        System.out.println("\n================================");
        System.out.println("       PROGRAM FINISHED");
        System.out.println("================================");
    }
}