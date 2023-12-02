# QuickStart:

In order to try the textual view, the client as of now can define a new ReversiView and print it out using System.out.println();. The user can write out turns manually by calling the turn method on a defined model.

# Model:

The Model for this project contains an interface called ReversiModel and
a class that implements this interface called BasicReversiModel.

The ReversiModel interface has various methods that handle the actions of the game, such as starting, going through turns, and checking if the game is over.

In addition, various getter methods are present such as getBoard(), which returns the game board, and getNumRows(), which returns the number of rows in this Reversi game.

## Representations:

Each piece of this game is represented by the GamePiece class. This class takes in 2 parameters:

- color, which represents the color of this piece
- coordinate, which represents the coordinate of this piece

### Color

Color is represented with an Enumeration called GamePieceColor that has three types:

- Black
- White
- Empty

GamePieceColor has a method that returns the Color of each representation (Black for Black, White for White, and Gray for Empty).

### Coordinates

Coordinates are represented in an axial form, with (0, 0) being the center of the board.

- Going to the right -> x increases
- Going downwards -> y increases

Coordinates are in the Coordinate class, which has two parameters:

- x, which represents the x-coordinate
- y, which represents the y-coordinate

The Coordinate class has getters and setters for each of these parameters, in addition to methods overriding equals() and hashCode().

## Model Methods

### startGame()

Begins the Reversi game by calling helpers to:

- Construct the board, which is a list of GamePiece
- Surrounds the center of the board with pieces.
  - Each GamePiece is added to the whitePieces and blackPieces lists respectively

### turn()

The turn() method controls each turn of this Reversi game.

This method takes in two parameters:

- Coordinate destCoordinate
  - Represents the selected coordinate of the user
- GamePieceColor color
  - Represents the user's game color

A potentialCoordinates list is created, which keeps track of all potential moves that have a chance of being valid.

It works in this order:

1. Make sure the game has already started
2. Create a List of all of the surrounding coordinates of the destCoordinate variable
   - Controlled by a helper called loopSurroundingCoordinates which adds the surrounding coordinates to a new List
3. Filters out the coordinates if they aren't next to a valid color
   - This is controlled by a helper called nextToValidColor, which returns true if the given coordinate is not the opposite color of the user's color
4. Throws an IllegalArgumentException if there are no coordinates left in the potentialCoordinates list
   - This means that a move is not possible from destCoordinate

### 4.5: Direction

Directions in our implementation are represented with an Enumeration called Direction.

This Enumeration has six possible directions:

- DiagonalLeftUp
- DiagonalLeftDown
- DiagonalRightUp
- DiagonalRightDown
- HorizontalRight
- Horizontalleft

5. Loops through each potential coordinate in the List, and:

   - Finds the direction from destCoordinate to the potential coordinate
   - Loops through that direction with a helper called loopDirection()
     - The purpose of this helper is to create a run of coordinates if the move is valid
     - The helper has a switch for each coordinate
     - In each switch, the code checks the color of the next coordinate
     - If the color could be valid, the coordinate is added to this run
     - If the color is invalid, the method returns false, and the code throws an IllegalArgumentException later on

6. Assuming the run is valid, the model loops through each coordinate in the correct run and changes all of its coordinate colors to be the user's color

### isGameOver()

Checks if the game is over by looping through the gameBoard list

- If the board has a single empty space, the game is not considered to be over.

# View:

The View for this project contains an interface called ReversiView.

The textual implementation for this assignment implements the interface, and is called ReversiTextualView.

The interface has one method, called render(), which renders the View

## ReversiTextualView:

The ReversiTextualView class takes in a model, and has a toString() method which displays the View as textual output.

### toString()

1. Creates a new StringBuilder and appends the correct amount of padding.
2. Loops through every game piece, and renders it according to color

- Black Pieces = "X"
- White Pieces = "O"
- Empty Spaces = "\_"

3. Returns the StringBuilder as a String

## Class Invariant:

maxSize in the BasicReversiModel class is always odd

- Ensured in the constructor (throws an IllegalArgumentException otherwise)
- Variable is final

## Organization:

Our code is organized by the model and view packages within the src directory. A testing directory is also present, which contains separate classes for testing various aspects of the game, such as color, coordinates, the model, and the view.

# Changes for Part 2:

- Added a method to pass a turn. This method was recommended in the instructions for this week's homework so we wrote it in.
  - Added this method which returns the opposite color to the user, which can pass the turn in a future controller
  - Wrote tests for this method as well.
- Added a getter method that returns the size of the game board, just in case it might be useful later on.
- Created a helper method that checks if any possible moves are left. This was written to aid in checking if the game is over. This can also be useful in the future to suggest that a player should pass their turn.
- Added a test if the user tries to make a move outside of the game board. This test was added in response to the self-evaluation for the first assignment.
- Refactored the ReversiModel Interface to extend the ReadonlyReversiModel Interface described below, but add the additional methods that mutate the game board and/or state.
- Updated Javadocs to be more descriptive according to TA request.
- Abstracted a GamePiece by creating a Piece interface that GamePiece implements. This was done because of TA recommendations.
- Some methods that were specific to color, such as getWhitePieces() and getBlackPieces(), were abstracted to be getPiecesFromColor(GamePieceColor gp) to avoid code duplication. This was also done because of TA feedback.
- Fixed a bug we later found in the turn() method of the BasicReversiModel class. We found that our previous code would accidentally flip some tiles it shouldn't have in an edge case we previously didn't think of to test.
- Player text file now includes a description for potential methods according to TA comments.
- Refactored the gameNotStartedException() method to be private to the BasicReversiModel class because of TA recommendation.

## Input:

- User can click a cell to highlight it.
- User can click the 'p' key to pass their turn.
- User can click the 'm' key to complete their turn with the clicked cell.

# New classes for Part 2:

## Model Classes:

### ReadonlyReversiModel Interface:

- This Interface has only the observer methods for a ReversiModel. This is to ensure that no model that Implements this interface can mutate the game board and/or the game state.
- Most of these methods are getters, such as the getNumRows() method, which just returns the int value of the number of rows in this Reversi model's game board.
- Some methods in this Interface are also designed to return the current state of the game, such as the noMoves() method, which just returns a boolean if there are any moves present in this Reversi game for the passed-in GamePieceColor.

### Piece Interface:

- This interface is designed to abstract out a GamePiece. It has getter methods which return a Piece's color and coordinate, in addition to having a method that set's its color to a new GamePieceColor.

## View Classes:

### Hexagon:

- This class is responsible for creating a single Hexagon for the Reversi board. It extends the ReversiPolygon class as defined below to build upon its functionality. Hexagons have a center Coordinate and an apothem, which is the distance from the center to the midpoint of a single edge of the Hexagon. In addition, a hypotenuse is also defined, which is calculated from the apothem variable.
- There is one method that is called, which is createHexagon(). This method adds all points of each corner of the Hexagon into Arrays by calling the addPoint() method from the Polygon superclass.

### ReversiPolygon:

- The ReversiPolygon abstract class extends Java's Polygon class. This abstract class also has variables for the center, apothem, and hypotenuse like the Hexagon class, but also has another variable called degreesToCorner, which represents the degrees from 0 to the top-rightmost corner of this ReversiPolygon.
- This class has exclusively three getter methods, which return the center, apothem, and degreesToCorner respectively.

### ReversiGUIView:

- The ReversiGUIView class is responsible for initializing the GUI view. It takes in a ReadonlyReversiModel, which allows the view to call various methods from the model without the possiblility of accidental mutation. In addition, a player number int is also taken, which sets the title of the view to reflect the player who has this view.
- The constructor initializes and creates the game board, the defined variables, and the JFrame. In addition, a MouseAdapter is added to listen for mouse clicks. Only the mouseClicked() method from the interface is Overridden. A KeyAdapter is also added, which indicates if the user wishes to pass their turn or move into the selected coordinate.
- The getYHeight() method calculates the y-height of this board. This method uses some trigonometry to find this.
- The createBoard() method creates every Hexagon of the board. It has two helpers, buildNegative() and buildPositive(), which each help with this.
  - buildNegative() builds all the Hexagons in the negative y-direction (upwards of the origin at the center of the board)
  - buildPositive() builds all the Hexagons in the positive y-direction (downwards of the origin at the center of the board)
- Both of these helper methods are called recursively until they throw an IllegalArgumentException, which only happens when no more rows can be built
  - In addition, these helpers call another method called buildHexagons(), which builds out a single row of Hexagons.
- The last few methods just set the visibility, render the GUI view, and initialize the board by adding all player pieces into a List. In addition, the ReversiPanel is added to the view.

### ReversiPanel:

- The ReversiPanel class extends Java's JPanel class and implements the IReversiPanel interface. The panel's job is to paint every component of the Reversi game.
  - To do this, it overrides the paintComponent() method, which fills in every Hexagon defined in the panel with its color. This is represented with a Map<ReversiPolygon, Color>, where each ReversiPolygon (Hexagon in this case) is linked with its color.
    - In addition, this method adds all player pieces and paints them onto the board.

## View Interfaces:

- It's important to note that these interfaces do not have any methods yet, but were built-in to handle any future functionality that might be required.

### IReversiFrame

- This Interface represents a frame for Reversi.
- The ReversiGUIView class implements this interface.

### IReversiPanel:

- This Interface represents a panel for Reversi.
- The ReversiPanel class implements this interface.

## Strategy Classes and Interface:

### ReversiStrategy Interface:

- This Interface represents Reversi strategies to be used by a player or an AI
- The Interface has one method, called findBestTurn(), which every strategy inherits.
  - The goal of this method is to find the Coordinate that has the best turn given the strategy.
- There are six implementations of this Interface, which are all described below.

### MaximumCaptures Class:

- This strategy class extends the ReversiStrategyExceptions Abstract Class. The goal of this strategy is to find the turn that gives the current player the most amount of captured tiles.
  - The findBestTurn() method finds the best turn by looping through all possible turns and adding them to a Map, which maps each Coordinate to an Integer of the number of tiles that would be flipped by this turn.
  - Once the map is done, a for-loop loops through the map and finds all turns that would potentially flip the most tiles
    - The method then finds the top-leftmost Coordinate and prioritizes that, finally returning a Coordinate.
- It's important to note that this method will always return a Coordinate unless the game is over already or the passed-in GamePieceColor is Empty.
  - In this case, it will throw an IllegalArgumentException
- The method also throws an IllegalStateException if the model is null or if the game hasn't started.
  - Both of these Exceptions are inherited from the ReversiStrategyExceptions Abstract Class.

### CornerStrategies Abstract Class:

- This Abstract Class is in charge of abstracting a method present in both corner strategies below. This method extends the ReversiStrategyExceptions Abstract Class and adds a method called findCorners, whose only job is to find every corner Coordinate of the passed-in model and return a list of all of the corners as Coordinates.

### PlayCorner Class:

- The PlayCorner class represents a strategy whereby the user or AI prioritizes playing pieces in the corner of the game board. This is because pieces in the corner can never be captured by the opposing player. This corner extends the CornerStrategies Abstract Class as described above, which extends the ReversiStrategyExceptions Abstract Class.
- This strategy also takes a backup strategy as a fallback in case no corner moves are found. This fallback strategy is returned if the findBestTurn() method couldn't find any valid corner moves.
- In addition, an IllegalArgumentException is thrown if the passed-in GamePieceColor is Empty, while an IllegalStateException is thrown if the model is null or if the game hasn't started.
  - Both of these Exceptions are inherited from the ReversiStrategyExceptions Abstract Class.

### AvoidAdjacentCornerSpaces Class:

- This Reversi strategy prioritizes Reversi moves that avoid tiles that are adjacent to any corners. This is because moving into an adjacent corner space allows the opponent to capture the corner, which means that they hold the corner for the rest of the game.
- Similarly to the PlayCorner class, this class extends the the CornerStategies Abstract Class which implements the ReversiStrategy Interface.
- The findBestTurn() method is also similar to the other in the PlayCorner class in that a fallback strategy is passed into the constructor in case no possible moves that fit this strategy are found.
  - The method also throws an IllegalArgumentException if an Empty GamePieceColor is passed in, as well as throwing an IllegalStateException if the model is null or if the game hasn't started.

### MinMaxStrategy Class:

- The MinMaxStrategy Class implements a Reversi strategy that prioritizes making moves that cause the opponent to capture the least amount of tiles on their next turn. Similarly to the MaximumCaptures strategy class, this class also has a fallback in case no moves that minimize the opponent's next move are found. It's important to note that this class assumes the opponent is using a MaximumCaptures Reversi Strategy for all of their turns.
- The findBestTurn() method throws the same exceptions as the other strategies from the ReversiStrategyExceptions Abstract Class which this class extends.
  - This being an IllegalArgumentException if the passed-in GamePieceColor is Empty.
  - Along with an IllegalStateException if the given model is null.

### ReversiStrategyExceptions Abstract Class:

- This Abstract Class handles all methods that could potentially throw exceptions in any Reversi strategy. This would include if the passed-in GamePieceColor to the findBestTurn() method is Empty or if the given model is null. This class implements the ReversiStrategy Interface
  - An IllegalArgumentException and IllegalStateException are thrown respectively.

### TryTwoStrategies Class:

- The TryTwoStrategies class takes in two Reversi Strategies. The first of these strategies is tried first, with the second being used as a fallback in case the first strategy fails. Some strategies throw an IllegalArgumentException if no moves could be found that work with the strategy, so there is a try/catch in the findBestTurn() method that accounts for this.

# Changes for Part 3:

- Added callbacks in the ReversiGUIView and BasicReversiModel classes to the new Features Interfaces
- Added a new variable called Listeners that is a List<ModelFeatures> in the BasicReversiModel class. This allows the model to have a list of all listeners that are notified when various events in the game occur, like a game ending.
- Changed the constructor of BasicReversiModel to include “this.\_\_\_” for all variable assignments. This was done just for clarity.
- Refactored the BasicReversiModel’s turn(), noMoves(), and passTurn() methods to call the Features listeners if completed. These methods now send notifications to all listeners.
- Changed the turn() method in the BasicReversiModel class to reflect turns not happening if the game is over or there are no moves left for the given GamePieceColor.
- Changed the ReversiTextualView and ReversiGUIView classes to have an addFeatures() method that adds the given ViewFeatures to a list of features.
- Removed System.out.println() of the translated model coordinates
- Added a ViewFeatures listener for the ReversiTextualView class
- Fixed a bug in the turn() method that caused extra runs to be added for the user
  - Added a helper method for the loopDirection() method that removes duplicates from the list of all correct runs.
  - Added a method to test this bug that passed
- Made the playerNumber in the ReversiGUIView class final to reflect an IntelliJ IDEA suggestion.
- Set the ReversiStrategy backup variable to be private according to TA specifications.
- Changed the ReversiStrategy interface to take in a ReadonlyReversiModel for the findBestTurn() method rather than a mutable ReversiModel. This was done to ensure that strategies cannot cheat and mutate the board to their liking.
- Changed the didColorWin(GamePieceColor gpc) method in the ReversiModelMock class to add to the log to reflect new testing.

# New Classes for Part 3:

## View Classes:

### ViewFeatures Interface:

- This interface is responsible for notifying listeners about various events that can occur in a ReversiView. This interface has two methods–moveTurn(Coordinate coordinate) and passTurn(int playerNumber) that are called if the player has indicated to the view that they want to move or pass their turn respectively.

## Model Classes:

### Player Interface:

- This interface represents a single player. A player can either be a HumanPlayer or a MachinePlayer. There are various getters and setters in the Player interface, with another method designed to run a turn.

### HumanPlayer Class:

- This class represents a single human player for this ReversiGame. This class has a player number and a GamePieceColor, and inherits from the Player interface.

### MachinePlayer Class:

- This class represents a single machine player. This machine player also takes in a player number and a GamePieceColor, but also takes in a model to run a turn and a ReversiStrategy to determine the best turn to run.
- This class has a private method called findTurnCoordinate() which finds the best turn coordinate using the given ReversiStrategy.

### ModelFeatures Interface:

- This interface notifies listeners about various events that can occur in a ReversiModel. This includes notifying listeners when a turn is complete, when the player wants to pass their turn, and when this Reversi game is over.

## Controller Classes:

### ReversiController Interface:

- This interface represents all of the actions that a controller for this Reversi game might need to have. This interface has only one method, called go(), which starts the controller. This method is mostly designed for the machine players, who do not have a user to rely on to start the game for them.

### ReversiGUIController Class:

- This class is responsible for tying together a ReversiModel with a ReversiGUIView. The ReversiGUIController acts as a listener for both the model and the view, and thus implements both Features interfaces. This ReversiGUIController is notified by both the view and the model when something interesting occurs, and acts accordingly to run the game.

# Main Class Updates and Command Line Arguments:

## Main Class Updates:

- The Main class now has a helper method called buildPlayer(), which returns a new Player as specified by the command line arguments.
- The main() method now creates one model, two views, two players as specified in the command line arguments, and two controllers with these players. In addition, it starts the game by calling the startGame() method of the ReversiModel.
- The Command Line Arguments are as follows:
  - human - represents a human player
  - maxcaptures - represents an AI player that uses the MaximumCaptures ReversiStrategy
  - playcorner - represents an AI player that employs the PlayCorner ReversiStrategy
  - avoidadjacent - represents an AI player that uses the AvoidAdjacentCornerSpaces ReversiStrategy
- Examples of a command line argument could look as such:
  - human human
  - human maxcaptures
  - maxcaptures playcorner
