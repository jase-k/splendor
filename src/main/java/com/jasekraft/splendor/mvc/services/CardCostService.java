package com.jasekraft.splendor.mvc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jasekraft.splendor.mvc.models.CardCost;
import com.jasekraft.splendor.mvc.repositories.CardCostRepository;

@Service
public class CardCostService {
	private final CardCostRepository cardcostRepo;
	
	public CardCostService(CardCostRepository cardcostRepo) {
		this.cardcostRepo = cardcostRepo;
	}

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
}