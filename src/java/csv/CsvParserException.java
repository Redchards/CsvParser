package csv;

/**
 * Exception possibility thrown from {@link ICsvLineParser} implementations.
 *
 */
public class CsvParserException extends Exception {

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = -5099029027672004052L;
	
	public CsvParserException(int line, int position) {
		super();
		line_ = line;
		position_ = position;
	}
	
	public CsvParserException(int line, int position, String msg) {
		super(msg);
		line_ = line;
		position_ = position;
	}
	
	public CsvParserException(int line, int position, Throwable cause) {
		super(cause);
		line_ = line;
		position_ = position;
	}
	
	public CsvParserException(int line, int position, String msg, Throwable cause) {
		super(msg, cause);
		line_ = line;
		position_ = position;
	}
	
	public CsvParserException() {
		this(0, 0);
	}
	
	public CsvParserException(String msg) {
		this(0, 0, msg);
	}
	
	public CsvParserException(Throwable cause) {
		this(0, 0, cause);
	}
	
	public CsvParserException(String msg, Throwable cause) {
		this(0, 0, msg, cause);
	}
	
	@Override
	public String getMessage() {
		return "(line : " + line_ + ", position : " + position_ + ") " + super.getMessage();
	}
	
	public void setLine(int line) {
		line_ = line;
	}
	
	public void setPosition(int position) {
		position_ = position;
	}
	
	private int line_;
	private int position_;
}
