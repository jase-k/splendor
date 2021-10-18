package com.jasekraft.splendor.mvc.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
    
    @RequestMapping(value="/games/{gameId}/taketokens/{playerId}", method = RequestMethod.POST)
    public Game takeToken(@PathVariable("gameId") Long gameId, @PathVariable("playerId") Long playerId, @RequestBody Map<String, Object> body) {
    	Game game = playerServ.addTokens(gameId, playerId, (Long[])body.get("tokens"));
    	return game;
    }
    
    @RequestMapping(value="/games/{gameId}/takecard/{playerId}", method = RequestMethod.POST)
    public Game takeCard(@PathVariable("gameId") Long gameId, @PathVariable("playerId") Long playerId, @RequestBody Map<String, Object> body) {
    	Game game = playerServ.addCard(gameId, playerId, (Long)body.get("card_id"));
    	return game;
    }
    
    @RequestMapping(value="/games/{gameId}/reservecard/{playerId}", method = RequestMethod.POST)
    public Game reserveCard(@PathVariable("gameId") Long gameId, @PathVariable("playerId") Long playerId, @RequestBody Map<String, Object> body) {
    	Game game = playerServ.reserveCard(gameId, playerId, (Long)body.get("card_id"));
    	return game;
    }
    
    @RequestMapping(value="/games/{gameId}/takenoble/{playerId}", method = RequestMethod.POST)
    public Game takeNoble(@PathVariable("gameId") Long gameId, @PathVariable("playerId") Long playerId, @RequestBody Map<String, Object> body) {
    	Game game = playerServ.addNoble(gameId, playerId, (Long)body.get("noble_id"));
    	return game;
    }
}

