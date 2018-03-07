package com.pg.springb.front.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.pg.springb.front.documents.EmailSentLogDocument;

@Repository
public interface EmailSentLogRepository extends MongoRepository<EmailSentLogDocument, String>{

	@Override
	public List<EmailSentLogDocument> findAll();
	
	@Query(value = "{ 'emailSentTS':{ $gte: ?0 , $lte: ?1}}")
	public List<EmailSentLogDocument> getLastOneDayLogs(long fromDate, long toDate);
	
}
