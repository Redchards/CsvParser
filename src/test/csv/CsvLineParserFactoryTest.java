/**
 * 
 */
package csv;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 *
 */
public class CsvLineParserFactoryTest {

	@Test
	public void shouldBeUnquotedParser() {
		ICsvLineParser parser = CsvLineParserFactory.create(CsvFormatSpecifier.SQL);
		assertEquals("The type of the constructed object should be UnquotedCsvLineParser", UnquotedCsvLineParser.class, parser.getClass());
	}
	
	@Test
	public void shouldBeQuotedParser() {
		ICsvLineParser parser = CsvLineParserFactory.create(CsvFormatSpecifier.RFC);
		assertEquals("The type of the constructed object should be QuotedCsvLineParser", QuotedCsvLineParser.class, parser.getClass());
	}

}
