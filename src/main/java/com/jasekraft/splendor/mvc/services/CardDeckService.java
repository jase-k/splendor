package com.jasekraft.splendor.mvc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jasekraft.splendor.mvc.models.CardDeck;
import com.jasekraft.splendor.mvc.models.Deck;
import com.jasekraft.splendor.mvc.models.Card;
import com.jasekraft.splendor.mvc.repositories.CardDeckRepository;

@Service
public class CardDeckService {
	private final CardDeckRepository carddeckRepo;
	private final CardService cardServ;
	private final DeckService deckServ;
	
    @Autowired
	public CardDeckService(CardDeckRepository carddeckRepo,
			CardService cardServ, DeckService deckServ) {
		this.carddeckRepo = carddeckRepo;
		this.cardServ = cardServ;
	    this.deckServ = deckServ;
	}
    
    //CRUD
    public List<CardDeck> all() {
        return carddeckRepo.findAll();
    }

    public CardDeck create(CardDeck p) {
        return carddeckRepo.save(p);
    }

    public CardDeck find(Long id) {
        Optional<CardDeck> optionalCardDeck = carddeckRepo.findById(id);
        if(optionalCardDeck.isPresent()) {
            return optionalCardDeck.get();
        } else {
            return null;
        }
    }

    public void delete(long id) {
    	carddeckRepo.deleteById(id);
    }
    
    public CardDeck update(CardDeck carddeck) {
    	Optional<CardDeck> optionalCardDeck = carddeckRepo.findById(carddeck.getId());
    	if(optionalCardDeck.isPresent()) {
    		carddeckRepo.save(carddeck);
    		return carddeck;
    	}
    	else {
    		return null;
    	}
    }
    
    // Add remove relations
	public void addRelation(Long cardId,Long deckId) {
		Card thisCard = cardServ.find(cardId);
	    Deck thisDeck = deckServ.find(deckId);
	    thisCard.getDecks().add(thisDeck);
	    cardServ.update(thisCard);	
	}
	
	public void removeRelation(Long cardId, Long deckId) {
		Card thisCard = cardServ.find(cardId);
	    Deck thisDeck = deckServ.find(deckId);
	    thisCard.getDecks().remove(thisDeck);
	    cardServ.update(thisCard);	
	}
    
}