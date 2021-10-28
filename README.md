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
This will initialize the db as below with the standard cards, tokens, and noble rows added
!["DB ERD"](https://github.com/jase-k/splendor/blob/development/splendor%20ERD.PNG)

### /users/new method=[POST]
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

### /login
```js
let body = {
	username: USERNAME,
	password: PASSWORD
	}
```
Upon successful login a ```USER``` object will be return. (see above)

### /games/new method=[GET]
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

### /games/join method=[POST]
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

### /games/start method=[POST]
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

### /games/GAME_ID/takecard/PLAYER_ID         method = [POST]
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

### /games/GAME_ID/taketokens/PLAYER_ID     method=[ POST]
```js
Body {
	Tokens : [ token_ids]
}
```
This call removes tokens from the ```tokenPool``` of the ```GAME``` object and adds them to the ```PLAYER``` object. 
*Constraints = Cannot take gold coin unless resering a card. (see below) Cannot take 2 of the same token if that would leave less than 2 tokens in the ```GAME.tokenPool```. Must take either 2 of the same tokens, or 3 different ones. 

### /games/GAME_ID/reservecard/PLAYER_ID  		method = [POST]
```js
let body {
	card_id: CARD_ID
}
```
This call will remove one gold token from the ```GAME.tokenPool``` and add it to ```PLAYER```. It also removes the ```CARD``` object from the ```DECK``` and adds the ```CARD``` to the ```PLAYER.cards``` and a 0 to the end of the ```PLAYER.ownedcards```



### /games/GAME_ID /takenoble/PLAYER_ID      method= [POST]
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

### /games/GAME_ID method=[GET]
This call will return a updated ```GAME``` object. 
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
*Full Game Object Example Below*

## Other Notes

### Game End
Game ends at the end of the round when one or more player has 15 points. (Calculated by owned cards and nobles)
The game object will appoint the champion at this point, but allow for further game play. 




COMPLETE ```GAME``` OBJECT SAMPLE DATA
```js
{
    "id": 7,
    "turn": 0,
    "decks": [
        {
            "id": 16,
            "color": "green",
            "current_position": 0,
            "cards": [
                {
                    "id": 16,
                    "score": 1,
                    "tokenName": "sapphire",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 0,
                        "emerald": 0,
                        "sapphire": 0,
                        "ruby": 4
                    }
                },
                {
                    "id": 29,
                    "score": 0,
                    "tokenName": "diamond",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 0,
                        "emerald": 0,
                        "sapphire": 0,
                        "ruby": 3
                    }
                },
                {
                    "id": 32,
                    "score": 1,
                    "tokenName": "diamond",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 0,
                        "emerald": 4,
                        "sapphire": 0,
                        "ruby": 0
                    }
                },
                {
                    "id": 25,
                    "score": 0,
                    "tokenName": "diamond",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 1,
                        "emerald": 1,
                        "sapphire": 1,
                        "ruby": 1
                    }
                },
                {
                    "id": 33,
                    "score": 0,
                    "tokenName": "emerald",
                    "tokenCost": {
                        "diamond": 1,
                        "onyx": 1,
                        "emerald": 0,
                        "sapphire": 1,
                        "ruby": 1
                    }
                },
                {
                    "id": 36,
                    "score": 0,
                    "tokenName": "emerald",
                    "tokenCost": {
                        "diamond": 1,
                        "onyx": 0,
                        "emerald": 1,
                        "sapphire": 3,
                        "ruby": 0
                    }
                },
                {
                    "id": 30,
                    "score": 0,
                    "tokenName": "diamond",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 1,
                        "emerald": 2,
                        "sapphire": 1,
                        "ruby": 1
                    }
                },
                {
                    "id": 22,
                    "score": 0,
                    "tokenName": "ruby",
                    "tokenCost": {
                        "diamond": 2,
                        "onyx": 1,
                        "emerald": 1,
                        "ruby": 0,
                        "sapphire": 1
                    }
                },
                {
                    "id": 35,
                    "score": 0,
                    "tokenName": "emerald",
                    "tokenCost": {
                        "diamond": 2,
                        "onyx": 0,
                        "emerald": 0,
                        "sapphire": 0,
                        "ruby": 2
                    }
                },
                {
                    "id": 26,
                    "score": 0,
                    "tokenName": "diamond",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 1,
                        "emerald": 0,
                        "sapphire": 0,
                        "ruby": 2
                    }
                },
                {
                    "id": 13,
                    "score": 0,
                    "tokenName": "sapphire",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 3,
                        "emerald": 0,
                        "sapphire": 0,
                        "ruby": 0
                    }
                },
                {
                    "id": 4,
                    "score": 0,
                    "tokenName": "onyx",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 1,
                        "emerald": 1,
                        "sapphire": 0,
                        "ruby": 3
                    }
                },
                {
                    "id": 39,
                    "score": 0,
                    "tokenName": "emerald",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 2,
                        "emerald": 0,
                        "sapphire": 1,
                        "ruby": 2
                    }
                },
                {
                    "id": 7,
                    "score": 0,
                    "tokenName": "onyx",
                    "tokenCost": {
                        "diamond": 2,
                        "onyx": 0,
                        "emerald": 0,
                        "sapphire": 2,
                        "ruby": 1
                    }
                },
                {
                    "id": 5,
                    "score": 0,
                    "tokenName": "onyx",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 0,
                        "emerald": 3,
                        "sapphire": 0,
                        "ruby": 0
                    }
                },
                {
                    "id": 31,
                    "score": 0,
                    "tokenName": "diamond",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 1,
                        "emerald": 2,
                        "sapphire": 2,
                        "ruby": 0
                    }
                },
                {
                    "id": 34,
                    "score": 0,
                    "tokenName": "emerald",
                    "tokenCost": {
                        "diamond": 2,
                        "onyx": 0,
                        "emerald": 0,
                        "sapphire": 1,
                        "ruby": 0
                    }
                },
                {
                    "id": 21,
                    "score": 0,
                    "tokenName": "ruby",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 0,
                        "emerald": 0,
                        "ruby": 0,
                        "sapphire": 3
                    }
                },
                {
                    "id": 14,
                    "score": 0,
                    "tokenName": "sapphire",
                    "tokenCost": {
                        "diamond": 1,
                        "onyx": 1,
                        "emerald": 1,
                        "sapphire": 0,
                        "ruby": 2
                    }
                },
                {
                    "id": 10,
                    "score": 0,
                    "tokenName": "sapphire",
                    "tokenCost": {
                        "diamond": 1,
                        "onyx": 2,
                        "emerald": 0,
                        "sapphire": 0,
                        "ruby": 0
                    }
                },
                {
                    "id": 15,
                    "score": 0,
                    "tokenName": "sapphire",
                    "tokenCost": {
                        "diamond": 1,
                        "onyx": 0,
                        "emerald": 2,
                        "sapphire": 0,
                        "ruby": 2
                    }
                },
                {
                    "id": 27,
                    "score": 0,
                    "tokenName": "diamond",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 0,
                        "emerald": 0,
                        "sapphire": 2,
                        "ruby": 2
                    }
                },
                {
                    "id": 12,
                    "score": 0,
                    "tokenName": "sapphire",
                    "tokenCost": {
                        "diamond": 3,
                        "onyx": 1,
                        "emerald": 0,
                        "sapphire": 1,
                        "ruby": 0
                    }
                },
                {
                    "id": 19,
                    "score": 0,
                    "tokenName": "ruby",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 2,
                        "emerald": 0,
                        "ruby": 0,
                        "sapphire": 2
                    }
                },
                {
                    "id": 18,
                    "score": 0,
                    "tokenName": "ruby",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 0,
                        "emerald": 1,
                        "ruby": 0,
                        "sapphire": 2
                    }
                },
                {
                    "id": 11,
                    "score": 0,
                    "tokenName": "sapphire",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 2,
                        "emerald": 2,
                        "sapphire": 0,
                        "ruby": 0
                    }
                },
                {
                    "id": 20,
                    "score": 0,
                    "tokenName": "ruby",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 0,
                        "emerald": 3,
                        "ruby": 1,
                        "sapphire": 1
                    }
                },
                {
                    "id": 2,
                    "score": 0,
                    "tokenName": "onyx",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 0,
                        "emerald": 2,
                        "sapphire": 0,
                        "ruby": 1
                    }
                },
                {
                    "id": 38,
                    "score": 0,
                    "tokenName": "emerald",
                    "tokenCost": {
                        "diamond": 1,
                        "onyx": 2,
                        "emerald": 0,
                        "sapphire": 1,
                        "ruby": 1
                    }
                },
                {
                    "id": 24,
                    "score": 1,
                    "tokenName": "ruby",
                    "tokenCost": {
                        "diamond": 4,
                        "onyx": 0,
                        "emerald": 0,
                        "ruby": 0,
                        "sapphire": 0
                    }
                },
                {
                    "id": 6,
                    "score": 0,
                    "tokenName": "onyx",
                    "tokenCost": {
                        "diamond": 1,
                        "onyx": 0,
                        "emerald": 1,
                        "sapphire": 2,
                        "ruby": 1
                    }
                },
                {
                    "id": 28,
                    "score": 0,
                    "tokenName": "diamond",
                    "tokenCost": {
                        "diamond": 1,
                        "onyx": 3,
                        "emerald": 0,
                        "sapphire": 0,
                        "ruby": 1
                    }
                },
                {
                    "id": 17,
                    "score": 0,
                    "tokenName": "ruby",
                    "tokenCost": {
                        "diamond": 1,
                        "onyx": 1,
                        "emerald": 1,
                        "ruby": 0,
                        "sapphire": 1
                    }
                },
                {
                    "id": 23,
                    "score": 0,
                    "tokenName": "ruby",
                    "tokenCost": {
                        "diamond": 2,
                        "onyx": 2,
                        "emerald": 1,
                        "ruby": 0,
                        "sapphire": 0
                    }
                },
                {
                    "id": 8,
                    "score": 1,
                    "tokenName": "onyx",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 0,
                        "emerald": 0,
                        "sapphire": 4,
                        "ruby": 0
                    }
                },
                {
                    "id": 37,
                    "score": 0,
                    "tokenName": "emerald",
                    "tokenCost": {
                        "diamond": 3,
                        "onyx": 0,
                        "emerald": 0,
                        "sapphire": 0,
                        "ruby": 0
                    }
                },
                {
                    "id": 40,
                    "score": 1,
                    "tokenName": "emerald",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 4,
                        "emerald": 0,
                        "sapphire": 0,
                        "ruby": 0
                    }
                },
                {
                    "id": 9,
                    "score": 0,
                    "tokenName": "sapphire",
                    "tokenCost": {
                        "diamond": 1,
                        "onyx": 1,
                        "emerald": 1,
                        "sapphire": 0,
                        "ruby": 1
                    }
                },
                {
                    "id": 1,
                    "score": 0,
                    "tokenName": "onyx",
                    "tokenCost": {
                        "diamond": 1,
                        "onyx": 0,
                        "emerald": 1,
                        "sapphire": 1,
                        "ruby": 1
                    }
                },
                {
                    "id": 3,
                    "score": 0,
                    "tokenName": "onyx",
                    "tokenCost": {
                        "diamond": 2,
                        "onyx": 0,
                        "emerald": 2,
                        "sapphire": 0,
                        "ruby": 0
                    }
                }
            ]
        },
        {
            "id": 17,
            "color": "yellow",
            "current_position": 0,
            "cards": [
                {
                    "id": 53,
                    "score": 1,
                    "tokenName": "ruby",
                    "tokenCost": {
                        "diamond": 3,
                        "onyx": 3,
                        "emerald": 0,
                        "ruby": 0,
                        "sapphire": 2
                    }
                },
                {
                    "id": 69,
                    "score": 2,
                    "tokenName": "emerald",
                    "tokenCost": {
                        "diamond": 5,
                        "onyx": 0,
                        "emerald": 0,
                        "sapphire": 3,
                        "ruby": 0
                    }
                },
                {
                    "id": 48,
                    "score": 1,
                    "tokenName": "sapphire",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 3,
                        "emerald": 3,
                        "sapphire": 2,
                        "ruby": 0
                    }
                },
                {
                    "id": 42,
                    "score": 1,
                    "tokenName": "onyx",
                    "tokenCost": {
                        "diamond": 3,
                        "onyx": 2,
                        "emerald": 3,
                        "sapphire": 0,
                        "ruby": 0
                    }
                },
                {
                    "id": 41,
                    "score": 1,
                    "tokenName": "onyx",
                    "tokenCost": {
                        "diamond": 3,
                        "onyx": 0,
                        "emerald": 2,
                        "sapphire": 3,
                        "ruby": 0
                    }
                },
                {
                    "id": 60,
                    "score": 1,
                    "tokenName": "diamond",
                    "tokenCost": {
                        "diamond": 2,
                        "onyx": 0,
                        "emerald": 0,
                        "sapphire": 3,
                        "ruby": 3
                    }
                },
                {
                    "id": 46,
                    "score": 3,
                    "tokenName": "onyx",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 6,
                        "emerald": 0,
                        "sapphire": 0,
                        "ruby": 0
                    }
                },
                {
                    "id": 52,
                    "score": 3,
                    "tokenName": "sapphire",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 0,
                        "emerald": 0,
                        "sapphire": 6,
                        "ruby": 0
                    }
                },
                {
                    "id": 50,
                    "score": 2,
                    "tokenName": "sapphire",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 0,
                        "emerald": 5,
                        "sapphire": 0,
                        "ruby": 0
                    }
                },
                {
                    "id": 65,
                    "score": 1,
                    "tokenName": "emerald",
                    "tokenCost": {
                        "diamond": 2,
                        "onyx": 3,
                        "emerald": 0,
                        "sapphire": 0,
                        "ruby": 3
                    }
                },
                {
                    "id": 54,
                    "score": 1,
                    "tokenName": "ruby",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 3,
                        "emerald": 0,
                        "ruby": 2,
                        "sapphire": 3
                    }
                },
                {
                    "id": 56,
                    "score": 2,
                    "tokenName": "ruby",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 5,
                        "emerald": 0,
                        "ruby": 0,
                        "sapphire": 0
                    }
                },
                {
                    "id": 67,
                    "score": 2,
                    "tokenName": "emerald",
                    "tokenCost": {
                        "diamond": 4,
                        "onyx": 1,
                        "emerald": 0,
                        "sapphire": 2,
                        "ruby": 0
                    }
                },
                {
                    "id": 64,
                    "score": 3,
                    "tokenName": "diamond",
                    "tokenCost": {
                        "diamond": 6,
                        "onyx": 0,
                        "emerald": 0,
                        "sapphire": 0,
                        "ruby": 0
                    }
                },
                {
                    "id": 68,
                    "score": 2,
                    "tokenName": "emerald",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 0,
                        "emerald": 0,
                        "sapphire": 0,
                        "ruby": 5
                    }
                },
                {
                    "id": 45,
                    "score": 2,
                    "tokenName": "onyx",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 0,
                        "emerald": 5,
                        "sapphire": 0,
                        "ruby": 3
                    }
                },
                {
                    "id": 70,
                    "score": 3,
                    "tokenName": "emerald",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 0,
                        "emerald": 6,
                        "sapphire": 0,
                        "ruby": 0
                    }
                },
                {
                    "id": 49,
                    "score": 2,
                    "tokenName": "sapphire",
                    "tokenCost": {
                        "diamond": 2,
                        "onyx": 4,
                        "emerald": 0,
                        "sapphire": 0,
                        "ruby": 1
                    }
                },
                {
                    "id": 63,
                    "score": 2,
                    "tokenName": "diamond",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 3,
                        "emerald": 0,
                        "sapphire": 0,
                        "ruby": 5
                    }
                },
                {
                    "id": 55,
                    "score": 2,
                    "tokenName": "ruby",
                    "tokenCost": {
                        "diamond": 1,
                        "onyx": 0,
                        "emerald": 2,
                        "ruby": 0,
                        "sapphire": 4
                    }
                },
                {
                    "id": 43,
                    "score": 2,
                    "tokenName": "onyx",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 0,
                        "emerald": 4,
                        "sapphire": 1,
                        "ruby": 2
                    }
                },
                {
                    "id": 57,
                    "score": 2,
                    "tokenName": "ruby",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 0,
                        "emerald": 3,
                        "ruby": 0,
                        "sapphire": 5
                    }
                },
                {
                    "id": 66,
                    "score": 1,
                    "tokenName": "emerald",
                    "tokenCost": {
                        "diamond": 3,
                        "onyx": 0,
                        "emerald": 2,
                        "sapphire": 0,
                        "ruby": 3
                    }
                },
                {
                    "id": 47,
                    "score": 1,
                    "tokenName": "sapphire",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 2,
                        "emerald": 3,
                        "sapphire": 0,
                        "ruby": 3
                    }
                },
                {
                    "id": 44,
                    "score": 2,
                    "tokenName": "onyx",
                    "tokenCost": {
                        "diamond": 5,
                        "onyx": 0,
                        "emerald": 0,
                        "sapphire": 0,
                        "ruby": 0
                    }
                },
                {
                    "id": 58,
                    "score": 3,
                    "tokenName": "ruby",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 0,
                        "emerald": 0,
                        "ruby": 6,
                        "sapphire": 0
                    }
                },
                {
                    "id": 59,
                    "score": 1,
                    "tokenName": "diamond",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 0,
                        "emerald": 3,
                        "sapphire": 3,
                        "ruby": 2
                    }
                },
                {
                    "id": 61,
                    "score": 2,
                    "tokenName": "diamond",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 2,
                        "emerald": 1,
                        "sapphire": 0,
                        "ruby": 4
                    }
                },
                {
                    "id": 62,
                    "score": 2,
                    "tokenName": "diamond",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 0,
                        "emerald": 0,
                        "sapphire": 5,
                        "ruby": 0
                    }
                },
                {
                    "id": 51,
                    "score": 2,
                    "tokenName": "sapphire",
                    "tokenCost": {
                        "diamond": 3,
                        "onyx": 5,
                        "emerald": 0,
                        "sapphire": 0,
                        "ruby": 0
                    }
                }
            ]
        },
        {
            "id": 18,
            "color": "blue",
            "current_position": 0,
            "cards": [
                {
                    "id": 76,
                    "score": 4,
                    "tokenName": "sapphire",
                    "tokenCost": {
                        "diamond": 7,
                        "onyx": 0,
                        "emerald": 0,
                        "sapphire": 0,
                        "ruby": 0
                    }
                },
                {
                    "id": 71,
                    "score": 3,
                    "tokenName": "onyx",
                    "tokenCost": {
                        "diamond": 3,
                        "onyx": 0,
                        "emerald": 5,
                        "sapphire": 3,
                        "ruby": 3
                    }
                },
                {
                    "id": 73,
                    "score": 4,
                    "tokenName": "onyx",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 3,
                        "emerald": 3,
                        "sapphire": 0,
                        "ruby": 6
                    }
                },
                {
                    "id": 83,
                    "score": 3,
                    "tokenName": "diamond",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 3,
                        "emerald": 3,
                        "sapphire": 3,
                        "ruby": 5
                    }
                },
                {
                    "id": 74,
                    "score": 5,
                    "tokenName": "onyx",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 3,
                        "emerald": 0,
                        "sapphire": 0,
                        "ruby": 7
                    }
                },
                {
                    "id": 80,
                    "score": 4,
                    "tokenName": "ruby",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 0,
                        "emerald": 7,
                        "ruby": 0,
                        "sapphire": 0
                    }
                },
                {
                    "id": 81,
                    "score": 4,
                    "tokenName": "ruby",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 0,
                        "emerald": 6,
                        "ruby": 3,
                        "sapphire": 3
                    }
                },
                {
                    "id": 77,
                    "score": 4,
                    "tokenName": "sapphire",
                    "tokenCost": {
                        "diamond": 6,
                        "onyx": 3,
                        "emerald": 0,
                        "sapphire": 3,
                        "ruby": 0
                    }
                },
                {
                    "id": 78,
                    "score": 5,
                    "tokenName": "sapphire",
                    "tokenCost": {
                        "diamond": 7,
                        "onyx": 0,
                        "emerald": 0,
                        "sapphire": 3,
                        "ruby": 0
                    }
                },
                {
                    "id": 85,
                    "score": 4,
                    "tokenName": "diamond",
                    "tokenCost": {
                        "diamond": 3,
                        "onyx": 6,
                        "emerald": 0,
                        "sapphire": 0,
                        "ruby": 3
                    }
                },
                {
                    "id": 79,
                    "score": 3,
                    "tokenName": "ruby",
                    "tokenCost": {
                        "diamond": 3,
                        "onyx": 3,
                        "emerald": 3,
                        "ruby": 0,
                        "sapphire": 5
                    }
                },
                {
                    "id": 75,
                    "score": 3,
                    "tokenName": "sapphire",
                    "tokenCost": {
                        "diamond": 3,
                        "onyx": 5,
                        "emerald": 3,
                        "sapphire": 0,
                        "ruby": 3
                    }
                },
                {
                    "id": 90,
                    "score": 5,
                    "tokenName": "emerald",
                    "tokenCost": {
                        "onyx": 0,
                        "emerald": 3,
                        "sapphire": 7,
                        "ruby": 0
                    }
                },
                {
                    "id": 82,
                    "score": 5,
                    "tokenName": "ruby",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 0,
                        "emerald": 7,
                        "ruby": 3,
                        "sapphire": 0
                    }
                },
                {
                    "id": 88,
                    "score": 4,
                    "tokenName": "emerald",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 0,
                        "emerald": 0,
                        "sapphire": 7,
                        "ruby": 0
                    }
                },
                {
                    "id": 86,
                    "score": 5,
                    "tokenName": "diamond",
                    "tokenCost": {
                        "diamond": 3,
                        "onyx": 7,
                        "emerald": 0,
                        "sapphire": 0,
                        "ruby": 0
                    }
                },
                {
                    "id": 87,
                    "score": 3,
                    "tokenName": "emerald",
                    "tokenCost": {
                        "diamond": 5,
                        "onyx": 3,
                        "emerald": 0,
                        "sapphire": 3,
                        "ruby": 3
                    }
                },
                {
                    "id": 84,
                    "score": 4,
                    "tokenName": "diamond",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 7,
                        "emerald": 0,
                        "sapphire": 0,
                        "ruby": 0
                    }
                },
                {
                    "id": 89,
                    "score": 4,
                    "tokenName": "emerald",
                    "tokenCost": {
                        "diamond": 3,
                        "onyx": 0,
                        "emerald": 3,
                        "sapphire": 6,
                        "ruby": 0
                    }
                },
                {
                    "id": 72,
                    "score": 4,
                    "tokenName": "onyx",
                    "tokenCost": {
                        "diamond": 0,
                        "onyx": 0,
                        "emerald": 0,
                        "sapphire": 0,
                        "ruby": 7
                    }
                }
            ]
        }
    ],
    "champion": [],
    "tokenPool": {
        "gold": 5,
        "diamond": 4,
        "onyx": 4,
        "emerald": 4,
        "sapphire": 4,
        "ruby": 4
    },
    "nobles": [
        {
            "id": 3,
            "score": 3,
            "tokenCost": {
                "diamond": 4,
                "onyx": 0,
                "emerald": 0,
                "ruby": 0,
                "sapphire": 4
            }
        },
        {
            "id": 5,
            "score": 3,
            "tokenCost": {
                "diamond": 0,
                "onyx": 0,
                "emerald": 4,
                "ruby": 0,
                "sapphire": 4
            }
        }
    ],
    "players": [
        {
            "id": 12,
            "character_id": 1,
            "user": {
                "id": 4,
                "email": "jasekraft12@gmail.com",
                "username": "jaecheese"
            },
            "cards": [],
            "turn": 0,
            "tokenPool": {
                "gold": 0,
                "diamond": 0,
                "onyx": 0,
                "emerald": 0,
                "ruby": 0,
                "sapphire": 0
            },
            "ownedCards": "",
            "nobles": []
        }
    ]
}
```
