package com.pg.springb.front.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.pg.springb.front.documents.ResendConfigDocument;

@Repository
public interface ResendConfigRepository extends MongoRepository<ResendConfigDocument, String> {

	@Override
	public List<ResendConfigDocument> findAll();
	
	@Query(value= "{'status' : ?0 }")
	public List<ResendConfigDocument> findAllByStatus(String status);
	
	@Query(value= "{'clientId' : ?0 }")
	public List<ResendConfigDocument> findAllByClientId(String clientId);
}
