package com.jasekraft.splendor.mvc.services;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.jasekraft.splendor.mvc.models.Card;
import com.jasekraft.splendor.mvc.models.Deck;
import com.jasekraft.splendor.mvc.models.Game;
import com.jasekraft.splendor.mvc.models.Noble;
import com.jasekraft.splendor.mvc.models.Token;
import com.jasekraft.splendor.mvc.repositories.GameRepository;

@Service
public class GameService {
	private final GameRepository gameRepo;
	private final TokenService tokenServ;
	private final NobleService nobleServ;
	private final DeckService deckServ;
	private final CardService cardServ;
	private final CardDeckService cardDeckServ;
	
	private final int totalTokens = 7;
	private final int goldTokens = 5;
	private final int totalNobles = 4;
	
	public GameService(GameRepository gameRepo, TokenService tokenServ, 
			NobleService nobleServ, DeckService deckServ, CardService cardServ,
			CardDeckService cardDeckServ) {
		this.gameRepo = gameRepo;
		this.tokenServ = tokenServ;
        this.nobleServ = nobleServ;
        this.deckServ = deckServ;
        this.cardServ = cardServ;
        this.cardDeckServ = cardDeckServ;
	}
	//Unique
	public void initialize(Game game) {
		//Adds 3 decks for game and shuffle them 
		List<Deck> decks = deckServ.all();
		for(Deck d : decks) {
			d.setCards(shuffleCards(d.getCards()));
			List<Card> cards = d.getCards();
			for(int i = 0 ; i<cards.size(); i++) {
				Card thisCard = cardServ.find(cards.get(i).getId());
			    d.getCards().add(thisCard);
			    // adds relation card to deck
			    cardDeckServ.find(d, thisCard).setPosition(i);
			}
			//adds relation deck to cards
			game.getDecks().add(d);
		}
		//Add nobles to the game
		List<Noble> nobles = shuffleNobles(nobleServ.all());
		for(int i = 0; i<totalNobles;i++) {
			Noble thisNoble = nobleServ.find(nobles.get(i).getId());
		    game.getNobles().add(thisNoble);
		}
		//Add tokens to the game
		List<Token> tokens = tokenServ.all();
		for(Token t : tokens) {
			if(t.getName().equals("gold"))
				for(int i = 0; i<goldTokens; i++) {
					Token thisToken = tokenServ.find(t.getId());
					game.getTokens().add(thisToken);
				}
			else
				for(int i = 0; i<totalTokens; i++) {
					Token thisToken = tokenServ.find(t.getId());
					game.getTokens().add(thisToken);
				}			
		}
		
	}
	//CRUD
    public List<Game> all() {
        return gameRepo.findAll();
    }

    public Game create(Game p) {
        return gameRepo.save(p);
    }

    public Game find(Long id) {
        Optional<Game> optionalGame = gameRepo.findById(id);
        if(optionalGame.isPresent()) {
            return optionalGame.get();
        } else {
            return null;
        }
    }

    public void delete(long id) {
    	gameRepo.deleteById(id);
    }
    
    public Game update(Game game) {
    	Optional<Game> optionalGame = gameRepo.findById(game.getId());
    	if(optionalGame.isPresent()) {
    		gameRepo.save(game);
    		return game;
    	}
    	else {
    		return null;
    	}
    }
    
    // Add remove tokens
	public void addToken(Long tokenId,Long gameId) {
		Token thisToken = tokenServ.find(tokenId);
	    Game thisGame = find(gameId);
	    thisGame.getTokens().add(thisToken);
	    update(thisGame);	
	}
	
	public void removeToken(Long tokenId,Long gameId) {
		Token thisToken = tokenServ.find(tokenId);
	    Game thisGame = find(gameId);
	    thisGame.getTokens().remove(thisToken);
	    update(thisGame);	
	}
	
    // Add remove nobles
	public void addNoble(Long nobleId,Long gameId) {
		Noble thisNoble = nobleServ.find(nobleId);
	    Game thisGame = find(gameId);
	    thisGame.getNobles().add(thisNoble);
	    update(thisGame);	
	}
	
	public void removeNoble(Long nobleId,Long gameId) {
		Noble thisNoble = nobleServ.find(nobleId);
	    Game thisGame = find(gameId);
	    thisGame.getNobles().remove(thisNoble);
	    update(thisGame);	
	}
	
	// Shuffle Algorithm (modified Fisher-Yates)
	public List<Noble> shuffleNobles(List<Noble> nobles){
		Noble fake;
		int randNum;
		for(int i = nobles.size()-1;i>0;i--) {
			randNum = ThreadLocalRandom.current().nextInt(0, i + 1);
			fake = nobles.get(i);
			nobles.set(i, nobles.get(randNum));
			nobles.set(randNum, fake);
		}
		return nobles;
	}
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