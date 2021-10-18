package com.jasekraft.splendor.mvc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jasekraft.splendor.mvc.models.Player;
import com.jasekraft.splendor.mvc.models.Card;
import com.jasekraft.splendor.mvc.models.PlayerCard;
import com.jasekraft.splendor.mvc.models.Deck;
import com.jasekraft.splendor.mvc.models.PlayerCard;
import com.jasekraft.splendor.mvc.repositories.PlayerCardRepository;

@Service
public class PlayerCardService {
	private final PlayerCardRepository playerCardRepo;
	private final PlayerService playerServ;
	private final CardService cardServ;
	
    @Autowired
	public PlayerCardService(PlayerCardRepository playerCardRepo,
			PlayerService playerServ, CardService cardServ) {
		this.playerCardRepo = playerCardRepo;
        this.playerServ = playerServ;
        this.cardServ = cardServ;
	}
	
	//CRUD
    public List<PlayerCard> all() {
        return playerCardRepo.findAll();
    }

    public PlayerCard create(PlayerCard p) {
        return playerCardRepo.save(p);
    }

    public PlayerCard find(Long id) {
        Optional<PlayerCard> optionalPlayerCard = playerCardRepo.findById(id);
        if(optionalPlayerCard.isPresent()) {
            return optionalPlayerCard.get();
        } else {
            return null;
        }
    }
    
    public PlayerCard find(Card card, Player player) {
        Optional<PlayerCard> optionalPlayerCard = playerCardRepo.findByCardAndPlayer(card, player);
        if(optionalPlayerCard.isPresent()) {
            return optionalPlayerCard.get();
        } else {
            return null;
        }
    }


    public void delete(long id) {
    	playerCardRepo.deleteById(id);
    }
    
    public PlayerCard update(PlayerCard playerCard) {
    	Optional<PlayerCard> optionalPlayerCard = playerCardRepo.findById(playerCard.getId());
    	if(optionalPlayerCard.isPresent()) {
    		playerCardRepo.save(playerCard);
    		return playerCard;
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