/*
 Timothy Gutzke
 COSC 1174 48L
 11/21/2021
 Poker Project Assignment
 */

import javafx.application.Application;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class PokerGame4 extends Application {

    public void start(Stage primaryStage){
        
        //This pane is the game board.
        Pane pane = new Pane();
                
        //Format the game board pane.
        pane.setStyle("-fx-font-size: 24;");
        //Set the background color of the game board to a velvet color.
        pane.setBackground(new Background(new BackgroundFill(Color.rgb(117, 42, 42), CornerRadii.EMPTY, Insets.EMPTY)));

        //The pane for holding the "Deal New Hand" button.
        Pane buttonPane = new Pane();

        //Create the card deck that will be used for the game.
        CardDeck deck = new CardDeck(54);   
        
        //The poker player.
        PokerPlayer pokerPlayer = new PokerPlayer(deck);

        //Set the poker player pane to be proportional to the parent game board pane.
        pokerPlayer.layoutXProperty().bind(pane.widthProperty().subtract(pokerPlayer.widthProperty()).divide(2));
        pokerPlayer.layoutYProperty().bind(pane.heightProperty().subtract(pokerPlayer.heightProperty()).divide(4));
        
        //The Deal New Hand button starts every poker round except for round one at the start of the game.
        Button btNewDeal = new Button("Deal New Hand");
        //Add the button to the button pane so it can be positioned on the game board.
        buttonPane.getChildren().add(btNewDeal);
        //buttonPane.setPadding(new Insets(50, 50, 50, 50));
        buttonPane.layoutXProperty().bind(pane.widthProperty().subtract(buttonPane.widthProperty()).divide(2));
        buttonPane.layoutYProperty().bind(pane.heightProperty().subtract(buttonPane.heightProperty()).divide(1.1));

        //Label to show the remaining number of cards in the deck.
        Label lbCardsRemaining = new Label("54 Cards Remaining");
        //Create a Font object to set the format of the text in the cards remaining label.
        Font fBoldArial = Font.font("Arial", FontWeight.BOLD , FontPosture.REGULAR, 20);
        lbCardsRemaining.setFont(fBoldArial);   
        //Set a minimum size for the cards remaining label.
        lbCardsRemaining.setMinHeight(70);
        lbCardsRemaining.setMinWidth(70);    
        
        BorderPane cardsRemainingPane = new BorderPane();
        //Set the Cards Remaining pane to be proportional to the parent game board pane.
        cardsRemainingPane.layoutXProperty().bind(pane.widthProperty().subtract(cardsRemainingPane.widthProperty()).multiply(.05));
        cardsRemainingPane.layoutYProperty().bind(pane.heightProperty().subtract(cardsRemainingPane.heightProperty()).multiply(.1));
        cardsRemainingPane.setStyle("-fx-background-color: rgb(216, 192, 32)");
        cardsRemainingPane.setPrefHeight(150);
        cardsRemainingPane.setPrefWidth(250);
        cardsRemainingPane.setCenter(lbCardsRemaining);
                
        //Add the poker player pane to the game board pane.
        pane.getChildren().add(pokerPlayer);        
        //Add the "Deal New Hand" button to the game board pane.
        pane.getChildren().add(buttonPane);    
        //Add the remaining cards label to the game board pane.
        pane.getChildren().addAll(cardsRemainingPane);
        
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
        
        ///////////////////////////////// ANONYMOUS EVENT HANDLER /////////////////////////////////        
        //After the Draw button in the PokerPlayer pane is clicked, the round is finished and the game resumes when the 
        //player clicks the "Deal New Hand" button in the PokerGame pane.
        btNewDeal.setOnAction(new EventHandler<ActionEvent>() {
            
            //The handle method is required for an Event Handler.
            public void handle(ActionEvent e){   
                
                //Draw 5 cards for the player's next poker hand.
                pokerPlayer.pokerHand.drawNewPokerHand(deck);

                //Get the number of remaining cards.
                int remainingCards = deck.getCardsRemaining();

                if(remainingCards < 10){

                    deck.reshuffleDeck(54);                    
                }

                lbCardsRemaining.setText(String.valueOf(deck.getCardsRemaining()) + " Cards Remaining");

                //Set the draw count for the poker player back to 0.
                pokerPlayer.setDrawCount(0);
                //Clear the bet checkboxes.
                pokerPlayer.clearBetCheckBoxes();
            }            
        }); 
    }

    public static void main(String[] args) throws Exception {

        Application.launch(args);            
    }
}
