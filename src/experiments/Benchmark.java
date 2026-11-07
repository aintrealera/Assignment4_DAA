package experiments;

import algorithms.*;

import java.io.File;
import java.io.PrintWriter;
import java.util.Random;

public class Benchmark {

    private static final int RUNS = 5;

    private static final String ALPHABET = "ABCD";

    private static final Random random = new Random(42);

    public static void main(String[] args) {

        new File("results").mkdirs();

        try (PrintWriter writer =
                     new PrintWriter("results/results.csv")) {

            writer.println(
                    "Category,Algorithm,Size,Case,AverageTimeNs"
            );

            System.out.println("BENCHMARK STARTED");

            runDPExperiments(writer);

            runMatchingExperiments(writer);

            runKasaiExperiments(writer);

            System.out.println("\nRESULTS SAVED!");

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    private static String generateString(int length) {

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {

            int index = random.nextInt(ALPHABET.length());

            result.append(ALPHABET.charAt(index));
        }

        return result.toString();
    }

    private static long measure(Runnable task) {

        long total = 0;

        for (int i = 0; i < RUNS; i++) {

            long start = System.nanoTime();

            task.run();

            long end = System.nanoTime();

            total += end - start;
        }

        return total / RUNS;
    }

    // Dynamic Programming
    private static void runDPExperiments(PrintWriter writer) {

        System.out.println("\nDYNAMIC PROGRAMMING");

        int[] sizes = {100, 500, 1000};

        for (int size : sizes) {

            String first = generateString(size);

            String second = generateString(size);

            long lcsTime = measure(() ->
                    LCS.findLCS(first, second)
            );

            long editTime = measure(() ->
                    EditDistance.calculate(first, second)
            );

            save(writer, "DP", "LCS",
                    size, "Random", lcsTime);

            save(writer, "DP", "EditDistance",
                    size, "Random", editTime);

            System.out.println("Size: " + size);

            System.out.println("LCS: " + lcsTime + " ns");

            System.out.println("Edit Distance: " + editTime + " ns");
        }
    }

    // String Matching
    private static void runMatchingExperiments(PrintWriter writer) {

        System.out.println("\nSTRING MATCHING");

        int[] sizes = {10000, 50000, 100000};

        for (int size : sizes) {

            String text = generateString(size);

            String present = text.substring(
                    size / 2,
                    size / 2 + 10
            );

            String absent = "ZZZZZZZZZZ";

            testMatching(writer, text, present,
                    size, "Present");

            testMatching(writer, text, absent,
                    size, "Absent");
        }
    }

    private static void testMatching(
            PrintWriter writer,
            String text,
            String pattern,
            int size,
            String testCase) {

        long naiveTime = measure(() ->
                NaiveSearch.search(text, pattern)
        );

        long kmpTime = measure(() ->
                KMP.search(text, pattern)
        );

        long rabinTime = measure(() ->
                RabinKarp.search(text, pattern)
        );

        save(writer, "Matching", "Naive",
                size, testCase, naiveTime);

        save(writer, "Matching", "KMP",
                size, testCase, kmpTime);

        save(writer, "Matching", "RabinKarp",
                size, testCase, rabinTime);

        System.out.println(
                "Size: " + size + " | " + testCase
        );

        System.out.println("Naive: " + naiveTime);

        System.out.println("KMP: " + kmpTime);

        System.out.println("Rabin-Karp: " + rabinTime);
    }

    // Kasai
    private static void runKasaiExperiments(PrintWriter writer) {

        System.out.println("\nKASAI ALGORITHM");

        int[] sizes = {1000, 5000, 10000};

        for (int size : sizes) {

            String text = generateString(size);

            int[] suffixArray =
                    Kasai.buildSuffixArray(text);

            long time = measure(() ->
                    Kasai.buildLCP(text, suffixArray)
            );

            save(writer, "Kasai", "Kasai",
                    size, "Random", time);

            System.out.println(
                    "Size: " + size + " | " + time + " ns"
            );
        }
    }

    private static void save(
            PrintWriter writer,
            String category,
            String algorithm,
            int size,
            String testCase,
            long time) {

        writer.println(
                category + ","
                        + algorithm + ","
                        + size + ","
                        + testCase + ","
                        + time
        );

        writer.flush();
    }
}