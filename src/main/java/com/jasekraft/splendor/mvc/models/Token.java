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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name; 
    
    @JsonBackReference
    @OneToMany(mappedBy="token", fetch = FetchType.LAZY)
    private List<Card> cardvalues;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
			name = "cards_cost",
			joinColumns = @JoinColumn(name = "token_id"),
			inverseJoinColumns = @JoinColumn(name = "card_id")
    		)
    //@JsonIgnoreProperties("tokens")
    @JsonBackReference
    private List<Card> cards; 
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    		name = "nobles_cost",
    		joinColumns = @JoinColumn(name = "token_id"),
    		inverseJoinColumns = @JoinColumn(name = "noble_id")
    		)
    //@JsonIgnoreProperties("tokens")
    @JsonBackReference
    private List<Noble> nobles;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    		name = "games_tokens",
    		joinColumns = @JoinColumn(name = "token_id"),
    		inverseJoinColumns = @JoinColumn(name = "game_id")
    		)
    //@JsonIgnoreProperties("tokens")
    @JsonBackReference
    private List<Game> games; 
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    		name = "players_tokens",
    		joinColumns = @JoinColumn(name = "token_id"),
    		inverseJoinColumns = @JoinColumn(name = "player_id")
    		)
    //@JsonIgnoreProperties("tokens")
    @JsonBackReference
    private List<Player> players;

    
	public Token() {
	}
	
	public Token(String name) {
		this.name = name;
	}

	public Token(String name, List<Card> cardvalues, List<Card> cards, List<Noble> nobles, List<Game> games,
			List<Player> players) {
		super();
		this.name = name;
		this.cardvalues = cardvalues;
		this.cards = cards;
		this.nobles = nobles;
		this.games = games;
		this.players = players;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public List<Card> getCardvalues() {
		return cardvalues;
	}


	public void setCardvalues(List<Card> cardvalues) {
		this.cardvalues = cardvalues;
	}


	public List<Card> getCards() {
		return cards;
	}


	public void setCards(List<Card> cards) {
		this.cards = cards;
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


	public List<Player> getPlayers() {
		return players;
	}


	public void setPlayers(List<Player> players) {
		this.players = players;
	}
    
	
    
}
