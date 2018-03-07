package com.pg.springb.front.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pg.springb.front.documents.ClientConfigDocument;
import com.pg.springb.front.documents.ReportFieldsEntityMapping;
import com.pg.springb.front.documents.ReportScheduleDocument;
import com.pg.springb.front.documents.ResendConfigDocument;
import com.pg.springb.front.documents.UserDocument;

public class XLFileProcessing {
	
	private static final Logger log = LoggerFactory.getLogger(XLFileProcessing.class);
	
	public List<ClientConfigDocument> parseClientConfigXL(File file) throws Exception{
		Map<String, Integer> headerMap = new HashMap<String, Integer>();
		List<ClientConfigDocument> clientList=new ArrayList<ClientConfigDocument>();
		FileInputStream fis=null;
		Workbook workbook=null;
		DataFormatter df = new DataFormatter();
		try{			
			fis = new FileInputStream(file);
			
			if (file.getName().toString().endsWith(".xls"))
				   workbook = new HSSFWorkbook(fis);
				else
				   workbook = new XSSFWorkbook(fis);
			String requestorId=getReqestorIdFromUploadedFile(file.getName());
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowItr = sheet.iterator();

			boolean isHeaderFound=false;
			while (rowItr.hasNext()) {
				Row row = rowItr.next();
				Iterator<Cell> cellItr = row.cellIterator();
				int maxCol=row.getLastCellNum();
				
				ClientConfigDocument clientBean=new ClientConfigDocument();
				List<String> cellList = new ArrayList<String>();
				for(int i=0; i<maxCol; i++) {
					Cell cell = row.getCell(i, Row.CREATE_NULL_AS_BLANK);
					String cellVal = "";
					
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						cellVal = (String) cell.getStringCellValue();
						break;
					case Cell.CELL_TYPE_NUMERIC:
						cellVal = df.formatCellValue(cell);
						//cellVal = (String) Double.toString(cell.getNumericCellValue());
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						cellVal = cell.getBooleanCellValue() ? "True" : "False";
						break;
					default:
						cellVal=cell.getStringCellValue();
						break;
					}					
					cellList.add(cellVal.trim());
				}
					
					if(!isHeaderFound){
//						headerMap = isClientConfigHeader(cellList);
						headerMap= findHeader(cellList, FrontierConstants.CLIENT_CONFIG_XL_HEADER);
						log.info("clientConfigHeaderMap>>"+headerMap);
						if(headerMap.size()>=FrontierConstants.CLIENT_CONFIG_XL_HEADER.length)
							isHeaderFound=true;
								continue;}
					
					if(cellList.size()<headerMap.size())
						continue;
					
					clientBean.setRequestor(requestorId);
					clientBean.setClientId(cellList.get(headerMap.get(FrontierConstants.CLIENT_CONFIG_XL_HEADER[0])));
					clientBean.setGfcId(cellList.get(headerMap.get(FrontierConstants.CLIENT_CONFIG_XL_HEADER[1])));
					clientBean.setLei(cellList.get(headerMap.get(FrontierConstants.CLIENT_CONFIG_XL_HEADER[2])));
					clientBean.setActive(cellList.get(headerMap.get(FrontierConstants.CLIENT_CONFIG_XL_HEADER[3])));
					
					//check for duplicates
					boolean isClientBeanDuplicate=false;
					for(ClientConfigDocument cBean:clientList){
						if(clientBean.getClientId().equalsIgnoreCase(cBean.getClientId()) && 
								clientBean.getGfcId().equalsIgnoreCase(cBean.getGfcId())){
							isClientBeanDuplicate=true;
							log.info("Duplicate row found in clientConfig XL:"+clientBean);
						}
					}
					if(!isClientBeanDuplicate)
					clientList.add(clientBean);
			}
		}catch(Exception ex){
			log.error("Exception in clientConfigXl processing:"+ex.getMessage());
			throw ex;
		}finally{
			try{if(workbook != null)workbook.close();}catch(IOException e){}
			try{if(fis != null)fis.close();}catch(IOException e){}
		}
		return clientList;
	}
	
	public List<ReportScheduleDocument> parseClientReportSchedulesXL(File file)throws Exception{
		
		Map<String, Integer> headerMap = new HashMap<String, Integer>();		
		List<ReportScheduleDocument> rptConfigList=new ArrayList<ReportScheduleDocument>();
		FileInputStream fis=null;
		Workbook workbook=null;
		DataFormatter df = new DataFormatter();
		try{
			fis = new FileInputStream(file);
			
			if (file.getName().toString().endsWith(".xls"))
				   workbook = new HSSFWorkbook(fis);
				else
				   workbook = new XSSFWorkbook(fis);
			
			String requestorId=getReqestorIdFromUploadedFile(file.getName());
			Sheet sheet = workbook.getSheetAt(0);
			boolean isHeaderFound=false;
			Iterator<Row> rowIterator = sheet.iterator();
			
			while (rowIterator.hasNext()) {
				
				Row row = rowIterator.next();
//				Iterator<Cell> cellIterator = row.cellIterator();
				int maxCol=row.getLastCellNum();
				List<String> cellList = new ArrayList<String>();
				ReportScheduleDocument rptConfig=new ReportScheduleDocument();
				
				for(int i=0; i<maxCol; i++) {					
					
					Cell cell = row.getCell(i, Row.CREATE_NULL_AS_BLANK);
					String cellVal = "";					
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						cellVal = (String) cell.getStringCellValue();
						break;
					case Cell.CELL_TYPE_NUMERIC:
						cellVal = df.formatCellValue(cell);
						//cellVal = (String) Double.toString(cell.getNumericCellValue());
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						cellVal = cell.getBooleanCellValue() ? "True" : "False";
						break;
					default:
//						cellVal=cell.getStringCellValue();
						break;
					}
					cellList.add(cellVal.trim());
				}
				
				if(!isHeaderFound){
//					headerMap = isReportConfigHeader(cellList);
//					log.info("firstM():"+headerMap);
					headerMap = findHeader(cellList,FrontierConstants.REPORT_CONFIG_XL_HEADER);
					log.info("reportConfig headerMap:"+headerMap);
					if(headerMap.size()>=FrontierConstants.REPORT_CONFIG_XL_HEADER.length-FrontierConstants.REPORT_CONFIG_OPT_HEADER.length)
					isHeaderFound=true;
					continue;}
				
				if(cellList.size()<headerMap.size()-FrontierConstants.REPORT_CONFIG_OPT_HEADER.length)
					continue;
				
				rptConfig.setRequestor(requestorId);
				
				for(int i=0;i<FrontierConstants.REPORT_CONFIG_XL_HEADER.length;i++){
					String header=FrontierConstants.REPORT_CONFIG_XL_HEADER[i];
					Integer index=headerMap.get(header);
					if(index==null || cellList.size()<=index) continue;
					
					log.info(index+"<index---cellList.size()>"+cellList.size()+" "+(cellList.size()<index));
					String data=cellList.get(index);
					switch(i){
						case 0:rptConfig.setClientId(data);break;
						case 1:rptConfig.setOrganization(data);break;
						case 2:rptConfig.setIntervalEmailTo(data);break;
						case 3:rptConfig.setIntervalEmailCc(data);break;
						case 4:rptConfig.setIntervalEmailBcc(data);break;
						case 5:rptConfig.setIntervalFile(data);break;
						case 6:rptConfig.setIntervalMinute(data);break;
						case 7:rptConfig.setDailyEmailTo(data);break;
						case 8:rptConfig.setDailyEmailCc(data);break;
						case 9:rptConfig.setDailyEmailBcc(data);break;
						case 10:rptConfig.setDailyFile(data);break;
						case 11:rptConfig.setDailyTime(data);break;
						case 12:rptConfig.setWeeklyFile(data);break;
						case 13:rptConfig.setDayOfWeek(data);break;
						case 14:rptConfig.setHashKey(data);break;
						case 15:rptConfig.setMtls(data);break;
					}
				}
				
				log.info("new parsed rptConfig:"+rptConfig);
//				rptConfig.setClientId(cellList.get(headerMap.get(FrontierConstants.REPORT_CONFIG_XL_HEADER[0])));
//				rptConfig.setOrganization(cellList.get(headerMap.get(FrontierConstants.REPORT_CONFIG_XL_HEADER[1])));
//				
//				rptConfig.setIntervalEmailTo(cellList.get(headerMap.get(FrontierConstants.REPORT_CONFIG_XL_HEADER[2])));
//				rptConfig.setIntervalEmailCc(cellList.get(headerMap.get(FrontierConstants.REPORT_CONFIG_XL_HEADER[3])));
//				rptConfig.setIntervalEmailBcc(cellList.get(headerMap.get(FrontierConstants.REPORT_CONFIG_XL_HEADER[4])));
//				rptConfig.setIntervalFile(cellList.get(headerMap.get(FrontierConstants.REPORT_CONFIG_XL_HEADER[5])));
//				rptConfig.setIntervalMinute(cellList.get(headerMap.get(FrontierConstants.REPORT_CONFIG_XL_HEADER[6])));
//				
//				rptConfig.setDailyEmailTo(cellList.get(headerMap.get(FrontierConstants.REPORT_CONFIG_XL_HEADER[7])));
//				rptConfig.setDailyEmailCc(cellList.get(headerMap.get(FrontierConstants.REPORT_CONFIG_XL_HEADER[8])));
//				rptConfig.setDailyEmailBcc(cellList.get(headerMap.get(FrontierConstants.REPORT_CONFIG_XL_HEADER[9])));
//				rptConfig.setDailyFile(cellList.get(headerMap.get(FrontierConstants.REPORT_CONFIG_XL_HEADER[10])));
//				rptConfig.setDailyTime(cellList.get(headerMap.get(FrontierConstants.REPORT_CONFIG_XL_HEADER[11])));
//				
//				if(headerMap.get(FrontierConstants.REPORT_CONFIG_XL_HEADER[12])!=null &&
//						cellList.size()>headerMap.get(FrontierConstants.REPORT_CONFIG_XL_HEADER[12])){
//					rptConfig.setWeeklyFile(cellList.get(headerMap.get(FrontierConstants.REPORT_CONFIG_XL_HEADER[12])));	
//				}
//				if(headerMap.get(FrontierConstants.REPORT_CONFIG_XL_HEADER[13])!=null &&
//						cellList.size()>headerMap.get(FrontierConstants.REPORT_CONFIG_XL_HEADER[13])){
//					rptConfig.setDayOfWeek(cellList.get(headerMap.get(FrontierConstants.REPORT_CONFIG_XL_HEADER[13])));	
//				}
//				
//				rptConfig.setHashKey(rptConfig.getClientId());
//				if(headerMap.get(FrontierConstants.REPORT_CONFIG_XL_HEADER[14])!=null &&
//					cellList.size()>headerMap.get(FrontierConstants.REPORT_CONFIG_XL_HEADER[14])){
//						String hashKey=cellList.get(headerMap.get(FrontierConstants.REPORT_CONFIG_XL_HEADER[14]));
//						if(hashKey.length()>0)
//							rptConfig.setHashKey(hashKey);
//				}
//				rptConfig.setMtls("N");
//				if(headerMap.get(FrontierConstants.REPORT_CONFIG_XL_HEADER[15])!=null &&
//					cellList.size()>headerMap.get(FrontierConstants.REPORT_CONFIG_XL_HEADER[15])){
//						String mtls=cellList.get(headerMap.get(FrontierConstants.REPORT_CONFIG_XL_HEADER[15]));
//						if(mtls.length()>0)
//							rptConfig.setMtls(mtls);
//				}
				
				//checking for duplicate
				boolean isRptBeanDuplicate=false;
				for(ReportScheduleDocument rptBean:rptConfigList){
					if(rptBean.getClientId().equalsIgnoreCase(rptConfig.getClientId())){
						isRptBeanDuplicate=true;
						log.info("Duplicate row found in rportConfig XL:"+rptConfig);
					}
				}
				if(!isRptBeanDuplicate)
				rptConfigList.add(rptConfig);
			}
		}catch(Exception ex){			
			log.error("Exception in reportConfigXl processing:"+ex.getMessage());
			throw ex;
		}finally{
			try{if(workbook != null)workbook.close();}catch(IOException e){}
			try{if(fis != null)fis.close();}catch(IOException e){}
		}
		return rptConfigList;
	}
	
	public List<UserDocument> parseUserListXL(File file)throws Exception{
		
		Map<String, Integer> headerMap = new HashMap<String, Integer>();
		List<UserDocument> usersList=new ArrayList<UserDocument>();
		FileInputStream fis=null;
		Workbook workbook=null;
		DataFormatter df = new DataFormatter();
		try{			
			fis = new FileInputStream(file);
			if (file.getName().toString().endsWith(".xls"))
				   workbook = new HSSFWorkbook(fis);
				else
				   workbook = new XSSFWorkbook(fis);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowItr = sheet.iterator();

			boolean isHeaderFound=false;
			while (rowItr.hasNext()) {
				Row row = rowItr.next();
				Iterator<Cell> cellItr = row.cellIterator();
				int maxCol=row.getLastCellNum();
				
				UserDocument userBean=new UserDocument();
				List<String> cellList = new ArrayList<String>();
				for(int i=0; i<maxCol; i++) {
					Cell cell = row.getCell(i, Row.CREATE_NULL_AS_BLANK);
					String cellVal = "";
					
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						cellVal = (String) cell.getStringCellValue();
						break;
					case Cell.CELL_TYPE_NUMERIC:
						cellVal = df.formatCellValue(cell);
						//cellVal = (String) Double.toString(cell.getNumericCellValue());
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						cellVal = cell.getBooleanCellValue() ? "True" : "False";
						break;
					default:
						cellVal=cell.getStringCellValue();
						break;
					}
					cellList.add(cellVal.trim());
				}
					
					if(!isHeaderFound){
						headerMap = findHeader(cellList,FrontierConstants.USER_XL_HEADER);
						log.info("user header map:"+headerMap);
						if(headerMap.size()>=FrontierConstants.USER_XL_HEADER.length)
							isHeaderFound=true;
								continue;
					}
					
					if(cellList.size()<headerMap.size())
						continue;
					
					userBean.setUserId(cellList.get(headerMap.get(FrontierConstants.USER_XL_HEADER[0])));
					userBean.setUserPassword(cellList.get(headerMap.get(FrontierConstants.USER_XL_HEADER[1])));
					userBean.setActive(cellList.get(headerMap.get(FrontierConstants.USER_XL_HEADER[2])));
					//checking for duplicate
					boolean isUserDuplicate=false;
					for(UserDocument usrBean:usersList){
						if(usrBean.getUserId().equalsIgnoreCase(userBean.getUserId())){
							isUserDuplicate=true;
							log.info("Duplicate row found in userList XL:"+userBean);
						}
					}
					if(!isUserDuplicate)
					usersList.add(userBean);
			}
		}catch(Exception ex){
			log.error("Exception in user xl processing:"+ex.getMessage());
			throw ex;
		}finally{
			try{if(workbook != null)workbook.close();}catch(IOException e){}
			try{if(fis != null)fis.close();}catch(IOException e){}
		}
		return usersList;
	}
	
	public List<ResendConfigDocument> parseResendConfigXL(File file) throws Exception{
		Map<String, Integer> headerMap = new HashMap<String, Integer>();
		List<ResendConfigDocument> resendConfigList=new ArrayList<ResendConfigDocument>();
		FileInputStream fis=null;
		Workbook workbook=null;
		DataFormatter df = new DataFormatter();
		try{			
			fis = new FileInputStream(file);
			
			if (file.getName().toString().endsWith(".xls"))
				   workbook = new HSSFWorkbook(fis);
				else
				   workbook = new XSSFWorkbook(fis);
			String requestorId=getReqestorIdFromUploadedFile(file.getName());
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowItr = sheet.iterator();

			boolean isHeaderFound=false;
			while (rowItr.hasNext()) {
				Row row = rowItr.next();
				Iterator<Cell> cellItr = row.cellIterator();
				int maxCol=row.getLastCellNum();
				
				ResendConfigDocument resendBean=new ResendConfigDocument();
				List<String> cellList = new ArrayList<String>();
				for(int i=0; i<maxCol; i++) {
					Cell cell = row.getCell(i, Row.CREATE_NULL_AS_BLANK);
					String cellVal = "";
					
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						cellVal = (String) cell.getStringCellValue();
						break;
					case Cell.CELL_TYPE_NUMERIC:
						cellVal = df.formatCellValue(cell);
						//cellVal = (String) Double.toString(cell.getNumericCellValue());
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						cellVal = cell.getBooleanCellValue() ? "True" : "False";
						break;
					default:
						cellVal=cell.getStringCellValue();
						break;
					}					
					cellList.add(cellVal.trim());
				}
				
				if(!isHeaderFound){
					headerMap= findHeader(cellList, FrontierConstants.RESEND_CONFIG_HEADER);
					log.info("resendConfigHeaderMap>>"+headerMap);
					if(headerMap.size()>=(FrontierConstants.RESEND_CONFIG_HEADER.length-3))
						isHeaderFound=true;
							continue;}
				
				if(cellList.size()<headerMap.size())
					continue;
				
				for(int i=0;i<FrontierConstants.RESEND_CONFIG_HEADER.length;i++){
					Integer index=headerMap.get(FrontierConstants.RESEND_CONFIG_HEADER[i]);
					if(index==null || cellList.size()<=index) continue;
//					log.info(index+"<index---cellList.size()>"+cellList.size()+" "+(cellList.size()<index));
					String data=cellList.get(index);
					switch(i){
							case 0:resendBean.setClientId(data);break;
							case 1:resendBean.setReportType(data);break;
							case 2:resendBean.setReportDate(data);break;
							case 3:resendBean.setReportTime(data);break;
							case 4:resendBean.setFromDate(data);break;
							case 5:resendBean.setFromTime(data);break;
					}
				}
				resendBean.setRequestor(requestorId);
				resendConfigList.add(resendBean);
		}
			
	}catch(Exception ex){
		log.error("Exception in resendConfigXl processing:"+ex.getMessage());
		throw ex;
	}finally{
		try{if(workbook != null)workbook.close();}catch(IOException e){}
		try{if(fis != null)fis.close();}catch(IOException e){}
	}
		return resendConfigList;
	}
	
	public List<ReportFieldsEntityMapping> parseReportingFieldsXL(File file, String[] headers) throws Exception{
		
		List<ReportFieldsEntityMapping> rptFieldsMappignList=new ArrayList<ReportFieldsEntityMapping>();
		Map<String, Integer> headerMap = new HashMap<String, Integer>();
		
		FileInputStream fis=null;
		Workbook workbook=null;
		DataFormatter df = new DataFormatter();
		try{			
			fis = new FileInputStream(file);
			
			if (file.getName().toString().endsWith(".xls"))
				   workbook = new HSSFWorkbook(fis);
				else
				   workbook = new XSSFWorkbook(fis);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowItr = sheet.iterator();

			boolean isHeaderFound=false;
			while (rowItr.hasNext()) {
				Row row = rowItr.next();
				int maxCol=row.getLastCellNum();
				List<String> cellList = new ArrayList<String>();
				
				for(int i=0; i<maxCol; i++) {
					Cell cell = row.getCell(i, Row.CREATE_NULL_AS_BLANK);
					String cellVal = "";
					
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						cellVal = (String) cell.getStringCellValue();
						break;
					case Cell.CELL_TYPE_NUMERIC:
						cellVal = df.formatCellValue(cell);
						//cellVal = (String) Double.toString(cell.getNumericCellValue());
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						cellVal = cell.getBooleanCellValue() ? "True" : "False";
						break;
					default:
						cellVal=cell.getStringCellValue();
						break;
					}					
					cellList.add(cellVal.trim());
				}
				
				if(!isHeaderFound){
					headerMap= findHeader(cellList, headers);
					log.info("reportFieldsMappingHeaderMap>>"+headerMap);
					if(headerMap.size()>=(headers.length))
						isHeaderFound=true;
							continue;}
				
				if(cellList.size()<headerMap.size())
					continue;
				
				ReportFieldsEntityMapping rptFieldsMapping=new ReportFieldsEntityMapping();
				for(int i=0;i<headers.length;i++){
					Integer index=headerMap.get(headers[i]);
					if(index==null || cellList.size()<=index) continue;
//					log.info(index+"<index---cellList.size()>"+cellList.size()+" "+(cellList.size()<index));
					String data=cellList.get(index);
					switch(i){
							case 0:rptFieldsMapping.setFieldName(data);break;
							case 1:rptFieldsMapping.setAttributeName(data);break;
							case 2:rptFieldsMapping.setAttributeLevel(data);break;
					}
				}
				rptFieldsMappignList.add(rptFieldsMapping);
		}
	}catch(Exception ex){
		log.error("Exception in resendConfigXl processing:"+ex.getMessage());
		throw ex;
	}finally{
		try{if(workbook != null)workbook.close();}catch(IOException e){}
		try{if(fis != null)fis.close();}catch(IOException e){}
	}
		return rptFieldsMappignList;
	}
	
	private String getReqestorIdFromUploadedFile(String fileName){
		return fileName.split(FrontierConstants.FILE_NAME_SEPARATOR)[1];
	}

	private Map<String, Integer> findHeader(List<String> xlRow, String[] headers){
		Map<String, Integer> headerMap = new java.util.HashMap<String, Integer>();
		for(int i=0;i<xlRow.size();i++){
			for(String header:headers){
				if(xlRow.get(i)!= null && 
						(xlRow.get(i).equalsIgnoreCase(header) || 
								xlRow.get(i).toUpperCase().contains(header.toUpperCase()))) {
					headerMap.put(header, i);
					break;
				} } }
		return headerMap;
	}
}
