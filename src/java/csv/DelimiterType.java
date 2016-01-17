package csv;

/**
 * Enumeration used to describe the CSV delimiters.
 *
 */
public enum DelimiterType {
	Semicolon(';'),
	Comma(','),
	Tab('\t');
	
	DelimiterType(char value) {
		value_ = value;
	}
	
	public char value() {
		return value_;
	}
	
	private char value_;
}
