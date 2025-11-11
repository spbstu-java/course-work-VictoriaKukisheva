package lab3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class Translator {

    // Modified to accept file path
    public static HashMap<String, String> checkFile(String filePath) throws FileReadException, InvalidFileFormatException {
        HashMap<String, String> dictionary = new HashMap<>();

        try (BufferedReader file = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;

            while ((line = file.readLine()) != null){
                lineNumber++;
                String[] parts = line.split("\\|");

                if (parts.length != 2){
                    throw new InvalidFileFormatException("Invalid format on line " + lineNumber + ": " + line);
                }

                String english = parts[0].trim().toLowerCase();
                String russian = parts[1].trim();

                dictionary.put(english, russian);
            }

            return dictionary;

        } catch (IOException e){
            throw new FileReadException("Cannot read file. Error: " + e.getMessage(), e);
        }
    }

    public static String translateText(HashMap<String, String> dictionary, String text) {
        String[] words = text.split("\\s+");
        StringBuilder result = new StringBuilder();

        int i = 0;
        while (i < words.length) {
            String longestMatch = null;
            String translation = null;

            for (int k = i + 1; k <= words.length; k++) {
                String phrase = String.join(" ", Arrays.copyOfRange(words, i, k)).toLowerCase();

                if (dictionary.containsKey(phrase)) {
                    longestMatch = phrase;
                    translation = dictionary.get(phrase);
                }
            }

            if (translation != null) {
                result.append(translation).append(" ");
                i += longestMatch.split(" ").length;
            } else {
                result.append(words[i]).append(" ");
                i++;
            }
        }

        return result.toString().trim();
    }
}