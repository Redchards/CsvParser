package csv;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
	public UnquotedCsvLineParser(CsvFormatSpecifier format, CsvParsedResult header, boolean throwIfNotExists) {
		if(format.getQuoteType() != QuoteType.Unquoted) {
			throw new IllegalArgumentException("Can't construct a parser parsing unquoted CSV with a quoted format specification !");
		}
		format_ = format;
		throwIfNotExists_ = throwIfNotExists;
		
		List<String> formatHeader = format.getHeader();
		
		if(format_.isWithHeader()) {
			if((formatHeader == null) && (header != null)) {
				mapping_ = HeaderMappingBuilder.build(header);
			}
			else if(formatHeader != null) {
				mapping_ = HeaderMappingBuilder.build(new CsvParsedResult(formatHeader));
			}
			else {
				throw new IllegalArgumentException("Can't build a column mapped line parser without specifying header");
			}
		}
		else {
			mapping_ = null;
		}
	}
	
	public UnquotedCsvLineParser(CsvFormatSpecifier format, boolean throwIfNotExists) {
		this(format, null, throwIfNotExists);
	}
	
	public UnquotedCsvLineParser(CsvFormatSpecifier format, CsvParsedResult header) {
		this(format, header, false);
	}
	
	public UnquotedCsvLineParser(CsvFormatSpecifier format) {
		this(format, null);
	}
	
	public UnquotedCsvLineParser(CsvParsedResult header) {
		this(defaultFormat_, header);
	}
	
	public UnquotedCsvLineParser(boolean throwIfNotExists) {
		this(defaultFormat_, null, throwIfNotExists);
	}
	
	public UnquotedCsvLineParser() {
		this(defaultFormat_, null);
	}
	
	@Override
	public CsvParsedResult parseLine(String line) {
		ArrayList<String> result = new ArrayList<String>(Arrays.asList(line.split(Character.toString(format_.getDelimiterValue()))));
		int numberOfTrailingDelimiters = 0;
		
		while((numberOfTrailingDelimiters < line.length()) && (line.charAt(line.length() - numberOfTrailingDelimiters - 1) == format_.getDelimiterValue())) {
			++numberOfTrailingDelimiters;
			result.add("");
		}
		
		return new CsvParsedResult(result, mapping_, throwIfNotExists_);
	}
	
	private CsvFormatSpecifier format_;
	private final HashMap<String, Integer> mapping_;
	private final boolean throwIfNotExists_;
	private static CsvFormatSpecifier defaultFormat_ = CsvFormatSpecifier.RFC;
}
