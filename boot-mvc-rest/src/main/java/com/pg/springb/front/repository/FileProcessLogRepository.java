package com.pg.springb.front.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.pg.springb.front.documents.FileProcessLogDocument;

@Repository
public interface FileProcessLogRepository extends MongoRepository<FileProcessLogDocument, String>{
	
	@Override
	public List<FileProcessLogDocument> findAll();
	
	@Query(value = "{ 'fileName' : ?0 }")
	List<FileProcessLogDocument> findAllByFileName(String fileName);
	
}
