package com.pg.springb.front.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

//import com.citi.reghub.core.cache.client.CacheClient;
import com.pg.springb.front.documents.ActiveHostDocument;
import com.pg.springb.front.documents.ClientConfigDocument;
import com.pg.springb.front.documents.EmailSentLogDocument;
import com.pg.springb.front.documents.FileProcessLogDocument;
import com.pg.springb.front.documents.ReportFieldsDocument;
import com.pg.springb.front.documents.ReportScheduleDocument;
import com.pg.springb.front.documents.ReportingTransDocument;
import com.pg.springb.front.documents.ResendConfigDocument;
import com.pg.springb.front.documents.UserDocument;
import com.pg.springb.front.repository.ActiveHostRepository;
import com.pg.springb.front.repository.ClientConfigRepository;
import com.pg.springb.front.repository.EmailSentLogRepository;
import com.pg.springb.front.repository.FileProcessLogRepository;
import com.pg.springb.front.repository.ReportFieldsRepository;
import com.pg.springb.front.repository.ReportScheduleRepository;
import com.pg.springb.front.repository.ReportingTransRepository;
import com.pg.springb.front.repository.ResendConfigRepository;
import com.pg.springb.front.repository.UserRepository;

@Service
public class DBFacadeService {

	@Autowired
	ClientConfigRepository clientConfigRepository;
	
	@Autowired
	ReportScheduleRepository reportConfigRepository;
	
	@Autowired
	ReportingTransRepository reportingTransRepository;
	
//	@Autowired
//	ReportingFieldsRepository reportingFieldsRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EmailSentLogRepository emailSentLogRepository;
	
//	@Autowired
//	ClientEmailProcessRepository emailProcessRepository;
	
	@Autowired
	ActiveHostRepository activeHostRepository;
	
	@Autowired
	FileProcessLogRepository fileProcessLogRepository;
	
	@Autowired
	ResendConfigRepository resendConfigRepository;
	
	@Autowired
	ReportFieldsRepository reportFieldsRepository;
	
	//@Autowired
    //private CacheClient cacheClient;
	
	@Autowired
    MongoTemplate mongoTemplate;
	
	/*-----------------ActiveHostRepository-------------*/
	public List<ActiveHostDocument> findAllActiveHost(){
		return activeHostRepository.findAll();
	}
	public List<ActiveHostDocument> saveActiveHost(List<ActiveHostDocument> entities){
		return activeHostRepository.save(entities);
	}
//	public List<ActiveHostDocument> getActiveHost(){
//		return activeHostRepository.findAll();
//	}
	/*-----------------ActiveHostRepository-------------*/
	
	/*-----------------UserRepository-------------*/
	public List<UserDocument> findAllUsers(){
		return userRepository.findAll();
	}
	public List<UserDocument> findAllUsersByUserId(String userId){
		return userRepository.findAllByUserId(userId);
	}
	public UserDocument saveUser(UserDocument entity){
		return userRepository.save(entity);
	}
	public List<UserDocument> saveUsers(List<UserDocument> entites){
		return userRepository.save(entites);
	}
	/*-----------------UserRepository-------------*/
	
	/*-----------------ClientConfigRepository-------------*/
	public List<ClientConfigDocument> getClientConfigDocumentList(){
		return clientConfigRepository.findAll();	
	}
	public List<ClientConfigDocument> getClientConfigDocumentActiveList(){
		return clientConfigRepository.findAllActive();	
	}
	public List<ClientConfigDocument> getClientConfigbyClientId(String clientId){
		return clientConfigRepository.getClientConfigbyClientId(clientId);
	}
	public List<ClientConfigDocument> getActiveClientsGFCID(String clientId){
		return clientConfigRepository.getActiveClientsGFCID(clientId);
	}
	public List<ClientConfigDocument> addUpdateClientConfigDocument(List<ClientConfigDocument> clientConfigDocumentList){
		return clientConfigRepository.save(clientConfigDocumentList);		
	}
	public List<ClientConfigDocument> findClientConfigByStatus(String active){
		return clientConfigRepository.findAllbyStatus(active);
	}
	public Long deleteClientConfigbyStatus(String active){
		return clientConfigRepository.deleteAllByStatus(active);
	}
	/*-----------------ClientConfigRepository-------------*/
	
	/*-----------------ReportConfigRepository-------------*/
	public List<ReportScheduleDocument> getReportScheduleDocumentList(){
		 return reportConfigRepository.findAll();
	}
	public List<ReportScheduleDocument> getReportConfigByClientId(String clientId){
		 return reportConfigRepository.findAllByClientId(clientId);
	}
	public List<ReportScheduleDocument> getIntervalRptCfgList(){
		return  reportConfigRepository.findIntervalRptCfgList();				
	}
	public List<ReportScheduleDocument> getDailyRptCfgList(){
		return  reportConfigRepository.findDailyRptCfgList();				
	}
	public List<ReportScheduleDocument> addUpdateReportScheduleDocumentList(List<ReportScheduleDocument> reportScheduleDocumentList){
		return reportConfigRepository.save(reportScheduleDocumentList);
	}   
	public ReportScheduleDocument addUpdateReportConfigDocument(ReportScheduleDocument reportScheduleDocument){
		return reportConfigRepository.save(reportScheduleDocument);
	}
	public List<ReportScheduleDocument> findAllReportConfigByIntervalReady(){
		return reportConfigRepository.findAllByIntervalReady();
	}
	public List<ReportScheduleDocument> findAllReportConfigByDailyReady(){
		return reportConfigRepository.findAllByDailyReady();
	}
	/*-----------------ReportConfigRepository-------------*/
	
//	public List<ReportingFieldsDocument> getReportingFieldsList(){
		//cacheClient.put(FrontierConstants.REPORTINT_FIELD_CACHE, value, frontierClient.getProperties()); // push
        //cacheClient.get(fieldDocKey, frontierClient.getProperties()); // pull
//		return reportingFieldsRepository.findAll();	
//	}
	
	/*-----------------ReportingTransRepository-------------*/
	public List<ReportingTransDocument> getReportingTransDocumentList(){
		return reportingTransRepository.findAll();		 		
	}
	public List<ReportingTransDocument> getReportingTransactionByClientId(String clientId){
		return reportingTransRepository.findAllByClientId(clientId);		 		
	}
	public List<ReportingTransDocument> getReportingTransactionByClientIdnLastUpdatedTs(String clientId, long fromDate, long toDate){
		return reportingTransRepository.findAllByClientIdnLastUpdatedTs(clientId,fromDate,toDate);		 		
	}
	public List<ReportingTransDocument> saveReportingTransDocumentList(List<ReportingTransDocument> lst){
		return reportingTransRepository.save(lst);		 		
	}
	public List<ReportingTransDocument> getIntervalTransactions(String status, String clientId){
		return reportingTransRepository.getIntervalTransactions(status, clientId);			
	}
	
	public List<ReportingTransDocument> getIntervalTransactionsAll( String clientId){
		return reportingTransRepository.getIntervalTransactionsAll(clientId);
	}
	
	public List<ReportingTransDocument> getDailyTransactions(String clientId, long currTime, long ystrDayTime){
		return reportingTransRepository.getDailyTransactions(clientId,currTime,ystrDayTime);
	  }
	
	public List<ReportingTransDocument> addUpdateReportingTransDocumentList(List<ReportingTransDocument> reportingTransDocumentList){
		return reportingTransRepository.save(reportingTransDocumentList);
	}
	public List<ReportingTransDocument> getDeletableTransactions(long beyondDT){
		return reportingTransRepository.getDeletableTransactions(beyondDT);
	}
	public void deleteTransactions(List<ReportingTransDocument> deletableTrns){
		reportingTransRepository.delete(deletableTrns);
	}
	/*-----------------ReportingTransRepository-------------*/
	
	/*-----------------ReportSentLogDocument-------------*/
	public List<EmailSentLogDocument> findAllEmailSentLog(){
		return emailSentLogRepository.findAll();
	}
	public List<EmailSentLogDocument> findAllEmailSentLogByEmailSentTs(long fromTs,long toTs){
		return emailSentLogRepository.getLastOneDayLogs(fromTs, toTs);
	}
	public EmailSentLogDocument saveEmailSentLog(EmailSentLogDocument entity){
		return emailSentLogRepository.save(entity);
	}
//	public List<ReportSentLogDocument> saveReportSentLog(List<ReportSentLogDocument> list){
//		return reportSentLogRepository.save(list);
//	}
//	public List<ReportSentLogDocument> findAllActiveHostByEmailSentTS(long fromDate,long toDate){
//		return reportSentLogRepository.getLastOneDayLogs(fromDate, toDate);
//	}
	/*-----------------ReportSentLogDocument-------------*/
	
	/*-----------------ClientEmailProcessRepository-------------*/
//	public List<ClientEmailProcessDocument> getClientEmailProcessList(){
//		return emailProcessRepository.findAll();
//	}
//	public List<ClientEmailProcessDocument> findIntervalRptProcessList(){
//		return emailProcessRepository.findIntervalRptProcessList();
//	}
//	public List<ClientEmailProcessDocument> findDailyRptProcessList(){
//		return emailProcessRepository.findDailyRptProcessList();
//	}
//	public ClientEmailProcessDocument saveClientEmailProcessDocumentt(ClientEmailProcessDocument document){
//		return emailProcessRepository.save(document);
//	}
	/*-----------------ClientEmailProcessRepository-------------*/
	
	/*-----------------FileProcessLogRepository-------------*/
	public List<FileProcessLogDocument> findAllFileProcessLog(){
		return fileProcessLogRepository.findAll();
	}
	public List<FileProcessLogDocument> findAllFileProcessLogByFileName(String fileName){
		return fileProcessLogRepository.findAllByFileName(fileName);
	}
	public FileProcessLogDocument saveFileProcessLog(FileProcessLogDocument entity){
		return fileProcessLogRepository.save(entity);
	}
	/*-----------------FileProcessLogRepository-------------*/
	
	/*-----------------ResendConfigRepository-------------*/
	public List<ResendConfigDocument> findAllResendConfig(){
		return resendConfigRepository.findAll();
	}
	public List<ResendConfigDocument> findAllResendConfigByStatus(String status){
		return resendConfigRepository.findAllByStatus(status);
	}
	public ResendConfigDocument saveResendConfig(ResendConfigDocument entity){
		return resendConfigRepository.save(entity);
	}
	public List<ResendConfigDocument> saveResendConfig(List<ResendConfigDocument> entities){
		return resendConfigRepository.save(entities);
	}
	/*-----------------ResendConfigRepository-------------*/
	
	/*-----------------ReportFieldsRepository-------------*/
	public List<ReportFieldsDocument> findAllReportFields(){
		return reportFieldsRepository.findAll();
	}
	public ReportFieldsDocument saveReportFields(ReportFieldsDocument entity){
		return reportFieldsRepository.save(entity);
	}
	public void deleteReportFields(List<ReportFieldsDocument> entities){
		reportFieldsRepository.delete(entities);
	}
	/*-----------------ReportFieldsRepository-------------*/
	
	
	/*----------------- ng services-------------*/
	public List<EmailSentLogDocument> rsLogFindAllLimitById(String id, int limit){
		Query query = new Query();
		if(StringUtils.isNotBlank(id)) {
			query.addCriteria(Criteria.where("id").gt(id));
		}
		query.with(new Sort(Sort.Direction.ASC, "id"));
		query.limit(limit);
		return mongoTemplate.find(query,EmailSentLogDocument.class);
	}
	
	public List<EmailSentLogDocument> rsLogfindByCrit(String critKey, String critVal, String lastId, int limit){
		Query query = new Query();
		
		if(StringUtils.isNotBlank(lastId) && !"id".equalsIgnoreCase(critKey)) {
			query.addCriteria(Criteria.where("id").gt(lastId));
		}
		if(StringUtils.isNotBlank(critKey) && StringUtils.isNotBlank(critVal)) {
			query.addCriteria(Criteria.where(critKey).is(critVal));
		}
		query.with(new Sort(Sort.Direction.ASC, "id"));
		query.limit(limit);
		return mongoTemplate.find(query,EmailSentLogDocument.class);
	}	
	
	public List<ReportingTransDocument> findAllLimitById(String id, int limit){
		Query query = new Query();
		if(StringUtils.isNotBlank(id)) {
			query.addCriteria(Criteria.where("id").gt(id));
		}
		query.with(new Sort(Sort.Direction.ASC, "id"));
		query.limit(limit);
		return mongoTemplate.find(query,ReportingTransDocument.class);
	}
	
	public List<ReportingTransDocument> findByCrit(String critKey, String critVal, String lastId, int limit){
		Query query = new Query();
		
		if(StringUtils.isNotBlank(lastId) && !"id".equalsIgnoreCase(critKey)) {
			query.addCriteria(Criteria.where("id").gt(lastId));
		}
		if(StringUtils.isNotBlank(critKey) && StringUtils.isNotBlank(critVal)) {
			query.addCriteria(Criteria.where(critKey).is(critVal));
		}
		query.with(new Sort(Sort.Direction.ASC, "id"));
		query.limit(limit);
		return mongoTemplate.find(query,ReportingTransDocument.class);
	}	
	/*----------------- ng services-------------*/

}