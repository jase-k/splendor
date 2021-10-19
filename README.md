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

```js
GAME = {
	"id" : 0,
	"champion" : {USER} //empty if game is unfinished
	"turn" : 1
	"tokens" :[ {TOKEN}] //Always 0-40 tokens
	"players" : [{PLAYER}] //Always 2-4 players
	"decks" : [{DECK}] //Always have 3
	"nobles" : [{NOBLE}] //Always one more than the number of players
}
```
```js
PLAYER = {
	"id" : 0,
	"user" : { USER },
	"noble" : [{NOBLE}], 
	"tokens" : {
					"ruby" : 0, 
					"emerald" : 0, 
					"sapphire" : 0, 
					"diamond" : 0, 
					"onyx" : 0
				},
	"cards" : [{CARD}],
}
```

```js
USER = {
	"id" : 0,
	"username" : "jaecheese",
	"created_at" : "2021-10-15",
	"updated_at" : "2021-10-15",
}
```
```js
DECK = {
	"id" : 0, 
	"game_id" : 0, 
	"color" : "blue", 
	"cards" : [{CARD}]
 }
```
```js
NOBLE = { 
	"id" : 0, 
	"score" : 3, 
	"cost" : {
		"ruby" : 0, 
		"emerald" : 0, 
		"sapphire" : 0, 
		"diamond" : 0, 
		"onyx" : 0
	}
}
```

```js
CARD = { 
	"id" : 0,
	"score" : 0, 
	"deck_id" : 0, 
	"token" : "diamond",
	"cost" : {
		"ruby" : 3, 
		"emerald" : 0, 
		"sapphire" : 0, 
		"diamond" : 0, 
		"onyx" : 3
	}
}
```

```js
GAME = {
	"id" : 0,
	"champion" : {
					"id" : 0,
					"username" : "jaecheese",
					"created_at" : "2021-10-15",
					"updated_at" : "2021-10-15",
				},
	"turn" : 1
	"tokens" : {
					"ruby" : 0, 
					"emerald" : 0, 
					"sapphire" : 0, 
					"diamond" : 0, 
					"onyx" : 0
				},
	"nobles" : [
					{ 
						"id" : 0, 
						"score" : 3, 
						"cost" : {
							"ruby" : 0, 
							"emerald" : 0, 
							"sapphire" : 0, 
							"diamond" : 0, 
							"onyx" : 0
					},
					{ 
						"id" : 0, 
						"score" : 3, 
						"cost" : {
							"ruby" : 0, 
							"emerald" : 0, 
							"sapphire" : 0, 
							"diamond" : 0, 
							"onyx" : 0
					},
				]
	"players" : [ 
					{
						"id" : 0,
						"user" : {
									"id" : 0,
									"username" : "jaecheese",
									"created_at" : "2021-10-15",
									"updated_at" : "2021-10-15",
								},
						"nobles" : 	[
										{ 
										"id" : 0, 
										"score" : 3, 
										"cost" : {
											"ruby" : 0, 
											"emerald" : 0, 
											"sapphire" : 0, 
											"diamond" : 0, 
											"onyx" : 0
											}
										},
									], 
						"tokens" : 	{
										"ruby" : 0, 
										"emerald" : 0, 
										"sapphire" : 0, 
										"diamond" : 0, 
										"onyx" : 0
									},
						"cards" : [
									{ 
										"id" : 0,
										"score" : 0, 
										"deck_id" : 0, 
										"token" : "ruby",
										"cost" : {
													"ruby" : 0, 
													"emerald" : 0, 
													"sapphire" : 0, 
													"diamond" : 0, 
													"onyx" : 0
												}
									}
									],
						},
						{
						"id" : 0,
						"user" : {
									"id" : 0,
									"username" : "jaecheese",
									"created_at" : "2021-10-15",
									"updated_at" : "2021-10-15",
								},
						"nobles" : 	[
										{ 
										"id" : 0, 
										"score" : 3, 
										"cost" : {
											"ruby" : 0, 
											"emerald" : 0, 
											"sapphire" : 0, 
											"diamond" : 0, 
											"onyx" : 0
											}
										},
									], 
						"tokens" : 	{
										"ruby" : 0, 
										"emerald" : 0, 
										"sapphire" : 0, 
										"diamond" : 0, 
										"onyx" : 0
									},
						"cards" : [
									{ 
										"id" : 0,
										"score" : 0, 
										"deck_id" : 0, 
										"token" : "ruby",
										"cost" : {
													"ruby" : 0, 
													"emerald" : 0, 
													"sapphire" : 0, 
													"diamond" : 0, 
													"onyx" : 0
												}
									}
									],
						},
					]
	"decks" : [ 
		{
			"id" : 0, 
			"game_id" : 0, 
			"color" : "green", 
			"cards" : [	
							{ 
								"id" : 0,
								"score" : 0, 
								"deck_id" : 0, 
								"token" : "ruby",
								"cost" : {
											"ruby" : 0, 
											"emerald" : 1, 
											"sapphire" : 1, 
											"diamond" : 1, 
											"onyx" : 1
										}
							},
							{ 
								"id" : 0,
								"score" : 0, 
								"deck_id" : 0, 
								"token" : "diamond",
								"cost" : {
											"ruby" : 4, 
											"emerald" : 0, 
											"sapphire" : 0, 
											"diamond" : 0, 
											"onyx" : 0
										}
							},// alot more cards would go here
						]
		},
		{
			"id" : 0, 
			"game_id" : 0, 
			"color" : "yellow", 
			"cards" : [	
							{ 
								"id" : 0,
								"score" : 0, 
								"deck_id" : 0, 
								"token" : "ruby",
								"cost" : {
											"ruby" : 0, 
											"emerald" : 1, 
											"sapphire" : 1, 
											"diamond" : 1, 
											"onyx" : 1
										}
							},
							{ 
								"id" : 0,
								"score" : 0, 
								"deck_id" : 0, 
								"token" : "diamond",
								"cost" : {
											"ruby" : 4, 
											"emerald" : 0, 
											"sapphire" : 0, 
											"diamond" : 0, 
											"onyx" : 0
										}
							},// alot more cards would go here
						]
		},
		{
			"id" : 0, 
			"game_id" : 0, 
			"color" : "blue", 
			"cards" : [	
							{ 
								"id" : 0,
								"score" : 0, 
								"deck_id" : 0, 
								"token" : "ruby",
								"cost" : {
											"ruby" : 0, 
											"emerald" : 1, 
											"sapphire" : 1, 
											"diamond" : 1, 
											"onyx" : 1
										}
							},
							{ 
								"id" : 0,
								"score" : 0, 
								"deck_id" : 0, 
								"token" : "diamond",
								"cost" : {
											"ruby" : 4, 
											"emerald" : 0, 
											"sapphire" : 0, 
											"diamond" : 0, 
											"onyx" : 0
										}
							},// alot more cards would go here
						]
		},
			]
}
```