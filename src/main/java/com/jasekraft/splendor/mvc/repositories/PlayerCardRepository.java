package com.jasekraft.splendor.mvc.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jasekraft.splendor.mvc.models.PlayerCard;

@Repository
public interface PlayerCardRepository extends CrudRepository<PlayerCard, Long>{
	 
	 List<PlayerCard> findAll();
	 
	 	 
}
