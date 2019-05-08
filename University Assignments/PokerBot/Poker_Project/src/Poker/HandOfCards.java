package Poker;


import java.util.ArrayList;

/**
 * Created by scott on 16/03/17.
 */
public class HandOfCards {

    private PlayingCard[] hand;
    private DeckOfCards deck;
    private ArrayList<Integer> cardsSetForDiscard = new ArrayList<Integer>();
    private int[] numOfEachCard = new int[Constants.SUIT_SIZE];
    private HandType typeOfHand;
    private HandGameValue value;
    private ProbabilityCalculator probCalc;
    private boolean firstSetup = true;


    public HandOfCards(DeckOfCards deck) {

        this.deck = deck;
        setup();
    }
    
    public PlayingCard getCard(int index){
    	return hand[index];
    }
    
    public void setCardAt(int index, PlayingCard card){
    	hand[index] = card;
    }

    // method to initialise everything; rather than in constructor
    private void setup(){
        if(firstSetup) {
            hand = new PlayingCard[Constants.HAND_SIZE];

            for (int i = 0; i < hand.length; i++) {
                hand[i] = this.deck.dealNext();
            }
        }
        sort();
        countNumCards();
        
        typeOfHand = new HandType(hand);
        value = new HandGameValue(typeOfHand.getHandType(), hand, numOfEachCard);
        probCalc = new ProbabilityCalculator(hand, typeOfHand.getHandType(), numOfEachCard);

        firstSetup = false;

    }

    /* Method to sort hand by gameValue of the cards; highest value first, ie Ace if there is one
    * Uses simple bubble sort to do this
    * Implemented comparable in PlayingCard class in favour of making this sort method tidier and easier to
    * understand, compareTo returns -1 if hand[i] < hand[j]
    */
    private void sort() {

        PlayingCard temp = null;

        for(int i = 0; i < Constants.HAND_SIZE-1; i++){

            for(int j = i+1; j < Constants.HAND_SIZE; j++){

                if(hand[i].compareTo(hand[j]) == -1){
                    temp = hand[i];
                    hand[i] = hand[j];
                    hand[j] = temp;
                }
            }
        }
    }


    // index passed in by player to set a card for discarding
    // this is to prevent the case where a card not intended to be discarded is trying to be discarded
    public boolean setCardForDiscard(int cardIndex){

        if(cardsSetForDiscard.size() < Constants.MAX_DISCARD){
            cardsSetForDiscard.add(cardIndex);
            return true;
        }
        else{
            return false;
        }
    }

    // method to replace a card to be discarded
    public PlayingCard replaceCardAt(int cardIndex){

        PlayingCard replaced = null;

        if(cardsSetForDiscard.contains(cardIndex)){
            replaced = hand[cardIndex];
            hand[cardIndex] = deck.dealNext();
            deck.returnCard(replaced);
        }

        return replaced;
    }


    // method to be called when the player is finished discarding to resort the hand
    public void discardFinished(){
        cardsSetForDiscard.clear();
        sort();
        //handGameValue = -1;
        countNumCards();
        setup();
        getGameValue();
    }

    /* Method to count the number of each card held in the current hand
      * Used to help evaluate hand formations as well as easier calculations
      */
    private void countNumCards(){
        numOfEachCard = new int[Constants.SUIT_SIZE];

        for(int i = 0; i < Constants.HAND_SIZE; i++){
            /* the reason it is gameValue - 2 is because the lowest valued card, a 2, would be in index 0
             * and the highest value card, an Ace = 14, would be in index 12
             */
            numOfEachCard[hand[i].getGameValue()-2]++;
        }
    }


    // Simple method to return local instance of the DeckOfCards
    public DeckOfCards returnDeck(){
        return deck;
    }

    public int getGameValue(){

        return value.getGameValue(typeOfHand.getHandGameValue());
    }

    // Method to print out the hand's cards as a string
    public String toString(){

        String handString = new String();

        for(int i = 0; i < Constants.HAND_SIZE-1; i++){
            handString += hand[i].toString() + " ";
        }


        return handString + hand[Constants.HAND_SIZE-1];
    }

    public int getDiscardProbability(int cardPosition) {

        return probCalc.getProbability(cardPosition);
    }
}
