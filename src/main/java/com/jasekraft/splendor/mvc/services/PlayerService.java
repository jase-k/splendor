package com.jasekraft.splendor.mvc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jasekraft.splendor.mvc.models.Card;
import com.jasekraft.splendor.mvc.models.Deck;
import com.jasekraft.splendor.mvc.models.Game;
import com.jasekraft.splendor.mvc.models.Noble;
import com.jasekraft.splendor.mvc.models.Player;
import com.jasekraft.splendor.mvc.models.PlayerCard;
import com.jasekraft.splendor.mvc.models.Token;
import com.jasekraft.splendor.mvc.models.User;
import com.jasekraft.splendor.mvc.repositories.PlayerRepository;

@Service
public class PlayerService {
    private final PlayerRepository playerRepo;
    private final GameService gameServ;
    private final TokenService tokenServ;
    private final CardService cardServ;
    private final PlayerCardService playerCardServ;
    private final NobleService nobleServ;
    
    @Autowired
    public PlayerService(PlayerRepository playerRepo, 
    		GameService gameServ, TokenService tokenServ,
    		CardService cardServ, PlayerCardService playerCardServ,
    		NobleService nobleServ) {
        this.playerRepo = playerRepo;
        this.gameServ = gameServ;
        this.tokenServ = tokenServ;
        this.cardServ = cardServ;
        this.playerCardServ = playerCardServ;
        this.nobleServ = nobleServ;
    }
    
    //Unique
	public List<Player> playersByGame(Game game){
		return playerRepo.findAllByGames(game);
	}
	
	public List<Player> playersByUsers(User user){
		return playerRepo.findAllByUser(user);
	}
	
	//CRUD
    public List<Player> all() {
        return playerRepo.findAll();
    }

    public Player create(Player p) {
        return playerRepo.save(p);
    }

    public Player find(Long id) {
        Optional<Player> optionalPlayer = playerRepo.findById(id);
        if(optionalPlayer.isPresent()) {
            return optionalPlayer.get();
        } else {
            return null;
        }
    }

    public void delete(long id) {
    	playerRepo.deleteById(id);
    }
    
    public Player update(Player deck) {
    	Optional<Player> optionalPlayer = playerRepo.findById(deck.getId());
    	if(optionalPlayer.isPresent()) {
    		playerRepo.save(deck);
    		return deck;
    	}
    	else {
    		return null;
    	}
    }
    
    // add Tokens
	public Game addTokens(Long gameId, Long playerId, Long[] tokenIds) {
		Player thisPlayer = find(playerId);
		Game thisGame = gameServ.find(playerId);
		List<Token> playerTokens = thisPlayer.getTokens();
		List<Token> gameTokens = thisGame.getTokens();
		for(Long tokenId : tokenIds) {
			playerTokens.add(tokenServ.find(tokenId));	
			gameTokens.remove(tokenServ.find(tokenId));
		}
		update(thisPlayer);
		gameServ.update(thisGame);
		return thisGame;
	}
	
	// add card 
    public Game addCard(Long gameId, Long playerId, Long cardId) {
    	Player thisPlayer = find(playerId);
    	Card card = cardServ.find(cardId);
    	List<Card> cards = thisPlayer.getCards();
    	Game thisGame = gameServ.find(gameId);
    	if(!cards.contains(card)) {
	    	List<Deck> decks = thisGame.getDecks();
	    	for(Deck deck : decks) {
	    		List<Card> deckCards = deck.getCards();
	    		if(deckCards.contains(card)) 
	    			deckCards.remove(card);
	    	}
	    	cards.add(card);
	    	update(thisPlayer);
    	}
    	// update happens before pCServ finds?
    	// updated(thisPlayer);
    	PlayerCard pC = playerCardServ.find(card, thisPlayer);
    	pC.setOwned(true);
    	playerCardServ.update(pC);
		gameServ.update(thisGame);
		return thisGame;
    }
    // reserve card
    public Game reserveCard(Long gameId, Long playerId, Long cardId) {
    	Game thisGame = gameServ.find(gameId);
    	Player thisPlayer = find(playerId);
    	Card card = cardServ.find(cardId);
    	List<Deck> decks = thisGame.getDecks();
    	List<Card> cards = thisPlayer.getCards();
    	for(Deck deck : decks) {
    		List<Card> deckCards = deck.getCards();
    		if(deckCards.contains(card)) 
    			deckCards.remove(card);
    	}
    	cards.add(card);
    	update(thisPlayer);
    	PlayerCard pC = playerCardServ.find(card, thisPlayer);
    	pC.setOwned(false);
    	playerCardServ.update(pC);
		gameServ.update(thisGame);
		return thisGame;
    }
    
	// add noble
    public Game addNoble(Long gameId, Long playerId, Long nobleId) {
    	Player thisPlayer = find(playerId);
    	Noble noble = nobleServ.find(nobleId);
    	List<Noble> nobles = thisPlayer.getNobles();
    	Game thisGame = gameServ.find(gameId);
    	nobles.add(noble);
    	thisGame.getNobles().remove(noble);
    	update(thisPlayer);
		gameServ.update(thisGame);
		return thisGame;
    }
}
