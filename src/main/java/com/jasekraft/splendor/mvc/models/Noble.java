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
import javax.persistence.Table;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name="noble")
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
    private List<Player> players;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    		name = "games_nobles",
    		joinColumns = @JoinColumn(name = "noble_id"),
    		inverseJoinColumns = @JoinColumn(name = "game_id")
    		)
    private List<Game> games;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    		name = "nobles_cost",
    		joinColumns = @JoinColumn(name = "noble_id"),
    		inverseJoinColumns = @JoinColumn(name = "token_id")
    		)
    private List<Token> tokens;
    
    public Noble() {}
    
    public Noble(Integer score) {
    	this.score = score;
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
}
