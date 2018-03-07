package com.pg.springb.front.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.pg.springb.front.documents.ClientEmailProcessDocument;


@Repository
public interface ClientEmailProcessRepository extends MongoRepository<ClientEmailProcessDocument,String>{
	
	List<ClientEmailProcessDocument> findAll();
	
	@Query(value = "{ 'reportType' : 'INTERVAL' }")
	List<ClientEmailProcessDocument> findIntervalRptProcessList();
	
	@Query(value = "{ 'reportType' : 'DAILY' }")
	List<ClientEmailProcessDocument> findDailyRptProcessList();
	
	
}
