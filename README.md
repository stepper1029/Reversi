## Overview: 
This project aims to make a playable Reversi game. As of now, this assumes the
standard rules and game play of Reversi, also known as Othello, but on a hexagonal board.
This means there are discs of colors white and black which can be placed on the board to
flip pieces of the opposite color and capture them. The winner is the one who captures the most
pieces. We made the code extensible for different board shapes, cell shapes, views, and even
alternate rule sets by making each of these things interfaces. All classes and interfaces assume
that they are working with interfaces rather than individual classes when applicable. For example,
A board class in the Board interface can work with any type of cell, so it only works with the
ReversiCell interface rather than, say, the HexCell class. This way, if we wanted to add square
cells in a SquareCell class, the board still works.

## Quick Start: 
To get started, a user can use the ReversiCreator class to create a game. So far, we
have only created BasicReversi on a hexagonal board with hexagonal squares so this is the default
of the factory. There also is no controller or main yet so a user has no way to interact with the 
model without explicitly calling methods like in a test class.

## Key components:
Components that would "drive" the control-flow of the system would be a Main class and a Creator
class. However, we don't yet have a main. I would also consider the Model a driving force because
it is the access point for all other components. Things like the view, Board interface, Cell
interface, etc are all "driven" by things like the Model, Creator, and Main.

## Key subcomponents:
The smallest subcomponent of the program is a single Cell. There is an interface for all cells, but
so far we have implemented only one HexCell class. This class has three fields, q, r, and s each 
representing a plane in the 3D cubic coordinate system we used to keep track of cells and the board.

A Board is the next noun which itself has the cells which make it up and black and white pieces. 
Because the board stores the cells in an array of arrays, to represent the rows and columns, there
are now two ways to access a cell. Either through the index of the row and column or through 
the coordinates q, r, s. We found to do background math q, r, s was more efficient, but row
and column was more readable and will make more sense to use in the view and controller later on.

Next we have the Model. The model contains a board, the current color, and the number of consecutive
passes. The Model enforces rules and behavior and then delegates to the board to actually perform
actions. The Model needs the curr color so that when it delegates place() to the board it knows
what color disc needs to be placed.

In the Model, we also have two enums we use throughout our code. First is CellDirection which contains
the possible directions from which you can make connections between a starting cell and another
cell on the board. These directions are represented by vectors which can be "added" to a cell
in order to get a new cell in that direction. This is used to traverse through the board and
find which pieces can be flipped and which moves are legal. The other enum is DiscColor which 
contains the colors that represent the different players. It is convention for Reversi to be played
by two players, represented by black and white, where black always goes first.

## Source organization:
All components having to do with the model are in the src.cs3500.reversi.model package. This 
includes things like the model, board, cells, DiscColor and CellDirection enums, and the Creator
class. All components having to do with the view are in the src.cs3500.reversi.view package. Right 
now, this only includes the text-based view and view interface. All tests are in the 
test.cs3500.reversi package, with package private model tests being in test.cs3500.reversi.model.