package algorithms;

import java.util.Arrays;

public class Kasai {

    // Build suffix array using prefix doubling
    public static int[] buildSuffixArray(String text) {

        int n = text.length();

        if (n == 0) {
            return new int[0];
        }

        Integer[] suffixes = new Integer[n];

        int[] rank = new int[n];
        int[] newRank = new int[n];

        for (int i = 0; i < n; i++) {

            suffixes[i] = i;

            rank[i] = text.charAt(i);
        }

        for (int k = 1; k < n; k *= 2) {

            final int step = k;
            final int[] currentRank = rank;

            Arrays.sort(suffixes, (a, b) -> {

                if (currentRank[a] != currentRank[b]) {

                    return Integer.compare(
                            currentRank[a],
                            currentRank[b]
                    );
                }

                int rankA = a + step < n
                        ? currentRank[a + step] : -1;

                int rankB = b + step < n
                        ? currentRank[b + step] : -1;

                return Integer.compare(rankA, rankB);
            });

            newRank[suffixes[0]] = 0;

            for (int i = 1; i < n; i++) {

                int previous = suffixes[i - 1];
                int current = suffixes[i];

                boolean different =
                        rank[previous] != rank[current];

                if (!different) {

                    int previousSecond =
                            previous + k < n
                                    ? rank[previous + k] : -1;

                    int currentSecond =
                            current + k < n
                                    ? rank[current + k] : -1;

                    different =
                            previousSecond != currentSecond;
                }

                newRank[current] =
                        newRank[previous] + (different ? 1 : 0);
            }

            int[] temp = rank;

            rank = newRank;
            newRank = temp;

            if (rank[suffixes[n - 1]] == n - 1) {
                break;
            }

            if (k > n / 2) {
                break;
            }
        }

        int[] suffixArray = new int[n];

        for (int i = 0; i < n; i++) {
            suffixArray[i] = suffixes[i];
        }

        return suffixArray;
    }

    // Kasai algorithm
    public static int[] buildLCP(String text, int[] suffixArray) {

        int n = text.length();

        int[] lcp = new int[n];
        int[] rank = new int[n];

        for (int i = 0; i < n; i++) {
            rank[suffixArray[i]] = i;
        }

        int common = 0;

        for (int i = 0; i < n; i++) {

            int position = rank[i];

            if (position == 0) {

                common = 0;

                continue;
            }

            int previousSuffix = suffixArray[position - 1];

            while (i + common < n
                    && previousSuffix + common < n
                    && text.charAt(i + common)
                    == text.charAt(previousSuffix + common)) {

                common++;
            }

            lcp[position] = common;

            if (common > 0) {
                common--;
            }
        }

        return lcp;
    }
}