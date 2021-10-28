## Splendor Game API
# Game Set-Up
To get started with this API, you can clone this repo, initialize the app and run the post request localhost:8080/dbinit with your MySQL password in the body: 
```js
let body = {
	password: DB_PASSWORD_HERE
	}
```
Make sure you create a schema in MySQL called 'splendor_schema'
This call should be used only once! Unless you delete your DB schema
This will initialize the db as below with the standard cards, tokens, and flowers rows added
![]{LINK}

# /users/new method=[POST]

# /games/new method=[GET]
Adds a game object to database and will allow players to join. *This does not add decks, tokens, or flowers to the object yet. 
```js
GAME = {
	"id" : 0,
	"champion" : [{USER}] //empty if game is unfinished | more than one if tie
	"turn" : 0
	"tokens" :[ {TOKEN}] //Always 0-40 tokens
	"players" : [{PLAYER}] //Always 2-4 players
	"decks" : [] //Always have 3
	"nobles" : [] //placeholder for nobles empty until garm
}
```
# /games/join method=[POST]
```js
let body = {
	game_id = GAME_ID,
	player_id = PLAYER_ID
	}
```
Adds player to game new Game object

# /games/start method=[POST]
```js
let body = {
	game_id = GAME_ID
	}
```
Adds 3 decks for game and shuffle them 
Add flowers to the game
Add tokens to the game
The game is ready for game play at this point. 


## Play Options

# /taketokens     method=[ POST]
```js
Body {
	Tokens : [ token_ids]
}
```
This call adds


# /takecard       method = [POST]
Body {
	cardobject
}


# /takenoble     method= [POST]
Body {
 	Noble_id
}

# /reserveacard 		method = [POST]
Body {
Card_id
}

# /endgame
Body {
	winner_id
}


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
# /games/{id}
```js
GAME = {
	"id" : 0,
	"champion" : [{USER}] //empty if game is unfinished | more than one if tie
	"turn" : 0 //total number of turns that have been played
	"tokens" :[{TOKEN}] //Always 0-40 tokens
	"players" : [{PLAYER}] //Always 2-4 players
	"decks" : [{DECK}] //Always have length of 3 | Each deck will have a list of cards
	"nobles" : [{NOBLE}] //Always one more than the number of players to start
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
