import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;


public class TextBuddyTest {
	
	@BeforeClass
	public static void setUp() {
		String[] arg=new String[1];
		arg[0]="mytestfile.txt";
		TextBuddy.main(arg);
	}
	
	@Test
	public void testAdd(){


	}

}
