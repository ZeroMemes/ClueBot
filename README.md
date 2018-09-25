# ClueBot [![License](https://img.shields.io/github/license/ZeroMemes/ClueBot.svg)](LICENSE)
A program I made a while ago to assist you in playing the board game clue.
It has been rotting on my laptop's HDD for 9 months at this point.

There's probably a lot of messy code in it because I really just wanted it to be functional, feel free to make any PRs.

Everything should be sufficiently documented via JavaDocs, if anything is missing feel free to create an issue.

## Capabilities
ClueBot is able to track the likelyhood of player's having cards as well as determine for certain that player's have a card given the history of suggestions made. It's basically tracking everything you would in a normal game of clue, but with a lot less work on your end. For more information on this, view the [Viewing Player Info](#viewing-player-info) section.

## Basic Tutorial
When you run the program, you will be prompted with the amount of players in the game. This cannot be changed.

![Player Count Entry Menu](https://i.imgur.com/txZ7fqT.png)

For each player, you must specify the amount of cards they have, and their name. For yourself, you should specify the cards that you have.

![Player Entry](https://i.imgur.com/J18ZQ0w.png)

Once you have entered all of the players, you will be greeted with this menu. This is the "home" menu. It contains a list of the players, the current case, and 4 buttons that link to other pop-up menus. You are able to right click on players in the list to view info about them.

![Main Menu](https://i.imgur.com/g6Rm0fA.png)

### Adding Suggestions

The "Add Suggestion" pop-up menu is fairly straight forward. For each suggestion that is made, it should be entered via the menu. There are 2 types of suggestions: Reveal and Pass.

A suggestion is considered a "Reveal" suggestion when another player revealed one of the cards in the suggestion. If you were the player who made the suggestion and a card was revealed to you, an additional dialog will pop-up once the suggestion information is entered that will prompt you for the card that was revealed.

A suggestion is considered a "Pass" suggestion when none of the players revealed a card. In this case, the "revealer" input combo box will be grayed out.

![Add Suggestion Pop-up](https://i.imgur.com/9DUrEse.png)
![Revealed Card Entry](https://i.imgur.com/62uLnPZ.png)

### Viewing Player Info

Continued from the "Adding Suggestions" section, now that we know that Lemon had the Mr. Green card, we can see that when we look at his player info.

![View Player Info](https://i.imgur.com/zrMoXV1.png)
![Player Info Dialog](https://i.imgur.com/furVTcG.png)

The possibility of a player having a card increases by 1 for each card we're uncertain of them having or not when they reveal a card in a suggestion to another player. Let's look at an example.

Let's say that Jurv revealed one of the cards in this suggestion to Lemon:

![Example Suggestion](https://i.imgur.com/P2YkXcK.png)

We know that Jurv must have one of these cards, so the possibility increases for all of them.

![Possibility Example](https://i.imgur.com/BIo5jZx.png)

### Viewing Suggestion History

The "View Suggestions" button will present you with this view. It is simply a table of all of the information that has been manually entered through the "Add Suggestion" view.

![Suggestion History](https://i.imgur.com/y5koaUE.png)

### Viewing the Card Table

The card table is a crude view of the possibilities and certainties of card possession for all players. This view will need to be maximized for full viewing experience, and even then it's pretty trash.

![Card Table](https://i.imgur.com/LYTQU3h.png)
