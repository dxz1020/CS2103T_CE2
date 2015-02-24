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
	public void testDisplay(){
		//null test case
		TextBuddy.executeCommand("clear");
		assertEquals("mytestfile.txt is empty",TextBuddy.executeCommand("display"));
		
		TextBuddy.executeCommand("add fairytale");
		TextBuddy.executeCommand("add peter pan");
		assertEquals("1. fairytale\n2. peter pan",TextBuddy.executeCommand("display"));
	}
	
	@Test
	public void testAdd(){
		TextBuddy.executeCommand("clear");
		assertEquals("added to mytestfile.txt: \"123\"",TextBuddy.executeCommand("add 123"));
		assertEquals("1. 123",TextBuddy.executeCommand("display"));
		assertEquals(1,TextBuddy.getFileSize());
	}
	
	@Test
	public void testDelete(){
		TextBuddy.executeCommand("clear");
		TextBuddy.executeCommand("add 123");
		TextBuddy.executeCommand("add 234");
		TextBuddy.executeCommand("add 456");
		TextBuddy.executeCommand("add 567");
		assertEquals("deleted from mytestfile.txt: \"123\"",TextBuddy.executeCommand("delete 1"));
		assertEquals(3,TextBuddy.getFileSize());
		assertEquals("deleted from mytestfile.txt: \"234\"",TextBuddy.executeCommand("delete 1"));
		assertEquals(2,TextBuddy.getFileSize());
		assertEquals("deleted from mytestfile.txt: \"567\"",TextBuddy.executeCommand("delete 2"));
		assertEquals(1,TextBuddy.getFileSize());
		assertEquals("deleted from mytestfile.txt: \"456\"",TextBuddy.executeCommand("delete 1"));
		assertEquals(0,TextBuddy.getFileSize());
		assertEquals("mytestfile.txt is empty",TextBuddy.executeCommand("delete 1"));
	}

}
