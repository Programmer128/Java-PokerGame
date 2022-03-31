/*
 Timothy Gutzke
 COSC 1174 48L
 11/21/2021
 Poker Project Assignment
 */

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.BackgroundFill;

//The hand pane holds a player's card hand. It is an HBox object that will hold VBox objects.
public class PokerHand extends HBox {     
        
     ////////////////////////////////////////// CONSTRUCTORS //////////////////////////////////////////

    //Allows a poker hand to be assigned to a player as an accessible property in the game board pane. 
    //The poker hand will be populated in the poker player constructor using the drawNewPokerHand method.
    public PokerHand(){

        //Set the formatting on the poker hand pane.        
        this.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.FULL)));         
        this.setPadding(new Insets(100, 100, 100, 100));
        this.setSpacing(20);
        this.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));        
    };


    //A poker hand will draw cards from a deck.
    //The cards are represented by CardDisplayBox objects that have their properties based on the integer value, representing
    //a card ID, that is retrieved as an int value from the last index of an array list representing a card deck.
    public PokerHand(CardDeck deck){        
        
        //Set the formatting on the poker hand pane.        
        this.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.FULL)));         
        this.setPadding(new Insets(100, 100, 100, 100));
        this.setSpacing(20);
        this.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));        
        
        //Draw a new poker hand (5 card display box objects).
        this.drawNewPokerHand(deck);

        //Get card image to set this panes preferred height.
        CardDisplayBox cardDisplayBox = (CardDisplayBox)this.getChildren().get(0);
        
        //Set the height of the poker hand HBox to the height of a CardDisplayBox card. 
        this.setPokerHandHeight(cardDisplayBox);
    }

    ////////////////////////////////////////// METHODS //////////////////////////////////////////

    //Method for setting up a poker hand with 5 card display boxes.
    public void drawNewPokerHand(CardDeck deck){

        //Remove all card display boxes.
        this.getChildren().clear();

        //We need 5 cards.
        for(int i = 0; i < 5; i++){

            //Create a new card display box and structure its properties based on the integer retrieved from the last index
            //of the CardDeck object passed in.
            CardDisplayBox cardDisplayBox = new CardDisplayBox(deck.getCard());
            //Add the card display box VBox to the poker hand HBox.
            this.getChildren().add(cardDisplayBox);
        }

        //Set the poker hand HBox height to at least the same height as the card display box. 
        //The card display box is matched to the height of its image when it is constructed.
        this.setPokerHandHeight((CardDisplayBox)this.getChildren().get(0));
    }

    //Set the poker hand HBox to at least the same height as the card display box.
    public void setPokerHandHeight(CardDisplayBox cardDisplayBox){

        this.setPrefHeight(cardDisplayBox.getHeight() * 2);
    }    
}
