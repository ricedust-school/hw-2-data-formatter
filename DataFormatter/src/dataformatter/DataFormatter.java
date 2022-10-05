package dataformatter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import com.opencsv.CSVWriter;

public class DataFormatter {
	private String filename;
	private String format;
	
	// records filename and format from user
	private void getArgs() {
		Scanner in = new Scanner(System.in);
		
		System.out.println("Key:\n-c for CSV\n-j for JSON\n-x for XML\n");
		System.out.print("Enter a filename followed by a conversion format in the form 'data.txt -c':");
		
		String input = in.nextLine();
		this.format = input.substring(input.length() - 2);
		this.filename = input.substring(0, input.length() - 3);
		
		in.close();
	}
	
	public void convert() {
		getArgs();
		
		switch (this.format) {
			case "-c": {
				writeToCSV();
				break;
			}
			case "-j": {
				writeToJSON();
				break;
			}
			case "-x": {
				writetoXML();
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected format: " + this.format);
		}
		
		System.out.println("File successfully created in current directory");
	}
	
	private void writeToCSV() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.filename));
			CSVWriter writer = new CSVWriter(new BufferedWriter(new FileWriter("CSV_data.csv")));
			
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				writer.writeNext(line.split("\t"));
			}
			
			reader.close();
			writer.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeToJSON() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.filename));
			BufferedWriter  writer = new BufferedWriter(new FileWriter("JSON_data.json"));
			JSONArray js = new JSONArray();
			
			String[] categories = reader.readLine().split("\t");
			String line;
			
			while ((line = reader.readLine()) != null) {
				int categoryIndex = 0;
				JSONObject entry = new JSONObject();
				
				for (String value : line.split("\t")) {
					entry.put(categories[categoryIndex], value);
					categoryIndex++;
				}
				
				js.put(entry);
			}
			writer.write(js.toString());
			reader.close();
			writer.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writetoXML() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.filename));
			BufferedWriter  writer = new BufferedWriter(new FileWriter("XML_data.xml"));
			JSONArray js = new JSONArray();
			
			String[] categories = reader.readLine().split("\t");
			String line;
			
			while ((line = reader.readLine()) != null) {
				int categoryIndex = 0;
				JSONObject entry = new JSONObject();
				
				for (String value : line.split("\t")) {
					entry.put(categories[categoryIndex], value);
					categoryIndex++;
				}
				
				js.put(entry);
			}
			
			writer.write(XML.toString(js, "Player"));
			reader.close();
			writer.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	@Override
	public String toString() {
		return this.filename + " " + this.format;
	}
	
	public static void main(String[] args) {
		DataFormatter df = new DataFormatter();
		df.convert();
	}
}
