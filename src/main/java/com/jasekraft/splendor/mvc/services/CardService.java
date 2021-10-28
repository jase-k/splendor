package com.jasekraft.splendor.mvc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jasekraft.splendor.mvc.models.Card;
import com.jasekraft.splendor.mvc.models.Deck;
import com.jasekraft.splendor.mvc.models.Player;
import com.jasekraft.splendor.mvc.repositories.CardRepository;

@Service
public class CardService {
	private final CardRepository cardRepo;
	
	public CardService(CardRepository cardRepo) {
		this.cardRepo = cardRepo;
	}
	
    //Unique
	public List<Card> cardsByDeck(Deck deck){
		return cardRepo.findAllByDecks(deck);
	}
	
	public List<Card> cardsByPlayers(Player player){
		return cardRepo.findAllByPlayers(player);
	}
	
	//CRUD
    public List<Card> all() {
        return cardRepo.findAll();
    }

    public Card create(Card p) {
        return cardRepo.save(p);
    }

    public Card find(Long id) {
        Optional<Card> optionalCard = cardRepo.findById(id);
        if(optionalCard.isPresent()) {
            return optionalCard.get();
        } else {
            return null;
        }
    }
    
    public void delete(long id) {
    	cardRepo.deleteById(id);
    }
    
    public Card update(Card card) {
    	Optional<Card> optionalCard = cardRepo.findById(card.getId());
    	if(optionalCard.isPresent()) {
    		cardRepo.save(card);
    		return card;
    	}
    	else {
    		return null;
    	}
    }
    
    public void init() {
    	Card card;
    	//Green Deck (IDs 1-40)
    	//Green deck - Loop through each token type
    	for(int i = 0; i<5; i++) {
	    	for(int j = 0; j<7; j++) {
	    		card = new Card(0);
	    		cardRepo.save(card);
	    	}
	    	card = new Card(1);
	    	cardRepo.save(card);
    	}
    	//Yellow Deck (IDs 41-70)
    	//Yellow deck - Loop through each token type
    	for(int i = 0; i<5; i++) {
	    	card = new Card(1);
	    	cardRepo.save(card);
	    	card = new Card(1);
	    	cardRepo.save(card);
	    	for(int j = 0; j<3; j++) {
	    		card = new Card(2);
	    		cardRepo.save(card);
	    	}
	    	card = new Card(3);
	    	cardRepo.save(card);
    	}
    	//Blue Deck (IDs 71-90)
    	//Blue deck - Loop through each token type
    	for(int i = 0; i<5; i++) {
	    	card = new Card(3);
	    	cardRepo.save(card);
	    	for(int j = 0; j<2; j++) {
	    		card = new Card(4);
	    		cardRepo.save(card);
	    	}
	    	card = new Card(5);
	    	cardRepo.save(card);
    	}
    	
    }
    
}