package csv;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/** CSV file format parser class. 
 *  
 *  <p>This class is able to parse CSV files, following the format definition you give at construction.
 *  <p>You'll need to close the internal file reader, using the {@link #close() close} method after use.
 *  <p>The default CSV file format is {@link CsvFormatSpecifier#RFC RFC}
 *  @see CsvFormatSpecifier
 */
public class CsvParser{
	/**
	 * Create a parser using the specified file path and CSV format.
	 * Opens a BufferedReader from to the file internally.
	 * 
	 * @param  filepath The path to the file to parse (relative or absolute)
	 * @param  format   The CSV format we want to parse.
	 * @throws IOException 
	 * @throws CsvParserException 
	 */
	public CsvParser(String filepath, CsvFormatSpecifier format, boolean throwIfNotExists) throws CsvParserException, IOException {
		filepath_ = filepath;
		reader_ = new BufferedReader(new FileReader(filepath));
		format_ = format;
		
		if(format.isWithHeader() && (format.getHeader() == null)) {
			ICsvLineParser tmpParser = CsvLineParserFactory.create(new CsvFormatSpecifier(format).withoutHeader());
			lineParser_ = CsvLineParserFactory.create(format_, tmpParser.parseLine(reader_.readLine()), throwIfNotExists);
		}
		else {
			lineParser_ = CsvLineParserFactory.create(format_);
		}
	}
	
	public CsvParser(String filepath, CsvFormatSpecifier format) throws CsvParserException, IOException {
		this(filepath, format, false);
	}
	
	public CsvParser(String filepath) throws CsvParserException, IOException {
		this(filepath, defaultFormat_);
	}
	
	/** 
	 * Parse n lines from the file. 
	 * The file pointer will advance by the number n of lines passed as parameter each time this method is called.
	 * If you need to read the file from the beginning or from a specific line, see the {@link #reset() reset} method,
	 * or the {@link #advance(int n) advance} method.
	 * <p> Also, if the argument numberOfLines is greater than the remaining number of lines in the file, no exception
	 * will be thrown, and only the remaining lines will be read.
	 * 
	 * @param numberOfLines Number of line to parse, and advance the file pointer by the number of line to read.
	 * @return ArrayList of parsed line, represented as ArrayLists of string also.
	 * Each ArrayLists can be thought as a row in a spreadsheet, and each element as a cell.
	 * @see #parse()
	 * @throws IOException
	 * @throws CsvParserException
	 */
	public ArrayList<CsvParsedResult> parse(int numberOfLines) throws IOException, CsvParserException {
		ArrayList<CsvParsedResult> parsedInput = new ArrayList<CsvParsedResult>();
		
		// Check if reader is ready ?
		String tmp;
		for(int i = 0; i < numberOfLines && ((tmp = reader_.readLine()) != null); ++i) {
			try {
				parsedInput.add(lineParser_.parseLine(tmp));
			}
			catch(CsvParserException e) {
				e.setLine(i + 1);
				throw e;
			}
		}
		
		return parsedInput;
	}
	
	/**
	 * Parse all lines from the file.
	 * After this call, the file pointer will be set to the end of the file.
	 * @return ArrayList of parsed line, represented as ArrayLists of string also.
	 * Each ArrayLists can be thought as a row in a spreadsheet, and each element as a cell.
	 * @see #parse(int numberOfLines)
	 * @throws IOException
	 * @throws CsvParserException
	 */
	public ArrayList<CsvParsedResult> parse() throws IOException, CsvParserException {
		ArrayList<CsvParsedResult> parsedInput = new ArrayList<CsvParsedResult>();
		
		// Here check also if the reader is ready ?
		String tmp;
		int currentLine = 0;
		
		while((tmp = reader_.readLine()) != null) {
			try {
				parsedInput.add(lineParser_.parseLine(tmp));
			}
			catch(CsvParserException e) {
				e.setLine(currentLine + 1);
				throw e;
			}
			++currentLine;
		}
		
		return parsedInput;
	}
	
	/**
	 * Reset the file stream pointer to the beginning of the file.
	 * <p> The internal buffered will however be thrown away.
	 * @throws IOException
	 */
	public void reset() throws IOException {
		reader_ = new BufferedReader(new FileReader(filepath_));
		if(format_.isWithHeader()) {
			advance(1);
		}
	}
	
	/**
	 * Advance the file stream pointer by n lines in the file.
	 * @param n Number of lines to advance.
	 * @throws IOException
	 */
	public void advance(int n) throws IOException {
		for(int i = 0; i < n; ++i) {
			reader_.readLine();
		}
	}
	
	/** 
	 * Close the internal file stream.
	 * <p> You need the call this method after use.
	 * @throws IOException  
	 */
	public void close() throws IOException{
		reader_.close();
	}
	
	public CsvFormatSpecifier getFormat() {
		return new CsvFormatSpecifier(format_);
	}
	
	private String filepath_;
	private BufferedReader reader_;
	private ICsvLineParser lineParser_;
	private CsvFormatSpecifier format_;
	private static CsvFormatSpecifier defaultFormat_ = CsvFormatSpecifier.RFC;
}
