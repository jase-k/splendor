Splendor Game App
Game Set-Up

/newgame
Adds a game object to database

/joingame
Adds player to game

/startgame
Adds 3 decks for game and shuffle them 
Add nobles to the game
Add tokens to the game


Play Options

/taketokens     method=[ POST]
Body {
	Tokens : [ token_ids]
}


/takecard       method = [POST]
Body {
	cardobject
}


/takenoble     method= [POST]
Body {
 	Noble_id
}

/reserveacard 		method = [POST]
Body {
Card_id
}

/endgame
Body {
	winner_id
}

