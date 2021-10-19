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
	
	// Colors in their natural order.
	private final String[] colors = {"onyx","sapphire","ruby","diamond","emerald"};
	// Cost of cards per set by order.
	private final int[][] setOrder = {
		{0,1,1,1,1}, {0,0,1,0,2}, {0,0,0,2,2}, {1,0,3,0,1}, {0,0,0,0,3},
		{0,2,1,1,1}, {0,2,1,2,0}, {0,4,0,0,0}, {0,3,0,3,2}, {2,0,0,3,3},
		{0,1,2,0,4}, {0,0,0,5,0}, {0,0,3,0,5}, {6,0,0,0,0}, 
		{0,3,3,3,5}, {0,0,7,0,0}, {3,0,6,0,3}, {3,0,7,0,0}
	};
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
    public CardCost find(Card card, Token token) {
        Optional<CardCost> optionalCardCost = cardcostRepo.findByCardAndToken(card,token);
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
	
	public void init() {
		cardServ.init();
		Card card;
		int position;
		int colorPos;
		Token token;
		//Green Deck (IDs 1-40)
    	//Green deck - Loop through each token type
		for(int i = 1; i<=40; i++) {
			card = cardServ.find(Long.valueOf(i));
			position = (i-1)%8;
			colorPos = (i-1)/8;
			token = tokenServ.find(colors[colorPos]);
			card.setToken(token);
			for(int j = 0;j<5;j++) {
				token = tokenServ.find(colors[(j+colorPos)%5]);
				CardCost cost = new CardCost(setOrder[position][j], card, token);
				//System.out.println(cost.getQuantity());
				cardcostRepo.save(cost);
			}
			//cardServ.update(card);
		}
		//Yellow Deck (IDs 41-70)
    	//Yellow deck - Loop through each token type
		for(int i = 1; i<=30; i++) {
			card = cardServ.find(Long.valueOf(i+40));
			position = (i-1)%6+8;
			colorPos = (i-1)/6;
			token = tokenServ.find(colors[colorPos]);
			card.setToken(token);
			for(int j = 0;j<5;j++) {
				token = tokenServ.find(colors[(j+colorPos)%5]);
				CardCost cost = new CardCost(setOrder[position][j], card, token);
				//System.out.println(cost.getQuantity());
				cardcostRepo.save(cost);
			}
			//cardServ.update(card);
		}
		//Blue Deck (IDs 71-90)
    	//Blue deck - Loop through each token type
		for(int i = 1; i<=20; i++) {
			card = cardServ.find(Long.valueOf(i+70));
			position = (i-1)%4+14;
			colorPos = (i-1)/4;
			token = tokenServ.find(colors[colorPos]);
			card.setToken(token);
			for(int j = 0;j<5;j++) {
				token = tokenServ.find(colors[(j+colorPos)%5]);
				CardCost cost = new CardCost(setOrder[position][j], card, token);
				//System.out.println(cost.getQuantity());
				cardcostRepo.save(cost);
			}
			//cardServ.update(card);
		}	
	}
}