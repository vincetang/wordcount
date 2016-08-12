package trgr.rd.wordcount;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MetadataParserTest {

	// the|a|of|and|this|that|be|to|not|is|we|in
	private MetadataParser parser;
	@Before
	public void setUp() throws Exception {
		WordCountApp app = new WordCountApp();
		this.parser = new MetadataParser();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test 
	public void testRemovingNoWords() {
		ArrayList<String> result = parser.parseLine("My sentence contains no stop words");
		System.out.println(result);
		assertEquals(result.size(), 6);
	}
	
	@Test
	public void testRemoveAllWords() {
		ArrayList<String> result = parser.parseLine("the a of and this that be to not is we is in");
		System.out.println(result);
		assertEquals(result.size(), 0);
	}
	
	@Test
	public void testRemovingWords() {
		ArrayList<String> result = parser.parseLine("testing a to ant banana is a there no can do we thing");
		System.out.println(result);
		assertEquals(result.size(), 8);	
	}

	@Test
	public void testText() {
		ArrayList<String> result = parser.parseLine("Beware the Jabberwock, my son!  The jaws that bite, the claws that catch! Beware the Jubjub bird, and shun  The frumious Bandersnatch!\""
				+ "He took his vorpal sword in hand:  Long time the manxome foe he sought --So rested he by the Tumtum tree,"
				+ "  And stood awhile in thought.And, as in uffish thought he stood,  The Jabberwock, with eyes of flame,"
				+ "Came whiffling through the tulgey wood,  And burbled as it came!One, two! One, two! And through and through"
				+ "  The vorpal blade went snicker-snack!He left it dead, and with its head  He went galumphing back."
				+ "\"And, has thou slain the Jabberwock?  Come to my arms, my beamish boy!"
				+ "O frabjous day! Callooh! Callay!'  He chortled in his joy."); 
				
			System.out.println(result);
	}
}
