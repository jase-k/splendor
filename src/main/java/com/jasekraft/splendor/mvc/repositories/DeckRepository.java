package com.jasekraft.splendor.mvc.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jasekraft.splendor.mvc.models.Deck;

@Repository
public interface DeckRepository extends CrudRepository<Deck, Long>{
	 
	// this method retrieves all the decks from the database
	 List<Deck> findAll();
	 
	 	 
}