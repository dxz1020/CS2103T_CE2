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
		String output=null;
		output=TextBuddy.executeCommand("clear");
		assertEquals("all content deleted from mytestfile.txt",output);
	}
	@Test
	public void testAdd(){
		String output=null;
		
		TextBuddy.executeCommand("clear");
		TextBuddy.executeCommand("add 123");
		output=TextBuddy.executeCommand("display");
		assertEquals("1. 123",output);
	}

}
