package com.pg.springb.front.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.pg.springb.front.documents.ClientConfigDocument;

@Repository
public interface ClientConfigRepository extends MongoRepository<ClientConfigDocument,String>{
	
	
	List<ClientConfigDocument> findAll();
	
	@Query(value = "{'active': 'Y' }")
	List<ClientConfigDocument> findAllActive();
	
	@Query(value = "{ 'clientId': ?0 }")
	List<ClientConfigDocument> getClientConfigbyClientId(String clientId);
	
	@Query(value = "{ 'clientId': ?0, 'active': 'Y' }")
	List<ClientConfigDocument> getActiveClientsGFCID(String clientId);
	
	@Query(value = "{'active': ?0 }")
	List<ClientConfigDocument> findAllbyStatus(String active);
	
	@Query(value="{'active': ?0 }", delete = true)
	Long deleteAllByStatus(String active);
}
