package com.jasekraft.splendor.mvc.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jasekraft.splendor.mvc.models.Token;

@Repository
public interface TokenRepository extends CrudRepository<Token, Long>{
	 
	// this method retrieves all the tokens from the database
	 List<Token> findAll();
	 
	 	 
}