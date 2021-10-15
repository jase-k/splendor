package com.jasekraft.splendor.mvc.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="players")
public class Player {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
	
	@ManyToMany
    @JoinTable(
        name = "players_cards", 
        joinColumns = @JoinColumn(name = "player_id"), 
        inverseJoinColumns = @JoinColumn(name = "card_id")
    )
	private List<Card> cards;
	
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    		name = "players_tokens",
    		joinColumns = @JoinColumn(name = "player_id"),
    		inverseJoinColumns = @JoinColumn(name = "token_id")
    		)
    private List<Token> tokens;
	
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    		name = "players_nobles",
    		joinColumns = @JoinColumn(name = "player_id"),
    		inverseJoinColumns = @JoinColumn(name = "noble_id")
    		)
    private List<Noble> nobles;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    		name = "games_players",
    		joinColumns = @JoinColumn(name = "player_id"),
    		inverseJoinColumns = @JoinColumn(name = "game_id")
    		)
    private List<Game> games;
    
    public Player() {}

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
    
    
}
