package com.jasekraft.splendor.mvc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jasekraft.splendor.mvc.models.Game;
import com.jasekraft.splendor.mvc.models.GamePlayer;
import com.jasekraft.splendor.mvc.models.Player;
import com.jasekraft.splendor.mvc.repositories.GamePlayerRepository;

@Service
public class GamePlayerService {
	  private final GamePlayerRepository gamePlayerRepo;
	  private final GameService gameServ;
	  private final PlayerService playerServ;
	    
	    @Autowired
	    public GamePlayerService(GamePlayerRepository gamePlayerRepo,
	    		GameService gameServ,
	    		PlayerService playerServ) {
	        this.gamePlayerRepo = gamePlayerRepo;
	        this.gameServ = gameServ;
	        this.playerServ = playerServ;
	    }
	    
	    //Unique
		public List<GamePlayer> gamePlayersByGame(Game game){
			return gamePlayerRepo.findAllByGame(game);
		}
		
		//CRUD
	    public List<GamePlayer> all() {
	        return gamePlayerRepo.findAll();
	    }

	    public GamePlayer create(GamePlayer p) {
	        return gamePlayerRepo.save(p);
	    }

	    public GamePlayer find(Long id) {
	        Optional<GamePlayer> optionalGamePlayer = gamePlayerRepo.findById(id);
	        if(optionalGamePlayer.isPresent()) {
	            return optionalGamePlayer.get();
	        } else {
	            return null;
	        }
	    }

	    public void delete(long id) {
	    	gamePlayerRepo.deleteById(id);
	    }
	    
	    public GamePlayer update(GamePlayer deck) {
	    	Optional<GamePlayer> optionalGamePlayer = gamePlayerRepo.findById(deck.getId());
	    	if(optionalGamePlayer.isPresent()) {
	    		gamePlayerRepo.save(deck);
	    		return deck;
	    	}
	    	else {
	    		return null;
	    	}
	    }
	    
	    // Add relation 
	    // This happens at start of game + also need to set turn counter to 0
		public void addRelation(Long gameId,Long playerId) {
			Game thisGame = gameServ.find(gameId);
		    Player thisPlayer = playerServ.find(playerId);
		    thisGame.getPlayers().add(thisPlayer);
		    gameServ.update(thisGame);	
		}
		public void removeRelation(Long gameId, Long playerId) {
			Game thisGame = gameServ.find(gameId);
		    Player thisPlayer = playerServ.find(playerId);
		    thisGame.getPlayers().remove(thisPlayer);
		    gameServ.update(thisGame);	
		}
}
