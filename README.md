#apProject_Part1

#description:
creating the dataModels of the game.
designing some parts of the games logic.
creating a command line interface for the game.
storing a players activity in a log file.

Cards:
Cards are stored as json files that are parsed when the program starts

Player:
each player has a profile(.json) that is used to store basic information like username and password 
players can sign up and login through the CLI

Heroes:
Hero Cards are stored as json files that are parsed when the program starts
each hero has a deck of cards that can contain 15 cards at most
each hero has a hero power and an special power

CLI:
available commands are:
available commands are: 
exit: log out
exit-a: log out and stop program
delete-player: deletes current player
collections: shows cards in collection.
store: goes to store
ls-a-heros: shows all heroes
ls-m-heros: shows current hero
select [Card name]: select a hero
ls-a-cards: shows all current cards
ls-m-cards: shows cards in deck
ls-n-cards: shows cards that you can add to your deck
add [Card name]: adds card to deck
remove [Card name]: removes card from deck
buy [Card name]: buy card
sell [Card name]: sell card
wallet: shows your balance
ls-s: shows cards you can sell
ls-b: shows cards you can buy
hearthstone--help: shows all commands
stat [card name]: shows cards stats

Logging
logs are stored as text files and are updated with every event

Libraries
1-json.simple: to create and read json files
1-JBCrypt: to hash password and check password during log-in process

