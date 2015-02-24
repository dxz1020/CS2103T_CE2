import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;


public class TextBuddyTest {
	
	@BeforeClass
	public static void setUp() {
		String filename="mytestfile.txt";
		TextBuddy.createNewFile(filename);
	}
	
	@Test
	public void testClear(){
		assertEquals("all content deleted from mytestfile.txt",TextBuddy.executeCommand("clear"));
		assertEquals(0,TextBuddy.getFileSize());
	}
	@Test
	public void testAdd(){
		TextBuddy.executeCommand("clear");
		TextBuddy.executeCommand("add 123");
		assertEquals("1. 123",TextBuddy.executeCommand("display"));
		assertEquals(1,TextBuddy.getFileSize());
	}

}
