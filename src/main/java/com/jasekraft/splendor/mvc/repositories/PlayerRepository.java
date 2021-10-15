package com.jasekraft.splendor.mvc.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.jasekraft.splendor.mvc.models.Game;
import com.jasekraft.splendor.mvc.models.Player;
import com.jasekraft.splendor.mvc.models.User;

public interface PlayerRepository extends CrudRepository<Player, Long> {
	List<Player> findAll();
	// Finds all Players in the game
	List<Player> findAllByGames(Game game);
	// Finds all Players from user (for continuing/ starting many games)
	List<Player> findAllByUser(User user);
}
