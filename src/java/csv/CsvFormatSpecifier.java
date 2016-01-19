package csv;

import java.util.ArrayList;
import java.util.List;

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
			                  DelimiterType delim,
			                  boolean withHeader) {
		quote_ = quoteType;
		delim_ = delim;
		withHeader_ = withHeader;
		header_ = null;
	}
	
	public CsvFormatSpecifier(QuoteType quoteType,
							  DelimiterType delim) {
		this(quoteType, delim, false);
	}
	
	public CsvFormatSpecifier(CsvFormatSpecifier other) {
		quote_ = other.quote_;
		delim_ = other.delim_;
		withHeader_ = other.withHeader_;
		if(other.header_ != null) {
			header_ = new ArrayList<String>(other.header_);
		}
		else {
			header_ = null;
		}
	}
	
	/**
	 * Set the quote type.
	 * @param quoteType The quote type used to quote CSV fields.
	 * @return A reference to the modified {@link CsvFormatSpecifier}
	 */
	public CsvFormatSpecifier set(QuoteType quoteType) {
		quote_ = quoteType;
		return new CsvFormatSpecifier(this);
	}
	
	/**
	 * Set the delimiter type.
	 * @param delim The delimiter type used to separate the CSV fields.
	 * @return A reference to the modified {@link CsvFormatSpecifier}
	 */
	public CsvFormatSpecifier set(DelimiterType delim) {
		delim_ = delim;
		return new CsvFormatSpecifier(this);
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
	
	public CsvFormatSpecifier withHeader() {
		withHeader_ = true;
		return new CsvFormatSpecifier(this);
	}
	
	public CsvFormatSpecifier withoutHeader() {
		withHeader_ = false;
		return new CsvFormatSpecifier(this);
	}
	
	public boolean isWithHeader() {
		return withHeader_;
	}
	
	public List<String> getHeader() {
		if(header_ == null) {
			return null;
		}
		else {
			return new ArrayList<String>(header_);
		}
	}
	
	private DelimiterType delim_;
	private QuoteType quote_;
	private boolean withHeader_;
	private List<String> header_;
	
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
