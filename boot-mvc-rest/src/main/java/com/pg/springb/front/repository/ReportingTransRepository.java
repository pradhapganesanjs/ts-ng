package com.pg.springb.front.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.pg.springb.front.documents.ReportingTransDocument;

@Repository
public interface ReportingTransRepository extends MongoRepository<ReportingTransDocument,String>{
	
	List<ReportingTransDocument> findAll();	
	
	@Query(value = "{ 'info.clientId' : ?0 }")
	List<ReportingTransDocument> findAllByClientId(String clientId);
	
	@Query(value = "{ 'info.clientId' : ?0, 'lastUpdatedTs':{ $gte: ?1 , $lte: ?2}}")
	List<ReportingTransDocument> findAllByClientIdnLastUpdatedTs(String clientId, long fromDate, long toDate);
	
	@Query(value = "{ 'status' : ?0, 'info.clientId' : ?1 }")	
	List<ReportingTransDocument> getIntervalTransactions(String status, String clientId);
	
	@Query(value = "{ '$or': [{'status':'REPORTABLE'},{'status':'NON_REPORTABLE'}] , 'info.clientId' : ?0 }")
	List<ReportingTransDocument> getIntervalTransactionsAll(String clientId);
	
	@Query(value = "{ 'info.clientId' : ?0, 'lastUpdatedTs':{ $lte: ?1 , $gt: ?2}}")
	List<ReportingTransDocument> getDailyTransactions(String clientId, long currDate, long ystrdayDate);

	@Query(value = "{ 'lastUpdatedTs':{ $lt: ?0 }}")
	List<ReportingTransDocument> getDeletableTransactions(long beyondDate);
	
}
