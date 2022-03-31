/*
 Timothy Gutzke
 COSC 1174 48L
 11/21/2021
 Poker Project Assignment
 */

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

//The poker player draws together the concepts of cards, a poker hand, and player decisions (hold/discard cards, amount of bet).
public class PokerPlayer extends BorderPane {    
        
    ////////////////////////////////////////// PROPERTIES & OBJECTS //////////////////////////////////////////

    //Index to ensure that cards are drawn for any hand only once. Otherwise a player could build a Royal Flush 
    //through successive draws from the deck with holds on select cards with a specific suit that also constitute
    //an Ace through 10 straight.
    private int drawCount;

    //The Draw button replaces discarded cards, calculates poker hand rank, and calculates bet results in one event.
    public Button btDraw = new Button("Draw");
    //The Bet It All button allows a game player to bet the entire amount of money in his pot.
    //It sets the bet for the hand equal to the player money value, and then invokes the same procedures as the Draw button to
    //resolve a poker hand.
    public Button btBetItAll = new Button("BET IT ALL!");
    //Button object must be in a pane in order to be able to position it inside another higher pane.
    Pane buttonPane = new Pane();
    //These are the regular available bets.
    CheckBox cbHundred = new CheckBox("100");
    CheckBox cbTwenty = new CheckBox("20");
    CheckBox cbOne = new CheckBox("1");

    //A checkbox pane for holding the bet amount options.
    public VBox betBox = new VBox(20);

    //Player money label. Player starts with $200.
    Label playerMoney = new Label("$200");
    //Pane for the the money label.
    BorderPane moneyLabelPane = new BorderPane();

    //Player's poker hand.
    public PokerHand pokerHand;

    ////////////////////////////////////////// CONSTRUCTOR //////////////////////////////////////////

    public PokerPlayer(CardDeck deck){

        //Initialize the draw count. This will make sure the Draw button only works once between new hand deals.
        drawCount = 0;

        //Create the poker hand with cards from the deck passed in.
        this.pokerHand = new PokerHand(deck);                 
        //Set label format.        
        playerMoney.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 24));
        
        //Format the checkboxes and its VBox pane.
        //Set minimum width for the VBox holding the bet checkboxes.
        betBox.setMinWidth(80);        
        //Set the text size and the background color.
        cbOne.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 20));
        cbTwenty.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 20));
        cbHundred.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 20));
      
        //Set the minimum width of the checkboxes.
        cbHundred.setPrefWidth(80);
        cbTwenty.setPrefWidth(80);
        cbOne.setPrefWidth(80);

        //Format the Bet It All button.        
        btBetItAll.setStyle("-fx-font-size: 14pt; -fx-background-color: rgb(216, 192, 32); -fx-font-weight: bolder;");

        //Add all the bet checkboxes and the Bet It All button to the betBox VBox.
        betBox.getChildren().addAll(cbHundred, cbTwenty, cbOne, btBetItAll);
        //Format the bet box.
        betBox.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY))); 
        betBox.setMinHeight(150);
        betBox.setMinWidth(150);

        //Set the text size and background color of the Draw button.
        btDraw.setStyle("-fx-font-size: 14pt; -fx-background-color: rgb(216, 192, 32); -fx-font-weight: bolder;");
                                       
        //Place the button object in the button pane.
        buttonPane.getChildren().add(btDraw);

        //Place the player money label in the money label pane.
        moneyLabelPane.setCenter(playerMoney);
        moneyLabelPane.setStyle("-fx-background-color: rgb(216, 192, 32)");
        moneyLabelPane.setLayoutX(50);
        moneyLabelPane.setLayoutY(50);
        moneyLabelPane.setPrefHeight(30);
        moneyLabelPane.setPrefWidth(100);

        
        //Add the pane objects to the poker player pane object.
        this.setCenter(pokerHand);
        this.setBottom(buttonPane);  
        this.setLeft(moneyLabelPane);    
        
        //Center the money label.
        BorderPane.setAlignment(playerMoney, Pos.CENTER);

        //Set the preferred dimensions of the button object.
        btDraw.setPrefWidth(125);
        btDraw.setPrefHeight(60);
        btDraw.setBackground(new Background(new BackgroundFill(Color.rgb(216, 192, 32), CornerRadii.EMPTY, Insets.EMPTY)));
        //Create Text object for button text formatting.
        playerMoney.setBackground(new Background(new BackgroundFill(Color.rgb(216, 192, 32), CornerRadii.EMPTY, Insets.EMPTY)));
        btDraw.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 20));
        
        this.setPadding(new Insets(100, 100, 100, 100));
        //Put space around the Draw button.
        BorderPane.setMargin(buttonPane, new Insets(50, 50, 50, 50));   

        //Set the player pane background color to orange and the pane text to 18px font size.
        this.setStyle("-fx-font-size: 18");
        this.setBackground(new Background(new BackgroundFill(Color.rgb(66, 119, 58), CornerRadii.EMPTY, Insets.EMPTY)));
        
        //Place the bet box on the right side of the player pane.
        this.setRight(betBox);
                
        ////////////////////////////////////////// ANONYMOUS EVENT HANDLER //////////////////////////////////////////
        //Clicking the Draw button in the PokerPlayer pane will collect bets, resolve winning/losing hands, and 
        //pay out an amount proportional to the poker hand rank or take the bet for the house.
        //After the Draw button is clicked, the round is finished and the game resumes when the player clicks the 
        //"Deal New Hand" button in the PokerGame pane.
        btDraw.setOnAction(new EventHandler<ActionEvent>() {
            
             //The handle method is required for an Event Handler.
             public void handle(ActionEvent e){  
                 
                //Only allow one draw event between each new deal event.
                if(drawCount == 0){

                    //Iterate through the poker hand HBox list of card display boxes and act on the hold/discard value
                    //of the card display box.
                    for(int i = 0; i < pokerHand.getChildren().size(); i++){

                        CardDisplayBox cardDisplayBox = (CardDisplayBox)pokerHand.getChildren().get(i);

                        //Find out if the card is to be discarded.
                        //If the card display box has a false value for holding it, then draw a new card and set
                        //the card display box.
                        if(!cardDisplayBox.getHold()){

                            //Since we are not holding the current card, set a new card in this card display box.
                            cardDisplayBox.replaceCard(deck.getCard());
                        }
                    }               

                    //Get all the money in the player's pot when cards were discarded/held.
                    int pokerBet = this.getPokerBet();
                    //Instantiate a new GameMechanics class to figure out the value of this poker hand.
                    GameMechanics gameMechanics = new GameMechanics();

                    //Get the numeric rank of the poker hand. The higher the number the better the hand.
                    int rank = gameMechanics.getPokerHandRank(pokerHand);   
                    //Convert the text string in the player money label to an int variable.                    
                    int money = Integer.parseInt(playerMoney.getText().substring(1));            

                    //If this was a winning poker hand, then continue.
                    if(rank >= 0){

                        //The payout is based on the rank of the poker hand.
                        pokerBet = rank * pokerBet;

                        playerMoney.setText("$" + String.valueOf(money + pokerBet));
                    }
                    else{
                        
                        //If this was not a winning poker hand, then first check if the player will have more than $0 after
                        //the bet is deducted.
                        if(money - pokerBet > 0){

                            //Subtract the player's bet amount from his money, and allow the game to continue.
                            playerMoney.setText("$" + String.valueOf(money - pokerBet));
                        }
                        else{

                            //If the amount after the bet is subtracted is 0 or less, then end the game. The text "Game Over" is
                            //set on the player's money label, and the game will crash the next round since the text cannot be
                            //converted to an int value.
                            playerMoney.setText("GAME OVER");
                        }
                    }

                    //Increase the draw count.
                    drawCount = drawCount + 1;
                }
            }

            //This gets the player's bet amount.
            private int getPokerBet() {
                //This will be returned as the paper amount.
                int pokerBet;

                //Check which bet is selected.
                if(cbHundred.isSelected()){

                    pokerBet = 100;
                }
                else if(cbTwenty.isSelected()){

                    pokerBet = 20;
                }
                else{

                    pokerBet = 1;
                }

                return pokerBet;
            }            
        });

        //Bet It All Button Handler
        btBetItAll.setOnAction(new EventHandler<ActionEvent>() {
            
            //The handle method is required for an Event Handler.
            public void handle(ActionEvent e){ 
                
                //Only allow one draw event between each new deal event.
                if(drawCount == 0){

                    //Iterate through the poker hand HBox list of card display boxes and act on the hold/discard value
                    //of the card display box.
                    for(int i = 0; i < pokerHand.getChildren().size(); i++){

                        CardDisplayBox cardDisplayBox = (CardDisplayBox)pokerHand.getChildren().get(i);

                        //Find out if the card is to be discarded.
                        //If the card display box has a false value for holding it, then draw a new card and set
                        //the card display box.
                        if(!cardDisplayBox.getHold()){

                            //Since we not holding the current card, set a new card in this card display box.
                            cardDisplayBox.replaceCard(deck.getCard());
                        }
                    }              
                    
                    //Instantiate a new GameMechanics class to figure out the value of this poker hand.
                    GameMechanics gameMechanics = new GameMechanics();

                    //Get the numeric rank of the poker hand. The higher the number the better the hand.
                    int rank = gameMechanics.getPokerHandRank(pokerHand);   
                    //Convert the text string in the player money label to an int variable.                    
                    int money = Integer.parseInt(playerMoney.getText().substring(1));            

                    //If this was a winning poker hand, then continue.
                    if(rank >= 0){

                        int winnings = money;

                        //The payout is based on the rank of the poker hand.
                        winnings = (int)(rank) * winnings;

                        playerMoney.setText("$" + String.valueOf(money + winnings));
                    }
                    else{
                        
                        //If this was not a winning poker hand, then the player loses all his money and the game is over.                   
                        playerMoney.setText("GAME OVER");                    
                    }

                    //Increase the draw count.
                    drawCount = drawCount + 1;
                }  
            }         
        });
    }
    
    ////////////////////////////////////////// GETTER & SETTER //////////////////////////////////////////

    //Getter method for the drawCount variable.
    public int getDrawCount(){

        return this.drawCount;
    }

     //Setter method for the drawCount variable.
    public void setDrawCount(int number){

        this.drawCount = number;
    }

    ////////////////////////////////////////// METHODS //////////////////////////////////////////

    //Clear the checkboxes of all checkmarks. Used when a new deal event occurs.
    public void clearBetCheckBoxes(){

        this.cbHundred.setSelected(false);
        this.cbTwenty.setSelected(false);
        this.cbOne.setSelected(false);
    }
}
