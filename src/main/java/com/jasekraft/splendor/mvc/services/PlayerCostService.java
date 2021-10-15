package com.jasekraft.splendor.mvc.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.jasekraft.splendor.mvc.models.Player;
import com.jasekraft.splendor.mvc.models.Token;

public class PlayerCostService {
	private final TokenService tokenServ;
	private final PlayerService playerServ;
	
    @Autowired
    public PlayerCostService(TokenService tokenServ, PlayerService playerServ) {
        this.tokenServ = tokenServ;
        this.playerServ = playerServ;
    }
    
	public void addRelation(Long tokenId,Long playerId) {
		Token thisToken = tokenServ.find(tokenId);
	    Player thisPlayer = playerServ.find(playerId);
	    thisToken.getPlayers().add(thisPlayer);
	    tokenServ.update(thisToken);	
	}
	
	public void removeRelation(Long tokenId, Long playerId) {
		Token thisToken = tokenServ.find(tokenId);
	    Player thisPlayer = playerServ.find(playerId);
	    thisToken.getPlayers().remove(thisPlayer);
	    tokenServ.update(thisToken);	
	}
}
