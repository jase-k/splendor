package com.jasekraft.splendor.mvc.services;

import java.util.List;
import java.util.Map;
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
import com.jasekraft.splendor.mvc.repositories.PlayerCardRepository;
import com.jasekraft.splendor.mvc.repositories.PlayerRepository;

@Service
public class PlayerService {
    private final PlayerRepository playerRepo;
    private final GameService gameServ;
    private final TokenService tokenServ;
    private final CardService cardServ;
    private final PlayerCardRepository playerCardRepo;
    private final NobleService nobleServ;
    
	// Colors in their natural order.
	private final String[] colors = {"onyx","sapphire","ruby","diamond","emerald", "gold"};
    
    @Autowired
    public PlayerService(PlayerRepository playerRepo, 
    		GameService gameServ, TokenService tokenServ,
    		CardService cardServ, PlayerCardRepository playerCardRepo,
    		NobleService nobleServ) {
        this.playerRepo = playerRepo;
        this.gameServ = gameServ;
        this.tokenServ = tokenServ;
        this.cardServ = cardServ;
        this.playerCardRepo = playerCardRepo;
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
    
    public PlayerCard findPC(Card card, Player player) {
        Optional<PlayerCard> optionalPlayerCard = playerCardRepo.findByCardAndPlayer(card, player);
        if(optionalPlayerCard.isPresent()) {
            return optionalPlayerCard.get();
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
	public Game addTokens(Long gameId, Long playerId, List<Integer>tokens) {
		Player thisPlayer = find(playerId);
		Game thisGame = gameServ.find(gameId);
    	if(thisGame.getTurn()%thisGame.getPlayers().size() != thisPlayer.getTurn())
    		return thisGame;
		List<Token> playerTokens = thisPlayer.getTokens();
		List<Token> gameTokens = thisGame.getTokens();
		//checks action
		
		//performs action
		for(Integer tokenId : tokens) {
			Token token = tokenServ.find(Long.valueOf(tokenId));
			String tName = token.getName();
			// Checking issue
			// Integer gamePool = thisGame.getTokenPool().get(tName);
			playerTokens.add(token);	
			gameTokens.remove(token);
			thisPlayer.getTokenPool().put(tName, 
					thisPlayer.getTokenPool().get(tName)+1);
			thisGame.getTokenPool().put(tName, 
					thisGame.getTokenPool().get(tName)-1);	
		}
		update(thisPlayer);
		thisGame.setTurn(thisGame.getTurn()+1);
		gameServ.update(thisGame);
		return thisGame;
	}
	
	// add card 
    public Game addCard(Long gameId, Long playerId, Long cardId) {
    	Game thisGame = gameServ.find(gameId);
    	Player thisPlayer = find(playerId);
    	if(thisGame.getTurn()%thisGame.getPlayers().size() != thisPlayer.getTurn())
    		return thisGame;
    	Card card = cardServ.find(cardId);
    	List<Card> cards = thisPlayer.getCards();
    	PlayerCard pC;
    	// Remove from deck / make relation
    	if(!cards.contains(card)) {
	    	List<Deck> decks = thisGame.getDecks();
	    	for(Deck deck : decks) {
	    		//List<Card> deckCards = deck.getCards();
	    		if(deck.getCards().contains(card)) 
	    			deck.getCards().remove(card);
	    	}
	    	pC = new PlayerCard(true, card, thisPlayer);
	    	playerCardRepo.save(pC);
	    	thisPlayer.setOwnedCards(thisPlayer.getOwnedCards()+"1");
    	}
    	else {
    		pC =  playerCardRepo.findByCardAndPlayer(card, thisPlayer).get();
    		pC.setOwned(true);
    		String ownedCards = thisPlayer.getOwnedCards();
    		thisPlayer.setOwnedCards(
    			ownedCards.substring(0, thisPlayer.getCards().indexOf(card))+"1"+
    			ownedCards.substring(thisPlayer.getCards().indexOf(card)+1));
    	}
    	// Pay for card
    	Map<String, Integer> cardCost = card.getTokenCost();
    	Map<String, Integer> playerPool = thisPlayer.getTokenPool();
    	Map<String, Integer> gamePool = thisGame.getTokenPool();
    	// cardCost returned to main pile
    	int extraNeeded = 0;
    	for(Map.Entry<String,Integer> entry : cardCost.entrySet()) {
    		playerPool.put(entry.getKey(), playerPool.get(entry.getKey())-entry.getValue());
    		gamePool.put(entry.getKey(), gamePool.get(entry.getKey())+entry.getValue());
    		for(int i = 0; i<entry.getValue();i++) {
    			//playerCardRepo.delete(playerCardRepo.findById(cardId));
    		}
    	}
    	playerPool.put("gold", playerPool.get("gold")-extraNeeded);
    	gamePool.put("gold", gamePool.get("gold")+extraNeeded);
    	// update happens before pCServ finds?
    	update(thisPlayer);
    	thisGame.setTurn(thisGame.getTurn()+1);
		gameServ.update(thisGame);
		return thisGame;
    }
    // reserve card
    public Game reserveCard(Long gameId, Long playerId, Long cardId) {
    	Game thisGame = gameServ.find(gameId);
    	Player thisPlayer = find(playerId);
    	if(thisGame.getTurn()%thisGame.getPlayers().size() != thisPlayer.getTurn())
    		return thisGame;
    	Card card = cardServ.find(cardId);
    	List<Deck> decks = thisGame.getDecks();
    	//List<Card> cards = thisPlayer.getCards();
    	for(Deck deck : decks) {
    		//List<Card> deckCards = deck.getCards();
    		if(deck.getCards().contains(card)) 
    			deck.getCards().remove(card);
    	}
    	//cards.add(card);
    	thisPlayer.setOwnedCards(thisPlayer.getOwnedCards()+"0");
    	update(thisPlayer);
    	PlayerCard pC = new PlayerCard(false, card, thisPlayer);
    	//pC.setOwned(false);
    	playerCardRepo.save(pC);
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
