package com.jasekraft.splendor.mvc.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jasekraft.splendor.mvc.models.Card;

@Repository
public interface CardRepository extends CrudRepository<Card, Long>{
	 
	// this method retrieves all the cards from the database
	 List<Card> findAll();
	 
	 	 
}