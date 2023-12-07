We were able to get their view and strategy successfully working. We could not implement their 
"player _ has passed" notification without changing and breaking our controller. Their
listeners notify all players of these messages, and we did not call our method to notify a 
player of a given message when a player passes.