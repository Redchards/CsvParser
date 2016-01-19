package csv;

/**
 * Interface for CSV line parser, used by the {@link CsvParser}.
 *
 * @see QuotedCsvLineParser
 * @see UnquotedCsvLineParser
 */
public interface ICsvLineParser {
	/**
	 * Parse a line, following the format passed at construction
	 * @param line The line to parse
	 * @return The parsed line. 
	 * <p>The elements of the returned list are the CSV fields.
	 * @throws CsvParserException
	 */
	public CsvParsedResult parseLine(String line) throws CsvParserException;
}
