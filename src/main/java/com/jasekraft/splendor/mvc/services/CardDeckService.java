package com.jasekraft.splendor.mvc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jasekraft.splendor.mvc.models.CardDeck;
import com.jasekraft.splendor.mvc.repositories.CardDeckRepository;

@Service
public class CardDeckService {
	private final CardDeckRepository carddeckRepo;
	
	public CardDeckService(CardDeckRepository carddeckRepo) {
		this.carddeckRepo = carddeckRepo;
	}

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
}