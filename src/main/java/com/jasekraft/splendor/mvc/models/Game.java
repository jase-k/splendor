package com.jasekraft.splendor.mvc.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(updatable=false)
	@DateTimeFormat(pattern="yyy-MM-dd")
	private Date createdAt;
	
	@DateTimeFormat(pattern="yyy-MM-dd")
	private Date updatedAt;
	
	@Positive
	private Integer turn;
	
    @OneToMany(mappedBy="game", fetch = FetchType.LAZY)
    private List<Deck> decks;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="champion_id")
    private Player champion;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    		name = "games_tokens",
    		joinColumns = @JoinColumn(name = "game_id"),
    		inverseJoinColumns = @JoinColumn(name = "token_id")
    		)
    private List<Token> tokens;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    		name = "games_nobles",
    		joinColumns = @JoinColumn(name = "game_id"),
    		inverseJoinColumns = @JoinColumn(name = "noble_id")
    		)
    private List<Noble> nobles;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    		name = "games_players",
    		joinColumns = @JoinColumn(name = "game_id"),
    		inverseJoinColumns = @JoinColumn(name = "player_id")
    		)
    private List<Player> players;
    

	public Game() {
	}

	public Game(@Positive Integer turn, List<Deck> decks, Player champion, List<Token> tokens, List<Noble> nobles) {
		super();
		this.turn = turn;
		this.decks = decks;
		this.champion = champion;
		this.tokens = tokens;
		this.nobles = nobles;
	}
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
		this.updatedAt = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Integer getTurn() {
		return turn;
	}

	public void setTurn(Integer turn) {
		this.turn = turn;
	}

	public List<Deck> getDecks() {
		return decks;
	}

	public void setDecks(List<Deck> decks) {
		this.decks = decks;
	}

	public Player getChampion() {
		return champion;
	}

	public void setChampion(Player champion) {
		this.champion = champion;
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
	
	
	
    
    
}
