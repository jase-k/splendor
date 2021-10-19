package com.jasekraft.splendor.mvc.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="decks_cards")
public class CardDeck {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Integer position;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="card_id")
    private Card card;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="deck_id")
    private Deck deck;

	public CardDeck() {
	}

	public CardDeck(Integer position, Card card, Deck deck) {
		super();
		this.position = position;
		this.card = card;
		this.deck = deck;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}
    
	   
    
}
