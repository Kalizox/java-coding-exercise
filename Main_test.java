package cgm;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

class Main_test {

	public void testwriteFile() throws IOException {
		File test_file = new File("./Test_file.txt");

		String expected = "test writing";
		
		try {
			Main.writeFile(test_file, expected.split(" "));
		} catch(IOException e) {
			e.printStackTrace();
		}
		String getStr = testReadFile();
		
		assertEquals(expected, getStr);
	}
	
	@Test
	public void testReadFileAt() throws IOException {
		Path path = Paths.get("./Test_file.txt");
		String[] str = {"test writing test writing"};
		String expected = String.join(" ", str);
		expected = expected.concat(" ");
		File test_file = new File("./Test_file.txt");
		
		try {
			Main.writeFile(test_file, str);
			Main.writeFile(test_file, str);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = Files.newBufferedReader(path);
		String result = reader.readLine();
		
		result = result.concat(reader.readLine());
		
		assertEquals(expected, result);
	}
	
	@Test
	public String testReadFile() throws IOException {
		Path path = Paths.get("./Test_file.txt");
		String expected = "test writing";
		
		BufferedReader reader = Files.newBufferedReader(path);
		
		String result = reader.readLine();
		assertEquals(expected, result);
		
		return result;
	}
	
	@Test 
	public void testCheckQuestion() {
		String test_1 = "test string", test_2 = "test string ?", test_3 = "test string";
		String test_4 = "test string ? answer_1", test_5 = "test string ? answer_1 answer_2";

		int pos = test_1.indexOf('?');
		assertEquals(-1, Main.checkQuestion(test_1, pos));
		
		pos = test_2.indexOf('?');
		assertEquals(-1, Main.checkQuestion(test_2, pos));
		
		for(int i = 0; i < 260; i++) {
			test_3 = test_3.concat("a");
		}
		test_3 = test_3.concat(" ? answer_1");
		
		pos = test_3.indexOf('?');
		assertEquals(-1, Main.checkQuestion(test_3, pos));
		
		pos = test_4.indexOf('?');
		assertEquals(0, Main.checkQuestion(test_4, pos));
		
		pos = test_5.indexOf('?');
		assertEquals(0, Main.checkQuestion(test_5, pos));
	}
	
	@Test
	public void testCheckAnswer() {
		String test_1 = "\"test answer_1\"", test_2 = "\"test answer_1\"", test_3 = "answer_1 answer_2";

		for(int i = 0; i < 260 ; i++) {
			test_1 = test_1.concat("a");
		}
		
		assertEquals(-1, Main.checkAnswer(test_1));
		assertEquals(0, Main.checkAnswer(test_2));
		assertEquals(-1, Main.checkAnswer(test_3));
	}
}
