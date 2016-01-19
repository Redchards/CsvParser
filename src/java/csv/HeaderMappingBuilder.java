package csv;

import java.util.HashMap;

class HeaderMappingBuilder {
	public static HashMap<String, Integer> build(CsvParsedResult parsedHeader) {
		HashMap<String, Integer> result = new HashMap<String, Integer>();
		
		for(int i = 0; i < parsedHeader.size(); ++i) {
			result.put(parsedHeader.get(i), i);
		}
		
		return result;
	}
}
