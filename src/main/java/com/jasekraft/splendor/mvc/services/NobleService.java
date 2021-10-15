package com.jasekraft.splendor.mvc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jasekraft.splendor.mvc.models.Game;
import com.jasekraft.splendor.mvc.models.Noble;
import com.jasekraft.splendor.mvc.models.Player;
import com.jasekraft.splendor.mvc.repositories.NobleRepository;

@Service
public class NobleService {
	private final NobleRepository nobleRepo;
	    
	@Autowired
	public NobleService(NobleRepository nobleRepo) {
	    this.nobleRepo = nobleRepo;
	}

	//Unique
	public List<Noble> noblesByGame(Game game){
		return nobleRepo.findAllByGames(game);
	}
	
	public List<Noble> noblesByPlayer(Player player){
		return nobleRepo.findAllByPlayers(player);
	}
	
	//CRUD
	public List<Noble> all() {
	    return nobleRepo.findAll();
	}
	
	public Noble create(Noble p) {
	    return nobleRepo.save(p);
	}
	
	public Noble find(Long id) {
	    Optional<Noble> optionalNoble = nobleRepo.findById(id);
	    if(optionalNoble.isPresent()) {
	        return optionalNoble.get();
	    } else {
	        return null;
	    }
	}
	
	public void delete(long id) {
		nobleRepo.deleteById(id);
	}
	
	public Noble update(Noble deck) {
		Optional<Noble> optionalNoble = nobleRepo.findById(deck.getId());
		if(optionalNoble.isPresent()) {
			nobleRepo.save(deck);
			return deck;
		}
		else {
			return null;
		}
	}
}
