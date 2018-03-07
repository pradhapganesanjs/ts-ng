package com.pg.springb.front.rest.controller;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pg.springb.front.util.FrontierServiceUtil;
import com.pg.springb.front.controller.FrontierController;
import com.pg.springb.front.documents.EmailSentLogDocument;
import com.pg.springb.front.documents.ReportingTransDocument;
import com.pg.springb.front.rest.helper.ApiCommonCritLimitReq;
import com.pg.springb.front.rest.helper.ApiCommonCriteriaReq;
import com.pg.springb.front.rest.helper.ApiCommonResponse;
import com.pg.springb.front.rest.helper.Error;
import com.pg.springb.front.rest.helper.ReportingTransCriteriaReq;
import com.pg.springb.front.rest.helper.ReportingTransLimitReq;
import com.pg.springb.front.rest.helper.ReportingWrapResponse;
import com.pg.springb.front.service.DBFacadeService;
import com.pg.springb.front.service.FileUploadService;

@EnableAutoConfiguration
@RestController
@RequestMapping("/api")
public class FrontierRestController {
	
	@Autowired
	DBFacadeService dbFacadeService;
	
	@Autowired
	FrontierServiceUtil frontierServiceUtil;
	
	@Autowired
	FileUploadService fileUploadService;
	
	private static final Logger log = LoggerFactory.getLogger(FrontierController.class);
	
	@GetMapping(value = "/headreporttransdoc")
	public @ResponseBody ApiCommonResponse<ReportingWrapResponse<ReportingTransDocument>> reportingTransDocumentColumns() {

		ReportingWrapResponse<ReportingTransDocument> repTransWrap = new ReportingWrapResponse<ReportingTransDocument>();
		repTransWrap.setHeaders(Arrays.asList(ReportingTransDocument.TOP_LEVEL_FIELDS));
		repTransWrap.setRecords(null);

		return new ApiCommonResponse<>(repTransWrap);
	}
	
	@PostMapping(value = "/reportslimitbycrit")
	public @ResponseBody ApiCommonResponse<ReportingWrapResponse<ReportingTransDocument>> reportsByCritLimit(
			@RequestBody(required = false) ReportingTransCriteriaReq reportCrit) {

		System.out.format("report crit %s", reportCrit.toString());
		try {

			if (null == reportCrit) {
				reportCrit = new ReportingTransCriteriaReq();
			}
			if (reportCrit.getLimit() == 0) {
				reportCrit.setLimit(500);
			}
			
			List<ReportingTransDocument> returnReportList = dbFacadeService.findByCrit(
																				reportCrit.getCritKey(), 
																				reportCrit.getCritVal(), 
																				reportCrit.getLastId(), 
																				reportCrit.getLimit());
			System.err.println(
					"reportingTransactionsList size " + (null == returnReportList ? null : returnReportList.size()));


			ReportingWrapResponse<ReportingTransDocument> repTransWrap = new ReportingWrapResponse<ReportingTransDocument>();
			repTransWrap.setHeaders(Arrays.asList(ReportingTransDocument.TOP_LEVEL_FIELDS));
			repTransWrap.setRecords(returnReportList);

			return new ApiCommonResponse<>(repTransWrap);

		} catch (Exception e) {
			return new ApiCommonResponse<>(null, Error.NUM.set("-500"),
					Error.MSG.set("getReportingTransDocumentList Failed! with error " + e.getMessage()));
		}
	}
	
	
	
	@PostMapping(value = "/reportsbylimit")
	public @ResponseBody ApiCommonResponse<ReportingWrapResponse<ReportingTransDocument>> reportsByLimit(
			@RequestBody(required = false) ReportingTransLimitReq reportReqLimit) {

		if (null == reportReqLimit) {
			reportReqLimit = new ReportingTransLimitReq();
		}
		if (null == reportReqLimit || reportReqLimit.getLimit() == 0) {
			reportReqLimit.setLimit(500);
		}

		try {

			List<ReportingTransDocument> returnReportList = dbFacadeService.findAllLimitById(
																	reportReqLimit.getLastId(),
																	reportReqLimit.getLimit());
			System.err.println(
					"reportingTransactionsList size " + (null == returnReportList ? null : returnReportList.size()));

			ReportingWrapResponse<ReportingTransDocument> repTransWrap = new ReportingWrapResponse<ReportingTransDocument>();
			repTransWrap.setHeaders(Arrays.asList(ReportingTransDocument.TOP_LEVEL_FIELDS));
			repTransWrap.setRecords(returnReportList);

			return new ApiCommonResponse<>(repTransWrap);

		} catch (Exception e) {
			return new ApiCommonResponse<>(null, Error.NUM.set("-500"),
					Error.MSG.set("getReportingTransDocumentList Failed! with error " + e.getMessage()));
		}
	}
	
	@GetMapping(value = "/reportsentlogheaders")
	public @ResponseBody ApiCommonResponse<ReportingWrapResponse<EmailSentLogDocument>> reportSentLogDocColumns() {

		ReportingWrapResponse<EmailSentLogDocument> repTransWrap = new ReportingWrapResponse<EmailSentLogDocument>();
		repTransWrap.setHeaders(getReportSentLogDocHeaders());
		repTransWrap.setRecords(null);

		return new ApiCommonResponse<>(repTransWrap);
	}

	private static List<String> getReportSentLogDocHeaders() {
		return Arrays.asList(new String[]{"id","clientId","reportEmail","fileName","reportType","emailSentTS","message","hashKey","secretKey","hostName"});
	}
	
	@PostMapping(value = "/reportsentloglimitbycrit")
	public @ResponseBody ApiCommonResponse<ReportingWrapResponse<EmailSentLogDocument>> reportSentLogByCritLimit(
			@RequestBody(required = false) ApiCommonCriteriaReq<EmailSentLogDocument> reportCrit) {

		
		System.out.format("report crit %s", reportCrit.toString());
		try {

			if(null == reportCrit) {
				reportCrit = new ApiCommonCriteriaReq<EmailSentLogDocument>();
			}
			
			if (reportCrit.getLimit() == 0) {
				reportCrit.setLimit(500);
			}
			
			List<EmailSentLogDocument> returnReportList = dbFacadeService.rsLogfindByCrit(
																				reportCrit.getCritKey(), 
																				reportCrit.getCritVal(), 
																				reportCrit.getLastId(), 
																				reportCrit.getLimit());
			System.err.println(
					"reportingTransactionsList size " + (null == returnReportList ? null : returnReportList.size()));


			ReportingWrapResponse<EmailSentLogDocument> repTransWrap = new ReportingWrapResponse<EmailSentLogDocument>();
			repTransWrap.setHeaders(getReportSentLogDocHeaders());
			repTransWrap.setRecords(returnReportList);

			return new ApiCommonResponse<>(repTransWrap);

		} catch (Exception e) {
			return new ApiCommonResponse<>(null, Error.NUM.set("-500"),
					Error.MSG.set("getEmailSentLogDocumentList Failed! with error " + e.getMessage()));
		}
	}
	
	
	
	@PostMapping(value = "/reportsentloglimit")
	public @ResponseBody ApiCommonResponse<ReportingWrapResponse<EmailSentLogDocument>> reportSentLogByLimit(
			@RequestBody(required = false) ApiCommonCritLimitReq<EmailSentLogDocument> reportReqLimit) {

		if (null == reportReqLimit) {
			reportReqLimit = new ApiCommonCritLimitReq<EmailSentLogDocument>();
		}
		if (reportReqLimit.getLimit() == 0) {
			reportReqLimit.setLimit(500);
		}

		try {

			List<EmailSentLogDocument> returnReportList = dbFacadeService.rsLogFindAllLimitById(
																	reportReqLimit.getLastId(),
																	reportReqLimit.getLimit());
			System.err.println(
					"reportingTransactionsList size " + (null == returnReportList ? null : returnReportList.size()));

			ReportingWrapResponse<EmailSentLogDocument> repTransWrap = new ReportingWrapResponse<EmailSentLogDocument>();
			repTransWrap.setHeaders(getReportSentLogDocHeaders());
			repTransWrap.setRecords(returnReportList);

			return new ApiCommonResponse<>(repTransWrap);

		} catch (Exception e) {
			return new ApiCommonResponse<>(null, Error.NUM.set("-500"),
					Error.MSG.set("getEmailSentLogDocumentList Failed! with error " + e.getMessage()));
		}
	}

	
}
