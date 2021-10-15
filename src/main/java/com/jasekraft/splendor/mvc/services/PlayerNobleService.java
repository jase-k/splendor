package com.jasekraft.splendor.mvc.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.jasekraft.splendor.mvc.models.Noble;
import com.jasekraft.splendor.mvc.models.Player;

public class PlayerNobleService {
	private final PlayerService playerServ;
	private final NobleService nobleServ;
	
    @Autowired
    public PlayerNobleService(PlayerService playerServ, NobleService nobleServ) {
        this.playerServ = playerServ;
        this.nobleServ = nobleServ;
    }
    
	public void addRelation(Long playerId,Long nobleId) {
		Player thisPlayer = playerServ.find(playerId);
	    Noble thisNoble = nobleServ.find(nobleId);
	    thisPlayer.getNobles().add(thisNoble);
	    playerServ.update(thisPlayer);	
	}
	
	public void removeRelation(Long playerId, Long nobleId) {
		Player thisPlayer = playerServ.find(playerId);
	    Noble thisNoble = nobleServ.find(nobleId);
	    thisPlayer.getNobles().remove(thisNoble);
	    playerServ.update(thisPlayer);	
	}
}
