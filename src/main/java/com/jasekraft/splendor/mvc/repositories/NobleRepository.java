package com.jasekraft.splendor.mvc.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.jasekraft.splendor.mvc.models.Game;
import com.jasekraft.splendor.mvc.models.Noble;
import com.jasekraft.splendor.mvc.models.Player;

public interface NobleRepository extends CrudRepository<Noble, Long> {
	List<Noble> findAll();
	// Finds all Nobles a Player owns
	List<Noble> findAllByPlayers(Player player);
	// Finds all Nobles in the game
	List<Noble> findAllByGames(Game game);
}
