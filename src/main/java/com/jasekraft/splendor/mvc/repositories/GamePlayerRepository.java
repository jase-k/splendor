package com.jasekraft.splendor.mvc.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.jasekraft.splendor.mvc.models.Game;
import com.jasekraft.splendor.mvc.models.GamePlayer;
import com.jasekraft.splendor.mvc.models.Player;

public interface GamePlayerRepository extends CrudRepository<GamePlayer, Long> {
	List<GamePlayer> findAll();
	
	// Finds the GamePlayer by Game (gets the turn) 
	// Returns a list of GP but to increment turn we have to do it numPlayers times?
	List<GamePlayer> findAllByGame(Game game);
	
	Optional<GamePlayer> findByGameAndPlayer(Game game, Player player);
}
