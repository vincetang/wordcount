package trgr.rd.wordcount;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileMetaDataTest {

	private  FileMetadata fmd;
	
	@Before
	public void setUp() throws Exception {
		String filename = "testfile.txt";
		int fileNumber = 1;
		fmd = new FileMetadata(filename, fileNumber);
		fmd.addWord("test");
		fmd.addWord("a");
		fmd.addWord("b");
		fmd.addWord("c");
		fmd.addWord("a");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testUniqueWords() {
		assertEquals(fmd.countUniqueWords(), 3);
	}

}
