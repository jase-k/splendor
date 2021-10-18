package com.jasekraft.splendor.mvc.services;

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
		nobleServ.create(nobleA1);
		//no other way to find after insertion
		nobleA1 = nobleServ.find(Long.valueOf(1));
		nobleA1.getTokens().add(tokenServ.find("red"));
		nobleA1.getTokens().add(tokenServ.find("red"));
		nobleA1.getTokens().add(tokenServ.find("red"));
		nobleA1.getTokens().add(tokenServ.find("red"));
		nobleA1.getTokens().add(tokenServ.find("green"));
		nobleA1.getTokens().add(tokenServ.find("green"));
		nobleA1.getTokens().add(tokenServ.find("green"));
		nobleA1.getTokens().add(tokenServ.find("green"));
		nobleServ.update(nobleA1);
		//A2 Charles Quint (Karl V for germans)
		Noble nobleA2 = new Noble(3);
		nobleServ.create(nobleA2);
		nobleA2 = nobleServ.find(Long.valueOf(2));
		nobleA2.getTokens().add(tokenServ.find("red"));
		nobleA2.getTokens().add(tokenServ.find("red"));
		nobleA2.getTokens().add(tokenServ.find("red"));
		nobleA2.getTokens().add(tokenServ.find("black"));
		nobleA2.getTokens().add(tokenServ.find("black"));
		nobleA2.getTokens().add(tokenServ.find("black"));
		nobleA2.getTokens().add(tokenServ.find("white"));
		nobleA2.getTokens().add(tokenServ.find("white"));
		nobleA2.getTokens().add(tokenServ.find("white"));
		nobleServ.update(nobleA2);
		//A3 Macchiavelli
		Noble nobleA3 = new Noble(3);
		nobleServ.create(nobleA3);
		nobleA3 = nobleServ.find(Long.valueOf(3));
		nobleA3.getTokens().add(tokenServ.find("blue"));
		nobleA3.getTokens().add(tokenServ.find("blue"));
		nobleA3.getTokens().add(tokenServ.find("blue"));
		nobleA3.getTokens().add(tokenServ.find("blue"));
		nobleA3.getTokens().add(tokenServ.find("white"));
		nobleA3.getTokens().add(tokenServ.find("white"));
		nobleA3.getTokens().add(tokenServ.find("white"));
		nobleA3.getTokens().add(tokenServ.find("white"));
		nobleServ.update(nobleA3);
		//B1 Isabel of Castille
		Noble nobleA4 = new Noble(3);
		nobleServ.create(nobleA4);
		nobleA4 = nobleServ.find(Long.valueOf(4));
		nobleA4.getTokens().add(tokenServ.find("black"));
		nobleA4.getTokens().add(tokenServ.find("black"));
		nobleA4.getTokens().add(tokenServ.find("black"));
		nobleA4.getTokens().add(tokenServ.find("black"));
		nobleA4.getTokens().add(tokenServ.find("white"));
		nobleA4.getTokens().add(tokenServ.find("white"));
		nobleA4.getTokens().add(tokenServ.find("white"));
		nobleA4.getTokens().add(tokenServ.find("white"));
		nobleServ.update(nobleA4);
		//B2 Suleiman the Magnificent
		Noble nobleA5 = new Noble(3);
		nobleServ.create(nobleA5);
		nobleA5 = nobleServ.find(Long.valueOf(5));
		nobleA5.getTokens().add(tokenServ.find("blue"));
		nobleA5.getTokens().add(tokenServ.find("blue"));
		nobleA5.getTokens().add(tokenServ.find("blue"));
		nobleA5.getTokens().add(tokenServ.find("blue"));
		nobleA5.getTokens().add(tokenServ.find("green"));
		nobleA5.getTokens().add(tokenServ.find("green"));
		nobleA5.getTokens().add(tokenServ.find("green"));
		nobleA5.getTokens().add(tokenServ.find("green"));
		nobleServ.update(nobleA5);
		//B3 Catherine of Medicis
		Noble nobleA6 = new Noble(3);
		nobleServ.create(nobleA6);
		nobleA6 = nobleServ.find(Long.valueOf(6));
		nobleA6.getTokens().add(tokenServ.find("red"));
		nobleA6.getTokens().add(tokenServ.find("red"));
		nobleA6.getTokens().add(tokenServ.find("red"));
		nobleA6.getTokens().add(tokenServ.find("green"));
		nobleA6.getTokens().add(tokenServ.find("green"));
		nobleA6.getTokens().add(tokenServ.find("green"));
		nobleA6.getTokens().add(tokenServ.find("blue"));
		nobleA6.getTokens().add(tokenServ.find("blue"));
		nobleA6.getTokens().add(tokenServ.find("blue"));
		nobleServ.update(nobleA6);
		//C1 Anne of Brittany
		Noble nobleA7 = new Noble(3);
		nobleServ.create(nobleA7);
		nobleA7 = nobleServ.find(Long.valueOf(7));
		nobleA7.getTokens().add(tokenServ.find("white"));
		nobleA7.getTokens().add(tokenServ.find("white"));
		nobleA7.getTokens().add(tokenServ.find("white"));
		nobleA7.getTokens().add(tokenServ.find("green"));
		nobleA7.getTokens().add(tokenServ.find("green"));
		nobleA7.getTokens().add(tokenServ.find("green"));
		nobleA7.getTokens().add(tokenServ.find("blue"));
		nobleA7.getTokens().add(tokenServ.find("blue"));
		nobleA7.getTokens().add(tokenServ.find("blue"));
		nobleServ.update(nobleA7);
		//C2 Henri VIII
		Noble nobleA8 = new Noble(3);
		nobleServ.create(nobleA8);
		nobleA8 = nobleServ.find(Long.valueOf(8));
		nobleA8.getTokens().add(tokenServ.find("black"));
		nobleA8.getTokens().add(tokenServ.find("black"));
		nobleA8.getTokens().add(tokenServ.find("black"));
		nobleA8.getTokens().add(tokenServ.find("black"));
		nobleA8.getTokens().add(tokenServ.find("red"));
		nobleA8.getTokens().add(tokenServ.find("red"));
		nobleA8.getTokens().add(tokenServ.find("red"));
		nobleA8.getTokens().add(tokenServ.find("red"));
		nobleServ.update(nobleA8);
		//D1 Elisabeth of Austria
		Noble nobleA9 = new Noble(3);
		nobleServ.create(nobleA9);
		nobleA9 = nobleServ.find(Long.valueOf(9));
		nobleA9.getTokens().add(tokenServ.find("blue"));
		nobleA9.getTokens().add(tokenServ.find("blue"));
		nobleA9.getTokens().add(tokenServ.find("blue"));
		nobleA9.getTokens().add(tokenServ.find("black"));
		nobleA9.getTokens().add(tokenServ.find("black"));
		nobleA9.getTokens().add(tokenServ.find("black"));
		nobleA9.getTokens().add(tokenServ.find("white"));
		nobleA9.getTokens().add(tokenServ.find("white"));
		nobleA9.getTokens().add(tokenServ.find("white"));
		nobleServ.update(nobleA9);
		//D2 Francis I of France
		Noble nobleA10 = new Noble(3);
		nobleServ.create(nobleA10);
		nobleA10 = nobleServ.find(Long.valueOf(10));
		nobleA10.getTokens().add(tokenServ.find("red"));
		nobleA10.getTokens().add(tokenServ.find("red"));
		nobleA10.getTokens().add(tokenServ.find("red"));
		nobleA10.getTokens().add(tokenServ.find("black"));
		nobleA10.getTokens().add(tokenServ.find("black"));
		nobleA10.getTokens().add(tokenServ.find("black"));
		nobleA10.getTokens().add(tokenServ.find("green"));
		nobleA10.getTokens().add(tokenServ.find("green"));
		nobleA10.getTokens().add(tokenServ.find("green"));
		nobleServ.update(nobleA10);	
	}
}
