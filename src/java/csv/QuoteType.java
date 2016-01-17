package csv;

/**
 * Enumeration used to describe the different quoting method used among CSV formats.
 * <p> This enumeration is used to set the quoting type of a {@link CsvParser} using the {@link CsvFormatSpecifier}.
 *
 */
public enum QuoteType {
	DoubleQuote('"'),
	SingleQuote('\''),
	Unquoted(' ');
	
	QuoteType(char value) {
		value_ = value;
	}
	
	public char value() {
		return value_;
	}
	
	private char value_;
}
