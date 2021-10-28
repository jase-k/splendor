package com.jasekraft.splendor.mvc.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jasekraft.splendor.mvc.models.Card;
import com.jasekraft.splendor.mvc.models.Player;
import com.jasekraft.splendor.mvc.models.PlayerCard;

@Repository
public interface PlayerCardRepository extends CrudRepository<PlayerCard, Long>{ 
	List<PlayerCard> findAll();
	Optional<PlayerCard> findByCardAndPlayer(Card card, Player player);
}
