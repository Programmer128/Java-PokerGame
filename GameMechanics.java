/*
 Timothy Gutzke
 COSC 1174 48L
 11/21/2021
 Poker Project Assignment
 */

import javafx.scene.layout.HBox;
import java.util.ArrayList;
import java.util.Collections;

//This class controls the logic of the game. It takes a poker hand HBox and determines the rank of the poker hand held in it.
public class GameMechanics {

    ////////////////////////////////////////// POKER HAND METHODS //////////////////////////////////////////

    //The array list of ordered card suits created by this method will be used for when card suits 
    //determine poker hand values.
    public ArrayList<Integer> getCardsuits(HBox pokerHand){

        //The collection of the suits of all cards in the hand will be returned to the caller.
        ArrayList<Integer> cardSuits = new ArrayList<Integer>();

         //Iterate through the poker hand HBox list of card display boxes and act on the hold/discard button value.
         for(int x = 0; x < pokerHand.getChildren().size(); x++){
           
            CardDisplayBox cardDisplayBox = (CardDisplayBox)pokerHand.getChildren().get(x);  
               
            //Add the currently iterated card rank value to the array list.
            cardSuits.add(cardDisplayBox.getCardSuit());
        }

        //Sort the card ranks in ascending order.
        Collections.sort(cardSuits);

        return cardSuits;
    }

    //The array list created by this method will be used to determine poker hand values.
    //This is used by all methods in this class that determine the value of a poker hand.
    public ArrayList<Integer> getCardRanks(HBox pokerHand){

        //The rank contents of the poker hand will be dumped into an array list and sorted for use in other methods.
        ArrayList<Integer> cardRanks = new ArrayList<Integer>();

         //Iterate through the poker hand HBox list of card display boxes and act on the hold/discard button value.
         for(int x = 0; x < pokerHand.getChildren().size(); x++){

            //Retrieve the next poker hand HBox child and cast it to a CardDisplayBox.           
            CardDisplayBox cardDisplayBox = (CardDisplayBox)pokerHand.getChildren().get(x);  
            
            //Add the currently iterated card display box object's rank to the array list.            
            cardRanks.add(cardDisplayBox.getCardRank());
        }

        //Sort the card ranks in ascending order.
        Collections.sort(cardRanks);

        return cardRanks;
    }   

    /////////////////////////// GET HAND RANK METHOD ////////////////////////////////
    //The type of poker hand determines the value of the poker hand. The better the hand, the higher its number.
    public int getPokerHandRank(PokerHand pokerHand){

        if(isRoyalFlush(pokerHand)){

            return 8;
        }

        if(isStraightFlush(pokerHand)){

            return 7;
        }

        if(isFourKind(pokerHand)){

            return 6;
        }

        if(isFullHouse(pokerHand)){

            return 5;
        }

        if(isFlush(pokerHand)){

            return 4;
        }

        if(isStraight(pokerHand)){

            return 3;
        }

        if(isThreeKind(pokerHand)){

            return 2;
        }

        if(isTwoPair(pokerHand)){

            return 1;
        }       

        //No winning hand.
        return -1;
    }   

    //A Royal Flush hand is a Straight Flush hand with a high card of 13.
    public boolean isRoyalFlush(PokerHand pokerHand){

        //Get a sorted poker hand by card rank.
        ArrayList<Integer> cardRanks = getCardRanks(pokerHand);

        //A royal flush is Ace high, or Joker high with a King card as the next card down in the straight.
        boolean first = cardRanks.get(4) == 13 || (cardRanks.get(4) == 14 && cardRanks.get(3) == 12);
        //A royal flush is a straight flush also.
        boolean second = isStraightFlush(pokerHand);

        return(first && second);
    }

    //A straight flush is both a straight and a flush at the same time.
    public boolean isStraightFlush(PokerHand pokerHand){        

        //The isFlush method and the isStraight method already account for possible Joker combinations.
        //If this is hand is both, then it is a Straight Flush hand.
        boolean first = isFlush(pokerHand);
        boolean second = isStraight(pokerHand);

        return(first && second);
    }

    public boolean isFourKind(PokerHand pokerHand){

        //Get a sorted poker hand by card rank.
        ArrayList<Integer> cardRanks = getCardRanks(pokerHand);

        int numberOfJokers = getJokers(cardRanks);

        if(numberOfJokers == 1){

            //Check all 3 of a kind combinations in a sorted poker hand.
            //Cannot use isThreeKind method because that duplicates the Joker cards when checking for Three of a Kind.
            //First 3 sorted cards.
            boolean first = cardRanks.get(0) == cardRanks.get(1) && cardRanks.get(1) == cardRanks.get(2);
            //Middle 3 sorted cards.
            boolean second = cardRanks.get(1) == cardRanks.get(2) && cardRanks.get(2) == cardRanks.get(3);
            //Last 3 sorted cards.
            boolean third = cardRanks.get(2) == cardRanks.get(3) && cardRanks.get(3) == cardRanks.get(4);

            //If there is a Three of a Kind combination, then the single Joker card creates a Four of a Kind hand.
            return (first || second || third);
        }

        //If there is a single pair hand, then this hand is a Four of a Kind with the two Joker cards.
        if(numberOfJokers == 2){

            //Check if there is a single pair in this hand. The isOnePair method is set up to not count the Joker pair in the hand.
            if(isOnePair(pokerHand)){

                return true;
            }
        }

        //Check for the two possible Four of A Kind combinations where you do not have any Joker cards.
        boolean first = cardRanks.get(0) == cardRanks.get(1) && cardRanks.get(1) == cardRanks.get(2) && cardRanks.get(2) == cardRanks.get(3);
        boolean second = cardRanks.get(1) == cardRanks.get(2) && cardRanks.get(2) == cardRanks.get(3) && cardRanks.get(3) == cardRanks.get(4);

        return(first || second);
    }

    public boolean isFlush(PokerHand pokerHand){

        //The true/false value to be returned.
        boolean first = true;
               
        int numberOfJokers = getJokers(cardRanks);


        //Get a sorted poker hand by card rank.
        ArrayList<Integer> cardSuits = getCardsuits(pokerHand);

        //If the first card suit equals the last card suit in a sorted array list, then this is a flush.
        int firstSuit = cardSuits.get(0);

        //We already have the first suit number in the line above. Start with the 2nd item in the cardSuits array list.
        for(int i = 1; i < cardSuits.size(); i++){

            //If the card suit is not equal to the first card's suit or is not a Joker card, then this hand is not a flush.
            if(!(cardSuits.get(i) == firstSuit || cardSuits.get(i) == 4)){                

                return false;
              
            }
        }

        return first;
    }

    public boolean isStraight(PokerHand pokerHand){

         //Get a sorted poker hand by card rank.
         ArrayList<Integer> cardRanks = getCardRanks(pokerHand);

         int numberOfJokers = getJokers(cardRanks);

         //Index for number of misses in the straight.
         int missedStepsIndex = 0;

         //Get the rank of the first card + 1 for the first comparison.
         Integer nextRank = cardRanks.get(0) + 1;

         for(int i = 1; i < cardRanks.size(); i++){

            if(cardRanks.get(i) != nextRank){

                missedStepsIndex = missedStepsIndex + 1;
            }

            //Start from this card to see if the next card is one rank higher.
            nextRank = cardRanks.get(i) + 1;
         }

         //If there are enough Joker cards in the hand to cover the missed steps in the straight check, then this is a straight.

         if(numberOfJokers >= missedStepsIndex){

            return true;
         }
         else{

            return false;
         }         
    }

    public boolean isFullHouse(PokerHand pokerHand){

        //Get a sorted poker hand by card rank.
        ArrayList<Integer> cardRanks = getCardRanks(pokerHand);
        
        if(getJokers(cardRanks) == 2){

            //Check all 3 of a kind combinations in a sorted poker hand.
            //Cannot use isThreeKind method because that duplicates the Joker cards when checking for Three of a Kind.
            //First 3 sorted cards.
            boolean first = cardRanks.get(0) == cardRanks.get(1) && cardRanks.get(1) == cardRanks.get(2);
            //Middle 3 sorted cards.
            boolean second = cardRanks.get(1) == cardRanks.get(2) && cardRanks.get(2) == cardRanks.get(3);
            //Last 3 sorted cards.
            boolean third = cardRanks.get(2) == cardRanks.get(3) && cardRanks.get(3) == cardRanks.get(4);

            //Set true/false value for whether the above code found a three of a kind combination.
            boolean ThreeKind = first || second || third;
                           
            //A One Pair hand or a Three of a Kind hand with two Joker cards is a Full House.
            if(isOnePair(pokerHand) || ThreeKind){

                return true;
            }  
        }

        if(getJokers(cardRanks) == 1){

            //Check all 3 of a kind combinations in a sorted poker hand.
            //Cannot use isThreeKind method because that duplicates the Joker cards when checking for Three of a Kind.
            //First 3 sorted cards.
            boolean first = cardRanks.get(0) == cardRanks.get(1) && cardRanks.get(1) == cardRanks.get(2);
            //Middle 3 sorted cards.
            boolean second = cardRanks.get(1) == cardRanks.get(2) && cardRanks.get(2) == cardRanks.get(3);
            //Last 3 sorted cards.
            boolean third = cardRanks.get(2) == cardRanks.get(3) && cardRanks.get(3) == cardRanks.get(4);

            //Set true/false value for whether the above code found a three of a kind combination.
            boolean ThreeKind = first || second || third;

            //Check all possible two-pair combinations in a sorted poker hand.
            boolean tpfirst = cardRanks.get(0) == cardRanks.get(1) && cardRanks.get(2) == cardRanks.get(3);
            boolean tpsecond = cardRanks.get(0) == cardRanks.get(1) && cardRanks.get(3) == cardRanks.get(4);
            boolean tpthird = cardRanks.get(1) == cardRanks.get(2) && cardRanks.get(3) == cardRanks.get(4);

            //If any one of the three comparisons is true, then thsi is a two pair hand.
            boolean twoPair = tpfirst || tpsecond || tpthird;

            //A Two Pair hand or a Three of a Kind hand with one Joker card is a Full House.
            if(twoPair || ThreeKind){

                return true;
            }  
        }

        //Two possible rank combinations for a Full House combination.
        boolean first =  cardRanks.get(0) == cardRanks.get(1) && cardRanks.get(1) == cardRanks.get(2) && cardRanks.get(3) == cardRanks.get(4);
        boolean second =  cardRanks.get(0) == cardRanks.get(1) && cardRanks.get(2) == cardRanks.get(3) && cardRanks.get(3) == cardRanks.get(4);

        return(first || second);
    }

    public boolean isThreeKind(PokerHand pokerHand){

        //Get a sorted poker hand by card rank.
        ArrayList<Integer> cardRanks = getCardRanks(pokerHand);

        //Get the number of Jokers in this hand.
        int numberOfJokers = getJokers(cardRanks);

        if(numberOfJokers == 2){

            return true;
        }     
        
        //A pair card combination + 1 Joker = 3 of a Kind.
        if(numberOfJokers == 1){

            //The condition needed for a Joker card to make up a Three of a Kind combination.
            if(isOnePair(pokerHand)){

                return true;
            }       
        }

        //Check all 3 of a kind combinations in a sorted poker hand.
        //First 3 sorted cards.
        boolean first = cardRanks.get(0) == cardRanks.get(1) && cardRanks.get(1) == cardRanks.get(2);
        //Middle 3 sorted cards.
        boolean second = cardRanks.get(1) == cardRanks.get(2) && cardRanks.get(2) == cardRanks.get(3);
        //Last 3 sorted cards.
        boolean third = cardRanks.get(2) == cardRanks.get(3) && cardRanks.get(3) == cardRanks.get(4);

        //If one of the three conditions is true, then this is a three of a kind hand.
        return(first || second || third);
    }

    //Check if poker hand has a two pair.
    //If you have two Joker cards, you already have a Three of a Kind hand, which is a higher rank.
    public boolean isTwoPair(PokerHand pokerHand){

         //Get a sorted poker hand by card rank.
         ArrayList<Integer> cardRanks = getCardRanks(pokerHand);       

        //If the highest card is a Joker and there is a single pair, then there is a two pair in this hand.
        if(cardRanks.get(4) == 14 && isOnePair(pokerHand)){

            return true;
        }

        //Check all possible two-pair combinations in a sorted poker hand.
        boolean first = cardRanks.get(0) == cardRanks.get(1) && cardRanks.get(2) == cardRanks.get(3);
        boolean second = cardRanks.get(0) == cardRanks.get(1) && cardRanks.get(3) == cardRanks.get(4);
        boolean third = cardRanks.get(1) == cardRanks.get(2) && cardRanks.get(3) == cardRanks.get(4);

        //If any one of the three comparisons is true, then thsi is a two pair hand.
        return(first || second || third);
    }

    //Check if poker hand has a single Pair
    //Single pair hands are not winning poker hand combinations, but this is used by the isThreeKind method when 
    //one Joker card is present in the hand.
    public boolean isOnePair(PokerHand pokerHand){

        //Get a sorted poker hand by card rank.
        ArrayList<Integer> cardRanks = getCardRanks(pokerHand);
        Collections.sort(cardRanks);

        //Check if any of the sorted cards are equal to the next card in the list.
        boolean first = cardRanks.get(0) == cardRanks.get(1);
        boolean second = cardRanks.get(1) == cardRanks.get(2);
        boolean third = cardRanks.get(2) == cardRanks.get(3);
        //To eliminate the two Joker pair from consideration. These cards would be used in at least a 3 of a Kind combination
        //so should not return a true value if there are two Jokers in the hand.
        boolean fourth;

        //Do not count a pair of Jokers. Two Joker cards will always constitute a Three of a Kind hand at a minimum.
        //Running the card rank calculation in order of rank precedence allows us to use this single pair check in hand 
        //calculations for higher ranked hand combinations without contaminating them with the Joker pair that is already there.
        if(cardRanks.get(3) != 14){

            fourth = cardRanks.get(3) == cardRanks.get(4);
        }
        else{

            fourth = false;            
        }       

        //If one of the above comparisons is true, then this is at least a single pair hand.
        return(first || second || third || fourth);     
    } 

    //There are two Joker cards in the deck.
    //Since Joker cards are metamophorse cards, hands with one or two of these cards must be processed differently
    //to account for all card identities Joker cards can assume to incarnate a winning poker hand combination.
    //Get the number of Joker cards in the poker hand. The number of Joker cards in the card deck is two.
    public static int getJokers(ArrayList<Integer> pokerHand){

        //Joker count index for this poker hand.
        int numberOfJokers = 0;

        //Loop through the poker hand card numbers.
        for(int i = 0; i < 5; i++){

            if(numberOfJokers < 2){

                int card = pokerHand.get(i);           
                
                    if(card == 14 ){

                        numberOfJokers = numberOfJokers + 1;
                    }
                }
        }

        return numberOfJokers;
    }       
}
