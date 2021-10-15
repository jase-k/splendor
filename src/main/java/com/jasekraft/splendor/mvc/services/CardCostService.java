package com.jasekraft.splendor.mvc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jasekraft.splendor.mvc.models.CardCost;
import com.jasekraft.splendor.mvc.models.Card;
import com.jasekraft.splendor.mvc.models.Token;
import com.jasekraft.splendor.mvc.repositories.CardCostRepository;

@Service
public class CardCostService {
	private final CardCostRepository cardcostRepo;
	private final TokenService tokenServ;
	private final CardService cardServ;
	
    @Autowired	
	public CardCostService(CardCostRepository cardcostRepo,
			TokenService tokenServ, CardService cardServ) {
		this.cardcostRepo = cardcostRepo;
        this.tokenServ = tokenServ;
        this.cardServ = cardServ;
	}
	
	// CRUD
    public List<CardCost> all() {
    	
        return cardcostRepo.findAll();
    }

    public CardCost create(CardCost p) {
        return cardcostRepo.save(p);
    }

    public CardCost find(Long id) {
        Optional<CardCost> optionalCardCost = cardcostRepo.findById(id);
        if(optionalCardCost.isPresent()) {
            return optionalCardCost.get();
        } else {
            return null;
        }
    }
        
    public void delete(long id) {
    	cardcostRepo.deleteById(id);
    }
    
    public CardCost update(CardCost cardcost) {
    	Optional<CardCost> optionalCardCost = cardcostRepo.findById(cardcost.getId());
    	if(optionalCardCost.isPresent()) {
    		cardcostRepo.save(cardcost);
    		return cardcost;
    	}
    	else {
    		return null;
    	}
    }
    
    // Relation methods
    
	public void addRelation(Long tokenId,Long cardId) {
		Token thisToken = tokenServ.find(tokenId);
	    Card thisCard = cardServ.find(cardId);
	    thisToken.getCards().add(thisCard);
	    tokenServ.update(thisToken);	
	}
	
	public void removeRelation(Long tokenId, Long cardId) {
		Token thisToken = tokenServ.find(tokenId);
	    Card thisCard = cardServ.find(cardId);
	    thisToken.getCards().remove(thisCard);
	    tokenServ.update(thisToken);	
	}
}