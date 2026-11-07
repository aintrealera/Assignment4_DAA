package tests;

import algorithms.*;

import java.util.Arrays;
import java.util.List;

public class AlgorithmTests {

    public static void main(String[] args) {

        System.out.println("===== ALGORITHM TESTS =====");

        testLCS();

        testEditDistance();

        testStringMatching();

        testKasai();

        System.out.println("\nALL TESTS COMPLETED!");
    }

    private static void testLCS() {

        System.out.println("\nLCS TESTS");

        check(LCS.findLCS("abc", "abc") == 3,
                "Identical strings");

        check(LCS.findLCS("abc", "xyz") == 0,
                "Different strings");

        check(LCS.findLCS("", "abc") == 0,
                "Empty string");
    }

    private static void testEditDistance() {

        System.out.println("\nEDIT DISTANCE TESTS");

        check(EditDistance.calculate("abc", "abc") == 0,
                "Identical strings");

        check(EditDistance.calculate("", "abc") == 3,
                "Empty string");

        check(EditDistance.calculate("cat", "bat") == 1,
                "Substitution");

        check(EditDistance.calculate("cat", "cats") == 1,
                "Insertion");

        check(EditDistance.calculate("cats", "cat") == 1,
                "Deletion");
    }

    private static void testStringMatching() {

        System.out.println("\nSTRING MATCHING TESTS");

        checkSearch("abcde", "ab",
                Arrays.asList(0), "Beginning");

        checkSearch("abcde", "cd",
                Arrays.asList(2), "Middle");

        checkSearch("abcde", "de",
                Arrays.asList(3), "End");

        checkSearch("ababab", "ab",
                Arrays.asList(0, 2, 4), "Multiple occurrences");

        checkSearch("abcdef", "xyz",
                Arrays.asList(), "Pattern absent");

        checkSearch("abc", "abcd",
                Arrays.asList(), "Pattern longer than text");

        checkSearch("abc", "",
                Arrays.asList(0, 1, 2, 3), "Empty pattern");
    }

    private static void checkSearch(
            String text,
            String pattern,
            List<Integer> expected,
            String name) {

        List<Integer> naive =
                NaiveSearch.search(text, pattern);

        List<Integer> kmp =
                KMP.search(text, pattern);

        List<Integer> rabin =
                RabinKarp.search(text, pattern);

        check(naive.equals(expected),
                name + " - Naive");

        check(kmp.equals(expected),
                name + " - KMP");

        check(rabin.equals(expected),
                name + " - Rabin-Karp");

        check(naive.equals(kmp) && kmp.equals(rabin),
                name + " - Consistency");
    }

    private static void testKasai() {

        System.out.println("\nKASAI TESTS");

        String text = "banana";

        int[] expectedSA = {5, 3, 1, 0, 4, 2};

        int[] expectedLCP = {0, 1, 3, 0, 0, 2};

        int[] actualSA =
                Kasai.buildSuffixArray(text);

        int[] actualLCP =
                Kasai.buildLCP(text, actualSA);

        check(Arrays.equals(actualSA, expectedSA),
                "Suffix Array");

        check(Arrays.equals(actualLCP, expectedLCP),
                "LCP Array");
    }

    private static void check(boolean condition, String name) {

        if (condition) {

            System.out.println("[PASS] " + name);

        } else {

            System.out.println("[FAIL] " + name);

            throw new RuntimeException(
                    "Test failed: " + name
            );
        }
    }
}