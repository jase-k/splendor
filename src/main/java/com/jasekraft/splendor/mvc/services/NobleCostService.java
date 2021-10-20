package com.jasekraft.splendor.mvc.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jasekraft.splendor.mvc.models.Noble;
import com.jasekraft.splendor.mvc.models.Token;

@Service
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
	// Adds nobles to DB
	public void init() {
		//List<Token> tokens = tokenServ.all();
		//A1 Mary Stuart 4 ruby 4 emerald
		Noble nobleA1 = new Noble(3);	
		nobleA1.getTokens().add(tokenServ.find("ruby"));
		nobleA1.getTokens().add(tokenServ.find("ruby"));
		nobleA1.getTokens().add(tokenServ.find("ruby"));
		nobleA1.getTokens().add(tokenServ.find("ruby"));
		nobleA1.getTokens().add(tokenServ.find("emerald"));
		nobleA1.getTokens().add(tokenServ.find("emerald"));
		nobleA1.getTokens().add(tokenServ.find("emerald"));
		nobleA1.getTokens().add(tokenServ.find("emerald"));
		nobleServ.create(nobleA1);
		//A2 Charles Quint (Karl V for germans) 3 ruby 3 onyx 3 diamond
		Noble nobleA2 = new Noble(3);
		nobleA2.getTokens().add(tokenServ.find("ruby"));
		nobleA2.getTokens().add(tokenServ.find("ruby"));
		nobleA2.getTokens().add(tokenServ.find("ruby"));
		nobleA2.getTokens().add(tokenServ.find("onyx"));
		nobleA2.getTokens().add(tokenServ.find("onyx"));
		nobleA2.getTokens().add(tokenServ.find("onyx"));
		nobleA2.getTokens().add(tokenServ.find("diamond"));
		nobleA2.getTokens().add(tokenServ.find("diamond"));
		nobleA2.getTokens().add(tokenServ.find("diamond"));
		nobleServ.create(nobleA2);
		//A3 Macchiavelli 4 sapphire 4 diamond
		Noble nobleA3 = new Noble(3);
		nobleA3.getTokens().add(tokenServ.find("sapphire"));
		nobleA3.getTokens().add(tokenServ.find("sapphire"));
		nobleA3.getTokens().add(tokenServ.find("sapphire"));
		nobleA3.getTokens().add(tokenServ.find("sapphire"));
		nobleA3.getTokens().add(tokenServ.find("diamond"));
		nobleA3.getTokens().add(tokenServ.find("diamond"));
		nobleA3.getTokens().add(tokenServ.find("diamond"));
		nobleA3.getTokens().add(tokenServ.find("diamond"));
		nobleServ.create(nobleA3);
		//B1 Isabel of Castille 4 onyx 4 diamond
		Noble nobleA4 = new Noble(3);
		nobleA4.getTokens().add(tokenServ.find("onyx"));
		nobleA4.getTokens().add(tokenServ.find("onyx"));
		nobleA4.getTokens().add(tokenServ.find("onyx"));
		nobleA4.getTokens().add(tokenServ.find("onyx"));
		nobleA4.getTokens().add(tokenServ.find("diamond"));
		nobleA4.getTokens().add(tokenServ.find("diamond"));
		nobleA4.getTokens().add(tokenServ.find("diamond"));
		nobleA4.getTokens().add(tokenServ.find("diamond"));
		nobleServ.create(nobleA4);
		//B2 Suleiman the Magnificent 4 sapphire 4 emerald
		Noble nobleA5 = new Noble(3);
		nobleA5.getTokens().add(tokenServ.find("sapphire"));
		nobleA5.getTokens().add(tokenServ.find("sapphire"));
		nobleA5.getTokens().add(tokenServ.find("sapphire"));
		nobleA5.getTokens().add(tokenServ.find("sapphire"));
		nobleA5.getTokens().add(tokenServ.find("emerald"));
		nobleA5.getTokens().add(tokenServ.find("emerald"));
		nobleA5.getTokens().add(tokenServ.find("emerald"));
		nobleA5.getTokens().add(tokenServ.find("emerald"));
		nobleServ.create(nobleA5);
		//B3 Catherine of Medicis 3 ruby 3 emerald 3 sapphire
		Noble nobleA6 = new Noble(3);
		nobleA6.getTokens().add(tokenServ.find("ruby"));
		nobleA6.getTokens().add(tokenServ.find("ruby"));
		nobleA6.getTokens().add(tokenServ.find("ruby"));
		nobleA6.getTokens().add(tokenServ.find("emerald"));
		nobleA6.getTokens().add(tokenServ.find("emerald"));
		nobleA6.getTokens().add(tokenServ.find("emerald"));
		nobleA6.getTokens().add(tokenServ.find("sapphire"));
		nobleA6.getTokens().add(tokenServ.find("sapphire"));
		nobleA6.getTokens().add(tokenServ.find("sapphire"));
		nobleServ.create(nobleA6);
		//C1 Anne of Brittany 3 diamond 3 emerald 3 sapphire
		Noble nobleA7 = new Noble(3);
		nobleA7.getTokens().add(tokenServ.find("diamond"));
		nobleA7.getTokens().add(tokenServ.find("diamond"));
		nobleA7.getTokens().add(tokenServ.find("diamond"));
		nobleA7.getTokens().add(tokenServ.find("emerald"));
		nobleA7.getTokens().add(tokenServ.find("emerald"));
		nobleA7.getTokens().add(tokenServ.find("emerald"));
		nobleA7.getTokens().add(tokenServ.find("sapphire"));
		nobleA7.getTokens().add(tokenServ.find("sapphire"));
		nobleA7.getTokens().add(tokenServ.find("sapphire"));
		nobleServ.create(nobleA7);
		//C2 Henri VIII 4 onyx 4 ruby
		Noble nobleA8 = new Noble(3);
		nobleA8.getTokens().add(tokenServ.find("onyx"));
		nobleA8.getTokens().add(tokenServ.find("onyx"));
		nobleA8.getTokens().add(tokenServ.find("onyx"));
		nobleA8.getTokens().add(tokenServ.find("onyx"));
		nobleA8.getTokens().add(tokenServ.find("ruby"));
		nobleA8.getTokens().add(tokenServ.find("ruby"));
		nobleA8.getTokens().add(tokenServ.find("ruby"));
		nobleA8.getTokens().add(tokenServ.find("ruby"));
		nobleServ.create(nobleA8);
		//D1 Elisabeth of Austria 3 sapphire 3 onyx 3 diamond
		Noble nobleA9 = new Noble(3);
		nobleA9.getTokens().add(tokenServ.find("sapphire"));
		nobleA9.getTokens().add(tokenServ.find("sapphire"));
		nobleA9.getTokens().add(tokenServ.find("sapphire"));
		nobleA9.getTokens().add(tokenServ.find("onyx"));
		nobleA9.getTokens().add(tokenServ.find("onyx"));
		nobleA9.getTokens().add(tokenServ.find("onyx"));
		nobleA9.getTokens().add(tokenServ.find("diamond"));
		nobleA9.getTokens().add(tokenServ.find("diamond"));
		nobleA9.getTokens().add(tokenServ.find("diamond"));
		nobleServ.create(nobleA9);
		//D2 Francis I of France 3 ruby 3 onyx 3 emerald
		Noble nobleA10 = new Noble(3);	
		nobleA10.getTokens().add(tokenServ.find("ruby"));
		nobleA10.getTokens().add(tokenServ.find("ruby"));
		nobleA10.getTokens().add(tokenServ.find("ruby"));
		nobleA10.getTokens().add(tokenServ.find("onyx"));
		nobleA10.getTokens().add(tokenServ.find("onyx"));
		nobleA10.getTokens().add(tokenServ.find("onyx"));
		nobleA10.getTokens().add(tokenServ.find("emerald"));
		nobleA10.getTokens().add(tokenServ.find("emerald"));
		nobleA10.getTokens().add(tokenServ.find("emerald"));
		nobleServ.create(nobleA10);	
	}
}
