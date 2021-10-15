package com.jasekraft.splendor.mvc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jasekraft.splendor.mvc.models.Game;
import com.jasekraft.splendor.mvc.models.Player;
import com.jasekraft.splendor.mvc.models.User;
import com.jasekraft.splendor.mvc.repositories.PlayerRepository;

@Service
public class PlayerService {
    private final PlayerRepository playerRepo;
    
    @Autowired
    public PlayerService(PlayerRepository playerRepo) {
        this.playerRepo = playerRepo;
    }
    
    //Unique
	public List<Player> playersByGame(Game game){
		return playerRepo.findAllByGames(game);
	}
	
	public List<Player> playersByUsers(User user){
		return playerRepo.findAllByUser(user);
	}
	
	//CRUD
    public List<Player> all() {
        return playerRepo.findAll();
    }

    public Player create(Player p) {
        return playerRepo.save(p);
    }

    public Player find(Long id) {
        Optional<Player> optionalPlayer = playerRepo.findById(id);
        if(optionalPlayer.isPresent()) {
            return optionalPlayer.get();
        } else {
            return null;
        }
    }

    public void delete(long id) {
    	playerRepo.deleteById(id);
    }
    
    public Player update(Player deck) {
    	Optional<Player> optionalPlayer = playerRepo.findById(deck.getId());
    	if(optionalPlayer.isPresent()) {
    		playerRepo.save(deck);
    		return deck;
    	}
    	else {
    		return null;
    	}
    }
}
