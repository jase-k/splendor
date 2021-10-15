package com.jasekraft.splendor.mvc.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jasekraft.splendor.mvc.models.Card;
import com.jasekraft.splendor.mvc.models.Deck;
import com.jasekraft.splendor.mvc.models.Player;

@Repository
public interface CardRepository extends CrudRepository<Card, Long>{
	 //this method retrieves all the cards from the database
	 List<Card> findAll();
	 // Retrieves all cards in a certain deck.
	 List<Card> findAllByDecks(Deck deck);
	 // Retrieves all cards from a player.
	 List<Card> findAllByPlayers(Player player);
	 // Retrieves the remainder of cards. 
	 //If not in deck and if not in player then in game
	 //List<Card> findByPlayersNotContainsAndDecksNotContains(Player player, Deck deck);
}