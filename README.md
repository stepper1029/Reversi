# Reversi

A popular board game, also known as Othello, where players capture tiles to conquer the board.

# Project Overview


## ← [Model](src/cs3500/model)
This folder holds all the logic for the rules of the game as well as aspects of the board. This includes,
possible moves, different boards (square, hexagonal, etc), different pieces, and different rulesets.

## ← [View](src/cs3500/view)
This folder holds the view aspect of our MVC framework. There are two types of views, text-based used
mostly for testing and Swing GUI used for interactive gameplay. This is how we communicate what's happening
in the game to the players.

## ← [Controller](src/cs3500/controller)
This folder "controls" the flow of the game, passing to the model and view when appropriate. When a player
interacts with the view in some capacity(mouse clicks, keyboard actions, etc.), the action gets handed to 
the controller to decide what to do. The controller is what allows us to keep the model and view separate, 
allowing for varied gameplay and scalable architecture.

## ← [Provider](src/cs3500/provider)
This folder contains code from our "providers", peers who gave us their implemented view that we had to 
make work with our implemented model. This helped us practice collaboration with other teams as well as 
working with "clients" to whom we provided our code.

These are just a few of the main components, but many folders have their own specific READMEs that go 
into much more detail about methods and interactions. 
