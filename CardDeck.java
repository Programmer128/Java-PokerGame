/*
 Timothy Gutzke
 COSC 1174 48L
 11/21/2021
 Poker Project Assignment
 */

import java.util.ArrayList;

//Every card game starts with a card deck.
//This class represents a deck of cards and all card deck actions.
public class CardDeck {

    ////////////////////////////////////////// PROPERTIES & OBJECTS //////////////////////////////////////////

    //The deck of cards is an array list of card ID numbers. These numbers represent the file names of the image .PNG files
    //the game player will see on screen.
    private ArrayList<Integer> deck = new ArrayList<Integer>();
    
    ////////////////////////////////////////// CONSTRUCTOR //////////////////////////////////////////

    //CardDeck constructor. Automatically populates a card deck.
    public CardDeck(int numCards){
        
        //Set up the cards based on the number of cards in play.
        populateDeck(this.deck, numCards);
        //Shuffle the deck object three times.
        shuffleDeck(this.deck);
        shuffleDeck(this.deck);
        shuffleDeck(this.deck);
    }

    ////////////////////////////////////////// GETTERS & SETTERS //////////////////////////////////////////

    //Getter for the private deck variable representing the deck of cards.    
    public int getCard(){

        //Get the top card (last item in the array list).
        int card = this.deck.get(this.deck.size() - 1);
        //Remove the top card from the deck.
        this.deck.remove(this.deck.size() - 1);

        //Return the top card.
        return card;
    }

    ////////////////////////////////////////// METHODS //////////////////////////////////////////

    //Set up a new deck with all cards.
    public void populateDeck(ArrayList<Integer> deck, int numCards){

        //numCards represents the number of cards the deck should hold.
        for(int i = 1; i <= numCards; i++){           

            //Add the new card to the deck.
            deck.add(i);
        }
    }

    //Shuffle the cards in the deck.
    public void shuffleDeck(ArrayList<Integer> deck){

        java.util.Collections.shuffle(deck);
    }    

    //When the card deck gets below a certain amount of cards, a new deck will need to be created and shuffled.
    public void reshuffleDeck(int numCards){

        //Clear the elements in the current deck.
        //The remaining cards are discarded.
        this.deck.clear();
        //Create a new card deck.
        populateDeck(this.deck, numCards);  
        //Shuffle the deck three times.  
        shuffleDeck(deck);   
        shuffleDeck(deck);
        shuffleDeck(deck); 
    }

    //Check the number of remaining cards in the deck.
    //This will be used in the game board pane to check if the game card deck needs to be reconstituted and reshuffled.
    public int getCardsRemaining(){

        //Return the current size of the array list for this CardDeck object.
        return this.deck.size();
    }
}
