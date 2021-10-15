package com.jasekraft.splendor.mvc.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jasekraft.splendor.mvc.models.Game;

@Repository
public interface GameRepository extends CrudRepository<Game, Long>{
	 
	// this method retrieves all the games from the database
	 List<Game> findAll();
	 
}