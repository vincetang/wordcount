package trgr.rd.wordcount;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

class MetadataParser {

    // Regex patterns for text processing of files
    private static final Pattern PATTERN_STOP_WORDS = Pattern.compile(getStopWordsRegex());
    private static final Pattern PATTERN_PUNC = Pattern.compile("[^\\w\\s]");
    private static final Pattern PATTERN_MULTI_SPACE = Pattern.compile("[\\s]{2,}");


    private static Set<String> wordSet;

    /**
     * Reads a file and returns an object representing the file's metadata
     *
     * @param fileNumber - the sequential number of the file
     * @param path       - the file being parsed
     * @return a FileMetadata object
     */
    FileMetadata parse(int fileNumber, Path path)
    {
        try (BufferedReader reader = new BufferedReader(Files.newBufferedReader(path, Charset.forName("UTF-8"))))
        {

            String line;

            FileMetadata fmd = new FileMetadata(path.getFileName().toString(), fileNumber);
            List<String> words;
            while ((line = reader.readLine()) != null)
            {
                // Remove stop words and get an array of words
                words = parseLine(line);

                // add each word to the FileMetadata object
                fmd.addWords(words);
            }

            // Once done processing, return fmd object
            return fmd;
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Uses regex to parse line into an array of words
     *
     * @param line - A string of a line in the file
     * @return An arraylist where each element is a word
     */
    private List<String> parseLine(String line)
    {

        ArrayList<String> wordList = new ArrayList<>(Arrays.asList(line.split(" ")));

        List<String> cleanedList = new ArrayList<>();

        for (String word : wordList)
        {

            if (!wordSet.contains(word))
            {
                cleanedList.add(word);
            }

        }

        // Clean up null and space elements
        cleanedList.removeAll(Collections.singleton(""));
        cleanedList.removeAll(Collections.singleton(" "));

        return cleanedList;
    }

    /**
     * Parses the file containing stop words, separated by new lines
     *
     * @return a regex string to capture stop words in the file
     */
    private static String getStopWordsRegex()
    {
        try
        {
            Path filePath = Paths.get("./target/classes/stopwords.txt");

            try (BufferedReader reader = new BufferedReader(Files.newBufferedReader(filePath)))
            {
                String line;
                wordSet = new HashSet<>();

                while ((line = reader.readLine()) != null)
                {
                    wordSet.add(line);
                }

                StringBuilder stopWordsRegex = new StringBuilder("(?i)\\b(");
                wordSet.forEach(word -> stopWordsRegex.append(word).append("|"));
                stopWordsRegex.replace(stopWordsRegex.length() - 1, stopWordsRegex.length(), "");
                stopWordsRegex.append(")\\b");
                System.out.println(stopWordsRegex);

                return stopWordsRegex.toString();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return "";
    }
}
