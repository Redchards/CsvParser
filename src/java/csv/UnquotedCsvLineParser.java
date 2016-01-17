package csv;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class used to parse a {@link String} given in a CSV format <b>without quotes</b>, specified at construction.
 * Parsing without quotes can be faster, but you can't escape the delimiters then.
 *
 */
public class UnquotedCsvLineParser implements ICsvLineParser {
	/**
	 * The CSV format used to parse the lines.
	 * @param format The {@link CsvFormatSpecifier} to be used.
	 */
	public UnquotedCsvLineParser(CsvFormatSpecifier format) {
		if(format.getQuoteType() != QuoteType.Unquoted) {
			throw new IllegalArgumentException("Can't construct a parser parsing unquoted CSV with a quoted format specification !");
		}
		format_ = format;
	}
	
	@Override
	public List<String> parseLine(String line) {
		ArrayList<String> result = new ArrayList<String>(Arrays.asList(line.split(Character.toString(format_.getDelimiterValue()))));
		int numberOfTrailingDelimiters = 0;
		
		while((numberOfTrailingDelimiters < line.length()) && (line.charAt(line.length() - numberOfTrailingDelimiters - 1) == format_.getDelimiterValue())) {
			++numberOfTrailingDelimiters;
			result.add("");
		}
		
		return result;
	}
	
	private CsvFormatSpecifier format_;
}
