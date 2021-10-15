package com.jasekraft.splendor.mvc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jasekraft.splendor.mvc.models.Game;
import com.jasekraft.splendor.mvc.models.Player;
import com.jasekraft.splendor.mvc.services.GamePlayerService;
import com.jasekraft.splendor.mvc.services.GameService;
import com.jasekraft.splendor.mvc.services.PlayerService;
import com.jasekraft.splendor.mvc.services.UserService;

// Mappings are all tentative 
@RestController
public class GameApi {
	 private final UserService userServ;
    private final GameService gameServ;
    private final PlayerService playerServ;
    private final GamePlayerService gamePlayerServ;
    @Autowired
    public GameApi(UserService userServ, GameService gameServ, PlayerService playerServ, 
    		GamePlayerService gamePlayerServ){
    	this.userServ = userServ;
        this.gameServ = gameServ;
        this.playerServ = playerServ;
        this.gamePlayerServ = gamePlayerServ;
    }
    @RequestMapping("/games")
    @ResponseBody
    public List<Game> index() {
        return gameServ.all();
    }
    
    @RequestMapping("/games/new")
    public Game createGame() {
    	Game g = new Game();
    	return gameServ.create(g);
    }
    
    @RequestMapping(value="/games/join", method=RequestMethod.POST)
    public Game joinGame(@RequestParam(value="user_id") Long userId, @RequestParam(value="game_id") Long gameId) {
        Player player = playerServ.create(new Player(userServ.findUser(userId)));
        gamePlayerServ.addRelation(gameId, player.getId());
        return gameServ.find(gameId);
    }
    
    @RequestMapping(value="/games/start", method=RequestMethod.POST)
    public Game startGame(@RequestParam(value="game_id") Long gameId) {
    	Game game = gameServ.find(gameId);
    	gameServ.initialize(game);
    	return game;
    }
}

