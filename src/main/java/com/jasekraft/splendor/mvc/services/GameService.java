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
import com.jasekraft.splendor.mvc.repositories.CardDeckRepository;
import com.jasekraft.splendor.mvc.repositories.GameRepository;

@Service
public class GameService {
	private final GameRepository gameRepo;
	private final TokenService tokenServ;
	private final NobleService nobleServ;
	private final CardDeckService cardDeckServ;
	
	private int totalTokens = 7; // 3 players 5 2 players 4
	private int totalNobles = 4; // players + 1
	
	public GameService(GameRepository gameRepo, TokenService tokenServ, 
			NobleService nobleServ, CardDeckService cardDeckServ) {
		this.gameRepo = gameRepo;
		this.tokenServ = tokenServ;
        this.nobleServ = nobleServ;
        this.cardDeckServ = cardDeckServ;
	}
	//Unique
	public void initialize(Game game) {
		// Sets things based on players
		int playerSize = game.getPlayers().size();
		for(int i = 0; i< playerSize;i++) {
			game.getPlayers().get(i).setTurn(i);
		}
		totalNobles = playerSize+1;
		if(playerSize == 3) totalTokens = 5;
		if(playerSize == 2) totalTokens = 4;
		game.setTurn(1);
		//Adds 3 decks for game and shuffle them 
		// Initializes cards in deck;
		cardDeckServ.init(game);
		//Add nobles to the game
		nobleServ.init(game, totalNobles);
		//Add tokens to the game
		tokenServ.init(game, totalTokens);
		update(game);
		//gameRepo.save(game);
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
	

}