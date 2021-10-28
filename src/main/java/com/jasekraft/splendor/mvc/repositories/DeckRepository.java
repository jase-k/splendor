package com.jasekraft.splendor.mvc.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jasekraft.splendor.mvc.models.Deck;
import com.jasekraft.splendor.mvc.models.Game;

@Repository
public interface DeckRepository extends CrudRepository<Deck, Long>{
	 
	// this method retrieves all the decks from the database
	List<Deck> findAll();
	// Retrieves all decks from a specific game
	List<Deck> findAllByGame(Game game);
}