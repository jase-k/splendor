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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="cards")
public class Card {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(updatable=false)
	@DateTimeFormat(pattern="yyy-MM-dd")
	private Date createdAt;
	
	@DateTimeFormat(pattern="yyy-MM-dd")
	private Date updatedAt;
	
	@PositiveOrZero
	private Integer score;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="token_id")
    private Token token;
	
	@ManyToMany
    @JoinTable(
            name = "players_cards", 
            joinColumns = @JoinColumn(name = "card_id"), 
            inverseJoinColumns = @JoinColumn(name = "player_id")
        )
	private List<Player> players;
	
	@ManyToMany
    @JoinTable(
            name = "decks_cards", 
            joinColumns = @JoinColumn(name = "card_id"), 
            inverseJoinColumns = @JoinColumn(name = "deck_id")
        )
	private List<Deck> decks;
	
	@ManyToMany
	@JoinTable(
			name = "cards_cost",
			joinColumns = @JoinColumn(name = "card_id"),
			inverseJoinColumns = @JoinColumn(name = "token_id")
			)
	private List<Token> tokens;

	public Card() {
	}

	public Card(@Positive Integer score, Token token, List<Player> players, List<Deck> decks, List<Token> tokens) {
		super();
		this.score = score;
		this.token = token;
		this.players = players;
		this.decks = decks;
		this.tokens = tokens;
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

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public List<Deck> getDecks() {
		return decks;
	}

	public void setDecks(List<Deck> decks) {
		this.decks = decks;
	}

	public List<Token> getTokens() {
		return tokens;
	}

	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}
	
	
	
	
}
