package csv;

import java.util.HashMap;
import java.util.List;

public class CsvParsedResult {
	
	public CsvParsedResult(List<String> data, HashMap<String, Integer> headerMapping, boolean throwIfNotExists) {
		data_ = data;
		headerMapping_ = headerMapping;
		throwIfNotExists_ = throwIfNotExists;
	}
	
	public CsvParsedResult(List<String> data, HashMap<String, Integer> headerMapping) {
		this(data, headerMapping, true);
	}
	
	public CsvParsedResult(List<String> data) {
		this(data, null, true);
	}
	
	public CsvParsedResult(List<String> data, boolean throwIfNotExists) {
		this(data, null, throwIfNotExists);
	}
	
	public String get(int index) {
		String result = data_.get(index);
		if(throwIfNotExists_ && (result == null)) {
			throw new IllegalStateException("The record number " + Integer.toString(index + 1) + " do not exist in the CSV row !");
		}
		return result;
	}
	
	public String get(String key) {
		if(headerMapping_ == null) {
			throw new IllegalStateException("Can't access mapped data, as no mapping is provided for the CSV row !");
		}
		
		Integer mappedIndex = headerMapping_.get(key);
		if((mappedIndex == null)) {
			if(throwIfNotExists_) {
				throw new IllegalStateException("The record at column '" + key + "' do not exist in the CSV row !");
			}
			else {
				return null;
			}
		}
		return data_.get(mappedIndex);
	}
	
	public int size() {
		return data_.size();
	}
	
	public void throwIfNotExists(boolean b) {
		throwIfNotExists_ = b;
	}
	
	public boolean isValid() {
		if(headerMapping_ == null) {
			return true;
		}
		return data_.size() == headerMapping_.size();
	}
	
	public HashMap<String, Integer> getMapping() {
		return headerMapping_;
	}
	
	/*public void setMapping(HashMap<String, Integer> newMapping) {
		headerMapping_ = new HashMap<String, Integer>(newMapping);
	}*/
	
	private List<String> data_;
	private final HashMap<String, Integer> headerMapping_;
	private boolean throwIfNotExists_;
}
