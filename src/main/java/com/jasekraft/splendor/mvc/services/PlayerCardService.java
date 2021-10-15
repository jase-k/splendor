package com.jasekraft.splendor.mvc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jasekraft.splendor.mvc.models.Player;
import com.jasekraft.splendor.mvc.models.Card;
import com.jasekraft.splendor.mvc.models.PlayerCard;
import com.jasekraft.splendor.mvc.repositories.PlayerCardRepository;

@Service
public class PlayerCardService {
	private final PlayerCardRepository playercardRepo;
	private final PlayerService playerServ;
	private final CardService cardServ;
	
    @Autowired
	public PlayerCardService(PlayerCardRepository playercardRepo,
			PlayerService playerServ, CardService cardServ) {
		this.playercardRepo = playercardRepo;
        this.playerServ = playerServ;
        this.cardServ = cardServ;
	}
	
	//CRUD
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
    
    //Add remove relation
	public void addRelation(Long playerId,Long cardId) {
		Player thisPlayer = playerServ.find(playerId);
	    Card thisCard = cardServ.find(cardId);
	    thisPlayer.getCards().add(thisCard);
	    playerServ.update(thisPlayer);	
	}
	
	public void removeRelation(Long playerId, Long cardId) {
		Player thisPlayer = playerServ.find(playerId);
	    Card thisCard = cardServ.find(cardId);
	    thisPlayer.getCards().remove(thisCard);
	    playerServ.update(thisPlayer);	
	}
}