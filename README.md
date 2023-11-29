## Overview: 
This project aims to make a playable Reversi game. As of now, this assumes the
standard rules and game play of Reversi, also known as Othello, but on a hexagonal board.
This means there are disks of colors white and black which can be placed on the board to
flip pieces of the opposite color and capture them. The winner is the one who captures the most
pieces. We made the code extensible for different board shapes, cell shapes, views, and even
alternate rule sets by making each of these things interfaces. The game allows for 2-players, which
can be either human or AI players. Most classes and interfaces assume that they are working with 
interfaces rather than individual classes when applicable. For example, a board class in the Board 
interface can work with any type of cell, so it only works with the ReversiCell interface rather 
than, say, the HexCell class. This way, if we wanted to add square cells in a SquareCell class, 
the board still works.

## Quick Start:
To get started, a user can run the main class and begin interacting with the displayed view. This 
game will have BasicReversi rules on a hexagonal board with hexagonal cells. To customize the game,
the player can input 1 or 3 args. If only one arg is input, it should be an int to change
the board size. If three args are input, the first one remains an int to customize the board size.
The following two args are to specify the player types, args[1] being for the first player, and
args[2] being for the second player. Inputting "human" creates a human player, and inputting
"strategy1", "strategy2", "strategy3", or "strategy4" creates and AI player. Strategy1 uses the
least complex strategy, MostPieces. Strategy4 chains all the strategies together, and so is
the hardest AI player to compete against. From Strategy1 to Strategy4, the AI adds a new 
method of choosing a move and becomes increasingly complex and difficult.

## Key components:
Components that would "drive" the control-flow of the system would be our Main, Controller
and View classes. The Main gets the program running while the View takes in user input to push 
the game forward. The controller helps the different components communicate and oversees their
interactions.

## Key subcomponents:

### Interface ReversiCell and Class HexCell:
The smallest subcomponent of the program is a single Cell. There is an interface for all cells, but
so far we have implemented only one HexCell class. This class has three fields, q, r, and s each 
representing a plane in the 3D cubic coordinate system we used to keep track of cells and the board.
The origin is at (0, 0, 0), which is in the center of the board. q moves from bottom left
to top right of the grid. r moves from top to bottom of the grid. s moves from bottom right to 
top left of the grid (see the HexCell class for more details).

### Interface Board and Class HexBoard:
A Board is the next noun which itself has the cells which make it up and black and white pieces. 
Because the board stores the cells in an array of arrays, to represent the rows and columns, there
are now two ways to access a cell. Either through the index of the row and column or through 
the coordinates q, r, s. We found to do background math q, r, s was more efficient, but row
and column was more readable and will make more sense to use in the view and controller later on.
Rows are organized by horizontal rows of the hexagonal board. The origin (0, 0) is in the top left
of the board, and row number increases vertically down, while cell number increases horizontally 
to the right(see the HexBoard class for more details).

### Model:
Next we have the Model. The model contains a board, and the number of consecutive
passes. The Model enforces rules and behavior and then delegates to the board to actually perform
actions. The model is split into a read-only and mutable interface. The read-only interface 
provides observable methods, while the mutable interface provides the pass() and place() methods
to interact with the model. The model has the ability to add ModelFeatures listeners to send 
notifications to the controller when it is a player's turn or when the game is over. 

In the Model, we also have two enums we use throughout our code. First is CellDirection which 
contains the possible directions from which you can make connections between a starting cell and 
another cell on the board. These directions are represented by vectors which can be "added" to a 
cell in order to get a new cell in that direction. This is used to traverse through the board and
find which pieces can be flipped and which moves are legal. The other enum is DiskColor which 
contains the colors that represent the different players. It is convention for Reversi to be played
by two players, represented by black and white, where black always goes first.

### Controller:
The controller is a listener for the Model and the View, meaning it implements both the
PlayerActions interface and the ModelFeatures interface (see more below). It takes notifications 
like turns and invalid moves from the model and sends them to players via the View. It also takes 
view interactions from the player in the form of mouse clicks and keyboard presses and sends them 
to the model. Each player has its own controller and the controller is ignorant to the type of
player it belongs to, human or computer. 

### View: 
The View has a ReversiView interface to promise functionality of the frame. This is implemented
by GraphicalView class which renders the frame of our GUI. This holds two panels, one for the 
score and one for the board. The board gets its own Panel class called SimpleReversiBoard. This 
board allows the user to highlight cells through mouse clicks and place disks using the 'enter' key.
Players can also pass their turn using the 'P' key. The background of the board becomes light blue
when it is the corresponding player's turn. It turns white when it is no longer that player's turn
and does not allow for any interactions with the board. In addition to the background being light 
blue, all possible moves for the current player are highlighted in light pink. When the user 
selects a cell with their mouse, it is highlighted in cyan. A user cannot select a cell that
already has a disk placed on it. It has the ability to register a PlayerActions listener to notify
the controller when a human player makes a move. 

## PlayerActions
An interface to represent player actions that come from either a human player through the view or
an AI player through the AI player class. It is implemented by the controller and contains the
functionality of handling a player passing and playing. Notifies the listener when either of these
events occur and handles the event properly. 

## ModelFeatures
An interface to represent notifications that a model can send. It is implemented by the controller
and contains the functionality of handling a notification that it is the corresponding player's
turn or that the game is over. Notifies the listener when either of these events occur and handles
the event properly. 

### Strategy:
The strategy determines how a player should move in a game of Reversi. It includes decisions for
placing and passing. the FallibleReversiStrategy interface represents a strategy that may fail, 
while the InfallibleReversiStrategy represents a strategy that should never fail and will throw 
an error if it cannot return a move. Players have an InfallibleReversiStrategy field. All strategy
objects work by taking in a list of valid moves to choose from and filtering through the list
based on its particular strategy, and then breaking ties. Ties are currently broken by choosing the
uppermost, leftmost option, which is the purpose of the TopLeftComparator. Strategies can be 
composed dynamically, allowing us to mix and match between them. 

There are 4 specific strategy classes: 
- MostPieces: chooses the move that flips the most pieces
- AvoidCornerAdjacent: chooses moves that are not next to one of the six corners
- ChooseCorners: chooses moves that are in one of the six corners
- MiniMax: tries to minimize the scoring options for the other player

### Player:
The Player interface has two methods: play and getColor. play allows the player to choose a move, 
or return Optional.empty() if they wish to pass. This information will then be handled by the
controller. getColor allows the controller to access the color associated with the player in order
to pass into model methods. A player can either be a human player or an AI player, which makes
moves based on a strategy. It has the ability to register a PlayerActions listener to notify
the controller when an AI player makes a move.

## Source organization:
All components having to do with the model are in the src.cs3500.reversi.model package. This 
includes things like the model, board, cells, DiskColor and CellDirection enums, and the Creator
class. All components having to do with the view are in the src.cs3500.reversi.view package. This 
includes both the textual view and the GUI. The stub of a controller is in the 
src.cs3500.reversi.controller. All tests are in the test.cs3500.reversi package, with package 
private model tests being in test.cs3500.reversi.model.

## Changes since Part 1:
- HexTextView no longer implements the view interface. It's still included in the view package, but 
would not implement any of the methods needed in the graphical view. The textview was just for 
testing purposes before we fully implemented the view. 
- The model still keeps track of which color's turn it is to move, but the place() and pass() 
methods also take in a DiskColor. This way the controller can pass in a color based on input it
is getting from the players and the model can enforce the rules by ensuring that the correct
player is moving.
- New methods were created to make a copy of the model and board so that the strategy can have a
copy of the current model to test different moves.
- Updated the Player interface to contain a method called play and getColor, instead of pass
and play. Based on how the strategy determines moves, this setup makes more sense for taking in 
input from the strategy objects and passing it to the controller. 
- Added a new method to the Cell interface that determines whether the given integer values are
all coordinates of the HexCell. This makes it easier to implement the strategy and check whether
a cell is a corner or next to a corner. 
- Implemented all required aspects of part 2 as outlined above. 

## Extra credit (part 2):
All four strategies were implemented and can be composed. See Strategy section above for more
details.

## Changes since Part 2:
- There is no longer a keyboard listener and instead the view uses a features interface. So the 
controller now implements two features interfaces.
- The view highlights the cells which are valid moves.
- Implemented all required aspects of part 3 as outlined above. 