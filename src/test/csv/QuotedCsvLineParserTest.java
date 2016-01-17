/**
 * 
 */
package csv;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class QuotedCsvLineParserTest {
	
	@Test(expected = CsvParserException.class)
	public void RFCFormatParsingShouldFail() throws CsvParserException {
		parser = new QuotedCsvLineParser(CsvFormatSpecifier.RFC);
		String tstString = "\"Hello\", \"World";
		
		parser.parseLine(tstString);
	}
	
	@Test
	public void RFCFormatWithInternalQuote() {
		parser = new QuotedCsvLineParser(CsvFormatSpecifier.RFC);
		String tstString = "\"First\", Second, T\"hird, \"Last\"";
		List<String> result = returnOrFailOnException(tstString);
		
		ensureSize(result, 4);
		assertEquals("First element must be the string 'First'", "First", result.get(0));
		assertEquals("Second element must be the string 'Second'", "Second", result.get(1));
		assertEquals("Third element must be the string 'T\"hird'",  "T\"hird", result.get(2));
		assertEquals("Fourth element must be the string 'Last'", "Last", result.get(3));
	}
	
	@Test
	public void RFCFormatWithTrailingDelimiter() {
		parser = new QuotedCsvLineParser(CsvFormatSpecifier.RFC);
		String tstString = "First, Second,";
		List<String> result = returnOrFailOnException(tstString);
		
		ensureSize(result, 2);
		assertEquals("First element must be the string 'First'", "First", result.get(0));
		assertEquals("Second element must be the string 'Second'", "Second", result.get(1));
	}
	
	@Test
	public void RFCFormatWithJumpline() {
		parser = new QuotedCsvLineParser(CsvFormatSpecifier.RFC);
		String tstString = "\"First\",Second,Hello\nWorld !";
		List<String> result = returnOrFailOnException(tstString);
		
		ensureSize(result, 3);
		assertEquals("First element must be the string 'First", "First", result.get(0));
		assertEquals("Second element must be the string 'Second'", "Second", result.get(1));
		assertEquals("Third element must be the string 'Hello World !' with a line jumping between 'Hello' and 'World !'", "Hello\nWorld !", result.get(2));
	}
	
	
	@Test
	public void RFCFormatWithTwoTrailingDelimiters() {
		parser = new QuotedCsvLineParser(CsvFormatSpecifier.RFC);
		String tstString = "First, Second,,";
		List<String> result = returnOrFailOnException(tstString);
		
		ensureSize(result, 3);
		assertEquals("First element must be the string 'First'", "First", result.get(0));
		assertEquals("Second element must be the string 'Second'", "Second", result.get(1));
		assertEquals("Third element must be an empty string", "", result.get(2));
	}
	
	@Test
	public void RFCFormatWithOnlyDelimiters() {
		parser = new QuotedCsvLineParser(CsvFormatSpecifier.RFC);
		String tstString = ",,,,";
		List<String> result = returnOrFailOnException(tstString);
		
		ensureSize(result, 4);
		for(int i = 0; i < 4; ++i) {
			assertEquals("Element must be an empty string", "", result.get(i));
		}
	}
	
	@Test
	public void TDFFormatWithInternalQuote() {
		parser = new QuotedCsvLineParser(CsvFormatSpecifier.TDF);
		String tstString = "\"First\"\tSecond\tT\"hird\t\"Last\"";
		List<String> result = returnOrFailOnException(tstString);
		
		ensureSize(result, 4);
		assertEquals("First element must be the string 'First'", "First", result.get(0));
		assertEquals("Second element must be the string 'Second'", "Second", result.get(1));
		assertEquals("Third element must be the string 'T\"hird'",  "T\"hird", result.get(2));
		assertEquals("Fourth element must be the string 'Last'", "Last", result.get(3));
	}
	
	@Test
	public void TDFFormatWithTrailingDelimiter() {
		parser = new QuotedCsvLineParser(CsvFormatSpecifier.TDF);
		String tstString = "First\tSecond\t";
		List<String> result = returnOrFailOnException(tstString);
		
		ensureSize(result, 2);
		assertEquals("First element must be the string 'First'", "First", result.get(0));
		assertEquals("Second element must be the string 'Second'", "Second", result.get(1));
	}
	
	@Test
	public void TDFFormatWithJumpline() {
		parser = new QuotedCsvLineParser(CsvFormatSpecifier.TDF);
		String tstString = "\"First\"\tSecond\tHello\nWorld !";
		List<String> result = returnOrFailOnException(tstString);
		
		ensureSize(result, 3);
		assertEquals("First element must be the string 'First", "First", result.get(0));
		assertEquals("Second element must be the string 'Second'", "Second", result.get(1));
		assertEquals("Third element must be the string 'Hello World !' with a line jumping between 'Hello' and 'World !'", "Hello\nWorld !", result.get(2));
	}
	
	@Test
	public void TDFFormatWithTwoTrailingDelimiters() {
		parser = new QuotedCsvLineParser(CsvFormatSpecifier.TDF);
		String tstString = "First\t Second\t\t";
		List<String> result = returnOrFailOnException(tstString);
		
		ensureSize(result, 3);
		assertEquals("First element must be the string 'First'", "First", result.get(0));
		assertEquals("Second element must be the string 'Second'", "Second", result.get(1));
		assertEquals("Third element must be an empty string", "", result.get(2));
	}
	
	@Test
	public void TDFFormatWithOnlyDelimiters() {
		parser = new QuotedCsvLineParser(CsvFormatSpecifier.TDF);
		String tstString = "\t\t\t\t";
		List<String> result = returnOrFailOnException(tstString);
		
		ensureSize(result, 4);
		for(int i = 0; i < 4; ++i) {
			assertEquals("Element must be an empty string", "", result.get(i));
		}
	}
	
	private List<String> returnOrFailOnException(String line) {
		try {
			return parser.parseLine(line);
		}
		catch(CsvParserException e) {
			fail("Parser thrown an exception :" + e.getMessage());
		}
		return null;
	}
	
	private void ensureSize(List<String> list, int size) {
		assertEquals("There must be " + size + " elements in the returned list", size, list.size());
	}

	QuotedCsvLineParser parser;
}
