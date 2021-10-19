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
		//A1 Mary Stuart 4 red 4 green
		Noble nobleA1 = new Noble(3);	
		nobleA1.getTokens().add(tokenServ.find("red"));
		nobleA1.getTokens().add(tokenServ.find("red"));
		nobleA1.getTokens().add(tokenServ.find("red"));
		nobleA1.getTokens().add(tokenServ.find("red"));
		nobleA1.getTokens().add(tokenServ.find("green"));
		nobleA1.getTokens().add(tokenServ.find("green"));
		nobleA1.getTokens().add(tokenServ.find("green"));
		nobleA1.getTokens().add(tokenServ.find("green"));
		nobleServ.create(nobleA1);
		//A2 Charles Quint (Karl V for germans) 3 red 3 black 3 white
		Noble nobleA2 = new Noble(3);
		nobleA2.getTokens().add(tokenServ.find("red"));
		nobleA2.getTokens().add(tokenServ.find("red"));
		nobleA2.getTokens().add(tokenServ.find("red"));
		nobleA2.getTokens().add(tokenServ.find("black"));
		nobleA2.getTokens().add(tokenServ.find("black"));
		nobleA2.getTokens().add(tokenServ.find("black"));
		nobleA2.getTokens().add(tokenServ.find("white"));
		nobleA2.getTokens().add(tokenServ.find("white"));
		nobleA2.getTokens().add(tokenServ.find("white"));
		nobleServ.create(nobleA2);
		//A3 Macchiavelli 4 blue 4 white
		Noble nobleA3 = new Noble(3);
		nobleA3.getTokens().add(tokenServ.find("blue"));
		nobleA3.getTokens().add(tokenServ.find("blue"));
		nobleA3.getTokens().add(tokenServ.find("blue"));
		nobleA3.getTokens().add(tokenServ.find("blue"));
		nobleA3.getTokens().add(tokenServ.find("white"));
		nobleA3.getTokens().add(tokenServ.find("white"));
		nobleA3.getTokens().add(tokenServ.find("white"));
		nobleA3.getTokens().add(tokenServ.find("white"));
		nobleServ.create(nobleA3);
		//B1 Isabel of Castille 4 black 4 white
		Noble nobleA4 = new Noble(3);
		nobleA4.getTokens().add(tokenServ.find("black"));
		nobleA4.getTokens().add(tokenServ.find("black"));
		nobleA4.getTokens().add(tokenServ.find("black"));
		nobleA4.getTokens().add(tokenServ.find("black"));
		nobleA4.getTokens().add(tokenServ.find("white"));
		nobleA4.getTokens().add(tokenServ.find("white"));
		nobleA4.getTokens().add(tokenServ.find("white"));
		nobleA4.getTokens().add(tokenServ.find("white"));
		nobleServ.create(nobleA4);
		//B2 Suleiman the Magnificent 4 blue 4 green
		Noble nobleA5 = new Noble(3);
		nobleA5.getTokens().add(tokenServ.find("blue"));
		nobleA5.getTokens().add(tokenServ.find("blue"));
		nobleA5.getTokens().add(tokenServ.find("blue"));
		nobleA5.getTokens().add(tokenServ.find("blue"));
		nobleA5.getTokens().add(tokenServ.find("green"));
		nobleA5.getTokens().add(tokenServ.find("green"));
		nobleA5.getTokens().add(tokenServ.find("green"));
		nobleA5.getTokens().add(tokenServ.find("green"));
		nobleServ.create(nobleA5);
		//B3 Catherine of Medicis 3 red 3 green 3 blue
		Noble nobleA6 = new Noble(3);
		nobleA6.getTokens().add(tokenServ.find("red"));
		nobleA6.getTokens().add(tokenServ.find("red"));
		nobleA6.getTokens().add(tokenServ.find("red"));
		nobleA6.getTokens().add(tokenServ.find("green"));
		nobleA6.getTokens().add(tokenServ.find("green"));
		nobleA6.getTokens().add(tokenServ.find("green"));
		nobleA6.getTokens().add(tokenServ.find("blue"));
		nobleA6.getTokens().add(tokenServ.find("blue"));
		nobleA6.getTokens().add(tokenServ.find("blue"));
		nobleServ.create(nobleA6);
		//C1 Anne of Brittany 3 white 3 green 3 blue
		Noble nobleA7 = new Noble(3);
		nobleA7.getTokens().add(tokenServ.find("white"));
		nobleA7.getTokens().add(tokenServ.find("white"));
		nobleA7.getTokens().add(tokenServ.find("white"));
		nobleA7.getTokens().add(tokenServ.find("green"));
		nobleA7.getTokens().add(tokenServ.find("green"));
		nobleA7.getTokens().add(tokenServ.find("green"));
		nobleA7.getTokens().add(tokenServ.find("blue"));
		nobleA7.getTokens().add(tokenServ.find("blue"));
		nobleA7.getTokens().add(tokenServ.find("blue"));
		nobleServ.create(nobleA7);
		//C2 Henri VIII 4 black 4 red
		Noble nobleA8 = new Noble(3);
		nobleA8.getTokens().add(tokenServ.find("black"));
		nobleA8.getTokens().add(tokenServ.find("black"));
		nobleA8.getTokens().add(tokenServ.find("black"));
		nobleA8.getTokens().add(tokenServ.find("black"));
		nobleA8.getTokens().add(tokenServ.find("red"));
		nobleA8.getTokens().add(tokenServ.find("red"));
		nobleA8.getTokens().add(tokenServ.find("red"));
		nobleA8.getTokens().add(tokenServ.find("red"));
		nobleServ.create(nobleA8);
		//D1 Elisabeth of Austria 3 blue 3 black 3 white
		Noble nobleA9 = new Noble(3);
		nobleA9.getTokens().add(tokenServ.find("blue"));
		nobleA9.getTokens().add(tokenServ.find("blue"));
		nobleA9.getTokens().add(tokenServ.find("blue"));
		nobleA9.getTokens().add(tokenServ.find("black"));
		nobleA9.getTokens().add(tokenServ.find("black"));
		nobleA9.getTokens().add(tokenServ.find("black"));
		nobleA9.getTokens().add(tokenServ.find("white"));
		nobleA9.getTokens().add(tokenServ.find("white"));
		nobleA9.getTokens().add(tokenServ.find("white"));
		nobleServ.create(nobleA9);
		//D2 Francis I of France 3 red 3 black 3 green
		Noble nobleA10 = new Noble(3);	
		nobleA10.getTokens().add(tokenServ.find("red"));
		nobleA10.getTokens().add(tokenServ.find("red"));
		nobleA10.getTokens().add(tokenServ.find("red"));
		nobleA10.getTokens().add(tokenServ.find("black"));
		nobleA10.getTokens().add(tokenServ.find("black"));
		nobleA10.getTokens().add(tokenServ.find("black"));
		nobleA10.getTokens().add(tokenServ.find("green"));
		nobleA10.getTokens().add(tokenServ.find("green"));
		nobleA10.getTokens().add(tokenServ.find("green"));
		nobleServ.create(nobleA10);	
	}
}
