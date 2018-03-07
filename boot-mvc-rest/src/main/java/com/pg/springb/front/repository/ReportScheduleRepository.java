package com.pg.springb.front.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.pg.springb.front.documents.ReportScheduleDocument;

@Repository
public interface ReportScheduleRepository extends MongoRepository<ReportScheduleDocument,String>{
	
	List<ReportScheduleDocument> findAll();
	
	@Query(value="{'clientId' : ?0}")
	List<ReportScheduleDocument> findAllByClientId(String clientId);
	
	@Query(value = "{ 'intervalFile' : 'Y' }")
	List<ReportScheduleDocument> findIntervalRptCfgList();
	
	@Query(value = "{ 'dailyFile' : 'Y' }")
	List<ReportScheduleDocument> findDailyRptCfgList();
	
	@Query(value="{ 'intervalFile' : 'Y' , 'intervalJobStatus' : 'READY' }")
	List<ReportScheduleDocument> findAllByIntervalReady();
	
	@Query(value="{ 'dailyFile' : 'Y' , 'dailyJobStatus' : 'READY' }")
	List<ReportScheduleDocument> findAllByDailyReady();
}
