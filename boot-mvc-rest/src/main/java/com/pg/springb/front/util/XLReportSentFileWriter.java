package com.pg.springb.front.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pg.springb.front.documents.EmailSentLogDocument;

public class XLReportSentFileWriter{
	
	private static final Logger log = LoggerFactory.getLogger(XLReportSentFileWriter.class);
	
	public boolean writeFile(String fileName, String outboundPath, String[] headerList, List<EmailSentLogDocument> dataList) {
		
		String sheetName = "Sheet1";// name of sheet
		SXSSFWorkbook wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
		SXSSFSheet sheet = wb.createSheet(sheetName);
		FileOutputStream out = null;
		try{
			int r=0,c=0;
			Row row = sheet.createRow(r);
			
			for(String header:headerList){			
				Cell cell = row.createCell(c++);			
				cell.setCellValue(header);
			}
			
			for ( r = 0; r < dataList.size(); r++) {
				EmailSentLogDocument data=dataList.get(r);
				row = sheet.createRow(r+1);
				for(c=0; c<headerList.length; c++){
					String val="";
					switch(c){
						case 0:val=data.getClientId();break;
						case 1:val=data.getReportType();break;
						case 2:val=data.getEmailTo();break;
						case 3:val=data.getEmailCc();break;
						case 4:val=data.getEmailBcc();break;
						case 5:val=data.getEmailSentTS().toString();break;
						case 6:val=data.getSubject();break;
						case 7:val=data.getAttachment();break;
					}
					Cell cell = row.createCell(c);
					cell.setCellValue(val);
				}
			}
			out = new FileOutputStream(outboundPath+fileName);
			wb.write(out);
			return true;
		}catch(Exception e){
			log.error("File write error:"+e);
		}finally{
			try{
					if(out != null){out.flush();out.close();}
				}catch(IOException e){}
			try{
					if(wb != null) wb.close();
				}catch(Exception e){}
			}
		return false;
	}
}
