package com.jasekraft.splendor.mvc.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.jasekraft.splendor.mvc.models.Noble;
import com.jasekraft.splendor.mvc.models.Token;

public class NobleCostService {
	private final TokenService tokenServ;
	private final NobleService nobleServ;
	
    @Autowired
    public NobleCostService(TokenService tokenServ, NobleService nobleServ) {
        this.tokenServ = tokenServ;
        this.nobleServ = nobleServ;
    }
    
	public void addRelation(Long tokenId,Long nobleId) {
		Token thisToken = tokenServ.find(tokenId);
	    Noble thisNoble = nobleServ.find(nobleId);
	    thisToken.getNobles().add(thisNoble);
	    tokenServ.update(thisToken);	
	}
	
	public void removeRelation(Long tokenId, Long nobleId) {
		Token thisToken = tokenServ.find(tokenId);
	    Noble thisNoble = nobleServ.find(nobleId);
	    thisToken.getNobles().remove(thisNoble);
	    tokenServ.update(thisToken);	
	}
}
