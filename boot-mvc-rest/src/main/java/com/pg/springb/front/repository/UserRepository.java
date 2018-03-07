package com.pg.springb.front.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.pg.springb.front.documents.UserDocument;

@Repository
public interface UserRepository extends MongoRepository<UserDocument,String>{
	
	@Override
	public List<UserDocument> findAll();
	
	@Query(value = "{ 'userId' : ?0 }")
	List<UserDocument> findAllByUserId(String userId);
}
