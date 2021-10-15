package com.jasekraft.splendor.mvc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jasekraft.splendor.mvc.models.Deck;
import com.jasekraft.splendor.mvc.repositories.DeckRepository;

@Service
public class DeckService {
	private final DeckRepository deckRepo;
	
	public DeckService(DeckRepository deckRepo) {
		this.deckRepo = deckRepo;
	}

    public List<Deck> all() {
    	
        return deckRepo.findAll();
    }

    public Deck create(Deck p) {
        return deckRepo.save(p);
    }

    public Deck find(Long id) {
        Optional<Deck> optionalDeck = deckRepo.findById(id);
        if(optionalDeck.isPresent()) {
            return optionalDeck.get();
        } else {
            return null;
        }
    }

    
    public void delete(long id) {
    	deckRepo.deleteById(id);
    }
    
    public Deck update(Deck deck) {
    	Optional<Deck> optionalDeck = deckRepo.findById(deck.getId());
    	if(optionalDeck.isPresent()) {
    		deckRepo.save(deck);
    		return deck;
    	}
    	else {
    		return null;
    	}
    }
}
