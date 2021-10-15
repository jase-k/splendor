package com.jasekraft.splendor.mvc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jasekraft.splendor.mvc.models.Token;
import com.jasekraft.splendor.mvc.repositories.TokenRepository;

@Service
public class TokenService {
	private final TokenRepository tokenRepo;
	
	public TokenService(TokenRepository tokenRepo) {
		this.tokenRepo = tokenRepo;
	}

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
}
