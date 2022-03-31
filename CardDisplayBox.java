/*
 Timothy Gutzke
 COSC 1174 48L
 11/21/2021
 Poker Project Assignment
 */

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

//The card is represented by a VBox container object that contains a card's properties and methods performed on individual
//cards. I combined the CardDisplayBox and the Card classes from the earlier iterations of this project.

//A poker card is represented in the game mechanics as a set of 2 numbers representing the card's rank and suit.
//A poker card is represented to the game player through an image file, an image view, and a hold/discard button.
//These two facets are combined in a CardDisplayBox object.
public class CardDisplayBox extends VBox {

    ////////////////////////////////////////// PROPERTIES & OBJECTS //////////////////////////////////////////
    
    //The card ID. Used to define the suit and rank of a card.  
    //The cardNumber variable corresponds to the number representing the title of a card .PNG file.
    private int cardNumber;
    //These two properties, suit and rank, define the value of a card for any Poker hand combination.
    //Card Suit
    private int cardSuit;
    //Card Rank
    private int cardRank;
    //Boolean index for holding the card properties or discarding the card properties.
    private boolean hold;
    //This represents the path to a card image.
    private Image cardImage;    
    //Allows card face (image) to be displayed.
    private ImageView cardImageView;   
    
    //The hold button indicates whether a card is held or discarded before drawing replacement cards.
    Button btHold = new Button("Hold");


     ////////////////////////////////////////// CONSTRUCTOR //////////////////////////////////////////
    
     //The CardDisplayBox constructor represents a card drawn for the initial hand.
     //The replaceCard method represents any card switches for this card display box.
     public CardDisplayBox(int number){

        //Set the ID number for the card properties.
        //The ID number represents the card image PNG file name.
        this.setCardNumber(number);
        //Setting the number also determines the path to the card image.
        this.setCardImage();    
        //Set the card Image View value to be the prior created Image object.
         this.setCardImageView();     
       
         //Set the card suit value to a value between 0 and 4.
         this.setCardSuit(this.calculateSuit(number));
        //Set the card rank value to a value between 1 and 13.
        this.setCardRank(this.calculateRank(number));
        //All cards start out as being held. 
        this.setHold(true);
        //Set the Hold button text.
        btHold.setText("Hold");
        //Set the Hold button text format.                
        btHold.setBackground(new Background(new BackgroundFill(Color.rgb(224, 224, 224), CornerRadii.EMPTY, Insets.EMPTY)));
        btHold.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 12));

        //Set the action for the hold button. We defined the event handler with an inner class.
        btHold.setOnAction(new HoldHandler());
        
        //Add the card image view and the hold button to this VBox object.
        //The hold button will be underneath its card image view.
        this.getChildren().add(this.getCardImageView());
        this.getChildren().add(this.btHold);     
        
        //Set the spacing between the card image and its related hold button.
        this.setSpacing(20);

        //Set the width of the hold button to the width of its associated card image.
        this.btHold.setPrefWidth(this.getCardImage().getWidth());
    }
    
    ////////////////////////////////////////// GETTERS & SETTERS //////////////////////////////////////////

    //Getter method for card suit.
    public int getCardSuit(){

        return this.cardSuit;
    } 

    //Setter method for card suit.
    public void setCardSuit(int number){
        
        //The suit number is a number from 0 (spades) to 4 (Jokers).
        if(number >= 0 && number <= 4){
            //Set the number for the suit.
            this.cardSuit = number;
        }
    }

    //Getter method for card rank.
    public int getCardRank(){

        return this.cardRank;
    } 

    //Setter method for card rank.
    public void setCardRank(int number){
        
        //Set the number for the rank.
        this.cardRank = number;
    }

    //Getter method for card number.
    public int getCardNumber(){

        return this.cardNumber;
    } 

    //Setter method for card number.
    public void setCardNumber(int number){
        
        //Set the number for the card.
        this.cardNumber = number;
    }

    //Getter method for hold value.
    public boolean getHold(){

        return this.hold;
    } 

    //Setter method for hold value.
    public void setHold(boolean value){
        
        //Set the number for the card.
        this.hold = value;

        //Set the appropriate text on the button based on the hold value.
        if(value){

            btHold.setText("Hold");
        }
        else{

            btHold.setText("Discard");
        }
    }

     //Getter method for card image. Returns the image file path.
     public Image getCardImage(){

        return this.cardImage;
    }   

    //Setter method the image file path of the card based on the current card number value.
    public void setCardImage(){

        //The card image is a PNG file on the file system.
        //The card number is the same number as the associated card image file name.
        this.cardImage = new Image("file:\\C:\\card\\" + String.valueOf(getCardNumber()) + ".png");        
    }

    //Getter method for card image view object.
    public ImageView getCardImageView(){       

        return cardImageView;
    }   
    
    //Setter method for creating a card image view.
    public void setCardImageView(){

        //Set the image for this card display box's to this object's Image object.
        this.cardImageView = new ImageView(this.getCardImage());
    }
    
    ////////////////////////////////////////// METHODS //////////////////////////////////////////

    //This is essentially the CardDisplayBox constructor laid out as a method. Keeping it for now in case a use comes
    //up for it.
    public void createCard(int newCardNumber){

        //Set the ID number for the card properties.
        //The ID number represents the card image PNG file name.
        this.setCardNumber(newCardNumber);
        //Setting the number also determines the path to the card image.
        this.setCardImage();    
        //Set the card Image View value.
         this.setCardImageView();     
       
         //Set the card suit value.
         this.setCardSuit(this.calculateSuit(newCardNumber));
        //Set the card rank value.
        this.setCardRank(this.calculateRank(newCardNumber));    
        this.setHold(true);

         //Set the action for the hold button.
         btHold.setOnAction(new HoldHandler());
        
        //Add the card image view and the hold button to this VBox object.
        this.getChildren().add(this.getCardImageView());
        this.getChildren().add(this.btHold);

        //Set the hold button to the same width as the card image.
        this.btHold.setPrefWidth(this.getCardImage().getWidth());
    }

    //If a card is discarded when the game pane's draw button is clicked, then this method replaces the old card with a new one.   
    public void replaceCard(int newCardNumber){      
        
        //Clear the pane of the old card elements.
        this.getChildren().clear();

        //Add the new card ID to the card display object.
        this.setCardNumber(newCardNumber);

        //Reconfigure the card ImageView object to the card display box.
        //These two methods automatically use the card number to create the Image object, and therefore also the ImageView object.
        this.setCardImage();     
        this.setCardImageView();
               
        //Set the card values based on the new card number for this CardDisplayBox object.
        this.setCardSuit(this.calculateSuit(newCardNumber));
        this.setCardRank(this.calculateRank(newCardNumber));      

        //Reset the hold value in case 2 draws are ever allowed.
        this.setHold(true);           

        this.getChildren().add(this.getCardImageView());
        this.getChildren().add(this.btHold);
    }   

    //We need to take these individual card numbers and generate a suit number to identify this card's
    //fellow set of cards of the same suit. Since the images show the player the suit, we did not worry about
    //identifying a specific type of suit. All we need for game logic is to know what other cards share the same 
    //suit as this card, unlike other cards where one or more suits play a special role, such as in Hearts (all Heart cards and 
    //Queen of Spades card), or Spades (where the Spades cards are the trump suit.)
    //If we wanted to go back and set constants, 0 = Spades, 1 = Hearts, 2 = Diamonds, and 3 = Clubs. 4 can be any suit (Joker).
    private int calculateSuit(int cardID){

        //If the card ID is less than 14, then it is a spade.
        if(cardID < 14){

            return 0;
        }

        //If the card is less thean 27, then it is a heart.
        if(cardID < 27){

            return 1;
        }

        //If the card is less thean 40, then it is a diamond.
        if(cardID < 40){

            return 2;
        }

         //If the card is less thean 53, then it is a club.
         if(cardID < 53){

            return 3;
        }

         //If the card is less thean 55, then it is a joker.
         if(cardID < 55){

            return 4;
        }

        //If the cardID is over 54, return an error code.
        return -1;
    }

    //This calculates the card rank. The suits and ranks are ordered by card ID numbers in the file name.     
    private int calculateRank(int cardID){

        //Note the Jokers will be identified by the Suit ID of 4, which will be processed in a way the other cards
        //are not. So it does not need to be ranked in relation to other cards once it is identified by its Suit ID.
        //Any arbitrary number used to stop the process as soon as a Joker is identified.
        if(cardID > 52){
        
            return 14;
        }
                
        //Call a recursive method to reduce the card value (number representing its file name) to a value between 1 and 13.        
        cardID = reduceCardToValue(cardID);

        //A card ID with a final value  of 1 is an Ace, and the highest ranked card.
        if(cardID == 1){

            return 13;
        }
        else{

            //If the card is not an Ace, then its value is bumped down 1 to make room for the Ace to occupy the highest 
            //13th rank. The 2 card becomes rank 1, the 3 card becomes rank 2, up to the King card being rank 12.
            cardID = cardID - 1;            
        }                
        
        return cardID;             
    }

    //This takes a raw card ID and reduces it to a value between 1 and 13. This is used to set the card rank.
    private int reduceCardToValue(int cardID){

        //Check if the card is a Joker. A Joker can be any card, so the game logic will treat a Joker card differently than
        //the other cards. We need to identify it before recursively reducing its ID number to a number between 1 and 13,
        //which would lead to wrong card results.
        //Jokers are already checked for in the only calling method in the current game, but we're checking again anyway.
        if(cardID > 52){

            //The Joker card occupies the highest rank (14) out of all the cards.
            return 14;
        }

        //If the card is not a Joker, then its final card ID will be between 1 and 13. This ID is calculated off of
        //the number that is the file name of the card image file.
        if(cardID > 14){

            //Step down the card number by 13 (the number of cards in a suit).
            cardID = cardID - 13;

            //Keep calling this method on itself until the base condition of an int value below 14 is calculated.
            cardID = reduceCardToValue(cardID);
        }

        //Return the value of this card achieved by negative increments of 13 from its card number.
        //Note this does not represent yet the card rank.
        return cardID;
    }    

    //Method for setting standard format for button text.
    public Text setButtonText(String text, int size){

        Text buttonText = new Text(text);

        buttonText.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, size));
        buttonText.setFill(Color.rgb(119, 10, 10));
        
        return buttonText;
    }

     ////////////////////////////////////////// EVENT HANDLER //////////////////////////////////////////

    //Handler for setting the hold status of a card.
    class HoldHandler implements EventHandler<ActionEvent> {

        //Override the handle method.
        @Override         
        public void handle(ActionEvent e) {
            
            //Reverse the hold value of the card. If it is true, then set to false, and vice-versa.
            if(getHold()){

                //Set the hold value to false.
                setHold(false);
                //Set the Hold button format.
                btHold.setBackground(new Background(new BackgroundFill(Color.rgb(216, 192, 32), CornerRadii.EMPTY, Insets.EMPTY))); 
                //Change the button text to discard so the player knows its current boolean setting.
                btHold.setText("Discard");
            }
            else{

                //Set the hold value to true.
                setHold(true);
                //Set the Hold button format.
                btHold.setBackground(new Background(new BackgroundFill(Color.rgb(224, 224, 224), CornerRadii.EMPTY, Insets.EMPTY)));
                
                //Set the button text so the player knows its current boolean setting.
                btHold.setText("Hold");             
            }              
        }        
    }
}