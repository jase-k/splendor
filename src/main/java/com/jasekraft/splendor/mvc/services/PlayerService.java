package com.jasekraft.splendor.mvc.services;

import java.util.HashMap;
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
    private final CardDeckService cardDeckServ;
    private final DeckService deckServ;
	// Colors in their natural order.
	private final String[] colors = {"onyx","sapphire","ruby","diamond","emerald", "gold"};
    
    @Autowired
    public PlayerService(PlayerRepository playerRepo, 
    		GameService gameServ, TokenService tokenServ,
    		CardService cardServ, PlayerCardRepository playerCardRepo,
    		NobleService nobleServ, CardDeckService cardDeckServ,
    		DeckService deckServ) {
        this.playerRepo = playerRepo;
        this.gameServ = gameServ;
        this.tokenServ = tokenServ;
        this.cardServ = cardServ;
        this.playerCardRepo = playerCardRepo;
        this.nobleServ = nobleServ;
        this.cardDeckServ = cardDeckServ;
        this.deckServ = deckServ;
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
		//Size is 1 = always gold
		if(tokens.size() == 1) {
				Token token = tokenServ.find("gold");
				if(!gameTokens.contains(token))
					return thisGame;
			}
		// size is 2
		else if(tokens.size() == 2) {
			Token token = tokenServ.find(Long.valueOf(tokens.get(0)));
			if(thisGame.getTokenPool().get(token.getName())<2)
				return thisGame;
		}
		//Size is 3
		else if(tokens.size() == 3) {
			if(tokens.get(0).equals(tokens.get(1)) || tokens.get(2).equals(tokens.get(1))|| tokens.get(2).equals(tokens.get(0)))
				return thisGame;
			for(Integer tokenId : tokens) {
				Token token = tokenServ.find(Long.valueOf(tokenId));
				if(!gameTokens.contains(token))
					return thisGame;
			}
		}
		else
			return thisGame;
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
    	Map<String, Integer> costReduction = new HashMap<>();
    	for(int i = 0; i<colors.length-1;i++) {
    		int total = 0;
    		for(Card c : cards) {
    			if(c.getTokenName().equals(colors[i])) total++;
    		}
    		costReduction.put(colors[i], total);
    	}
    	// Check if card can be paid
    	Map<String, Integer> cardCost = card.getTokenCost();
    	Map<String, Integer> playerPool = thisPlayer.getTokenPool();
    	Map<String, Integer> gamePool = thisGame.getTokenPool();
    	Map<String, Integer> finalTransaction = new HashMap<>();
    	// cardCost returned to main pile
    	int extraNeeded = 0;
    	for(Map.Entry<String,Integer> entry : cardCost.entrySet()) {
    		if(entry.getValue()>playerPool.get(entry.getKey())+costReduction.get(entry.getKey())) 
    			extraNeeded+= entry.getValue()-costReduction.get(entry.getKey())-playerPool.get(entry.getKey());
    		finalTransaction.put(entry.getKey(), Math.min(Math.max(0,
    			entry.getValue()-costReduction.get(entry.getKey())),playerPool.get(entry.getKey())));
    	}
    	if(extraNeeded > playerPool.get("gold"))
    		return thisGame;
    	finalTransaction.put("gold", extraNeeded);
    	List<Token> playerTokens = thisPlayer.getTokens();
		List<Token> gameTokens = thisGame.getTokens();
    	PlayerCard pC;
    	// Remove from deck / make relation
    	if(!cards.contains(card)) {
	    	List<Deck> decks = thisGame.getDecks();
	    	for(Deck deck : decks) {
	    		//List<Card> deckCards = deck.getCards();
	    		if(deck.getCards().contains(card)) {
	    			//cardDeckServ.delete(cardDeckServ.find(deck, card).getId());
	    			//card.getDecks().remove(deck);
	    			//cardServ.update(card);
	    			deck.getCards().remove(card);
	    			//cardDeckServ.removeRelation(deck.getId(), card.getId());
	    		}
	    			//deck.getCards().remove(card);
	    	}
	    	//pC = new PlayerCard(true, card, thisPlayer);
	    	//playerCardRepo.save(pC);
	    	gameServ.update(thisGame);
	    	cards.add(card);
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
    	thisGame = gameServ.find(gameId);
    	// Pay for card
    	for(Map.Entry<String,Integer> entry : finalTransaction.entrySet()) {
    		gamePool.put(entry.getKey(), gamePool.get(entry.getKey())+entry.getValue());
    		playerPool.put(entry.getKey(), playerPool.get(entry.getKey())-entry.getValue());
    		
    		for(int i = 0; i<entry.getValue();i++) {
    			Token token = tokenServ.find(entry.getKey());
    			if(playerTokens.contains(token)) {
    				playerTokens.remove(token);	
    				gameTokens.add(token);
    			}
    		}
    	}/*
    	playerPool.put("gold", playerPool.get("gold")-extraNeeded);
    	gamePool.put("gold", gamePool.get("gold")+extraNeeded);
    	Token gold = tokenServ.find("gold");
    	for(int i = 0; i<extraNeeded;i++) {
    		playerTokens.remove(gold);	
			gameTokens.add(gold);
    	}*/
    	// update happens before pCServ finds?
    	update(thisPlayer);
    	thisGame.setTurn(thisGame.getTurn()+1);
		gameServ.update(thisGame);
		//checkChampion(gameId);
		return checkChampion(gameId);
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
    		if(deck.getCards().contains(card)) {
    			//cardDeckServ.delete(cardDeckServ.find(deck, card).getId());
    			//deckServ.update(deck);
    			deck.getCards().remove(card);
    		}
    	}
    	//cards.add(card);
    	thisPlayer.setOwnedCards(thisPlayer.getOwnedCards()+"0");
 
    	PlayerCard pC = new PlayerCard(false, card, thisPlayer);
    	playerCardRepo.save(pC);
       	update(thisPlayer);

		thisGame = gameServ.update(thisGame);
		return thisGame;
    }
    
	// add noble through route
    public Game addNoble(Long gameId, Long playerId, Long nobleId) {
    	Player thisPlayer = find(playerId);
    	Noble noble = nobleServ.find(nobleId);
    	Game thisGame = gameServ.find(gameId);
    	if(thisGame.getTurn()%thisGame.getPlayers().size() != thisPlayer.getTurn())
    		return thisGame;
    	if(!thisGame.getNobles().contains(noble))
    		return thisGame;
    	List<Card> playerCards = thisPlayer.getCards();
    	String playerOwnership = thisPlayer.getOwnedCards();
    	Map<String, Integer> playerPool = new HashMap<>();
    	playerPool.put("onyx", 0);
    	playerPool.put("sapphire", 0);
    	playerPool.put("ruby", 0);
    	playerPool.put("diamond", 0);
    	playerPool.put("emerald", 0);
    	Map<String, Integer> nobleCost = noble.getTokenCost();
    	int i = 0;
    	for(Card card : playerCards) {
    		if(playerOwnership.charAt(i)=='1')
    			playerPool.put(card.getTokenName(),1+ playerPool.get(card.getTokenName()));
    		i++;
    	}
    	// reject if nobles cost more than player
    	for(Map.Entry<String,Integer> entry : nobleCost.entrySet()) {
    		if(entry.getValue() > playerPool.get(entry.getKey()))
    			return thisGame;
    	}
    	List<Noble> nobles = thisPlayer.getNobles();
    	
    	nobles.add(noble);
    	thisGame.getNobles().remove(noble);
    	update(thisPlayer);
		gameServ.update(thisGame);
		
		return checkChampion(gameId);
    }
    /*
    public Game addNoble(Game game, Player player) {
    	List<Player> players = game.getPlayers();
    	List<Noble> nobles =  game.getNobles();
    	return game;
    }*/
    public Game checkChampion(Long gameId) {
    	Game thisGame = gameServ.find(gameId);
    	List<Player> players = thisGame.getPlayers();
    	int score;
    	String cardOwner;
    	for(Player player : players) {
    		score = 0;
    		cardOwner =  player.getOwnedCards();
    		List<Card> pC =  player.getCards();
    		List<Noble> pN = player.getNobles();
    		for(Noble noble : pN) {
    			score+= noble.getScore();
    		}
    		for(int i = 0; i<pC.size();i++) {
    			score += cardOwner.charAt(i) == '1' ? pC.get(i).getScore() : 0;
    		}
    		if(score >= 15) {
    			thisGame.setChampion(player);
    		}
    	}
		gameServ.update(thisGame);
    	return thisGame;
    }
    
}
