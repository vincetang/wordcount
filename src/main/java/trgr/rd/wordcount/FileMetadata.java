package trgr.rd.wordcount;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


class FileMetadata {
	
	private String name;
	private int fileNumber;
	private Map<String, Integer> wordCounts;
	private int totalWordCount;
	
	/**
	 * Constructor for FileMetadata
	 * Takes a file name and the file number
	 * @param name - The simple name of the file
	 */
	FileMetadata(String name, int fileNumber) {
		this.name = name;
		this.fileNumber = fileNumber;
		this.wordCounts = new HashMap<>();
		this.totalWordCount = 0;
	}

	/**
	 * Adds words passed in as an arraylist to the hashmap.
	 * @param words - an arraylist of words to add
	 */
	void addWords(ArrayList<String> words) {
		words.forEach(this::addWord);
	}
	
	/**
	 * Adds a word to the hashmap. If the word exists, increment the count.
	 * Otherwise, add the word with a count of 1.
	 * Increments the total word count by 1
	 * @param word - the word being added
	 */
	private void addWord(String word) {
		if (wordCounts.containsKey(word))
			wordCounts.put(word, wordCounts.get(word) + 1);
		else
			wordCounts.put(word, 1);
		totalWordCount += 1;
	}
	
	/**
	 * Returns the number of unique words in the wordCounts HashMap
	 * @return int - the count of unique words
	 */
	private int countUniqueWords() {
		return Collections.frequency(new ArrayList<>(wordCounts.values()), 1);
	}
	
	/** Returns string of metadata in a readable format
	 */
	public String toString(){
		StringBuilder s = new StringBuilder("File#: " + this.fileNumber + " " + this.name + '\n'
				+ "Total Words: " + this.totalWordCount + '\n'
				+ "Unique Words: " + this.countUniqueWords() + '\n');
		
		s.append("Most Frequent Words:\n");
		
		// Sort the words by value
		Map sortedWords = getSortedWordCounts();
		
		Set keys = sortedWords.keySet();
		Iterator iter = keys.iterator(); 
		int i = 0;
		while (iter.hasNext() && i < 25){
			String word = (String) iter.next();
			Integer count = (Integer) sortedWords.get(word);
			s.append(word).append(" (").append(count).append(")\n");
			i++;
		}
		s.append('\n');
		
		return s.toString();
	}
	 
	/**
	 * This algorithm takes in a map and returns a map with values sorted in
	 * descending order
	 * This is a modified version of the algorithm taken from stackoverflow:
	 *  http://stackoverflow.com/a/2581754/5610531
	 * @param map The map to be sorted
	 * @return The sorted map
	 */
    static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
    	
	    List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
	    
	    Collections.sort( list, (o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));

		Map<K, V> result = new LinkedHashMap<>();
	    for (Map.Entry<K, V> entry : list)
	    {
	        result.put( entry.getKey(), entry.getValue() );
	    }
	    return result;
}
    
	/**
	 * Used to return a sorted map object and adds the word counts for this file
	 * to the top25Counts in the WordCountApp
	 * @return Returns a map sorted in descending order of counts (values)
	 */
	private Map getSortedWordCounts() {
		Map<String, Integer> sortedMap = sortByValue(this.wordCounts);
		WordCountApp.top25Counts.add(sortedMap);
		
		return sortedMap;
	}
	

}
