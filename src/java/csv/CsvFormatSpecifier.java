package csv;

/**
 * Class used to define the CSV file format.
 * <p>This class contains 3 basic format description :
 * <ul>
 * <li> {@link #RFC} </li>
 * <li> {@link #TDF} </li>
 * <li> {@link #SQL} </li>
 * </ul>
 * <p><b> WARNING : the </b>{@link QuoteType#Unquoted}<b> has a specific behaviour, and not the same as a plain space character ! </b>
 * 
 * @see QuoteType
 * @see DelimiterType
 */
public class CsvFormatSpecifier {
	/**
	 * @param quoteType The quote type used to quote CSV fields.
	 * @param delim The delimiter type used to separate the CSV fields.
	 */
	public CsvFormatSpecifier(QuoteType quoteType,
			                  DelimiterType delim) {
		this.quote_ = quoteType;
		this.delim_ = delim;
	}
	
	/**
	 * Set the quote type.
	 * @param quoteType The quote type used to quote CSV fields.
	 * @return A reference to the modified {@link CsvFormatSpecifier}
	 */
	public CsvFormatSpecifier set(QuoteType quoteType) {
		quote_ = quoteType;
		return this;
	}
	
	/**
	 * Set the delimiter type.
	 * @param delim The delimiter type used to separate the CSV fields.
	 * @return A reference to the modified {@link CsvFormatSpecifier}
	 */
	public CsvFormatSpecifier set(DelimiterType delim) {
		delim_ = delim;
		return this;
	}
	
	/**
	 * Return the delimiter type.
	 * @return The enum value of the delimiter used in the CSV format.
	 */
	public DelimiterType getDelimiterType() {
		return delim_;
	}
	
	/**
	 * Return the quote type.
	 * @return The enum value of the quote used in the CSV format.
	 */
	public QuoteType getQuoteType() {
		return quote_;
	}
	
	/**
	 * Return the delimiter value
	 * @return The character used as delimiter in the CSV format.
	 */
	public char getDelimiterValue() {
		return delim_.value();
	}
	
	/**
	 * Return the quote value.
	 * @return The character used as quote in the CSV format.
	 */
	public char getQuoteValue() {
		return quote_.value();
	}
	
	private DelimiterType delim_;
	private QuoteType quote_;
	
	/**
	 * A CSV format based on the RFC4180.
	 */
	public static CsvFormatSpecifier RFC = new CsvFormatSpecifier(QuoteType.DoubleQuote,
																  DelimiterType.Comma);
	
	/**
	 * A tabulation delimited CSV format.
	 */
	public static CsvFormatSpecifier TDF = new CsvFormatSpecifier(QuoteType.DoubleQuote,
																  DelimiterType.Tab);
	
	/**
	 * The SQL CSV format used by commands like SELECT INTO FILE.
	 * The format is tabulation delimited, and unquoted.
	 * @see UnquotedCsvLineParser
	 */
	public static CsvFormatSpecifier SQL = new CsvFormatSpecifier(QuoteType.Unquoted,
																  DelimiterType.Tab);
			
}
