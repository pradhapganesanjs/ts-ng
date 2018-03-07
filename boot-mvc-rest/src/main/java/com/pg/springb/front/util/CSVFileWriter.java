package com.pg.springb.front.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pg.springb.front.controller.FrontierController;
import com.pg.springb.front.documents.ReportFieldsEntityMapping;
import com.pg.springb.front.documents.ReportingTransDocument;

public class CSVFileWriter implements FileWriter{
	private static final Logger log = LoggerFactory.getLogger(FrontierController.class);
	@Override
	public boolean writeFile(String fileName,String outboundPath,  List<ReportFieldsEntityMapping> headerMapping, List<ReportingTransDocument> dataList){
		
		OutputStream os = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;		
		try{
			File csvFile = new File(outboundPath+fileName);
			os = new FileOutputStream(csvFile);
			osw = new OutputStreamWriter(os);
			bw = new BufferedWriter(osw);		
//			ReportingFieldsDocument reportingFieldsDocument = reportingFieldsList.get(0);
//			List<Map<String, String>> fields = reportingFieldsDocument.getFields();
//			List<String> headerList = this.getHeaders(fields);
			
			List<String> headerList =new ArrayList<String>();
			for(ReportFieldsEntityMapping header:headerMapping)			
				headerList.add(header.getFieldName());
			writeLinetoCsv(bw,headerList);
			
			for(ReportingTransDocument data:dataList){								
				List<String> dataRow=new ArrayList<String>();				
				for(ReportFieldsEntityMapping fieldMapping:headerMapping) {
					String val="";
					String fieldName = fieldMapping.getFieldName();
					String attributeName = fieldMapping.getFieldName();
					String attributeLevel = fieldMapping.getFieldName();
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
							val = String.valueOf(data.getInfo().get(attributeName));
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
					dataRow.add(val != null ? val:"" );
				}
				
				writeLinetoCsv(bw,dataRow);				
			}
			bw.flush();
			return true;
		}catch(Exception e){
			log.error("Exception occured:"+e);
		}finally{			
			try{if(bw != null)bw.close();}catch(IOException e){}
			try{if(osw != null)osw.close();}catch(IOException e){}
			try{if(os != null)os.close();}catch(IOException e){}}
		return false;
	}
	
	private void writeLinetoCsv(BufferedWriter bw,List<String> values)throws IOException {
		StringBuilder transRow = new StringBuilder();
		boolean first = true;		
		for(String value:values){				
			if(!first)
				transRow.append(FrontierConstants.CSV_SEPARATOR);	
			transRow.append(value);				
			first = false;
		}
		transRow.append("\n");	
		bw.write(transRow.toString());
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
