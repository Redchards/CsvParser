package csv;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		System.out.println("Hello world");

		try {
			CsvParser parser = new CsvParser("C:\\Users\\Loic\\Documents\\tst.csv");
			QuotedCsvLineParser tstPar = new QuotedCsvLineParser(CsvFormatSpecifier.TDF);
			String[] r = "h,,,h,,".split(",");
			System.out.println(tstPar.parseLine("\t\t\t\t").toString());
			for(String str : tstPar.parseLine("\t\t\t\t")) {
				System.out.println("Print : " + str);
			}
			for(ArrayList<String> arr : parser.parse()) {
				System.out.println(arr.toString());
				System.out.println("Printing each value);");
				for(String str : arr) {
					System.out.println(str);
				}
			}
			parser.reset();
			for(ArrayList<String> arr : parser.parse(2)) {
				System.out.println(arr.toString());
			}
			System.out.println("\n");
			
			for(String str : "Dan,34,2548,\"Lovin, it!\"".trim().split("\"")){
				System.out.println(" Hello : " + str);
			}
		}
		catch(FileNotFoundException e) {
			System.out.println("Can't find the desired file !");
			System.out.println("Message : " + e.getMessage());
		}
		catch(IOException e) {
			System.out.println("Error during file reading !");
			System.out.println("Message : " + e.getMessage());
		}
		catch(CsvParserException e) {
			System.out.println("Error during file parsing !");
			System.out.println("Message : " + e.getMessage());
		}
		finally {
		}
	}

}
