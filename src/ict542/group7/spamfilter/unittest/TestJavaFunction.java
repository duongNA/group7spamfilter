package ict542.group7.spamfilter.unittest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import org.junit.Test;

public class TestJavaFunction {
	
	@Test
	public void testStringTokenizer() {
		String str = "This is string duong1, duong2, duong3";
		StringTokenizer tokenizer = new StringTokenizer(str, ",");
		
		while(tokenizer.hasMoreTokens()) {
			System.out.println(tokenizer.nextToken());
		}
	}
	
	@Test
	public void testReadFile() {
		String filePath = "test/data/email-test";
		BufferedReader in = null;
		
		try {
			in = new BufferedReader(new FileReader(filePath));
			String line = null;
			System.out.println("-----File content------");
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {}
			}
		}
	}
}
