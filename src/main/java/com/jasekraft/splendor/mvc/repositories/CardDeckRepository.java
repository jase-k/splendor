package com.jasekraft.splendor.mvc.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jasekraft.splendor.mvc.models.CardDeck;

@Repository
public interface CardDeckRepository extends CrudRepository<CardDeck, Long>{
	 
	// this method retrieves all the carddeck rows from the database
	List<CardDeck> findAll();
	
	 	 
}