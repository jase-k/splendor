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

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="decks")
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(updatable=false)
	@DateTimeFormat(pattern="yyy-MM-dd")
	private Date createdAt;
	
	@DateTimeFormat(pattern="yyy-MM-dd")
	private Date updatedAt;
	
	private String color;
	
	private Integer current_position;
	    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="game_id")
    private Game game;
    
    @ManyToMany
    @JoinTable(
            name = "decks_cards", 
            joinColumns = @JoinColumn(name = "deck_id"), 
            inverseJoinColumns = @JoinColumn(name = "card_id")
        )
	private List<Card> cards;

	public Deck() {

	}

	public Deck(String color, Integer current_position, Game game, List<Card> cards) {
		super();
		this.color = color;
		this.current_position = current_position;
		this.game = game;
		this.cards = cards;
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
    
}
