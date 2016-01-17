package csv;
import java.util.ArrayList;
import java.util.List;

/**
 * A class used to parse a {@link String} given in a CSV format specified at construction.
 *
 */

//@TODO : Maybe should check the format in the constructor, or create a factory (here, we could technically pass an unquoted format)
public class QuotedCsvLineParser implements ICsvLineParser {
	/**
	 * The CSV format used to parse the lines.
	 * @param format The {@link CsvFormatSpecifier} to be used.
	 */
	public QuotedCsvLineParser(CsvFormatSpecifier format){
		if(format.getQuoteType() == QuoteType.Unquoted) {
			throw new IllegalArgumentException("Can't construct a parser parsing quoted CSV with an unquoted format specification !");
		}
		format_ = format;
	}
	
	@Override
	public List<String> parseLine(String line) throws CsvParserException {
		List<String> parsedInput = new ArrayList<String>();
		int begin = 0;
		int end = 0;
		boolean inhibiting = false;
		boolean ignoreQuote = false;
		
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i < line.length(); ++i) {
			if(line.charAt(i) == format_.getQuoteValue()) {
				int numberOfSpaces = 1;
				if(!ignoreQuote && !inhibiting) {
					builder.append(safeSubString(line, begin + 1, i + 1));
					begin = i + 1;

					inhibiting = true;
				}
				else if(inhibiting){
					if(i == line.length() - 1) {
						inhibiting = false;
					}
					else if(i + 1 < line.length()) {
						while(line.charAt(i + numberOfSpaces) == ' ') {
							++numberOfSpaces;
						}
						if(line.charAt(i + numberOfSpaces) == format_.getDelimiterValue()) {
							inhibiting = false;
						}
						else {
							end = i + 1;
						}
					}
				}
				else {
					end = i + 1;
				}
			}
			else if(((line.charAt(i) == format_.getDelimiterValue()) && !inhibiting)) {
				// Remove code duplication ?
				builder.append(safeSubString(line, begin, end));
				parsedInput.add(builder.toString());
				builder = new StringBuilder();
				
				begin = i + 1;
				int numberOfSpaces = 1;
				while((i + numberOfSpaces < line.length()) && line.charAt(i + numberOfSpaces) == ' ') {
					++numberOfSpaces;
				}
				if(numberOfSpaces > 1) {
					begin += numberOfSpaces - 1;
				}
				
				ignoreQuote = false;
			}
			else if(line.charAt(i) != ' '){
				end = i + 1;
			}			
			if(!ignoreQuote && line.charAt(i) !=  ' ' && line.charAt(i) != format_.getDelimiterValue()) {
				ignoreQuote = true;
			}
		}
		if(!inhibiting && (line.charAt(line.length() - 1) != format_.getDelimiterValue())) {
			builder.append(safeSubString(line, begin, end));
			parsedInput.add(builder.toString());
			builder = new StringBuilder();
		}
		if(inhibiting) {
			throw new CsvParserException(0, line.length() + 1,
		                                 " Unmatched '" + format_.getQuoteValue() + "' at the end of the line !");
		}
		
		return parsedInput;
	}
	
	private String safeSubString(String str, int begin, int end) {
		if(end > begin) {
			return str.substring(begin, end);
		}
		else {
			return "";
		}
	}
	
	private CsvFormatSpecifier format_;
}
