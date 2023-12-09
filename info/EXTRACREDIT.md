## Level 0: 
We implemented level 0 by updating the HexGUI class. By pressing the 'H' key, a player can enable
or disable hints, which tell you the number of pieces that move will flip. 

## Level 1: 
We implemented level 1 by creating an abstract model class called AbstractModel and making our
HexReversi and SquareReversi classes extend this class. We also had to include a new SquareCell 
class, which implements our ReversiCell interface as well as a new SquareBoard class. Tests for 
this can be found in the test package. Package-private methods are tested in the 
PackagePrivateSquareModelTests class, and other methods are tested within the TestSquareModel class.

## Level 2: 
We implemented level 2 by abstracting our board panel which is now extended by the classes HexPanel 
and SquarePanel. This way, the frame remains unchanged and our controller still works through 
interfaces. By abstracting, we were able to reduce duplicate code and only change what was
necessary (the shape of the cells and the board).

## Level 3: 
We did not parameterize our controller. Our controller class explicitly works
through interfaces and did not need to change in order to work with both views.

## Level 4: 
We were able to implement all 4 strategies using the same classes. We just had to move some 
observable methods from the strategy classes to the model/ cell interfaces. Tests for these are
found in PackagePrivateStrategyTests (at the bottom) and TestSquareStrategy. 