# Splendor Game API
## Game Set-Up
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

## /users/new method=[POST]
```js
let body = {
	username: USERNAME,
	email: EMAIL,
	password: PASSWORD,
	confirm: CONFIRM_PASSWORD 
	}
```
This will create a new user and return a User Object: 
```js
let USER = {
	"id" : INT,
	"username" : USERNAME,
	"email" : EMAIL
	"created_at" : DATETIME,
	"updated_at" : DATETIME,
}
```
* note that the password is not returned, but it does get hashed in the db for login purposes.*

# /login
```js
let body = {
	username: USERNAME,
	password: PASSWORD
	}
```
Upon successful login a ```USER``` object will be return. (see above)

## /games/new method=[GET]
Adds a game object to database and will allow players to join. *This does not add decks, tokens, or nobles to the object yet. 
```js
let GAME = {
	"id" : 0,
	"champion" : [{USER}] //empty if game is unfinished | more than one if tie
	"turn" : 0
	"tokenPool" :[ {TOKEN}] //Always 0-40 tokens
	"players" : [{PLAYER}] //Always 2-4 players
	"decks" : [] //Always have 3
	"nobles" : [] //placeholder for nobles empty until garm
}
```

# /games/join method=[POST]
```js
let body = {
	game_id = GAME_ID,
	user_id = USER_ID,
	character_id = INT //If you don't utilize characters in your application just pass 0. 
	}
```
Adds player to game new Game object. *```character_id``` allows for to create characters and store which player is associated with the character*
```js
PLAYER = {
	"id" : 0,
	"user" : { USER },
	"noble" : [{NOBLE}], 
	"tokenPool" : {
			"ruby" : 0, 
			"emerald" : 0, 
			"sapphire" : 0, 
			"diamond" : 0, 
			"onyx" : 0
		},
	"cards" : [{CARD}],
	"turn" : INT, // always be 0-3 based on how many other players in the game
	"ownedCards" : "" // String of 1's & 0's correlating with the array of cards. 1 = owned, 0 = reserved
}
```

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


# Play Options

## /games/GAME_ID/takecard/PLAYER_ID         method = [POST]
```js
let body {
	card_id: CARD_ID
}
```
This call removes the ```CARD``` object from the ```DECK``` and adds the ```CARD``` to the ```PLAYER.cards``` and a 1 to the end of the ```PLAYER.ownedcards```
Card OBJECT: 
```js
CARD = { 
	"id" : 0,
	"score" : 0, 
	"tokenName" : "diamond",
	"tokenCost" : {
		"ruby" : 3, 
		"emerald" : 0, 
		"sapphire" : 0, 
		"diamond" : 0, 
		"onyx" : 3
	}
}
```

## /games/GAME_ID/taketokens/PLAYER_ID     method=[ POST]
```js
Body {
	Tokens : [ token_ids]
}
```
This call removes tokens from the ```tokenPool``` of the ```GAME``` object and adds them to the ```PLAYER``` object. 
*Constraints = Cannot take gold coin unless resering a card. (see below) Cannot take 2 of the same token if that would leave less than 2 tokens in the ```GAME.tokenPool```. Must take either 2 of the same tokens, or 3 different ones. 

## /games/GAME_ID/reservecard/PLAYER_ID  		method = [POST]
```js
let body {
	card_id: CARD_ID
}
```
This call will remove one gold token from the ```GAME.tokenPool``` and add it to ```PLAYER```. It also removes the ```CARD``` object from the ```DECK``` and adds the ```CARD``` to the ```PLAYER.cards``` and a 0 to the end of the ```PLAYER.ownedcards```



## /games/GAME_ID /takenoble/PLAYER_ID      method= [POST]
```js
let body {
 	Noble_id
}
```
This call removes the ```NOBLE``` object from the game and adds it to the ```PLAYER```

NOBLE Object: 
```js
NOBLE = { 
	"id" : 3, 
	"score" : 3, 
	"tokenCost" : {
		"ruby" : 0, 
		"emerald" : 0, 
		"sapphire" : 0, 
		"diamond" : 0, 
		"onyx" : 0
	}
}
```

## /endgame
Body {
	winner_id
}




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
