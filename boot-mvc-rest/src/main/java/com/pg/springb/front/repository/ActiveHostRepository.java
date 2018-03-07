package com.pg.springb.front.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pg.springb.front.documents.ActiveHostDocument;

public interface ActiveHostRepository extends MongoRepository<ActiveHostDocument, String> {

	@Override
	public List<ActiveHostDocument> findAll();
}
