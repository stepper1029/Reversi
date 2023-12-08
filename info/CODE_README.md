We were not able to get their view and strategy successfully working. We had an error when 
converting between their coordinate system and ours. Without this bug, their strategy would work
with ours. The human human strategy works but a game with our human and their ai does not return a 
move since the conversion is incorrect. Still, their view works with ours. We also could not 
implement their "player _ has passed" notification without changing and breaking our controller. 
Their listeners notify all players of these messages, and we did not call our method to notify a 
player of a given message when a player passes.