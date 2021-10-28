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
@Table(name="players_cards")
public class PlayerCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Boolean owned;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="card_id")
    private Card card;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="player_id")
    private Player player;

	public PlayerCard() {
		super();
	}

	public PlayerCard(Boolean owned, Card card, Player player) {
		super();
		this.owned = owned;
		this.card = card;
		this.player = player;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getOwned() {
		return owned;
	}

	public void setOwned(Boolean owned) {
		this.owned = owned;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
    
    
    
}
