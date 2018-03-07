package com.pg.springb.front.util;

public class FileWriterFactory {

	public static FileWriter getFileWriter(String extension){
		
		if ( ".xlsx".equalsIgnoreCase(extension) )
		      return new XLSXFileWriter();
		
		else if ( ".csv".equalsIgnoreCase(extension) || ".psv".equalsIgnoreCase(extension))
			return new CSVFileWriter();
		
		else return new CSVFileWriter();
	}
}
