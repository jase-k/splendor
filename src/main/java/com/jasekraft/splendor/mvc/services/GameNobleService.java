package com.jasekraft.splendor.mvc.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.jasekraft.splendor.mvc.models.Game;
import com.jasekraft.splendor.mvc.models.Noble;

public class GameNobleService {
	private final GameService gameServ;
	private final NobleService nobleServ;
	
    @Autowired
    public GameNobleService(GameService gameServ, NobleService nobleServ) {
        this.gameServ = gameServ;
        this.nobleServ = nobleServ;
    }
    
	public void addRelation(Long gameId,Long nobleId) {
		Game thisGame = gameServ.find(gameId);
	    Noble thisNoble = nobleServ.find(nobleId);
	    thisGame.getNobles().add(thisNoble);
	    gameServ.update(thisGame);	
	}
	
	public void removeRelation(Long gameId, Long nobleId) {
		Game thisGame = gameServ.find(gameId);
	    Noble thisNoble = nobleServ.find(nobleId);
	    thisGame.getNobles().remove(thisNoble);
	    gameServ.update(thisGame);	
	}
}
