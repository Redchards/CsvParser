package csv;

import java.util.ArrayList;
import java.util.List;

public class CsvParsedResult implements ICsvParsedResult {

	public CsvParsedResult(ArrayList<ArrayList<String>> data) {
		formatedData_ = data;
		
		//for(String d : formatedData_)
	}
	
	@Override
	public String col(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String row(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	private ArrayList<ArrayList<String>> formatedData_;
	private ArrayList<List<String>> rows_;
}
