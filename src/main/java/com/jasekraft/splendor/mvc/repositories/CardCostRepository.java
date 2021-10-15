package com.jasekraft.splendor.mvc.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jasekraft.splendor.mvc.models.CardCost;

@Repository
public interface CardCostRepository extends CrudRepository<CardCost, Long>{
	 
	// this method retrieves all the cardcost rows from the database
	List<CardCost> findAll();
	  	 
}
