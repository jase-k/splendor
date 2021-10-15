package com.jasekraft.splendor.mvc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jasekraft.splendor.mvc.models.Game;
import com.jasekraft.splendor.mvc.repositories.GameRepository;

@Service
public class GameService {
	private final GameRepository gameRepo;
	
	public GameService(GameRepository gameRepo) {
		this.gameRepo = gameRepo;
	}

    public List<Game> all() {
    	
        return gameRepo.findAll();
    }

    public Game create(Game p) {
        return gameRepo.save(p);
    }

    public Game find(Long id) {
        Optional<Game> optionalGame = gameRepo.findById(id);
        if(optionalGame.isPresent()) {
            return optionalGame.get();
        } else {
            return null;
        }
    }

    
    public void delete(long id) {
    	gameRepo.deleteById(id);
    }
    
    public Game update(Game game) {
    	Optional<Game> optionalGame = gameRepo.findById(game.getId());
    	if(optionalGame.isPresent()) {
    		gameRepo.save(game);
    		return game;
    	}
    	else {
    		return null;
    	}
    }
}