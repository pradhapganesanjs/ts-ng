package com.pg.springb.front.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pg.springb.front.documents.ClientConfigDocument;

public class ExportFileWriter{
	
	private static final Logger log = LoggerFactory.getLogger(ExportFileWriter.class);
	
	public boolean writeFile(String fileName, String outboundPath, List<ClientConfigDocument> clientConfigList) {
		
		FileOutputStream fileOut = null;
		String sheetName = "Sheet1";
		SXSSFWorkbook workBook = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
		SXSSFSheet sheet = workBook.createSheet(sheetName);
		try{
			int rowNum=0, columnNum=0;
			Row excelRow = sheet.createRow(rowNum);
			
			for(String header:FrontierConstants.CLIENT_CONFIG_XL_HEADER){			
				Cell cell = excelRow.createCell(columnNum++);			
				cell.setCellValue(header);
			}
		
			for(ClientConfigDocument data:clientConfigList)
			{
				columnNum = 0;
				excelRow = sheet.createRow(++rowNum);
				
				for(String header:FrontierConstants.CLIENT_CONFIG_XL_HEADER){			
					Cell cell = excelRow.createCell(columnNum++);			
					String val="";
					if(header.equalsIgnoreCase("CLIENT_ID")){
						val=data.getClientId();
					}else if(header.equalsIgnoreCase("GFCID")){
						val=data.getGfcId();
					}else if(header.equalsIgnoreCase("LEI")){
						val=data.getLei();
					}else if(header.equalsIgnoreCase("ACTIVE")){
						val=data.getActive();
					}
					cell.setCellValue(val);
				}
			}
			fileOut = new FileOutputStream(outboundPath+fileName);
			workBook.write(fileOut);
			return true;
		}catch(Exception e){
			log.error("Export File write error:"+e.getMessage());
		}
		finally{			
					try{if(fileOut != null){
							fileOut.flush();
							fileOut.close();}
					}catch(IOException e){}
					try{if(workBook != null){
							workBook.close();}
					}catch(IOException e){}
				}
		return false;
	}
	
	public List<String> getHeaders(List<Map<String, String>> fields){
		ArrayList<String> headerList = new ArrayList<String>();
		
		for(Map<String, String> fieldMap : fields) { 
			String fieldName = fieldMap.get("fieldName");
			headerList.add(fieldName);
		}
		return headerList;
	}
}
