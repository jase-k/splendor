package com.jasekraft.splendor.mvc.services;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jasekraft.splendor.mvc.models.Card;
import com.jasekraft.splendor.mvc.models.CardCost;
import com.jasekraft.splendor.mvc.models.CardDeck;
import com.jasekraft.splendor.mvc.models.Deck;
import com.jasekraft.splendor.mvc.models.Game;
import com.jasekraft.splendor.mvc.repositories.CardDeckRepository;
import com.jasekraft.splendor.mvc.repositories.GameRepository;

@Service
public class CardDeckService {
	private final CardDeckRepository cardDeckRepo;
	private final CardService cardServ;
	private final DeckService deckServ;
	
    @Autowired
	public CardDeckService(CardDeckRepository cardDeckRepo,
			CardService cardServ, DeckService deckServ) {
		this.cardDeckRepo = cardDeckRepo;
		this.cardServ = cardServ;
	    this.deckServ = deckServ;
	}
    
    //CRUD
    public List<CardDeck> all() {
        return cardDeckRepo.findAll();
    }

    public CardDeck create(CardDeck p) {
        return cardDeckRepo.save(p);
    }

    public CardDeck find(Long id) {
        Optional<CardDeck> optionalCardDeck = cardDeckRepo.findById(id);
        if(optionalCardDeck.isPresent()) {
            return optionalCardDeck.get();
        } else {
            return null;
        }
    }
    
    public CardDeck find(Deck deck, Card card) {
        Optional<CardDeck> optionalCardDeck = cardDeckRepo.findByDeckAndCard(deck, card);
        if(optionalCardDeck.isPresent()) {
            return optionalCardDeck.get();
        } else {
            return null;
        }
    }

    public void delete(long id) {
    	cardDeckRepo.deleteById(id);
    }
    
    public CardDeck update(CardDeck cardDeck) {
    	Optional<CardDeck> optionalCardDeck = cardDeckRepo.findById(cardDeck.getId());
    	if(optionalCardDeck.isPresent()) {
    		cardDeckRepo.save(cardDeck);
    		return cardDeck;
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
	
	// Initialize all cards
	public void init(Game game) {
		List<Card> greenCards = shuffleCards(cardServ.all().subList(0, 40));
		Deck deckG = new Deck("green", 0, game, greenCards);
		deckServ.create(deckG);
		List<Card> yellowCards = shuffleCards(cardServ.all().subList(40, 70));
		Deck deckR = new Deck("yellow", 0, game, yellowCards);
		deckServ.create(deckR);
		List<Card> blueCards = shuffleCards(cardServ.all().subList(70, 90));
		Deck deckB = new Deck("blue", 0, game, blueCards);
		deckServ.create(deckB);
		List<Deck> decks = game.getDecks();
		decks.add(deckG);
		decks.add(deckR);
		decks.add(deckB);
		/*
		for(Deck d : decks) {
			List<Card> cards = d.getCards();
			for(int i = 0 ; i<cards.size(); i++) {
				CardDeck cD = new CardDeck(i, cards.get(i), d);
				cardDeckRepo.save(cD);
			}
		}*/
		//gameRepo.save(game);
	}
	
	// Shuffle Algorithm (modified Fisher-Yates)
	public List<Card> shuffleCards(List<Card> cards){
		Card fake;
		int randNum;
		for(int i = cards.size()-1;i>0;i--) {
			randNum = ThreadLocalRandom.current().nextInt(0, i + 1);
			fake = cards.get(i);
			cards.set(i, cards.get(randNum));
			cards.set(randNum, fake);
		}
		return cards;
	}
    
}