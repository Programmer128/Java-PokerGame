# Java-PokerGame

This is a JavaFX single hand Poker game. The player starts with a pot of money and bets that after the draw he will have a winning hand. The game continues until the player loses all his money. 
The card folder will either need to be copied directly under the C: drive, or the player will need to modify folder path in the CardDisplayBox.java file.

There are a couple of flaws in the program due to class deadline time constraints that I did not go back and fix. The game could have a cleaner ending than converting the betting money amount to a string of "Game Over" which causes a data type mismatch if the player tries to play another hand without restarting the game. Also the bet amount display could be modified so that the "Game Over" message did not overlap the bet amount display boundaries.

The algorithms for the poker hands came from http://www.mathcs.emory.edu/~cheung/Courses/170/Syllabus/10/pokerCheck.html. They were not used word for word, but the algorigthms at this link were used to figure out the logic for determining poker hand types.  I figured out how adding 2 jokers to the card deck affected hand calculations on my own. Therefore there is the possibility that there could be a wrong calculation where Joker cards are involved for a particular card rank, but I have not seen one yet while playing. The reader can view these calculations in the GameMechanics.java file if the reader would like to check how these determinations of hand rank are done with the jokers.
