package com.pg.springb.front.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.pg.springb.front.documents.ReportFieldsDocument;

@Repository
public interface ReportFieldsRepository extends MongoRepository<ReportFieldsDocument, String>{
	
	@Override
	public List<ReportFieldsDocument> findAll();
}
