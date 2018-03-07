package com.pg.springb.front.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pg.springb.front.util.FrontierServiceUtil;
import com.pg.springb.front.documents.ClientConfigDocument;
import com.pg.springb.front.documents.EmailSentLogDocument;
import com.pg.springb.front.documents.ReportScheduleDocument;
import com.pg.springb.front.documents.UserDocument;
import com.pg.springb.front.service.DBFacadeService;
import com.pg.springb.front.service.FileUploadService;

@EnableAutoConfiguration
@RestController
public class FrontierController {
	
	@Autowired
	DBFacadeService dbFacadeService;
	
	@Autowired
	FrontierServiceUtil frontierServiceUtil;
	
	@Autowired
	FileUploadService fileUploadService;
	
	private static final Logger log = LoggerFactory.getLogger(FrontierController.class);
	
	//PTT service
	@RequestMapping("/getClientConfigList")
	public String getClientConfigList() {
		//It will be used by PTT transactions service
		List<ClientConfigDocument> clientConfigEntityList = dbFacadeService.getClientConfigDocumentActiveList();
		System.out.println("Client config list:" + clientConfigEntityList);
		
		return frontierServiceUtil.getClientConfigListJson(clientConfigEntityList);
	}
	//PTT service
	@RequestMapping("/getClientConfigMap")
	public String getClientConfigMap() {
		//It will be used by PTT transactions service
		List<ClientConfigDocument> clientConfigEntityList = dbFacadeService.getClientConfigDocumentActiveList();
		return frontierServiceUtil.getClientConfigMapJson(clientConfigEntityList);
	}
	@RequestMapping("/getReportingFieldsList")
	public String getReportingFieldsList() {
		//It will be used by PTT transactions service
//		List<ReportingFieldsDocument> reportingFieldsList = dbFacadeService.getReportingFieldsList();
//		return reportingFieldsList.toString();
		return dbFacadeService.findAllReportFields().toString();
	}
	
	@RequestMapping("/getReportSentLog")
	public List<EmailSentLogDocument> getReportSentLogList(){
		return dbFacadeService.findAllEmailSentLog();
	}
	
	@RequestMapping("/getDeliveryReport")
	public String getDeliveryReport(){
		frontierServiceUtil.triggerDeliveryReport();
		return "Success";
	}
	
	@RequestMapping("/getReportingTransactions")
	public String getReportingTransactions() {
//		List<ReportingTransDocument> reportingTransactionsList = dbFacadeService.getReportingTransDocumentList();		
		return dbFacadeService.getReportingTransDocumentList().toString();
	}
	
	@RequestMapping("/getReportingTransactionsByClient")
	public String getReportingTransactionsByClient() {
//		List<ReportingTransDocument> reportingTransactionsList = dbFacadeService.getIntervalTransactions("REPORTABLE","GSAM123");	
		return dbFacadeService.getIntervalTransactions("REPORTABLE","GSAM123").toString();
	}

	@RequestMapping("/addUpdateClientConfigList")
	public String addUpdateClientConfigList() {
		fileUploadService.addClientConfig();
		return "success";
	}

	@RequestMapping("/getReportScheduleList")
	public String getReportScheduleList() {		
		List<ReportScheduleDocument> scheduleDocumentList = dbFacadeService.getReportScheduleDocumentList();
		System.out.println("ReportConfigList:" + scheduleDocumentList);
		return scheduleDocumentList.toString();
	}
	
	@RequestMapping("/addUpdateReportScheduleList")
	public String addUpdateReportScheduleList() {
		fileUploadService.addReportConfig();
		return "Success";
	}
	@RequestMapping("/triggerIntervalEmail")
	public String triggerIntervalEmail() {
		log.info("Interval Scheduler triggered");
		
		frontierServiceUtil.triggerIntervalEmail();
		log.info("Interval Scheduler finished");
	    return "Success";
	}
	
	@RequestMapping("/triggerDailyEmail")
	public String triggerDailyEmail() {
		log.info("Daily Scheduler triggered");
		frontierServiceUtil.triggerDailyEmail();
		log.info("Daily Scheduler finished");
	    return "Success";
	}
	
	@RequestMapping("/triggerDeleteTransaction")
	public String triggerDeleteTransaction() {
		log.info("Delete Transaction triggered");
		frontierServiceUtil.triggerDeleteTransaction();
		log.info("Delete Transaction finished");
	    return "Success";
	}
	
	@RequestMapping("/addDefaultUser")
	public String addDefaultUser() {
		List<UserDocument> userList=dbFacadeService.findAllUsersByUserId("frontier_root");
		if(userList!=null && userList.size()>0)
			return "Default user Already present!";
		UserDocument defaulutUser=new UserDocument();
		defaulutUser.setUserId("frontier_root");
		defaulutUser.setUserPassword("frontier_root123");
		defaulutUser= dbFacadeService.saveUser(defaulutUser);
		return "Default User added:"+defaulutUser.getUserId();
	}
	@RequestMapping("/getResendConfig")
	public String getResendConfig() {
		return dbFacadeService.findAllResendConfig().toString();
	}
}
