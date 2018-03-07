package com.pg.springb.front.scheduler;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pg.springb.front.util.FrontierServiceUtil;
import com.pg.springb.front.service.FileUploadService;
import com.pg.springb.front.service.FrontierService;

@Component
public class FrontierScheduler {
	
	@Autowired
	FrontierServiceUtil frontierService;
	
	@Autowired
	FileUploadService fileUploadService;
	
	@Autowired
	FrontierService fService;
	
	private static final Logger log = LoggerFactory.getLogger(FrontierScheduler.class);
	
	@Scheduled(cron = "0 */1 * * * *")
	public void triggerIntervalEmail(){
		
		log.info("Interval Scheduler triggered");
		try {
			//Sleep for a random amount of Milli Seconds Upto 5000
			Random random = new Random();
			int flagReadNUpdateTime = 1000;
		    Thread.sleep(random.nextInt(10)*flagReadNUpdateTime);
		} catch (InterruptedException e) {}
		frontierService.triggerIntervalEmail();
		log.info("Interval Scheduler finished");	    
	}
	
	@Scheduled(cron = "0 */1 * * * *")
	public void triggerDailyEmail() {
	    log.info("Daily Scheduler triggered");
	    try {
			//Sleep for a random amount of Milli Seconds Upto 5000
			Random random = new Random();
			int flagReadNUpdateTime = 1000;
		    Thread.sleep(random.nextInt(10)*flagReadNUpdateTime);
		} catch (InterruptedException e) {}
	    frontierService.triggerDailyEmail();
	    log.info("Daily Scheduler finished");
	  }
	
	@Scheduled(cron = "0 */1 * * * *")
	public void triggerResendEmail() {
		try{
			log.info("Resend Scheduler triggered");
			frontierService.triggerResendEmail();
			log.info("Resend Scheduler finished");
			}catch(Exception ex){log.error("<<triggerResendEmail>> error:"+ex.getMessage());}
		}
	@Scheduled(cron = "0 */2 * * * *")
	public void triggerClientConfigXLProcessing(){
		
	    log.info("ClientConfigXL Processing triggered");
	    try {
			//Sleep for a random amount of Milli Seconds Upto 5000
			Random random = new Random();
			int flagReadNUpdateTime = 1000;
		    Thread.sleep(random.nextInt(10)*flagReadNUpdateTime);
		} catch (InterruptedException e) {
			// No action
		}
	    log.info("ClientConfigXL Processing starting.....");
	    fileUploadService.addClientConfig();
	    log.info("ClientConfigXL Processing finished");
	  }
	
	@Scheduled(cron = "0 */2 * * * *")
	public void triggerReportConfigXLProcessing(){		
	    log.info("ReportConfigXL Processing triggered");
	    try {
			//Sleep for a random amount of Milli Seconds Upto 5000
			Random random = new Random();
			int flagReadNUpdateTime = 1000;
		    Thread.sleep(random.nextInt(10)*flagReadNUpdateTime);
		} catch (InterruptedException e) {}
	    
	    log.info("ReportConfigXL Processing starting...");
	    fileUploadService.addReportConfig();
	    log.info("ReportConfigXL Processing finished");
	  }
	
	@Scheduled(cron = "0 0 21 * * *")
	public void triggerReportForEmailSent(){		
		log.info("triggerReportSentDetail  triggered");
		frontierService.triggerDeliveryReport();
		log.info("triggerReportSentDetail  finished");
	   
	  }
	
	@Scheduled(cron = "0 0 23 * * *")
	public void triggerDeleteTransaction(){
		log.info("Delete Transaction  triggered");
		frontierService.triggerDeleteTransaction();
		log.info("Delete Transaction  finished");
		
	}
	
	@Scheduled(cron = "0 0 22 * * *")
	public void triggerFileDeletion(){
		log.info("File deletion triggered");
		fService.triggerFileDeletion();
		log.info("File deletion finished");
	}
}
