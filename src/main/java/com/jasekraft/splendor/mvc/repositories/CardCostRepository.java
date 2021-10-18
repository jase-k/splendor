package com.jasekraft.splendor.mvc.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jasekraft.splendor.mvc.models.Card;
import com.jasekraft.splendor.mvc.models.CardCost;
import com.jasekraft.splendor.mvc.models.Token;

@Repository
public interface CardCostRepository extends CrudRepository<CardCost, Long>{
	 
	// this method retrieves all the cardcost rows from the database
	List<CardCost> findAll();
	
	Optional<CardCost> findByCardAndToken(Card card, Token token);
}
