package com.jasekraft.splendor.mvc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jasekraft.splendor.mvc.models.PlayerCard;
import com.jasekraft.splendor.mvc.repositories.PlayerCardRepository;

@Service
public class PlayerCardService {
	private final PlayerCardRepository playercardRepo;
	
	public PlayerCardService(PlayerCardRepository playercardRepo) {
		this.playercardRepo = playercardRepo;
	}

    public List<PlayerCard> all() {
    	
        return playercardRepo.findAll();
    }

    public PlayerCard create(PlayerCard p) {
        return playercardRepo.save(p);
    }

    public PlayerCard find(Long id) {
        Optional<PlayerCard> optionalPlayerCard = playercardRepo.findById(id);
        if(optionalPlayerCard.isPresent()) {
            return optionalPlayerCard.get();
        } else {
            return null;
        }
    }

    
    public void delete(long id) {
    	playercardRepo.deleteById(id);
    }
    
    public PlayerCard update(PlayerCard playercard) {
    	Optional<PlayerCard> optionalPlayerCard = playercardRepo.findById(playercard.getId());
    	if(optionalPlayerCard.isPresent()) {
    		playercardRepo.save(playercard);
    		return playercard;
    	}
    	else {
    		return null;
    	}
    }
}