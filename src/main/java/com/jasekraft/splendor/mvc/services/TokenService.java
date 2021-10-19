package com.jasekraft.splendor.mvc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jasekraft.splendor.mvc.models.Game;
import com.jasekraft.splendor.mvc.models.Player;
import com.jasekraft.splendor.mvc.models.Token;
import com.jasekraft.splendor.mvc.repositories.TokenRepository;

@Service
public class TokenService {
	private final TokenRepository tokenRepo;

	private final int goldTokens = 5;
	
	public TokenService(TokenRepository tokenRepo) {
		this.tokenRepo = tokenRepo;
	}
	//Unique
	public List<Token> tokensByPlayer(Player	player){
		return tokenRepo.findAllByPlayers(player);
	}
	//CRUD
	public List<Token> all() {
        return tokenRepo.findAll();
    }

    public Token create(Token p) {
        return tokenRepo.save(p);
    }

    public Token find(Long id) {
        Optional<Token> optionalToken = tokenRepo.findById(id);
        if(optionalToken.isPresent()) {
            return optionalToken.get();
        } else {
            return null;
        }
    }
    
    public Token find(String name) {
        Optional<Token> optionalToken = tokenRepo.findByName(name);
        if(optionalToken.isPresent()) {
            return optionalToken.get();
        } else {
            return null;
        }
    }

    public void delete(long id) {
    	tokenRepo.deleteById(id);
    }
    
    public Token update(Token token) {
    	Optional<Token> optionalToken = tokenRepo.findById(token.getId());
    	if(optionalToken.isPresent()) {
    		tokenRepo.save(token);
    		return token;
    	}
    	else {
    		return null;
    	}
    }
    
    // initializes tokens for game
    public void init(Game game, int totalTokens) {
    	List<Token> tokens = all();
    	for(Token t : tokens) {
			if(t.getName().equals("gold"))
				for(int i = 0; i<goldTokens; i++) {
					Token thisToken = find(t.getId());
					game.getTokens().add(thisToken);
				}
			else
				for(int i = 0; i<totalTokens; i++) {
					Token thisToken = find(t.getId());
					game.getTokens().add(thisToken);
				}			
		}
    }
    // initializes tokens for all games
    public void init() {
    	Token tokenBla = new Token("onyx");
    	Token tokenBlu = new Token("sapphire");
    	Token tokenR = new Token("ruby");
    	Token tokenW = new Token("diamond");
    	Token tokenG = new Token("emerald");
    	Token tokenGol = new Token("gold");
    	tokenRepo.save(tokenBla);
    	tokenRepo.save(tokenBlu);
    	tokenRepo.save(tokenR);
    	tokenRepo.save(tokenW);
    	tokenRepo.save(tokenG);
    	tokenRepo.save(tokenGol);
    }
}
