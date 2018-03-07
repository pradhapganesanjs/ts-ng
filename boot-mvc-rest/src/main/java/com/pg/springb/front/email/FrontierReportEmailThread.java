package com.pg.springb.front.email;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pg.springb.front.util.FileWriterFactory;
import com.pg.springb.front.util.FrontierConstants;
import com.pg.springb.front.util.MailSender;
import com.pg.springb.front.documents.EmailSentLogDocument;
import com.pg.springb.front.documents.ReportFieldsEntityMapping;
import com.pg.springb.front.documents.ReportScheduleDocument;
import com.pg.springb.front.documents.ReportingTransDocument;
import com.pg.springb.front.documents.ResendConfigDocument;
import com.pg.springb.front.service.DBFacadeService;

public class FrontierReportEmailThread implements Runnable{
	private static final Logger log = LoggerFactory.getLogger(FrontierReportEmailThread.class);
	
	private String fileName;
	private String fileExtension;
	private String exportPath;
	private String emailMessage;
	private List<ReportFieldsEntityMapping> headerFields;
	private List<ReportingTransDocument> transactions;
	private ReportScheduleDocument reportConfig;
	private ResendConfigDocument resendConfig;
	private EmailSentLogDocument emailBo;
	private DBFacadeService dbFacadeService;
	
	public FrontierReportEmailThread(String fileName, String fileExtension, String exportPath, String emailMessage,  
			List<ReportFieldsEntityMapping> headerFields, List<ReportingTransDocument> transactions,
			ReportScheduleDocument reportConfig, ResendConfigDocument resendConfig, 
			EmailSentLogDocument emailBo, DBFacadeService dbFacadeService){
		this.fileName=fileName;
		this.fileExtension=fileExtension;
		this.exportPath=exportPath;
		this.emailMessage=emailMessage;
		this.headerFields=headerFields;
		this.transactions=transactions;
		this.reportConfig=reportConfig;
		this.resendConfig=resendConfig;
		this.emailBo=emailBo;
		this.dbFacadeService=dbFacadeService;
	}
	
	@Override
	public void run() {
		boolean isEmailSent = false;
		String reportType = emailBo.getReportType();
		LocalDateTime currTs=LocalDateTime.now(Clock.systemUTC());
		try{
			boolean isFileGenerated = FileWriterFactory.getFileWriter(fileExtension).writeFile(
					fileName, exportPath, headerFields, transactions);
			if(isFileGenerated){
				String[] attachFiles = { exportPath + fileName };
				MailSender.sendEmailWithAttachments(emailBo.getEmailTo(), emailBo.getEmailCc(), 
						emailBo.getEmailBcc(), emailBo.getSubject(), emailMessage, attachFiles);
				isEmailSent = true;
				log.info(reportType+"_EMAIL sent for "+emailBo.getClientId()+", attachment:"+fileName);
				if(isEmailSent){
					emailBo.setEmailSentTS(currTs);
					dbFacadeService.saveEmailSentLog(emailBo);
					
					if(FrontierConstants.CLIENT_REPORT_TYPE[0].equalsIgnoreCase(reportType)){
						for (ReportingTransDocument transaction : transactions){
							transaction.setStatus(FrontierConstants.TRANSACTION_STATUS_REPORTED); //enabled
							transaction.setLastUpdatedTs(currTs);
						}
						transactions=dbFacadeService.addUpdateReportingTransDocumentList(transactions);
						log.info("INTERVAL made  REPORTED for transactions:"+transactions);
					}
				}
			}
		}catch(Exception ex){
			log.error("Email send error:"+ex.getMessage());
		}finally{
			try{
				if(FrontierConstants.CLIENT_REPORT_TYPE[0].equals(reportType)){
					
					reportConfig.setIntervalJobStatus(FrontierConstants.CLIENT_JOB_READY);
					reportConfig.setLastUpdatedTs(currTs);
					if(isEmailSent)
						reportConfig.setIntervalEmailSentTS(currTs);
					dbFacadeService.addUpdateReportConfigDocument(reportConfig);
					log.info("finally reportConfig updated READY for INTERVAL"+emailBo.getClientId());
				}else if(FrontierConstants.CLIENT_REPORT_TYPE[1].equals(reportType)){
					
					reportConfig.setDailyJobStatus(FrontierConstants.CLIENT_JOB_READY);
					reportConfig.setLastUpdatedTs(currTs);
					if(isEmailSent)
						reportConfig.setDailyEmailSentTS(emailBo.getEmailSentTS());
					reportConfig.setLastUpdatedTs(currTs);	
					dbFacadeService.addUpdateReportConfigDocument(reportConfig);
					log.info("finally reportConfig updated READY for DAILY:"+emailBo.getClientId());
				}else{
					if(isEmailSent)
						resendConfig.setStatus("COMPLETED");
					else
						resendConfig.setStatus("PROCESS_FAILED");
					resendConfig.setLastUpdatedTs(currTs);
					dbFacadeService.saveResendConfig(resendConfig);
					log.info(" finallyresendConfig updated"+resendConfig.getStatus()+" for "+resendConfig.getReportType()+" "+emailBo.getClientId());
				}
			}catch(Exception e){log.error("finally dbUpdate error:"+e.getMessage());}
		}
	}
}