package com.pg.springb.front.service;

import java.io.File;
import java.io.FileFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.citi.frontier.util.FrontierConstants;

@Service
public class FrontierService {
	
	private static final Logger log = LoggerFactory.getLogger(FrontierService.class);
	
	@Value("${frontier.export.path}")
	private String exportPath;
	
	@Value("${frontier.upload.path}")
	private String uploadPath;
	
	public void triggerFileDeletion(){
		
		FileFilter fileFilter = new FileFilter() {
			public boolean accept(File file) {
				return file.isFile();
			}
		};
		
		for (int i = 0; i < FrontierConstants.REPORT_FOLDERS.length; i++) {
			
				File folder=new File(exportPath	+ FrontierConstants.REPORT_FOLDERS[i]);
				
			    	File[] listFiles = folder.listFiles(fileFilter);
			    	
			    	long eligibleForDeletion = System.currentTimeMillis() -  (30 * 24 * 60 * 60 * 1000L); 
			    
			    	for (File listFile : listFiles) {
			    		 try{
			                if (listFile.lastModified() < eligibleForDeletion) 
		                	listFile.delete();
			    		 }catch(Exception ex){
			    			 log.info("ountbound file deleting exception:"+ex.getMessage());
			    		 }
				}
		}
		
		for (int i = 0; i < FrontierConstants.UPLOAD_FOLDERS.length; i++) {
			
			File folder=new File(uploadPath + FrontierConstants.UPLOAD_FOLDERS[i]);
			File[] listFiles = folder.listFiles(fileFilter);
			long eligibleForDeletion = System.currentTimeMillis() -  (90 * 24 * 60 * 60 * 1000L);
			
			for (File listFile : listFiles) {
	    		 try{
	                if (listFile.lastModified() < eligibleForDeletion) 
	                	listFile.delete();
	    		 }catch(Exception ex){
	    			 log.info("inbound file deleting exception:"+ex.getMessage());
	    		 }
			}
		}
	}
}
