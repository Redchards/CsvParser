/**
 * 
 */
package csv;

/**
 *
 */
public class CsvLineParserFactory {
	public static ICsvLineParser create(CsvFormatSpecifier format, CsvParsedResult mapping, boolean throwIfNotExists) {
		if(format.getQuoteType() != QuoteType.Unquoted) {
			return new QuotedCsvLineParser(format, mapping, throwIfNotExists);
		}
		else {
			return new UnquotedCsvLineParser(format, mapping, throwIfNotExists);
		}	
	}
	
	public static ICsvLineParser create(CsvFormatSpecifier format, CsvParsedResult mapping) {
		return create(format, mapping, false);
	}
	
	public static ICsvLineParser create(CsvFormatSpecifier format, boolean throwIfNotExists) {
		return create(format, null, throwIfNotExists);
	}
	
	public static ICsvLineParser create(CsvFormatSpecifier format) {
		return create(format, null);
	}
}
