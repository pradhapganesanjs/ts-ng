package com.pg.springb.front.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pg.springb.front.documents.ReportFieldsEntityMapping;
import com.pg.springb.front.documents.ReportingTransDocument;

public class XLSXFileWriter implements FileWriter {
	private static final Logger log = LoggerFactory.getLogger(XLSXFileWriter.class);
	
	@Override
	public boolean writeFile(String fileName, String outboundPath, List<ReportFieldsEntityMapping> headerMapping, List<ReportingTransDocument> dataList) {
		
		FileOutputStream fileOut = null;
		String sheetName = "Sheet1";
		SXSSFWorkbook workBook = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
		SXSSFSheet sheet = workBook.createSheet(sheetName);

		try{
//			ReportingFieldsDocument reportingFieldsDocument = headerList.get(0);
//			List<Map<String, String>> fields = reportingFieldsDocument.getFields();
//			List<String> headers = getHeaders(fields);
			int rowNum=0, columnNum=0;
			Row excelRow = sheet.createRow(rowNum);
			
			for(ReportFieldsEntityMapping header:headerMapping){			
				Cell cell = excelRow.createCell(columnNum++);			
				cell.setCellValue(header.getFieldName());
			}
		
			for(ReportingTransDocument data:dataList)
			{
				columnNum = 0;
				excelRow = sheet.createRow(++rowNum);
				
				for(ReportFieldsEntityMapping fieldMapping:headerMapping) {
					Cell cell = excelRow.createCell(columnNum++);
					
					String val="";
					String fieldName = fieldMapping.getFieldName();
					String attributeName = fieldMapping.getAttributeName();
					String attributeLevel = fieldMapping.getAttributeLevel();
					if("main".equals(attributeLevel)) {
						if("sourceId".equals(attributeName)) {
							val = data.getSourceId();
							
						}else if("executionTs".equals(attributeName)) {
							LocalDateTime localDateTime = data.getExecutionTs();
							val = localDateTime == null ? "": localDateTime.toString();							
							
						}else if("sourceStatus".equals(attributeName)) {
							val = data.getSourceStatus();							
						}
					}else { // info 
						if(data.getInfo().get(attributeName) != null){
							if (data.getInfo().get(attributeName) instanceof java.util.Date) {
								TimeZone utc = TimeZone.getTimeZone("UTC");
						        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm s'Z'");
						        formatter.setTimeZone(utc);
						        val = formatter.format(data.getInfo().get(attributeName));
							}else {
								val = String.valueOf(data.getInfo().get(attributeName));
							}
						}else{
							val = "";
						}
						
						//Setting 999 for these fields
						if(val.equals("") && fieldName.equals("TrdRegPublicationReason")) {
							val = "999";
						}else if(val.equals("") && fieldName.equals("LastLiquidityIndicator")) {
							val = "999";
						}
						
					}
					cell.setCellValue(val != null ? val:"");
				}
			}
//			log.info("Writing to XLSX file :"+fileName);
			fileOut = new FileOutputStream(outboundPath+fileName);
			workBook.write(fileOut);
//			log.info("writeFile Completed:"+fileName);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			log.error("writeFile error:"+e.getMessage());
		}
		finally{			
				try{
					if(fileOut != null){
						fileOut.flush();
						fileOut.close();
					}
				}catch(IOException e){}
				
				try{
					if(workBook != null){
						workBook.close();
					}
				}catch(IOException e){}
		}
		return false;
	}
	
//	public List<String> getHeaders(List<Map<String, String>> fields){
//		ArrayList<String> headerList = new ArrayList<String>();
//		
//		for(Map<String, String> fieldMap : fields) { 
//			String fieldName = fieldMap.get("fieldName");
//			headerList.add(fieldName);
//		}
//		return headerList;
//	}
}
