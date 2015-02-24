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
		//test printout
		assertEquals("all content deleted from mytestfile.txt",TextBuddy.executeCommand("clear"));
		//check file size is empty
		assertEquals(0,TextBuddy.getFileSize());
	}

	@Test
	public void testDisplay(){
		//empty file test case
		TextBuddy.executeCommand("clear");
		assertEquals("mytestfile.txt is empty",TextBuddy.executeCommand("display"));
		
		TextBuddy.executeCommand("add fairytale");
		TextBuddy.executeCommand("add peter pan");
		assertEquals("1. fairytale\n2. peter pan",TextBuddy.executeCommand("display"));
	}

	@Test
	public void testAdd(){
		TextBuddy.executeCommand("clear");
		//test printout
		assertEquals("added to mytestfile.txt: \"123\"",TextBuddy.executeCommand("add 123"));
		//check file size and display content to see the match
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
		//test printout and check if deletion is successful after every deletion
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

	@Test
	public void testSort(){
		//empty file sort test
		TextBuddy.executeCommand("clear");
		assertEquals("mytestfile.txt is empty",TextBuddy.executeCommand("sort"));
		assertEquals("mytestfile.txt is empty",TextBuddy.executeCommand("display"));
		assertEquals(0, TextBuddy.getFileSize());
		//insert in ascending order
		TextBuddy.executeCommand("clear");
		TextBuddy.executeCommand("add 1");
		TextBuddy.executeCommand("add 2");
		TextBuddy.executeCommand("add 3");
		assertEquals(3, TextBuddy.getFileSize());
		assertEquals("tasks sorted", TextBuddy.executeCommand("sort"));
		assertEquals("1. 1\n2. 2\n3. 3", TextBuddy.executeCommand("display"));

		//random order insert test
		TextBuddy.executeCommand("clear");
		TextBuddy.executeCommand("add 1");
		TextBuddy.executeCommand("add 3");
		TextBuddy.executeCommand("add 2");
		assertEquals(3, TextBuddy.getFileSize());
		assertEquals("tasks sorted", TextBuddy.executeCommand("sort"));
		assertEquals("1. 1\n2. 2\n3. 3", TextBuddy.executeCommand("display"));
		
		//mixed case insert test
		TextBuddy.executeCommand("clear");
		TextBuddy.executeCommand("add C");
		TextBuddy.executeCommand("add b");
		TextBuddy.executeCommand("add A");
		TextBuddy.executeCommand("sort");
		assertEquals("1. A\n2. b\n3. C", TextBuddy.executeCommand("display"));
		
		//complex insert sort test
		TextBuddy.executeCommand("clear");
		TextBuddy.executeCommand("add Watermelon");
		TextBuddy.executeCommand("add Apple");
		TextBuddy.executeCommand("add Grape");
		TextBuddy.executeCommand("sort");
		assertEquals("1. Apple\n2. Grape\n3. Watermelon", TextBuddy.executeCommand("display"));
	}

	@Test
	public void testSearch(){
		//empty file search test
		TextBuddy.executeCommand("clear");
		assertEquals("mytestfile.txt is empty",TextBuddy.executeCommand("search apple"));
		
		//basic search test
		TextBuddy.executeCommand("clear");
		TextBuddy.executeCommand("add apple");
		TextBuddy.executeCommand("add peach");
		assertEquals("1. apple\n",TextBuddy.executeCommand("search apple"));
		assertEquals("2. peach\n",TextBuddy.executeCommand("search peach"));
		
		//substring search test
		TextBuddy.executeCommand("clear");
		TextBuddy.executeCommand("add thomas");
		TextBuddy.executeCommand("add computer");
		TextBuddy.executeCommand("add NUS");
		TextBuddy.executeCommand("add computer science");
		TextBuddy.executeCommand("add comp123");
		assertEquals("2. computer\n4. computer science\n5. comp123\n", TextBuddy.executeCommand("search comp"));
	}

}
