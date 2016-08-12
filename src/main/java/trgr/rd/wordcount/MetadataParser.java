package trgr.rd.wordcount;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Pattern;

class MetadataParser {

    // Regex patterns for text processing of files
	private static final Pattern PATTERN_STOP_WORDS = Pattern.compile("(?i)\\b(the|a|of|and|this|that|be|to|not|is|we|is|in)\\b");
	private static final Pattern PATTERN_PUNC = Pattern.compile("[^\\w\\s]");
	private static final Pattern PATTERN_MULTI_SPACE = Pattern.compile("[\\s]{2,}");


	/**
	 * Reads a file and returns an object representing the file's metadata
	 * @param fileNumber - the sequential number of the file
	 * @param path - the file being parsed
	 * @return a FileMetadata object
	 */
	FileMetadata parse(int fileNumber, Path path)
    {
		try (BufferedReader reader = new BufferedReader(Files.newBufferedReader(path, Charset.forName("US-ASCII"))))
        {

			String line;

			FileMetadata fmd = new FileMetadata(path.getFileName().toString(), fileNumber);
			ArrayList<String> words;
			while ((line = reader.readLine()) != null)
            {
				// Remove stop words and get an array of words
				words = parseLine(line);

				// add each word to the FileMetadata object
				fmd.addWords(words);
			}

			// Once done processing, return fmd object
			return fmd;
		}
		catch (IOException  e)
        {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Uses regex to parse line into an array of words
	 * @param line - A string of a line in the file
	 * @return An arraylist where each element is a word
	 */
	private ArrayList<String> parseLine(String line)
    {

		String result = PATTERN_STOP_WORDS.matcher(
				PATTERN_MULTI_SPACE.matcher(PATTERN_PUNC.matcher(line.toLowerCase()).replaceAll(""))
						.replaceAll("")).replaceAll("");

		ArrayList<String> wordList = new ArrayList<>(Arrays.asList(result.split(" ")));
		
		// Clean up null and space elements
		wordList.removeAll(Collections.singleton(""));
		wordList.removeAll(Collections.singleton(" "));
		
		return wordList;
	}
}
