package algorithms;

import java.util.ArrayList;
import java.util.List;

public class NaiveSearch {

    public static List<Integer> search(String text, String pattern) {

        List<Integer> positions = new ArrayList<>();

        if (pattern.isEmpty()) {

            for (int i = 0; i <= text.length(); i++) {
                positions.add(i);
            }

            return positions;
        }

        for (int i = 0; i <= text.length() - pattern.length(); i++) {

            int j;

            for (j = 0; j < pattern.length(); j++) {

                if (text.charAt(i + j) != pattern.charAt(j)) {
                    break;
                }
            }

            if (j == pattern.length()) {
                positions.add(i);
            }
        }

        return positions;
    }
}