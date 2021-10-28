package com.jasekraft.splendor.mvc.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jasekraft.splendor.mvc.models.Card;
import com.jasekraft.splendor.mvc.models.CardDeck;
import com.jasekraft.splendor.mvc.models.Deck;

@Repository
public interface CardDeckRepository extends CrudRepository<CardDeck, Long>{
	 
	// this method retrieves all the carddeck rows from the database
	List<CardDeck> findAll();
	// gets CardDecks by deck
	List<CardDeck> findByDeck(Deck deck);
	// gets CardDecks by deck
	Optional<CardDeck> findByDeckAndCard(Deck deck, Card card);
}