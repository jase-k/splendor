package com.jasekraft.splendor.mvc.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="players")
public class Player {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long character_id;
	
	//@JsonBackReference
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","players"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
	
	@ManyToMany
    @JoinTable(
        name = "players_cards", 
        joinColumns = @JoinColumn(name = "player_id"), 
        inverseJoinColumns = @JoinColumn(name = "card_id")
    )
	//@JsonIgnoreProperties("players")
	//@JsonManagedReference
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private List<Card> cards;
	
	private int turn;
	
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    		name = "players_tokens",
    		joinColumns = @JoinColumn(name = "player_id"),
    		inverseJoinColumns = @JoinColumn(name = "token_id")
    		)
    //@JsonIgnoreProperties("players")
    //@JsonManagedReference
    //@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    private List<Token> tokens;
    
    @Lob
    @Column(name = "token_pool", columnDefinition = "LONGBLOB")
    private HashMap <String, Integer> tokenPool;
    
    private String ownedCards;
	
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    		name = "players_nobles",
    		joinColumns = @JoinColumn(name = "player_id"),
    		inverseJoinColumns = @JoinColumn(name = "noble_id")
    		)
    //@JsonIgnoreProperties("players")
    //@JsonManagedReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Noble> nobles;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    		name = "games_players",
    		joinColumns = @JoinColumn(name = "player_id"),
    		inverseJoinColumns = @JoinColumn(name = "game_id")
    		)
    //@JsonIgnoreProperties("players")
    //@JsonBackReference
    @JsonIgnore
    private List<Game> games;
    
	//@JsonBackReference
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="game_id")
	private Game gameWon;
	
    public Player() {
    	this.cards = new ArrayList<>();
    	this.games = new ArrayList<>();
    	this.nobles = new ArrayList<>();
    	this.tokens = new ArrayList<>();
    	this.tokenPool = new HashMap<String, Integer>();
    }
    
    
    public Player(User user, Long character_id) {
    	this.user = user;
    	this.cards = new ArrayList<>();
    	this.games = new ArrayList<>();
    	this.nobles = new ArrayList<>();
    	this.tokens = new ArrayList<>();
    	this.tokenPool = new HashMap<String, Integer>();
    	this.tokenPool.put("ruby",0);
		this.tokenPool.put("sapphire",0);
		this.tokenPool.put("emerald",0);
		this.tokenPool.put("diamond",0);
		this.tokenPool.put("onyx",0);
		this.tokenPool.put("gold", 0);
		this.ownedCards = "";
		this.character_id = character_id;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public List<Token> getTokens() {
		return tokens;
	}

	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}

	public List<Noble> getNobles() {
		return nobles;
	}

	public void setNobles(List<Noble> nobles) {
		this.nobles = nobles;
	}

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}


	public HashMap<String, Integer> getTokenPool() {
		return tokenPool;
	}


	public void setTokenPool(HashMap<String, Integer> tokenPool) {
		this.tokenPool = tokenPool;
	}


	public int getTurn() {
		return turn;
	}


	public void setTurn(int turn) {
		this.turn = turn;
	}


	public Long getCharacter_id() {
		return character_id;
	}


	public void setCharacter_id(Long character_id) {
		this.character_id = character_id;
	}
    
	public String getOwnedCards() {
		return ownedCards;
	}


	public void setOwnedCards(String ownedCards) {
		this.ownedCards = ownedCards;
	}


	public Game getGameWon() {
		return gameWon;
	}


	public void setGameWon(Game gameWon) {
		this.gameWon = gameWon;
	}
}
