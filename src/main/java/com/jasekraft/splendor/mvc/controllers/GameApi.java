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
import com.jasekraft.splendor.mvc.models.User;
import com.jasekraft.splendor.mvc.services.CardCostService;
import com.jasekraft.splendor.mvc.services.GamePlayerService;
import com.jasekraft.splendor.mvc.services.GameService;
import com.jasekraft.splendor.mvc.services.NobleCostService;
import com.jasekraft.splendor.mvc.services.PlayerService;
import com.jasekraft.splendor.mvc.services.TokenService;
import com.jasekraft.splendor.mvc.services.UserService;

// Mappings are all tentative 
@RestController
public class GameApi {
	private final UserService userServ;
    private final GameService gameServ;
    private final PlayerService playerServ;
    private final GamePlayerService gamePlayerServ;
    private final TokenService tokenServ;
    private final NobleCostService nobleCostServ;
    private final CardCostService cardCostServ;
    
    @Autowired
    public GameApi(UserService userServ, GameService gameServ, 
    		PlayerService playerServ, GamePlayerService gamePlayerServ, 
    		TokenService tokenServ, NobleCostService nobleCostServ,
    		CardCostService cardCostServ){
    	this.userServ = userServ;
        this.gameServ = gameServ;
        this.playerServ = playerServ;
        this.gamePlayerServ = gamePlayerServ;
        this.tokenServ = tokenServ;
        this.nobleCostServ = nobleCostServ;
        this.cardCostServ = cardCostServ;
    }
    @RequestMapping("/games")
    @ResponseBody
    public List<Game> index() {
        return gameServ.all();
    }
    @RequestMapping(value = "/dbinit", method=RequestMethod.POST)
    public void init(@RequestBody Map<String, Object> body) {
        if(body.get("password").equals("root")) {
        	// creates the tokens
        	tokenServ.init();
        	// creates and assigns cost to nobles
        	nobleCostServ.init();
        	// creates and assigns cost to cards
        	cardCostServ.init();
        }
    }
    @RequestMapping("/games/")
    public List<Game> games() {
    	return gameServ.all();
    }
    @RequestMapping("/users/")
    public List<User> users() {
    	return userServ.all();
    }
    
    @RequestMapping("/games/new")
    public Game createGame() {
    	return gameServ.create(new Game());
    }
    @RequestMapping(value="/games/{id}", method=RequestMethod.DELETE)
    public String deleteGame(@PathVariable("id") Long id) {
        gameServ.delete(id);
        return "deleted";
    }
    
    @RequestMapping(value="/users/new", method=RequestMethod.POST)
    public User createUser(@RequestBody Map<String, Object> body) {
    	return userServ.create(new User((String)body.get("username"), 
    		(String)body.get("password"), (String)body.get("confirm")));
    }
    @RequestMapping(value="/users/{id}", method=RequestMethod.DELETE)
    public String deleteUser(@PathVariable("id") Long id) {
        userServ.delete(id);
        return "deleted";
    }
    
    @RequestMapping(value="/games/join", method=RequestMethod.POST)
    public Game joinGame(@RequestBody Map<String, Object> body) {
    	Long userId = Long.valueOf((Integer)body.get("user_id"));
    	Long gameId = Long.valueOf((Integer)body.get("game_id"));
        Player player = playerServ.create(new Player(userServ.findUser(userId)));
        gamePlayerServ.addRelation(gameId, player.getId());
        return gameServ.find(gameId);
    }
    @RequestMapping(value="/games/leave", method=RequestMethod.POST)
    public Game leaveGame(@RequestBody Map<String, Object> body) {
    	Long playerId = Long.valueOf((Integer)body.get("game_id"));
    	Long gameId = Long.valueOf((Integer)body.get("game_id"));
        gamePlayerServ.removeRelation(gameId, playerId);
        playerServ.delete(playerId);
        return gameServ.find(gameId);
    }
    
    @RequestMapping(value="/games/start", method=RequestMethod.POST)
    public Game startGame(@RequestBody Map<String, Object> body) {
    	Game game = gameServ.find(Long.valueOf((Integer)body.get("game_id")));
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

