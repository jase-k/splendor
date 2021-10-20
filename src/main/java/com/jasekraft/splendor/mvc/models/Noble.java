package com.jasekraft.splendor.mvc.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="nobles")
public class Noble {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	@PositiveOrZero
	private Integer score;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    		name = "players_nobles",
    		joinColumns = @JoinColumn(name = "noble_id"),
    		inverseJoinColumns = @JoinColumn(name = "player_id")
    		)
    //@JsonIgnoreProperties("nobles")
    //@JsonBackReference
    @JsonIgnore
    private List<Player> players;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    		name = "games_nobles",
    		joinColumns = @JoinColumn(name = "noble_id"),
    		inverseJoinColumns = @JoinColumn(name = "game_id")
    		)
    //@JsonIgnoreProperties("nobles")
    //@JsonBackReference
    @JsonIgnore
    private List<Game> games;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    		name = "nobles_cost",
    		joinColumns = @JoinColumn(name = "noble_id"),
    		inverseJoinColumns = @JoinColumn(name = "token_id")
    		)
    //@JsonIgnoreProperties("nobles")
    //@JsonManagedReference
    //@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    private List<Token> tokens;
    
	private HashMap <String, Integer> tokenCost;
	
    public Noble() {}
    
    public Noble(Integer score) {
    	this.score = score;
    	// Prevents null issues when accessing list after creation
    	this.tokens = new ArrayList<Token>();
    	this.players = new ArrayList<Player>();
    	this.games = new ArrayList<Game>();
    	this.tokenCost = new HashMap<String, Integer>();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}

	public List<Token> getTokens() {
		return tokens;
	}

	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}

	public HashMap<String, Integer> getTokenCost() {
		return tokenCost;
	}

	public void setTokenCost(HashMap<String, Integer> tokenCost) {
		this.tokenCost = tokenCost;
	}
}
