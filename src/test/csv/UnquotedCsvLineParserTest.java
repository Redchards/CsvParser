package csv;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class UnquotedCsvLineParserTest {

	@Test
	public void SQLFormatWithQuotes() {
		parser = new UnquotedCsvLineParser(CsvFormatSpecifier.SQL);
		String tstStr = "'First'\t\"Second\"\tThird";
		List<String> result = parser.parseLine(tstStr);
		
		ensureSize(result, 3);
		assertEquals("First element should be the string ''First''", "'First'", result.get(0));
		assertEquals("Second element should be the string '\"Second\"'", "\"Second\"", result.get(1));
		assertEquals("Third element should be the string 'Third'", "Third", result.get(2));
	}
	
	@Test
	public void SQLFormatWithTrailingDelimiter() {
		parser = new UnquotedCsvLineParser(CsvFormatSpecifier.SQL);
		String tstStr = "First\tSecond\tThird\t";
		List<String> result = parser.parseLine(tstStr);
		
		ensureSize(result, 4);
		assertEquals("First element should be the string 'First'", "First", result.get(0));
		assertEquals("Second element should be the string 'Second'", "Second", result.get(1));
		assertEquals("Third element should be the string 'Third'", "Third", result.get(2));
		assertEquals("Fourth element should be an empty string", "", result.get(3));
	}
	
	@Test
	public void SQLFormatWithTwoConsecutiveDelimiters() {
		parser = new UnquotedCsvLineParser(CsvFormatSpecifier.SQL);
		String tstStr = "First\t\tSecond";
		List<String> result = parser.parseLine(tstStr);
		
		ensureSize(result, 3);
		assertEquals("First element should be the string 'First'", "First", result.get(0));
		assertEquals("Second element should be an empty string", "", result.get(1));
		assertEquals("Third element should be the string 'Second'", "Second", result.get(2));
	}
	
	@Test
	public void SQLFormatWithTwoTrailingDelimiters() {
		parser = new UnquotedCsvLineParser(CsvFormatSpecifier.SQL);
		String tstStr = "First\tSecond\t\t";
		List<String> result = parser.parseLine(tstStr);
		
		ensureSize(result, 4);
		assertEquals("First element should be the string 'First'", "First", result.get(0));
		assertEquals("Second element should be the string 'Second'", "Second", result.get(1));
		assertEquals("Third element should be an empty string", "", result.get(2));
		assertEquals("Fourth element should be an empty string", "", result.get(3));
	}
	
	@Test
	public void SQLFormatWithOnlyDelimiters() {
		parser = new UnquotedCsvLineParser(CsvFormatSpecifier.SQL);
		String tstStr = "\t\t\t\t";
		List<String> result = parser.parseLine(tstStr);
		
		ensureSize(result, 4);
		for(int i = 0; i < 4; ++i) {
			assertEquals("All elements should be an empty string", "", result.get(i));
		}
	}
	
	private void ensureSize(List<String> list, int size) {
		assertEquals("There must be " + size + " elements in the returned list", size, list.size());
	}

	private UnquotedCsvLineParser parser;
}
