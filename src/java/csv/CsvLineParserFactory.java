/**
 * 
 */
package csv;

/**
 *
 */
public class CsvLineParserFactory {
	public static ICsvLineParser create(CsvFormatSpecifier format) {
		if(format.getQuoteType() != QuoteType.Unquoted) {
			return new QuotedCsvLineParser(format);
		}
		else {
			return new UnquotedCsvLineParser(format);
		}	
	}
}
